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
    
    /**
     * Konstruktor untuk membuat objek Barang tanpa path gambar.
     * Secara default path gambar diisi dengan "-".
     *
     * @param id ID barang
     * @param nama Nama barang
     * @param harga Harga barang
     * @param stok Jumlah stok barang
     * @param deskripsi Deskripsi barang
     * @param kategori Kategori barang
     */
    public Barang(String id, String nama, double harga, int stok, String deskripsi, String kategori) {
        this(id, nama, harga, stok, deskripsi, kategori, "-");
    }

    /** @return ID barang */
    public String getIdBarang() { return idBarang; }
    
    /** @return Nama barang */
    public String getNama() { return nama; }
    
    /** @return Harga barang */
    public double getHarga() { return harga; }
    
    /** @return Stok barang */
    public int getStok() { return stok; }
    
    /** @return Deskripsi barang */
    public String getDeskripsi() { return deskripsi; }
    
    /** @return Kategori barang */
    public String getKategori() { return kategori; }
    
    /** @return Path gambar barang */
    public String getImagePath() { return imagePath; }

    /** @param nama Mengubah nama barang */
    public void setNama(String nama) { this.nama = nama; }
    
    /** @param harga Mengubah harga barang */
    public void setHarga(double harga) { this.harga = harga; }
    
    /** @param stok Mengubah stok barang */
    public void setStok(int stok) { this.stok = stok; }
    
    /** @param deskripsi Mengubah deskripsi barang */
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    
    /** @param kategori Mengubah kategori barang */
    public void setKategori(String kategori) { this.kategori = kategori; }
    
    /** @param imagePath Mengubah path gambar barang */
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    /**
     * Mengupdate stok barang.
     *
     * @param newStok Jumlah stok baru
     */
    public void updateStok(int newStok) { this.stok = newStok; } 
}
