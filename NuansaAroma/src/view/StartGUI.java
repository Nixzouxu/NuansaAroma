package nuansaaroma.view;

import javax.swing.UIManager;
import nuansaaroma.Main;

public class StartGUI {
    public static void main(String[] args) {
        System.out.println("sedang memuat data aplikasi...");

        // 1. INISIALISASI DATA
        // Kita meminjam logic data dari Main.java agar tidak perlu buat ulang
        // Pastikan method initDataUntukGui() sudah di tambahkan di Main.java 
        Main.initDataUntukGui(); 

        // 2. ATUR TAMPILAN 
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Gagal memuat tema sistem: " + e.getMessage());
        }

        // 3. JALANKAN WINDOW LOGIN
        // Menggunakan EventQueue agar thread GUI aman 
        java.awt.EventQueue.invokeLater(() -> {
            // Kita panggil LoginRegisterFrame 
            new LoginRegisterFrame().setVisible(true);
        });
        
        System.out.println("Aplikasi GUI Berhasil Dijalankan!");
    }
}
