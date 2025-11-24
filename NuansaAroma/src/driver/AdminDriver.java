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
