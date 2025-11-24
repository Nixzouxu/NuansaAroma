package nuansaaroma.model;

import java.util.ArrayList;

/**
 * Kelas {@code Keranjang} merepresentasikan keranjang belanja yang digunakan
 * oleh customer untuk menampung daftar barang sebelum melakukan checkout.
 * 
 * <p>Kelas ini menyimpan list barang yang ditambahkan, menghitung total harga,
 * dan menyediakan operasi untuk menambah, menghapus, serta mengosongkan keranjang.</p>
 */
public class Keranjang {
    private ArrayList<Barang> barangList;
    private double totalHarga;

    /**
     * Membuat objek {@code Keranjang} baru dengan list barang kosong
     * dan total harga awal 0.
     */
    public Keranjang() {
        this.barangList = new ArrayList<>();
        this.totalHarga = 0;
    }
