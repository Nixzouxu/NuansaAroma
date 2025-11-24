package nuansaaroma.model;

import java.util.ArrayList;

/**
 * Kelas {@code Keranjang} merepresentasikan keranjang belanja yang digunakan
 * oleh customer untuk menampung daftar barang sebelum melakukan checkout.
 * 
 * <p>Kelas ini menyimpan list barang yang ditambahkan, menghitung total harga,
 * dan menyediakan operasi untuk menambah, menghapus, serta mengosongkan keranjang.</p>
 */
public class Keranjang {
    private ArrayList<Barang> barangList;
    private double totalHarga;

    /**
     * Membuat objek {@code Keranjang} baru dengan list barang kosong
     * dan total harga awal 0.
     */
    public Keranjang() {
        this.barangList = new ArrayList<>();
        this.totalHarga = 0;
    }

    /**
     * Menambahkan sebuah barang ke dalam keranjang dan memperbarui total harga.
     *
     * @param b barang yang akan ditambahkan
     */
    public void tambahBarang(Barang b){
        barangList.add(b);
        hitungTotal();
    }

    /**
     * Menghapus sebuah barang dari keranjang jika barang tersebut ada,
     * kemudian memperbarui total harga.
     *
     * @param b barang yang ingin dihapus
     */
    public void hapusBarang(Barang b) {
        if (barangList.remove(b)) {
            hitungTotal();
        }
    }

    // [FIX 1] Dibutuhkan saat checkout berhasil
    /**
     * Mengosongkan seluruh isi keranjang serta mengatur total harga menjadi 0.
     */
    public void kosongkan() {
        barangList.clear();
        totalHarga = 0;
    }

    // [FIX 2] Dibutuhkan untuk menampilkan total harga
    /**
     * Menghitung ulang total harga dari seluruh barang dalam keranjang.
     *
     * @return total harga seluruh barang dalam keranjang
     */
    public double hitungTotal() {
        totalHarga = 0;
        for (Barang b : barangList) {
            totalHarga += b.getHarga();
        }
        return totalHarga;
    }

    // [FIX 3] Dibutuhkan CustomerDriver untuk mengambil isi keranjang
    /**
     * Mengambil seluruh barang yang ada dalam keranjang.
     *
     * @return list barang dalam keranjang
     */
    public ArrayList<Barang> getItems() {
        return barangList;
    }
}


    
