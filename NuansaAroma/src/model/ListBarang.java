package nuansaaroma.model;

import java.util.ArrayList;

/**
 * Kelas {@code ListBarang} digunakan untuk menyimpan dan mengelola daftar
 * barang yang tersedia dalam sistem. Kelas ini menyediakan fitur untuk
 * menambah barang, menghapus barang berdasarkan ID, mengedit data barang,
 * mencari barang, dan menampilkan seluruh daftar barang.
 */
public class ListBarang {
    private ArrayList<Barang> barangList;
    private int kapasitasMax;

    /**
     * Membuat objek {@code ListBarang} baru dengan kapasitas maksimum default 100.
     */
    public ListBarang() {
        this.barangList = new ArrayList<>();
        this.kapasitasMax = 100;
    }

     /**
     * Menambahkan barang baru ke dalam list jika kapasitas belum penuh.
     *
     * @param b barang yang akan ditambahkan
     */
    public void tambahBarang(Barang b) {
        if (barangList.size() < kapasitasMax) {
            barangList.add(b);
        } else {
            System.out.println("Gudang Penuh!");
        }
    }
