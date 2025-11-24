package nuansaaroma.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ThemeArt {
    // --- PALET WARNA  ---
    
    public static final Color PRIMARY = new Color(30, 50, 80);      // Navy Gelap
    public static final Color BACKGROUND = new Color(248, 249, 250); // Putih Tulang
    public static final Color TEXT_DARK = new Color(44, 62, 80);    // Hitam Text
    
    // Warna Aksen & Status 
    public static final Color ACCENT = new Color(212, 172, 13);     // Emas
    public static final Color SUCCESS = new Color(39, 174, 96);     // Hijau
    public static final Color DANGER = new Color(192, 57, 43);      // Merah
    
    // Warna Hover Khusus
    public static final Color HOVER_COLOR = new Color(108, 92, 231); // Ungu Modern
    public static final Color DANGER_HOVER = new Color(231, 76, 60); // Merah Terang

    // Warna Default Tombol (Putih)
    public static final Color BTN_DEFAULT = Color.WHITE; 

    // --- FONT ---
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 16);

    /**
     * Membuat UI Tombol 
     */
    public static JButton createButton(String text, Color hoverColor) {
        JButton btn = new JButton(text);
        btn.setFont(BOLD_FONT);
        
        btn.setBackground(Color.WHITE);
        btn.setForeground(TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Border Tebal (2px) agar terlihat jelas
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(TEXT_DARK, 2, true), 
            new EmptyBorder(10, 25, 10, 25)
        ));

        // Efek Hover: Ganti Warna Background & Text
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(hoverColor);
                btn.setForeground(Color.red); 
                btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(hoverColor, 2, true), 
                    new EmptyBorder(10, 25, 10, 25)
                ));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(TEXT_DARK); // Balik hitam
                btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(TEXT_DARK, 2, true), 
                    new EmptyBorder(10, 25, 10, 25)
                ));
            }
        });

        return btn;
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(REGULAR_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 100), 1), 
            new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    /**
     * Memuat dan menskalakan gambar dari jalur resource (path relatif)
     * @param path Jalur relatif gambar (e.g., "nuansaaroma/assets/image3.jpeg")
     * @param width Lebar yang diinginkan
     * @param height Tinggi yang diinginkan
     * @return ImageIcon yang sudah diskalakan, atau null jika gagal
     */
    public static ImageIcon scaleImage(String path, int width, int height) {
        try {
            // Mengakses file dari dalam JAR/package (resource)
            // Menggunakan ThemeArt.class.getClassLoader() untuk memastikan loading resource yang benar
            URL imageUrl = ThemeArt.class.getClassLoader().getResource(path);
            
            if (imageUrl == null) {
                System.err.println("Gagal memuat gambar: File tidak ditemukan di jalur: " + path);
                return null;
            }
            
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            
            // Lakukan scaling (penyesuaian ukuran)
            Image img = originalIcon.getImage();
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            
            return new ImageIcon(scaledImage);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saat memuat/menskalakan gambar: " + e.getMessage());
            return null;
        }
    }
    
} 