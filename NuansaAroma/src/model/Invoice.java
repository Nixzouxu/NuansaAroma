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

