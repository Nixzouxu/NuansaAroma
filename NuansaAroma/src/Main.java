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

     /**
     * Inisialisasi admin default jika belum ada akun.
     */
    private static void initDefaultAdmin() {
        System.out.println("[SYSTEM] Membuat Admin Default...");
        Admin admin = new Admin("admin", "admin123", "Owner Nuansa", "owner@nuansa.com");
        listAkun.add(admin);
        simpanAkunKeFile(admin);
    }

    /**
     * Inisialisasi daftar barang default.
     */
    private static void initBarang() {
        listBarang.tambahBarang(new Barang("M001", "Dior Sauvage", 1850000, 15, "Parfum pria klasik yang elegan.", "Men", "images/dior_sauvage.jpeg"));
        listBarang.tambahBarang(new Barang("W001", "Chanel No 5", 2650000, 5, "Ikon keharuman wanita sepanjang masa.", "Women", "images/chanel_no5.jpeg"));
        listBarang.tambahBarang(new Barang("U001", "Baccarat 540", 4500000, 3, "Wangi unisex mewah dan langka.", "Unisex", "images/baccarat_540.jpeg"));
        listBarang.tambahBarang(new Barang("M002", "Bleu de Chanel", 2000000, 10, "Men (EDT)", "Men", "images/bleude_chanel.jpeg"));
        listBarang.tambahBarang(new Barang("W002", "Gucci Bloom", 1750000, 8, "Women (EDT)", "Women", "images/gucci_bloom.jpeg"));
        listBarang.tambahBarang(new Barang("U002", "Tom Ford Black Orchid", 3500000, 4, "Unisex (Parfum)", "Unisex", "images/tom_ford.jpeg"));
    }

    /**
     * Menjalankan aplikasi console.
     */
    public static void runApp() {
        while (true) {
            System.out.println("\n=== NUANSA AROMA SHOP ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Pilih: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                loginProcess();
            } else if (input.equals("2")) {
                menuRegister();
            } else if (input.equals("3")) {
                break;
            }
        }
    }

    /**
     * Proses login pengguna.
     */
    private static void loginProcess() {
        System.out.print("Username : ");
        String user = scanner.nextLine();
        System.out.print("Password : ");
        String pass = scanner.nextLine();

        boolean loginSukses = false;
        for (Akun a : listAkun) {
            if (a.validasiLogin(user, pass)) {
                sessionAkun = a;
                loginSukses = true;
                break;
            }
        }

        if (loginSukses) {
            System.out.println("Login Berhasil! Halo " + sessionAkun.getNama());
            if (sessionAkun instanceof Admin) {
                new AdminDriver((Admin) sessionAkun, listBarang, globalTransaksi).showMenu();
            } else if (sessionAkun instanceof Customer) {
                new CustomerDriver((Customer) sessionAkun, listBarang, globalTransaksi).showMenu();
            }
            sessionAkun = null;
        } else {
            System.out.println("Gagal: Username/Password salah!");
        }
    }

    /** @return Daftar semua akun */
    public static ArrayList<Akun> getListAkun() {
        return listAkun;
    }

    /** @return Daftar semua barang */
    public static ListBarang getListBarang() {
        return listBarang;
    }

    /**
     * Inisialisasi data untuk GUI.
     */
    public static void initDataUntukGui() {
        initBarang();
        loadDataAkun();
        loadDataTransaksi();
        if (listAkun.isEmpty()) initDefaultAdmin();
    }

    /** @return Daftar semua transaksi global */
    public static ArrayList<Transaksi> getGlobalTransaksi() {
        return globalTransaksi;
    }

/**
     * Memuat data transaksi dari file database.
     */
    private static void loadDataTransaksi() {
        File file = new File(FILE_TRX);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 5) {
                    String idTrx = data[0];
                    String username = data[1];
                    double total = Double.parseDouble(data[2]);
                    String status = data[3];
                    
                    ArrayList<Barang> items = new ArrayList<>();
                    if(data.length > 4 && !data[4].isEmpty()) {
                        String[] ids = data[4].split(",");
                        for(String idB : ids) {
                            Barang b = listBarang.cariBarang(idB);
                            if(b != null) items.add(b);
                        }
                    }

                    Customer cust = null;
                    for(Akun a : listAkun) {
                        if(a instanceof Customer && a.getId().equals(username)) {
                            cust = (Customer) a; break;
                        }
                    }

                    if(cust != null) {
                        Transaksi t = new Transaksi(cust, items, idTrx, total);
                        t.setStatus(status);
                        globalTransaksi.add(t);
                    }
                }
            }
        } catch (Exception e) { 
            System.out.println("Gagal load transaksi: " + e.getMessage()); 
        }
    }

    /**
     * Menyimpan transaksi baru ke file database.
     *
     * @param t Objek Transaksi yang disimpan
     */
    public static void simpanTransaksiKeFile(Transaksi t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_TRX, true))) {
            String barangIds = t.getBarangIds(); 
            String line = t.getIdTransaksi() + ";" + 
                          t.getCustomer().getId() + ";" + 
                          t.getTotalHarga() + ";" + 
                          t.getStatus() + ";" + 
                          barangIds;
            
            writer.write(line);
            writer.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Mengupdate status transaksi di file database.
     * Menulis ulang seluruh data transaksi agar status terbaru tersimpan.
     */
    public static void updateStatusTransaksiDiFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_TRX, false))) { 
            for(Transaksi t : globalTransaksi) {
                String barangIds = t.getBarangIds();
                String line = t.getIdTransaksi() + ";" + 
                              t.getCustomer().getId() + ";" + 
                              t.getTotalHarga() + ";" + 
                              t.getStatus() + ";" + 
                              barangIds;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
