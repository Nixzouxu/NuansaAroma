package nuansaaroma.model;

import java.util.Date;

/**
 * Representasi sebuah invoice (nota) yang berisi detail transaksi dan pembayaran.
 * Invoice menyimpan informasi seperti ID invoice, tanggal cetak, transaksi terkait,
 * serta metode pembayaran yang digunakan.
 */
public class Invoice {
    private Transaksi transaksi;
    private Pembayaran pembayaran;
    private Date tanggalCetak;
    private String idInvoice;

    /**
     * Konstruktor untuk membuat objek Invoice baru berdasarkan transaksi dan pembayaran.
     * ID invoice dibuat secara otomatis menggunakan timestamp agar unik.
     * Tanggal cetak diset ke waktu saat objek dibuat.
     *
     * @param transaksi  objek transaksi yang dicantumkan pada invoice
     * @param pembayaran metode pembayaran yang digunakan
     */
    public Invoice(Transaksi transaksi, Pembayaran pembayaran) {
        this.transaksi = transaksi;
        this.pembayaran = pembayaran;
        this.tanggalCetak = new Date();
        this.idInvoice = "INV-" + System.currentTimeMillis();
    }

    /**
     * Mencetak detail lengkap invoice ke layar, termasuk nomor invoice,
     * tanggal cetak, detail transaksi, metode pembayaran, dan total pembayaran.
     */
    public void cetak() {
        System.out.println("\n========== INVOICE NUANSA AROMA ==========");
        System.out.println("No Invoice   : " + idInvoice);
        System.out.println("Tanggal      : " + tanggalCetak);
        // Memanggil toString milik Transaksi
        System.out.println(transaksi.toString());
        System.out.println("Metode Bayar : " + pembayaran.getMetode());
        System.out.println("Total Bayar  : Rp" + pembayaran.getJumlah());
        System.out.println("==========================================");
    }
    
    /**
     * Mengembalikan representasi singkat dari invoice, terdiri dari
     * ID invoice, tanggal cetak, dan metode pembayaran.
     *
     * @return string ringkas tentang invoice
     */
    @Override
    public String toString() {
        return "Invoice [" + idInvoice + "] - " + tanggalCetak + " - " + pembayaran.getMetode();
    }
}

    

