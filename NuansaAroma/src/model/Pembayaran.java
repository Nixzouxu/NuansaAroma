package nuansaaroma.model;

/**
 * Kelas abstrak {@code Pembayaran} merepresentasikan metode pembayaran
 * yang digunakan dalam sistem. Setiap jenis pembayaran harus menurunkan kelas ini
 * dan mengimplementasikan proses pembayarannya masing-masing.
 *
 * Kelas ini menyimpan informasi umum seperti ID pembayaran dan jumlah yang harus dibayar.
 */
public abstract class Pembayaran {
    protected String id;
    protected double jumlah;

    /**
     * Membuat objek pembayaran baru dengan ID dan jumlah tertentu.
     *
     * @param id     ID unik untuk pembayaran
     * @param jumlah jumlah total yang harus dibayarkan
     */
    public Pembayaran(String id, double jumlah){
        this.id = id;
        this.jumlah = jumlah;
    }

    // Abstract method: Wajib diisi oleh subclass (QRIS, Bank, COD)
    /**
     * Method abstrak yang wajib diimplementasikan oleh setiap subclass,
     * seperti {@code QRIS}, {@code Bank}, atau {@code COD}.  
     * Method ini berisi proses pembayaran sesuai metode masing-masing.
     *
     * @return true jika pembayaran berhasil diproses
     */
    public abstract boolean prosesPembayaran();

    /**
     * Mengambil nama metode pembayaran berdasarkan nama class konkret.
     * Contoh: kelas {@code QRIS} akan menghasilkan string "QRIS".
     *
     * @return nama metode pembayaran
     */
    public String getMetode() {
        return this.getClass().getSimpleName(); // Mengambil nama class (misal: "QRIS")
    }
    
    /**
     * Mengambil jumlah uang yang harus dibayarkan.
     *
     * @return total jumlah pembayaran
     */
    public double getJumlah() { return jumlah; }
}


    
