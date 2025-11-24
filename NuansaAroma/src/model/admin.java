package nuansaaroma.model;

/**
 * Kelas Admin merupakan turunan dari kelas Akun yang merepresentasikan akun admin.
 * Admin memiliki kemampuan untuk mengelola barang dan transaksi.
 */
public class Admin extends Akun {
    /** Alamat email dari admin */
    private String email;

    /**
     * Konstruktor untuk membuat objek Admin baru.
     *
     * @param id       ID unik admin
     * @param password Password akun admin
     * @param nama     Nama lengkap admin
     * @param email    Alamat email admin
     */
    public Admin(String id, String password, String nama, String email) {
        super(id, password, "Admin", nama);
        this.email = email;
    }

    /**
     * Mengambil alamat email admin.
     *
     * @return alamat email admin
     */
    public String getEmail(){
        return email;
    }

    /**
     * Modul untuk mengelola barang.
     * Menampilkan pesan bahwa admin sedang mengakses modul kelola barang.
     */
    public void kelolaBarang() {
        System.out.println("Mengakses modul kelola barang...");
    }

    /**
     * Modul untuk melihat transaksi.
     * Menampilkan pesan bahwa admin sedang mengakses modul transaksi.
     */
    public void lihatTransaksi() {
        System.out.println("Mengakses modul transaksi...");
    }

    /**
     * Mengonfirmasi transaksi dengan mengubah statusnya menjadi "SELESAI".
     *
     * @param t Objek Transaksi yang akan dikonfirmasi
     */
    public void konfirmasiTransaksi(Transaksi t){
        if(t != null){
            t.setStatus("SELESAI");
            System.out.println("Transaksi " + t.getIdTransaksi() + " berhasil dikonfirmasi.");
        } else {
            System.out.println("Transaksi tidak valid.");
        }
    }
}
