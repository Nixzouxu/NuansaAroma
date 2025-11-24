package nuansaaroma.driver;

import nuansaaroma.model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminDriver extends Driver {
    private Admin akun;
    private ListBarang listBarang;
    private ArrayList<Transaksi> listTransaksi;
    private Scanner scanner;

    public AdminDriver(Admin akun, ListBarang listBarang, ArrayList<Transaksi> listTransaksi) {
        this.akun = akun;
        this.listBarang = listBarang;
        this.listTransaksi = listTransaksi;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        showMenuAdmin();
    }

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

     private void hapusBarang() {
        System.out.print("ID Barang dihapus: ");
        listBarang.hapusBarang(scanner.nextLine());
    }

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

    private void konfirmasiTransaksi() {
        lihatTransaksi();
        if (listTransaksi == null || listTransaksi.isEmpty()) return;

        System.out.print("ID Transaksi dikonfirmasi: ");
        String idT = scanner.nextLine();
        
        boolean found = false;
        for(Transaksi t : listTransaksi) {
            if(t.getIdTransaksi().equals(idT)) {
                akun.konfirmasiTransaksi(t);
                found = true;
                break;
            }
        }
        if(!found) System.out.println("ID tidak ditemukan.");
    }
}

