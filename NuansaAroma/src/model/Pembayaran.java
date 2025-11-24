package nuansaaroma.model;

/**
 * Kelas abstrak {@code Pembayaran} merepresentasikan metode pembayaran
 * yang digunakan dalam sistem. Setiap jenis pembayaran harus menurunkan kelas ini
 * dan mengimplementasikan proses pembayarannya masing-masing.
 *
 * <p>Kelas ini menyimpan informasi umum seperti ID pembayaran dan jumlah yang harus dibayar.</p>
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
