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

       
