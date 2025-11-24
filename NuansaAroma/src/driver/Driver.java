package nuansaaroma.driver;

/**
 * {@code Driver} adalah kelas abstrak yang berfungsi sebagai kerangka dasar
 * (base class) untuk semua kelas driver (controller) dalam sistem aplikasi Nuansa Aroma.
 * <p>
 * Setiap kelas yang mewarisi {@code Driver} bertanggung jawab untuk menangani
 * interaksi pengguna dan menampilkan menu yang relevan dengan peran pengguna
 * (misalnya, Admin atau Customer).
    */
public abstract class Driver {
    /**
     * Metode abstrak yang harus diimplementasikan oleh setiap subclass
     * untuk menampilkan menu utama yang spesifik sesuai peran pengguna.
     */
    public abstract void showMenu();
}

