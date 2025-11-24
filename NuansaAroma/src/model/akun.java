package nuansaaroma.model;

/**
 * Kelas Akun merepresentasikan akun pengguna dengan atribut dasar seperti
 * id, password, role, dan nama.
 */
public class Akun {
    /** ID unik akun */
    protected String id;
    
    /** Password akun */
    protected String password;
    
    /** Role atau tipe akun (misal: Admin, Customer) */
    protected String role;
    
    /** Nama lengkap pemilik akun */
    protected String nama;

    /**
     * Konstruktor untuk membuat objek Akun.
     *
     * @param id ID akun
     * @param password Password akun
     * @param role Role akun
     * @param nama Nama akun
     */
    public Akun(String id, String password, String role, String nama){
        this.id = id;
        this.password = password;
        this.role = role;
        this.nama = nama;
    }

    /**
     * Validasi login dengan mencocokkan ID dan password.
     *
     * @param user ID yang dimasukkan
     * @param pass Password yang dimasukkan
     * @return true jika ID dan password sesuai, false jika tidak
     */
    public boolean validasiLogin(String user, String pass){
        return this.id.equals(user) && this.password.equals(pass);
    }

    /**
     * Mengambil ID akun.
     *
     * @return ID akun
     */
    public String getId() {
        return id;
    }

   
