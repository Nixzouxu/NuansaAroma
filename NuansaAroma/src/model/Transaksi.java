package nuansaaroma.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Kelas {@code Transaksi} merepresentasikan satu proses transaksi
 * yang dilakukan oleh seorang {@link Customer}. Transaksi ini berisi daftar barang,
 * informasi tanggal transaksi, total harga, dan status proses transaksi.
 */
public class Transaksi {
    private Customer akun; 
    private ArrayList<Barang> barang; 
    private String idTransaksi;
    private Date tanggal;
    private String status; 
    private double totalHarga;

    // Constructor Updated
    /**
     * Membuat objek transaksi baru dengan informasi customer, daftar barang,
     * ID transaksi, dan total harga.
     *
     * @param akun         customer yang melakukan transaksi
     * @param barang       daftar barang yang dibeli
     * @param idTransaksi  ID unik transaksi
     * @param totalHarga   total harga transaksi
     */
    public Transaksi(Customer akun, ArrayList<Barang> barang, String idTransaksi, double totalHarga) {
        this.akun = akun;
        this.barang = barang; // Bisa null dulu saat testing Admin saja
        this.idTransaksi = idTransaksi;
        this.totalHarga = totalHarga;
        this.tanggal = new Date();
        this.status = "MENUNGGU";
    }

    /**
     * Mengubah status transaksi.
     *
     * @param status status baru transaksi (contoh: MENUNGGU, SELESAI, DIBATALKAN)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return ID unik transaksi
     */
    public String getIdTransaksi() { return idTransaksi; }

    /**
     * @return status transaksi saat ini
     */
    public String getStatus() { return status; }

    /**
     * @return customer yang melakukan transaksi
     */
    public Customer getCustomer() { return akun; }
