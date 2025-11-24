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

    /**
     * Menambahkan barang ke keranjang belanja.
     * Mengecek stok sebelum menambahkan.
     *
     * @param b Objek Barang yang akan ditambahkan
     */
    public void tambahKeKeranjang(Barang b) {
        if (b.getStok() > 0) {
            keranjang.tambahBarang(b); 
            System.out.println("> Sukses: " + b.getNama() + " masuk keranjang.");
        } else {
            System.out.println("> Gagal: Stok barang habis!");
        }
    }

    /** @return Keranjang belanja customer */
    public Keranjang getKeranjang() {
        return keranjang;
    }

    /**
     * Menambahkan invoice ke daftar invoice selesai.
     *
     * @param inv Objek Invoice yang selesai
     */
    public void addInvoice(Invoice inv) {
        invoiceSelesai.add(inv);
    }

    /**
     * Menampilkan riwayat invoice customer.
     * Jika belum ada transaksi, menampilkan pesan kosong.
     */
    public void lihatRiwayatInvoice() {
        System.out.println("\n=== RIWAYAT BELANJA " + nama.toUpperCase() + " ===");
        if (invoiceSelesai.isEmpty()) {
            System.out.println("Belum ada riwayat transaksi.");
        } else {
            for (Invoice inv : invoiceSelesai) {
                System.out.println(inv.toString());
            }
        }
    }

    /** @return Alamat customer */
    public String getAlamat() { return alamat; }
    
    /** @return Nomor HP customer */
    public String getNoHP() { return noHP; }

    /** @return Daftar invoice yang sudah selesai */
    public ArrayList<Invoice> getListInvoice() {
        return invoiceSelesai;
    }
}
