package nuansaaroma.model;

import java.util.ArrayList;

/**
 * Kelas Customer merupakan turunan dari Akun.
 * Merepresentasikan akun pelanggan dengan atribut keranjang, alamat, nomor HP, dan riwayat invoice.
 */
public class Customer extends Akun {
    /** Keranjang belanja milik customer */
    private Keranjang keranjang;
    
    /** Daftar invoice yang sudah selesai */
    private ArrayList<Invoice> invoiceSelesai;
    
    /** Alamat customer */
    private String alamat;
    
    /** Nomor HP customer */
    private String noHP;

    /**
     * Konstruktor untuk membuat objek Customer.
     *
     * @param id ID customer
     * @param password Password customer
     * @param nama Nama customer
     * @param alamat Alamat customer
     * @param noHP Nomor HP customer
     */
    public Customer(String id, String password, String nama, String alamat, String noHP) {
        super(id, password, "Customer", nama);
        this.alamat = alamat;
        this.noHP = noHP;
        this.keranjang = new Keranjang();
        this.invoiceSelesai = new ArrayList<>();
    }

    /**
     * Menampilkan daftar barang yang tersedia.
     *
     * @param lb Objek ListBarang yang akan ditampilkan
     */
    public void lihatBarang(ListBarang lb) {
        lb.tampilkanSemua();
    }

   
