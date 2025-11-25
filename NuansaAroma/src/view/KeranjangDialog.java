package nuansaaroma.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
// import java.util.Date;

import nuansaaroma.Main; // Akses Global Transaksi (Admin)
import nuansaaroma.model.*;

/**
 * Dialog tampilan keranjang belanja untuk customer.
 * Menampilkan daftar barang, total harga, dan proses checkout.
 */
public class KeranjangDialog extends JDialog {
    private Customer customer;
    private DefaultTableModel model;
    private JLabel lblTotal;
    private double totalBelanja = 0;

    /**
     * Membuat dialog keranjang belanja.
     * @param parent frame induk
     * @param customer customer yang sedang login
     */
    public KeranjangDialog(JFrame parent, Customer customer) {
        super(parent, "Keranjang Saya", true);
        this.customer = customer;
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Header
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlHeader.setBackground(ThemeArt.BACKGROUND);
        JLabel lblTitle = new JLabel("Daftar Belanjaan");
        lblTitle.setFont(ThemeArt.SUBHEADER_FONT);
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // Tabel Items
        String[] header = {"Nama Parfum", "Harga", "Kategori"};
        model = new DefaultTableModel(header, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(ThemeArt.REGULAR_FONT);
        table.getTableHeader().setFont(ThemeArt.BOLD_FONT);
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel Bawah (Total & Checkout)
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));
        panelBawah.setBackground(ThemeArt.PRIMARY);

        lblTotal = new JLabel("Total: Rp 0");
        lblTotal.setFont(ThemeArt.HEADER_FONT);
        lblTotal.setForeground(Color.WHITE);

        JButton btnCheckout = ThemeArt.createButton("BAYAR SEKARANG", ThemeArt.SUCCESS);

        panelBawah.add(lblTotal, BorderLayout.WEST);
        panelBawah.add(btnCheckout, BorderLayout.EAST);
        add(panelBawah, BorderLayout.SOUTH);

        refreshTable();

        // --- CHECKOUT FINAL ---
        btnCheckout.addActionListener(e -> {
            if (customer.getKeranjang().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keranjang Kosong! Belanja dulu yuk.");
                return;
            }
            
            // 1. Pilih Metode Pembayaran
            String[] options = {"QRIS", "Bank Transfer", "COD"};
            int choice = JOptionPane.showOptionDialog(this, 
                    "Total Tagihan: Rp " + String.format("%,.0f", totalBelanja) + "\nPilih Metode Pembayaran:", 
                    "Checkout",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                    null, options, options[0]);

            if (choice != -1) {
                prosesTransaksiFinal(choice);
            }
        });

        setVisible(true);
    }

    /**
     * Memperbarui tabel keranjang dan total belanja.
     */
    private void refreshTable() {
        model.setRowCount(0);
        ArrayList<Barang> items = customer.getKeranjang().getItems();
        totalBelanja = customer.getKeranjang().hitungTotal();
        
        for (Barang b : items) {
            model.addRow(new Object[]{ b.getNama(), "Rp " + String.format("%,.0f", b.getHarga()), b.getKategori() });
        }
        lblTotal.setText("Total: Rp " + String.format("%,.0f", totalBelanja));
    }

    /**
     * Memproses checkout final:
     * - membuat transaksi
     * - membuat pembayaran
     * - membuat invoice
     * - mengirim transaksi ke admin
     * - mengurangi stok
     * - mengosongkan keranjang
     * - menampilkan struk
     *
     * @param methodIndex index metode pembayaran yang dipilih
     */
    private void prosesTransaksiFinal(int methodIndex) {
        // 1. Setup Data Transaksi
        String idTrx = "TRX-" + System.currentTimeMillis();
        // PENTING: Gunakan 'new ArrayList<>(...)' agar list belanjaan terpisah dari keranjang yg akan dikosongkan
        ArrayList<Barang> belanjaan = new ArrayList<>(customer.getKeranjang().getItems());
        
        // Buat Objek Transaksi
        Transaksi trx = new Transaksi(customer, belanjaan, idTrx, totalBelanja);
        
        // Buat Objek Pembayaran
        Pembayaran pay = null;
        String idPay = "PAY-" + System.currentTimeMillis();
        if (methodIndex == 0) pay = new QRIS(idPay, totalBelanja, "NUANSA_QR");
        else if (methodIndex == 1) pay = new Bank(idPay, totalBelanja, "123456", "BCA");
        else pay = new COD(idPay, totalBelanja, customer.getAlamat());

        // 2. Buat Invoice untuk User
        Invoice invoice = new Invoice(trx, pay);
        customer.addInvoice(invoice); 
        
        // 3. update kirim data ke admin (Global Transaction)
        Main.getGlobalTransaksi().add(trx);
        Main.simpanTransaksiKeFile(trx);

        // 4. Kurangi Stok & Kosongkan Keranjang
        for(Barang b : belanjaan) {
            b.updateStok(b.getStok() - 1);
        }
        customer.getKeranjang().kosongkan();

        // 5. Tampilkan Struk
        String struk = "========== INVOICE RESMI ==========\n" +
                       "ID Transaksi : " + idTrx + "\n" +
                       "Tanggal      : " + new java.util.Date() + "\n" +
                       "Pelanggan    : " + customer.getNama() + "\n" +
                       "-----------------------------------\n";
        
        for(Barang b : belanjaan) {
            struk += b.getNama() + " - Rp " + String.format("%,.0f", b.getHarga()) + "\n";
        }
        
        struk += "-----------------------------------\n" +
                 "TOTAL BAYAR  : Rp " + String.format("%,.0f", totalBelanja) + "\n" +
                 "METODE BAYAR : " + pay.getMetode() + "\n" +
                 "STATUS       : LUNAS (Menunggu Konfirmasi Admin)\n" +
                 "===================================";

        JTextArea textArea = new JTextArea(struk);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Transaksi Berhasil!", JOptionPane.INFORMATION_MESSAGE);

        this.dispose();
    }

}
