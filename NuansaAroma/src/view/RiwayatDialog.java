package nuansaaroma.view;

import javax.swing.*;
import java.awt.*;
import nuansaaroma.model.*;

/**
 * Dialog untuk menampilkan riwayat transaksi (invoice) milik seorang customer.
 * Menampilkan daftar invoice dalam tampilan teks scrollable.
 */
public class RiwayatDialog extends JDialog {

    /**
     * Membuat dialog riwayat belanja customer.
     *
     * @param parent  frame induk
     * @param customer customer yang sedang login, digunakan untuk mengambil daftar invoice
     */
    public RiwayatDialog(JFrame parent, Customer customer) {
        super(parent, "Riwayat Belanja - " + customer.getNama(), true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Header
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pnlHeader.setBackground(ThemeArt.PRIMARY);
        JLabel lblTitle = new JLabel("Riwayat Transaksi Selesai");
        lblTitle.setFont(ThemeArt.SUBHEADER_FONT);
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // Konten Teks
        JTextArea txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtArea.setMargin(new Insets(20, 20, 20, 20));

        StringBuilder sb = new StringBuilder();
        if (customer.getListInvoice().isEmpty()) {
            sb.append("Belum ada riwayat belanja.\nAyo mulai belanja parfum favoritmu!");
        } else {
            for (Invoice inv : customer.getListInvoice()) {
                sb.append(inv.toString()).append("\n");
                sb.append("==================================================\n\n");
            }
        }
        txtArea.setText(sb.toString());
        add(new JScrollPane(txtArea), BorderLayout.CENTER);

        // Footer Tombol
        JPanel pnlBawah = new JPanel();
        pnlBawah.setBackground(Color.WHITE);
        pnlBawah.setBorder(new javax.swing.border.EmptyBorder(10,0,10,0));
        
        JButton btnClose = ThemeArt.createButton("TUTUP", ThemeArt.DANGER_HOVER);
        pnlBawah.add(btnClose);
        add(pnlBawah, BorderLayout.SOUTH);

        btnClose.addActionListener(e -> dispose());
        setVisible(true);
    }

}
