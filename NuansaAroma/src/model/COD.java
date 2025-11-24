package nuansaaroma.model;

/**
 * Kelas COD merupakan turunan dari Pembayaran.
 * Merepresentasikan metode pembayaran Cash On Delivery.
 */
public class COD extends Pembayaran {
    /** Alamat pengantaran barang */
    private String alamatPengantaran;

    /**
     * Konstruktor untuk membuat objek COD.
     *
     * @param id ID transaksi
     * @param jumlah Jumlah pembayaran
     * @param alamatPengantaran Alamat tujuan pengiriman
     */
    public COD(String id, double jumlah, String alamatPengantaran) {
        super(id, jumlah);
        this.alamatPengantaran = alamatPengantaran;
    }

    /**
     * Proses pembayaran menggunakan metode COD.
     * Menampilkan informasi alamat pengiriman, jumlah yang harus dibayar, dan status.
     *
     * @return true jika proses berhasil
     */
    @Override
    public boolean prosesPembayaran() {
        System.out.println("\n--- CASH ON DELIVERY ---");
        System.out.println("Barang akan dikirim ke: " + alamatPengantaran);
        System.out.println("Siapkan uang tunai Rp" + jumlah + " saat kurir tiba.");
        System.out.println("Status: DIPROSES.");
        return true;
    }
}
