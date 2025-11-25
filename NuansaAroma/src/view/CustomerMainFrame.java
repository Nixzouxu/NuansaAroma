package nuansaaroma.view;

import java.awt.*;
import javax.swing.*;
import nuansaaroma.Main;
import nuansaaroma.model.*;

/**
 * CustomerMainFrame adalah tampilan utama untuk pengguna role Customer.
 * Frame ini menampilkan katalog produk dalam bentuk card dan menyediakan
 * akses ke keranjang, riwayat transaksi, serta logout.
 *
 * <p>Fitur utama:
 * <ul>
 *   <li>Menampilkan daftar produk dalam panel scrollable.</li>
 *   <li>Setiap produk memiliki card berisi gambar, nama, kategori dan harga.</li>
 *   <li>Memiliki tombol tambah ke keranjang pada setiap produk.</li>
 *   <li>Header yang menampilkan jumlah item dalam keranjang.</li>
 *   <li>Tombol akses cepat: keranjang, riwayat, logout.</li>
 * </ul>
 */
public class CustomerMainFrame extends JFrame {

    /** Customer yang sedang login */
    private Customer customer;

    /** Panel yang menampilkan seluruh kartu produk */
    private JPanel panelProduk;

    /** Label jumlah item di keranjang */
    private JLabel lblCartCount;

    /**
     * Constructor frame utama customer.
     * Mengatur UI header, area produk, tombol navigasi, dan event listener.
     *
     * @param customer objek Customer yang sedang login
     */
    public CustomerMainFrame(Customer customer) {
        this.customer = customer;
        setTitle("Nuansa Aroma - Katalog");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeArt.PRIMARY);
        header.setBorder(new javax.swing.border.EmptyBorder(20, 40, 20, 40));

        JLabel lblLogo = new JLabel("NUANSA AROMA");
        lblLogo.setFont(ThemeArt.HEADER_FONT);
        lblLogo.setForeground(Color.WHITE);

        // Panel Tombol Kanan
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightHeader.setOpaque(false);

        lblCartCount = new JLabel("Keranjang (0)");
        lblCartCount.setForeground(new Color(255, 215, 0)); // Warna Emas
        lblCartCount.setFont(ThemeArt.BOLD_FONT);

        // Tombol Header (Hover jadi Ungu, Merah untuk Logout)
        JButton btnCart = ThemeArt.createButton("Lihat Keranjang", ThemeArt.HOVER_COLOR);
        JButton btnHistory = ThemeArt.createButton("Riwayat", ThemeArt.HOVER_COLOR);
        JButton btnLogout = ThemeArt.createButton("Keluar", ThemeArt.DANGER_HOVER);

        rightHeader.add(lblCartCount);
        rightHeader.add(btnCart);
        rightHeader.add(btnHistory);
        rightHeader.add(btnLogout);

        header.add(lblLogo, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- KONTEN (CENTER ALIGNED) ---
        // Menggunakan FlowLayout Center agar item di tengah kalau sedikit
        panelProduk = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        panelProduk.setBackground(ThemeArt.BACKGROUND);
        
        loadProdukCards();

        JScrollPane scroll = new JScrollPane(panelProduk);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // Actions
        btnCart.addActionListener(e -> {
            new KeranjangDialog(this, customer);
            updateCartUI();
        });

        btnHistory.addActionListener(e -> new RiwayatDialog(this, customer));

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginRegisterFrame().setVisible(true);
        });
    }

    /**
     * Mengupdate tampilan jumlah item keranjang
     * di label header.
     */
    private void updateCartUI() {
        lblCartCount.setText("Keranjang (" + customer.getKeranjang().getItems().size() + ")");
    }

    /**
     * Memuat seluruh kartu produk dari ListBarang
     * lalu me-render ulang panelProduk.
     */
    private void loadProdukCards() {
        panelProduk.removeAll();
        for (Barang b : Main.getListBarang().getList()) {
            panelProduk.add(createCard(b));
        }
        panelProduk.revalidate();
        panelProduk.repaint();
    }

    /**
     * Membuat sebuah kartu tampilan untuk satu barang.
     * Card berisi gambar, nama, kategori, harga, dan tombol tambah ke keranjang.
     *
     * @param b objek Barang yang akan ditampilkan
     * @return panel card yang siap ditampilkan
     */
    private JPanel createCard(Barang b) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(300, 450)); // Kartu lebih besar
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        // Shadow/Border Halus
        card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220), 2));

        // --- LOGIKA GAMBAR (DIPERBARUI & DIGABUNG) ---
        JLabel lblImage = new JLabel();
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Kita coba cari gambar dengan ID Barang (misal M001.jpg, M001.png)
        String imagePath = b.getImagePath(); // Ambil path yang sudah benar dari Main.java
        ImageIcon iconProduk = null;

        if (imagePath != null && !imagePath.isEmpty()) {
            java.io.File f = new java.io.File(imagePath);
    
         if (f.exists()) {
            ImageIcon raw = new ImageIcon(imagePath);
        if(raw.getIconWidth() > 0) {
             Image img = raw.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
             iconProduk = new ImageIcon(img);
        }
    } 
}

        if (iconProduk != null) {
            // Tampilkan Gambar Asli
            lblImage.setIcon(iconProduk);
            lblImage.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        } else {
            // Fallback: Tampilkan Ikon Bintang jika gambar tidak ketemu
            lblImage.setText("✦");
            lblImage.setFont(new Font("Serif", Font.PLAIN, 80));
            lblImage.setForeground(ThemeArt.PRIMARY);
        }
        

        // --- BAGIAN TEXT (SAMA SEPERTI SEBELUMNYA) ---
        JLabel lblNama = new JLabel(b.getNama());
        lblNama.setFont(ThemeArt.BOLD_FONT);
        lblNama.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblKat = new JLabel(b.getKategori());
        lblKat.setForeground(Color.GRAY);
        lblKat.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblHarga = new JLabel("Rp " + String.format("%,.0f", b.getHarga()));
        lblHarga.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHarga.setForeground(new Color(39, 174, 96)); // Hijau Duit
        lblHarga.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tombol Keranjang dengan Hover Ungu
        JButton btnAdd = ThemeArt.createButton("+ KERANJANG", ThemeArt.HOVER_COLOR);
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout Spacing
        card.add(Box.createVerticalStrut(30));
        card.add(lblImage); 
        card.add(Box.createVerticalStrut(20));
        card.add(lblNama);
        card.add(lblKat);
        card.add(Box.createVerticalStrut(20));
        card.add(lblHarga);
        card.add(Box.createVerticalStrut(30));
        card.add(btnAdd);

        btnAdd.addActionListener(e -> {
            if(b.getStok() > 0) {
                customer.tambahKeKeranjang(b);
                updateCartUI();
                JOptionPane.showMessageDialog(this, b.getNama() + " masuk keranjang!");
            } else {
                JOptionPane.showMessageDialog(this, "Stok Habis!", "Oops", JOptionPane.WARNING_MESSAGE);
            }
        });

        return card;
    }
}

