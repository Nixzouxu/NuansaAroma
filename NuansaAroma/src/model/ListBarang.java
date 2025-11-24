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

    /**
     * Menghapus barang berdasarkan ID. Menggunakan pencarian dengan metode
     * lambda dan {@code removeIf}.
     *
     * @param id ID barang yang ingin dihapus
     */
    public void hapusBarang(String id) {
        boolean removed = barangList.removeIf(b -> b.getIdBarang().equalsIgnoreCase(id));
        if (removed) {
            System.out.println("Barang dengan ID " + id + " berhasil dihapus.");
        } else {
            System.out.println("Barang tidak ditemukan.");
        }
    }

    /**
     * Mengubah data barang dengan mencocokkan ID barang lama dengan barang baru.
     *
     * @param bBaru objek barang baru yang berisi data pembaruan
     */
    public void editBarang(Barang bBaru) {
        for (int i = 0; i < barangList.size(); i++) {
            if (barangList.get(i).getIdBarang().equalsIgnoreCase(bBaru.getIdBarang())) {
                barangList.set(i, bBaru);
                System.out.println("Data barang berhasil diperbarui.");
                return;
            }
        }
        System.out.println("Barang yang akan diedit tidak ditemukan.");
    }
