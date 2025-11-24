package nuansaaroma.driver;

import nuansaaroma.model.*;
import java.util.ArrayList;
import java.util.Scanner;

// [FIX 1] Nama Class harus Huruf Besar (CustomerDriver)
public class CustomerDriver extends Driver {
    private Customer akun;
    private ListBarang listBarang; 
    private ArrayList<Transaksi> globalTransaksi; 
    private Scanner scanner;

    public CustomerDriver(Customer akun, ListBarang listBarang, ArrayList<Transaksi> globalTransaksi) {
        this.akun = akun;
        this.listBarang = listBarang;
        this.globalTransaksi = globalTransaksi;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        showMenuCustomer();
    }

    public void showMenuCustomer() {
        int pilihan = 0;
        do {
            System.out.println("\n======================================");
            System.out.println("   MENU PELANGGAN: " + akun.getNama());
            System.out.println("======================================");
            System.out.println("1. Lihat Katalog Parfum");
            System.out.println("2. Masukkan Barang ke Keranjang");
            System.out.println("3. Lihat Keranjang Saya");
            System.out.println("4. Checkout & Bayar");
            System.out.println("5. Riwayat Belanja");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            
            try {
                String input = scanner.nextLine();
                pilihan = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Masukkan angka valid!");
                continue;
            }
