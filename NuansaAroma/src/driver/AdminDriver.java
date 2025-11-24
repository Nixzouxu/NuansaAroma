package nuansaaroma.driver;

import nuansaaroma.model.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * {@code AdminDriver} adalah kelas driver yang menangani interaksi pengguna untuk
 * akun dengan peran Admin.
 * Kelas ini menyediakan menu untuk mengelola barang (parfum) dan transaksi.
    */

public class AdminDriver extends Driver {
    /** Akun Admin yang sedang login. */
    private Admin akun;
    /** Daftar koleksi semua objek Barang (parfum). */
    private ListBarang listBarang;
   /** Daftar semua Transaksi yang pernah terjadi. */
    private ArrayList<Transaksi> listTransaksi;
    /** Objek Scanner untuk menerima input dari pengguna. */
    private Scanner scanner;

    /**
     * Konstruktor untuk kelas AdminDriver.
     *   akun Objek Admin yang telah berhasil login.
     *  listBarang Objek ListBarang yang berisi data barang-barang.
     *  listTransaksi ArrayList Transaksi yang berisi riwayat transaksi.
     */
    public AdminDriver(Admin akun, ListBarang listBarang, ArrayList<Transaksi> listTransaksi) {
        this.akun = akun;
        this.listBarang = listBarang;
        this.listTransaksi = listTransaksi;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Mengimplementasikan method abstrak dari kelas {@code Driver}.
     * Method ini memanggil {@code showMenuAdmin()} untuk menampilkan menu utama Admin.
     */
    @Override
    public void showMenu() {
        showMenuAdmin();
    }
    
    /**
     * Menampilkan menu utama untuk Admin dan menangani navigasi antar pilihan menu.
     * Loop akan terus berjalan hingga Admin memilih opsi Logout (7).
     */
    public void showMenuAdmin() {
        int pilihan = 0;
        do {
            // [FIX] akun.getNama() sekarang aman karena class Admin extends Akun
            System.out.println("\n===================================");
            System.out.println("      DASHBOARD ADMIN: " + akun.getNama());
            System.out.println("===================================");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Edit Barang");
            System.out.println("3. Hapus Barang");
            System.out.println("4. Lihat Semua Barang");
            System.out.println("5. Lihat Transaksi Masuk");
            System.out.println("6. Konfirmasi Transaksi");
            System.out.println("7. Logout");
            System.out.print("Pilih menu: ");
            
            try {
                String input = scanner.nextLine();
                pilihan = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Masukkan angka valid!");
                continue; 
            }

             switch (pilihan) {
                case 1: inputBarangBaru(); break;
                case 2: inputEditBarang(); break;
                case 3: hapusBarang(); break;
                case 4: listBarang.tampilkanSemua(); break;
                case 5: lihatTransaksi(); break;
                case 6: konfirmasiTransaksi(); break;
                case 7: System.out.println("Logout berhasil."); break;
                default: System.out.println("Pilihan tidak tersedia.");
            }
        } while (pilihan != 7);
    }

    /**
     * Meminta input detail barang baru dari Admin dan menambahkannya ke {@code listBarang}.
     * Melakukan validasi sederhana untuk ID barang yang sudah ada dan input angka.
     * * @return Objek Barang baru yang berhasil dibuat dan ditambahkan, atau {@code null} jika gagal (misal: ID sudah ada atau input angka salah).
     */
    public Barang inputBarangBaru() {
        System.out.println("\n--- Tambah Barang Baru ---");
        try {
            System.out.print("ID Barang   : "); String id = scanner.nextLine();
            if(listBarang.cariBarang(id) != null) {
                System.out.println("Error: ID Barang sudah ada!"); return null;
            }
            System.out.print("Nama Parfum : "); String nama = scanner.nextLine();
            System.out.print("Harga       : "); double harga = Double.parseDouble(scanner.nextLine());
            System.out.print("Stok Awal   : "); int stok = Integer.parseInt(scanner.nextLine());
            System.out.print("Deskripsi   : "); String deskripsi = scanner.nextLine();
            System.out.print("Kategori    : "); String kategori = scanner.nextLine();

            Barang baru = new Barang(id, nama, harga, stok, deskripsi, kategori);
            listBarang.tambahBarang(baru);
            System.out.println("Sukses: Barang ditambahkan!");
            return baru;
        } catch (NumberFormatException e) {
            System.out.println("Error: Input angka salah!"); return null;
        }
    }

   /**
     * Meminta ID barang yang akan diedit, kemudian meminta input data baru (Nama, Harga, Stok).
     * Jika input kosong (tekan Enter), nilai lama akan dipertahankan.
     * * @return Objek Barang yang berhasil diperbarui, atau {@code null} jika barang tidak ditemukan atau terjadi error input.
     */
    public Barang inputEditBarang() {
        System.out.println("\n--- Edit Barang ---");
        listBarang.tampilkanSemua();
        System.out.print("Masukkan ID Barang: ");
        String id = scanner.nextLine();

        Barang lama = listBarang.cariBarang(id);
        if (lama != null) {
            try {
                // Tampilkan data lama agar admin tau apa yang diedit
                System.out.println("Edit: " + lama.getNama());
                
                System.out.print("Nama Baru (Enter skip): ");
                String nama = scanner.nextLine();
                if(nama.isEmpty()) nama = lama.getNama();

                System.out.print("Harga Baru (Enter skip): ");
                String strHarga = scanner.nextLine();
                double harga = strHarga.isEmpty() ? lama.getHarga() : Double.parseDouble(strHarga);

                System.out.print("Stok Baru (Enter skip): ");
                String strStok = scanner.nextLine();
                int stok = strStok.isEmpty() ? lama.getStok() : Integer.parseInt(strStok);

                // Update menggunakan Setter (Lebih efisien daripada buat object baru)
                lama.setNama(nama);
                lama.setHarga(harga);
                lama.setStok(stok);
                // Deskripsi & Kategori tidak diubah di menu simpel ini, tapi bisa ditambahkan
                
                System.out.println("Sukses: Data barang diperbarui.");
                return lama;
            } catch (Exception e) {
                System.out.println("Error saat edit."); return null;
            }
        }
        System.out.println("Barang tidak ditemukan.");
        return null;
    }

   /**
     * Meminta ID barang yang akan dihapus dan memanggil method hapus pada {@code listBarang}.
     */
     private void hapusBarang() {
        System.out.print("ID Barang dihapus: ");
        listBarang.hapusBarang(scanner.nextLine());
    }

   /**
     * Menampilkan semua transaksi yang ada dalam {@code listTransaksi}.
     * Memeriksa jika daftar transaksi kosong.
     */
    private void lihatTransaksi() {
        System.out.println("\n--- Daftar Transaksi Masuk ---");
        if (listTransaksi == null || listTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            for (Transaksi t : listTransaksi) {
                System.out.println(t.toString());
            }
        }
    }

   /**
     * Menampilkan daftar transaksi (memanggil {@code lihatTransaksi()}), 
     * kemudian meminta ID Transaksi untuk dikonfirmasi.
     * Memanggil method {@code konfirmasiTransaksi} dari objek {@code Admin}.
     */
    private void konfirmasiTransaksi() {
        lihatTransaksi();
        if (listTransaksi == null || listTransaksi.isEmpty()) return;

        System.out.print("ID Transaksi dikonfirmasi: ");
        String idT = scanner.nextLine();
        
        boolean found = false;
        for(Transaksi t : listTransaksi) {
            if(t.getIdTransaksi().equals(idT)) {
               // Asumsi: akun.konfirmasiTransaksi(t) akan mengubah status transaksi di objek t
                // dan mungkin menghapus/memindahkan transaksi dari listTransaksi jika sudah selesai
                akun.konfirmasiTransaksi(t);
                found = true;
                break;
            }
        }
        if(!found) System.out.println("ID tidak ditemukan.");
    }
}

