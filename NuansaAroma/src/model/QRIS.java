package nuansaaroma.model;

/**
 * Kelas {@code QRIS} merupakan implementasi konkret dari metode pembayaran
 * menggunakan QRIS. Pengguna akan melakukan pemindaian kode QR untuk melakukan
 * pembayaran.
 *
 * <p>Kelas ini menyimpan informasi seperti kode QR yang harus dipindai.</p>
 */
public class QRIS extends Pembayaran {
    private String kodeQR;

    /**
     * Membuat objek pembayaran QRIS baru.
     *
     * @param id      ID transaksi atau pembayaran
     * @param jumlah  jumlah total yang harus dibayar
     * @param kodeQR  kode QR yang akan ditampilkan kepada pengguna
     */
    public QRIS(String id, double jumlah, String kodeQR) {
        super(id, jumlah);
        this.kodeQR = kodeQR;
    }

    /**
     * Memproses pembayaran melalui QRIS dengan menampilkan kode QR,
     * meminta pengguna melakukan scan, lalu menampilkan status pembayaran.
     *
     * @return true apabila pembayaran berhasil diproses
     */
    @Override
    public boolean prosesPembayaran() {
        System.out.println("\n--- PEMBAYARAN QRIS ---");
        System.out.println("Scan QR Code ini: [" + kodeQR + "]");
        System.out.println("Memproses pembayaran Rp" + jumlah + "...");
        System.out.println("Status: BERHASIL.");
        return true;
    }
}
