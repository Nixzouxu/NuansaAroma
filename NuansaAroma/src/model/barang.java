package nuansaaroma.model;

/**
 * Kelas Barang merepresentasikan produk yang dijual.
 * Memiliki atribut seperti id, nama, harga, stok, deskripsi, kategori, dan path gambar.
 */
public class Barang {
    /** ID unik barang */
    private String idBarang;
    
    /** Nama barang */
    private String nama;
    
    /** Harga barang */
    private double harga;
    
    /** Stok barang tersedia */
    private int stok;
    
    /** Deskripsi barang */
    private String deskripsi;
    
    /** Kategori barang */
    private String kategori;
    
    /** Path gambar barang */
    private String imagePath;

    /**
     * Konstruktor untuk membuat objek Barang lengkap dengan path gambar.
     *
     * @param id ID barang
     * @param nama Nama barang
     * @param harga Harga barang
     * @param stok Jumlah stok barang
     * @param deskripsi Deskripsi barang
     * @param kategori Kategori barang
     * @param imagePath Path gambar barang
     */
    public Barang(String id, String nama, double harga, int stok, String deskripsi, String kategori, String imagePath) {
        this.idBarang = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.imagePath = imagePath; 
    }
    
   
