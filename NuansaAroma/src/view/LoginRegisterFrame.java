package nuansaaroma.view;

import javax.swing.*;
import java.awt.*;
import nuansaaroma.Main;
import nuansaaroma.model.*;

public class LoginRegisterFrame extends JFrame {
    private JTextField txtUserLogin, txtUserReg, txtNamaReg, txtAlamatReg, txtHpReg;
    private JPasswordField txtPassLogin, txtPassReg;

    public LoginRegisterFrame() {
        setTitle("Nuansa Aroma - Access");
        setSize(1000, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- KIRI: BRANDING ---
        JPanel panelKiri = new JPanel(new GridBagLayout());
        panelKiri.setBackground(ThemeArt.PRIMARY);
        panelKiri.setPreferredSize(new Dimension(400, 600));
        
        JLabel lblBrand = new JLabel("<html><center><h1 style='color:white; font-size:45px;'>NUANSA<br>AROMA</h1><p style='color:#dddddd; font-size:16px;'>The Essence of Luxury</p></center></html>");
        panelKiri.add(lblBrand);

        // --- KANAN: TAB LOGIN/REGISTER ---
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setFont(ThemeArt.BOLD_FONT);
        tabPane.setBackground(Color.WHITE);
        
        tabPane.addTab("  LOGIN  ", createCenterPanel(createLoginForm()));
        tabPane.addTab("  REGISTER  ", createCenterPanel(createRegisterForm()));

        add(panelKiri, BorderLayout.WEST);
        add(tabPane, BorderLayout.CENTER);
    }

    // Helper untuk menengahkan konten secara vertikal & horizontal
    private JPanel createCenterPanel(JPanel content) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(content);
        return wrapper;
    }

    private JPanel createLoginForm() {
        JPanel p = new JPanel(new GridLayout(0, 1, 15, 15)); // Jarak antar elemen lebih lega
        p.setBackground(Color.WHITE);
        p.setPreferredSize(new Dimension(350, 350)); // Ukuran Fix agar tidak melar

        JLabel lblTitle = new JLabel("LOGIN AREA", SwingConstants.CENTER);
        lblTitle.setFont(ThemeArt.HEADER_FONT);
        lblTitle.setForeground(ThemeArt.PRIMARY);
        p.add(lblTitle);

        txtUserLogin = ThemeArt.createTextField();
        txtPassLogin = new JPasswordField();
        txtPassLogin.setBorder(txtUserLogin.getBorder());
        txtPassLogin.setFont(ThemeArt.REGULAR_FONT);

        p.add(new JLabel("Username:")); p.add(txtUserLogin);
        p.add(new JLabel("Password:")); p.add(txtPassLogin);

        // Tombol dengan Warna Hover Ungu
        JButton btnLogin = ThemeArt.createButton("MASUK SEKARANG", ThemeArt.HOVER_COLOR);
        p.add(Box.createVerticalStrut(10));
        p.add(btnLogin);

        btnLogin.addActionListener(e -> aksiLogin());
        return p;
    }

    private JPanel createRegisterForm() {
        JPanel p = new JPanel(new GridLayout(0, 1, 10, 10));
        p.setBackground(Color.WHITE);
        p.setPreferredSize(new Dimension(350, 550)); // Lebih tinggi karena field banyak

        JLabel lblTitle = new JLabel("BUAT AKUN BARU", SwingConstants.CENTER);
        lblTitle.setFont(ThemeArt.HEADER_FONT);
        lblTitle.setForeground(ThemeArt.PRIMARY);
        p.add(lblTitle);

        txtUserReg = ThemeArt.createTextField();
        txtPassReg = new JPasswordField(); txtPassReg.setBorder(txtUserReg.getBorder());
        txtNamaReg = ThemeArt.createTextField();
        txtAlamatReg = ThemeArt.createTextField();
        txtHpReg = ThemeArt.createTextField();

        p.add(new JLabel("Username:")); p.add(txtUserReg);
        p.add(new JLabel("Password:")); p.add(txtPassReg);
        p.add(new JLabel("Nama Lengkap:")); p.add(txtNamaReg);
        p.add(new JLabel("Alamat:")); p.add(txtAlamatReg);
        p.add(new JLabel("No HP:")); p.add(txtHpReg);

        JButton btnReg = ThemeArt.createButton("DAFTAR", ThemeArt.HOVER_COLOR);
        p.add(Box.createVerticalStrut(10));
        p.add(btnReg);

        btnReg.addActionListener(e -> aksiRegister());
        return p;
    }

    private void aksiLogin() {
        String user = txtUserLogin.getText();
        String pass = new String(txtPassLogin.getPassword());

        System.out.println("Mencoba login: " + user); // Debugging

        Akun found = null;
        for (Akun a : Main.getListAkun()) {
            if (a.validasiLogin(user, pass)) {
                found = a;
                break;
            }
        }

        if (found != null) {
            // Tutup jendela login dulu
            this.dispose();
            
            // Cek Role dengan Debugging
            System.out.println("Login Berhasil! Role Class: " + found.getClass().getSimpleName());

            try {
                // Jika akun adalah Admin
                if (found instanceof Admin) {
                    System.out.println("Membuka Dashboard Admin...");
                    new AdminMainFrame((Admin) found).setVisible(true);
                } 
                // Jika akun adalah Customer
                else if (found instanceof Customer) {
                    System.out.println("Membuka Dashboard Customer...");
                    new CustomerMainFrame((Customer) found).setVisible(true);
                } 
                else {
                    JOptionPane.showMessageDialog(this, "Role Akun tidak dikenali!", "Error System", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal membuka dashboard: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login Gagal! Username atau Password Salah.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aksiRegister() {
        String u = txtUserReg.getText(); String p = new String(txtPassReg.getPassword());
        if(u.isEmpty() || p.isEmpty()) { JOptionPane.showMessageDialog(this, "Lengkapi data!"); return; }
        Main.getListAkun().add(new Customer(u, p, txtNamaReg.getText(), txtAlamatReg.getText(), txtHpReg.getText()));
        JOptionPane.showMessageDialog(this, "Registrasi Berhasil!");
        txtUserReg.setText("");
    }
}