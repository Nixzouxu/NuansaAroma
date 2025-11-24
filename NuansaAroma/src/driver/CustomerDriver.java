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

             switch (pilihan) {
                case 1:
                    akun.lihatBarang(listBarang);
                    break;
                case 2:
                    prosesTambahKeranjang();
                    break;
                case 3:
                    lihatKeranjang();
                    break;
                case 4:
                    buatTransaksi();
                    break;
                case 5:
                    akun.lihatRiwayatInvoice();
                    break;
                case 6:
                    System.out.println("Terima kasih sudah berbelanja!");
                    break;
                default:
                    System.out.println("Pilihan tidak ada.");
            }
        } while (pilihan != 6);
    }

    private void prosesTambahKeranjang() {
        akun.lihatBarang(listBarang);
        System.out.print("\nMasukkan ID Parfum yang ingin dibeli: ");
        String id = scanner.nextLine();
        
        Barang b = listBarang.cariBarang(id);
        if (b != null) {
            akun.tambahKeKeranjang(b);
        } else {
            System.out.println("Barang dengan ID tersebut tidak ditemukan.");
        }
    }

    private void lihatKeranjang() {
        Keranjang k = akun.getKeranjang();
        ArrayList<Barang> items = k.getItems();
        
        System.out.println("\n--- ISI KERANJANG SAYA ---");
        if (items.isEmpty()) {
            System.out.println("(Keranjang Kosong)");
        } else {
            for (Barang b : items) {
                System.out.println("- " + b.getNama() + " (Rp" + b.getHarga() + ")");
            }
            System.out.println("TOTAL HARGA: Rp" + k.hitungTotal());
        }
    }

public void buatTransaksi() {
        Keranjang k = akun.getKeranjang();
        if (k.getItems().isEmpty()) {
            System.out.println("Keranjang kosong, tidak bisa checkout.");
            return;
        }

        double total = k.hitungTotal();
        System.out.println("\n--- PROSES CHECKOUT ---");
        System.out.println("Total Tagihan: Rp" + total);
        
        // Pilih Pembayaran
        Pembayaran pay = pilihMetodePembayaran(total);
        if (pay == null) return; 

        // Proses Pembayaran
        if (pay.prosesPembayaran()) {
            String idTrx = "TRX-" + System.currentTimeMillis();
            
            // Clone list barang
            ArrayList<Barang> belanjaan = new ArrayList<>(k.getItems());
            
            Transaksi t = new Transaksi(akun, belanjaan, idTrx, total);
            
            globalTransaksi.add(t); 
            
            // Kurangi Stok
            for(Barang b : belanjaan) {
                int sisaStok = b.getStok() - 1; 
                b.setStok(sisaStok); // Pastikan class Barang punya method setStok
            }
            
