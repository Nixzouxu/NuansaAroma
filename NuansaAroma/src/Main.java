package nuansaaroma;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import nuansaaroma.driver.*;
import nuansaaroma.model.*;

/**
 * Kelas Main berfungsi sebagai titik awal (entry point) aplikasi NuansaAroma.
 * Mengelola data akun, barang, transaksi, dan menjalankan aplikasi melalui console.
 */
public class Main {
    /** Daftar semua akun (Admin dan Customer) */
    private static ArrayList<Akun> listAkun = new ArrayList<>();
    
    /** Daftar semua barang yang tersedia */
    private static ListBarang listBarang = new ListBarang();
    
    /** Daftar semua transaksi global */
    private static ArrayList<Transaksi> globalTransaksi = new ArrayList<>();
    
    /** Sesi akun yang sedang login */
    private static Akun sessionAkun = null; 
    
    /** Scanner untuk input pengguna */
    private static Scanner scanner = new Scanner(System.in);

    /** File database akun */
    private static final String DB_FILE = "Database_NuansaAroma.txt";
    
    /** File database transaksi */
    private static final String FILE_TRX = "database_transaksi.txt";

    /** 
     * Method utama, menjalankan inisialisasi data dan aplikasi.
     */
    public static void main(String[] args) {
        initDataUntukGui();
        runApp(); 
    }

    /**
     * Memuat data akun dari file database.
     */
    private static void loadDataAkun() {
        File file = new File(DB_FILE);
        if (!file.exists()) return; 
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 4) {
                    if (data[0].equals("ADMIN")) {
                        listAkun.add(new Admin(data[1], data[2], data[3], (data.length>4?data[4]:"-")));
                    } else {
                        listAkun.add(new Customer(data[1], data[2], data[3], (data.length>4?data[4]:"-"), (data.length>5?data[5]:"-")));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Menyimpan akun baru ke file database.
     *
     * @param akun Objek Akun yang akan disimpan
     */
    public static void simpanAkunKeFile(Akun akun) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DB_FILE, true))) {
            String line = "";
            if (akun instanceof Admin) {
                Admin a = (Admin) akun;
                line = "ADMIN;" + a.getId() + ";" + a.getPassword() + ";" + a.getNama() + ";admin@nuansa.com";
            } else {
                Customer c = (Customer) akun;
                line = "CUSTOMER;" + c.getId() + ";" + c.getPassword() + ";" + c.getNama() + ";" + c.getAlamat() + ";" + c.getNoHP();
            }
            writer.write(line); writer.newLine(); 
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Menu registrasi customer baru melalui console.
     */
    private static void menuRegister() {
        System.out.println("\n--- REGISTRASI PELANGGAN BARU ---");
        System.out.print("Username : ");
        String user = scanner.nextLine();
        
        for (Akun a : listAkun) {
            if (a.getId().equalsIgnoreCase(user)) {
                System.out.println("Gagal: Username sudah terpakai!");
                return;
            }
        }

        System.out.print("Password : ");
        String pass = scanner.nextLine();
        System.out.print("Nama Lengkap : ");
        String nama = scanner.nextLine();
        System.out.print("Alamat : ");
        String alamat = scanner.nextLine();
        System.out.print("No HP : ");
        String nohp = scanner.nextLine();

        Customer newCust = new Customer(user, pass, nama, alamat, nohp);
        listAkun.add(newCust);
        simpanAkunKeFile(newCust);
        System.out.println("Registrasi Berhasil! Silakan Login.");
    }

   
