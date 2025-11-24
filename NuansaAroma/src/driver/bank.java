package nuansaaroma.model;

/**
 * Kelas Bank merupakan turunan dari Pembayaran.
 * Merepresentasikan metode pembayaran melalui transfer bank.
 */
public class Bank extends Pembayaran {
    /** Nomor rekening bank tujuan */
    private String noRekening;
    
    /** Nama bank tujuan */
    private String namaBank;

    /**
     * Konstruktor untuk membuat objek Bank.
     *
     * @param id ID transaksi
     * @param jumlah Jumlah pembayaran
     * @param noRekening Nomor rekening bank tujuan
     * @param namaBank Nama bank tujuan
     */
    public Bank(String id, double jumlah, String noRekening, String namaBank) {
        super(id, jumlah);
        this.noRekening = noRekening;
        this.namaBank = namaBank;
    }

    /**
     * Proses pembayaran melalui transfer bank.
     * Menampilkan informasi bank, nomor rekening, jumlah, dan status pembayaran.
     *
     * @return true jika pembayaran berhasil
     */
    @Override
    public boolean prosesPembayaran() {
        System.out.println("\n--- TRANSFER BANK ---");
        System.out.println("Silakan transfer ke " + namaBank + " (" + noRekening + ")");
        System.out.println("Jumlah: Rp" + jumlah);
        System.out.println("Status: BERHASIL.");
        return true;
    }
}
