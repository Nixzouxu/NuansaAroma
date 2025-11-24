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

    /**
     * Mengembalikan total harga transaksi.
     *
     * @return total harga
     */
    public double hitungTotal() {
        return totalHarga;
    }

    /**
     * @return daftar barang dalam transaksi
     */
    public ArrayList<Barang> getBarang() {
        return barang;
    }

    /**
     * @return total harga transaksi
     */
    public double getTotalHarga() { 
        return totalHarga; 
    }

    /**
     * Menampilkan representasi transaksi dalam bentuk teks yang rapi.
     *
     * @return string berisi ringkasan transaksi
     */
    @Override
    public String toString() {
        // Null check biar ga error kalau Customer belum ada
        String namaCust = (akun != null) ? akun.getNama() : "Unknown";
        return String.format("ID: %s | Customer: %s | Tgl: %s | Status: %s | Total: Rp%.0f",
               idTransaksi, namaCust, tanggal.toString(), status, totalHarga);
    }

    /**
     * @return tanggal transaksi dibuat
     */
    public Date getTanggal() {
        return tanggal;
    }

    // Method helper untuk mengubah List Barang menjadi String (Contoh: "M001,W002,U001")
    // Ini agar bisa disimpan dalam satu baris teks di file
    /**
     * Mengubah daftar barang menjadi string ID-ID barang yang dipisahkan koma.
     * Contoh output: {@code "M001,W002,U001"}.
     *
     * @return string daftar ID barang
     */
    public String getBarangIds() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < barang.size(); i++) {
            sb.append(barang.get(i).getIdBarang());
            if (i < barang.size() - 1) sb.append(",");
        }
        return sb.toString();
    }
}


    
