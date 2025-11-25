package nuansaaroma.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import nuansaaroma.Main;
import nuansaaroma.model.*;

/**
 * Tampilan utama untuk Admin setelah login.
 * Menyediakan panel untuk manajemen barang dan melihat transaksi masuk.
 */
public class AdminMainFrame extends JFrame {
	private Admin admin;
	private JPanel contentPanel;
	private CardLayout cardLayout;
	
	// Components
	private JLabel lblTotalBarang, lblTotalStok;
	private JTable tableBarang, tableTransaksi;
	private DefaultTableModel modelBarang, modelTransaksi;

	/**
	 * Konstruktor frame admin.
	 * Inisialisasi layout, sidebar, content, dan event tombol.
	 *
	 * @param admin objek Admin yang sedang login
	 */
	public AdminMainFrame(Admin admin) {
		this.admin = admin;
		setTitle("Admin Dashboard - " + admin.getNama());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// --- 1. SIDEBAR ---
		JPanel sidebar = new JPanel();
		sidebar.setBackground(ThemeArt.PRIMARY);
		sidebar.setPreferredSize(new Dimension(260, 600));
		sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
		sidebar.setBorder(new javax.swing.border.EmptyBorder(30, 20, 30, 20));

		JLabel lblLogo = new JLabel("<html><center><h2 style='color:white;'>ADMIN<br>PANEL</h2></center></html>");
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		sidebar.add(lblLogo);
		sidebar.add(Box.createVerticalStrut(50));

		JButton btnDashboard = createSidebarButton("Manajemen Barang");
		JButton btnTransaksi = createSidebarButton("Transaksi Masuk");
		JButton btnLogout = ThemeArt.createButton("KELUAR", ThemeArt.DANGER);
		btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogout.setMaximumSize(new Dimension(200, 45));

		sidebar.add(btnDashboard);
		sidebar.add(Box.createVerticalStrut(15));
		sidebar.add(btnTransaksi);
		sidebar.add(Box.createVerticalGlue());
		sidebar.add(btnLogout);

		// --- 2. CONTENT ---
		contentPanel = new JPanel(new CardLayout());
		contentPanel.setBackground(ThemeArt.BACKGROUND);
		cardLayout = (CardLayout) contentPanel.getLayout();

		contentPanel.add(createDashboardPanel(), "DASHBOARD");
		contentPanel.add(createTransaksiPanel(), "TRANSAKSI");

		add(sidebar, BorderLayout.WEST);
		add(contentPanel, BorderLayout.CENTER);

		// --- EVENTS ---
		btnDashboard.addActionListener(e -> {
			refreshDataBarang();
			cardLayout.show(contentPanel, "DASHBOARD");
		});

		btnTransaksi.addActionListener(e -> {
			refreshDataTransaksi();
			cardLayout.show(contentPanel, "TRANSAKSI");
		});

		btnLogout.addActionListener(e -> {
			if(JOptionPane.showConfirmDialog(this, "Logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose();
				new LoginRegisterFrame().setVisible(true); 
			}
		});

		refreshDataBarang();
	}

	/**
	 * Membuat tombol sidebar dengan styling konsisten.
	 *
	 * @param text teks tombol
	 * @return JButton yang sudah distilasi
	 */
	private JButton createSidebarButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(ThemeArt.BOLD_FONT);
		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(255,255,255,30));
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
		btn.setMaximumSize(new Dimension(220, 50));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(ThemeArt.HOVER_COLOR); }
			public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(255,255,255,30)); }
		});
		return btn;
	}

	/**
	 * Membuat panel dashboard (list barang + statistik).
	 *
	 * @return JPanel dashboard
	 */
	private JPanel createDashboardPanel() {
		JPanel panel = new JPanel(new BorderLayout(20, 20));
		panel.setBackground(ThemeArt.BACKGROUND);
		panel.setBorder(new javax.swing.border.EmptyBorder(30, 30, 30, 30));

		JPanel pnlStats = new JPanel(new GridLayout(1, 3, 20, 0));
		pnlStats.setOpaque(false);
		lblTotalBarang = createCardStat("Total Produk", ThemeArt.PRIMARY);
		lblTotalStok = createCardStat("Total Stok", ThemeArt.ACCENT);
		pnlStats.add(lblTotalBarang);
		pnlStats.add(lblTotalStok);
		pnlStats.add(new JLabel("")); 
		panel.add(pnlStats, BorderLayout.NORTH);

		String[] col = {"ID", "Nama Parfum", "Harga", "Stok", "Kategori"};
		modelBarang = new DefaultTableModel(col, 0) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tableBarang = new JTable(modelBarang);
		setupTableDesign(tableBarang);
		panel.add(new JScrollPane(tableBarang), BorderLayout.CENTER);

		JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		pnlAction.setOpaque(false);
		
		JButton btnAdd = ThemeArt.createButton("Tambah (+)", ThemeArt.SUCCESS);
		JButton btnEdit = ThemeArt.createButton("Edit Barang", ThemeArt.HOVER_COLOR); 
		JButton btnDel = ThemeArt.createButton("Hapus", ThemeArt.DANGER);
		
		pnlAction.add(btnAdd);
		pnlAction.add(btnEdit);
		pnlAction.add(btnDel);
		panel.add(pnlAction, BorderLayout.SOUTH);

		btnAdd.addActionListener(e -> showDialogTambah());
		btnDel.addActionListener(e -> aksiHapus());
		btnEdit.addActionListener(e -> aksiEdit()); 

		return panel;
	}

	/**
	 * Membuat panel transaksi masuk.
	 *
	 * @return JPanel transaksi
	 */
	private JPanel createTransaksiPanel() {
		JPanel panel = new JPanel(new BorderLayout(20, 20));
		panel.setBackground(ThemeArt.BACKGROUND);
		panel.setBorder(new javax.swing.border.EmptyBorder(30, 30, 30, 30));

		JLabel lblTitle = new JLabel("Daftar Transaksi Masuk");
		lblTitle.setFont(ThemeArt.HEADER_FONT);
		lblTitle.setForeground(ThemeArt.PRIMARY);
		panel.add(lblTitle, BorderLayout.NORTH);

		String[] col = {"ID Trx", "Customer", "Total Belanja", "Status"};
		modelTransaksi = new DefaultTableModel(col, 0);
		tableTransaksi = new JTable(modelTransaksi);
		setupTableDesign(tableTransaksi);
		panel.add(new JScrollPane(tableTransaksi), BorderLayout.CENTER);

		JPanel pnlBawah = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlBawah.setOpaque(false);
		JButton btnConfirm = ThemeArt.createButton("Terima & Kirim Barang", ThemeArt.SUCCESS);
		pnlBawah.add(btnConfirm);
		panel.add(pnlBawah, BorderLayout.SOUTH);

		btnConfirm.addActionListener(e -> aksiKonfirmasi());

		return panel;
	}

	/**
	 * Atur tampilan tabel (font, tinggi baris, warna seleksi).
	 *
	 * @param table JTable yang akan diatur
	 */
	private void setupTableDesign(JTable table) {
		table.setRowHeight(40);
		table.setFont(ThemeArt.REGULAR_FONT);
		table.getTableHeader().setFont(ThemeArt.BOLD_FONT);
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setForeground(ThemeArt.PRIMARY);
		table.setShowVerticalLines(false);
		table.setSelectionBackground(new Color(230, 240, 255));
		table.setSelectionForeground(ThemeArt.TEXT_DARK);
	}

	/**
	 * Membuat label statistik berbentuk kartu.
	 *
	 * @param title judul kartu
	 * @param color warna pembatas kiri kartu
	 * @return JLabel yang diformat sebagai kartu statistik
	 */
	private JLabel createCardStat(String title, Color color) {
		String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		JLabel lbl = new JLabel("<html><div style='padding:15px; background:white; border-left:6px solid "+hex+"; width:180px;'>"
				+ "<span style='color:gray; font-size:12px;'>"+title+"</span><br>"
				+ "<span style='color:#333; font-size:28px; font-weight:bold;'>0</span></div></html>");
		return lbl;
	}

	/**
	 * Memperbarui teks pada label statistik.
	 *
	 * @param lbl label yang diubah
	 * @param title judul kartu
	 * @param value nilai statistik
	 * @param color warna pembatas kiri kartu
	 */
	private void updateStatLabel(JLabel lbl, String title, int value, Color color) {
		String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		lbl.setText("<html><div style='padding:15px; background:white; border-left:6px solid "+hex+"; width:180px;'>"
				+ "<span style='color:gray; font-size:12px;'>"+title+"</span><br>"
				+ "<span style='color:#333; font-size:28px; font-weight:bold;'>"+value+"</span></div></html>");
	}

	/**
	 * Memuat ulang data barang ke tabel dan memperbarui statistik.
	 */
	private void refreshDataBarang() {
		modelBarang.setRowCount(0);
		int totalStok = 0;
		for(Barang b : Main.getListBarang().getList()) {
			modelBarang.addRow(new Object[]{
				b.getIdBarang(), b.getNama(), "Rp " + String.format("%,.0f", b.getHarga()), b.getStok(), b.getKategori()
			});
			totalStok += b.getStok();
		}
		updateStatLabel(lblTotalBarang, "Total Produk", Main.getListBarang().getList().size(), ThemeArt.PRIMARY);
		updateStatLabel(lblTotalStok, "Total Stok", totalStok, ThemeArt.ACCENT);
	}

	/**
	 * Memuat ulang data transaksi ke tabel transaksi.
	 */
	private void refreshDataTransaksi() {
		modelTransaksi.setRowCount(0);
		// Mengambil data dari Global Transaksi di Main
		for(Transaksi t : Main.getGlobalTransaksi()) {
			modelTransaksi.addRow(new Object[]{
				t.getIdTransaksi(),
				t.getCustomer().getNama(),
				"Rp " + String.format("%,.0f", t.getTotalHarga()),
				t.getStatus() // Status Awal: "MENUNGGU"
			});
		}
	}

	// ----------------------------------------------------------------------
    // --- CRUD LOGIC MODIFIED ---
    // ----------------------------------------------------------------------

	/**
	 * Menampilkan dialog untuk menambah barang baru.
	 * Mengambil input dari user dan menambahkan ke ListBarang global.
	 */
	private void showDialogTambah() {
		JDialog d = new JDialog(this, "Tambah Barang", true);
		d.setSize(450, 550); 
		d.setLocationRelativeTo(this);
        // JDialog default menggunakan BorderLayout
		
        // BARU: JPanel untuk menampung form, dengan GridLayout dan Border
        JPanel pnlForm = new JPanel(new GridLayout(8, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
		
		JTextField tId = ThemeArt.createTextField();
		JTextField tNama = ThemeArt.createTextField();
		JTextField tHarga = ThemeArt.createTextField();
		JTextField tStok = ThemeArt.createTextField();
        JTextField tKat = ThemeArt.createTextField();
        JTextField tDeskripsi = ThemeArt.createTextField(); 
		JTextField tPath = ThemeArt.createTextField(); 
		
		pnlForm.add(new JLabel("  ID Barang:")); pnlForm.add(tId);
		pnlForm.add(new JLabel("  Nama Parfum:")); pnlForm.add(tNama);
		pnlForm.add(new JLabel("  Harga:")); pnlForm.add(tHarga);
		pnlForm.add(new JLabel("  Stok Awal:")); pnlForm.add(tStok);
		pnlForm.add(new JLabel("  Kategori:")); pnlForm.add(tKat);
        pnlForm.add(new JLabel("  Deskripsi:")); pnlForm.add(tDeskripsi); 
        pnlForm.add(new JLabel("  Image Path:")); pnlForm.add(tPath); 
		
		JButton btnSave = ThemeArt.createButton("SIMPAN", ThemeArt.SUCCESS);
		pnlForm.add(new JLabel("")); pnlForm.add(btnSave);
		
        d.add(pnlForm, BorderLayout.CENTER); // Tambahkan panel form ke dialog
        
		btnSave.addActionListener(e -> {
			try {
				double h = Double.parseDouble(tHarga.getText());
				int s = Integer.parseInt(tStok.getText());
				
				Barang b = new Barang(
                    tId.getText(), 
                    tNama.getText(), 
                    h, 
                    s, 
                    tDeskripsi.getText(), 
                    tKat.getText(),
                    tPath.getText()
                );
				Main.getListBarang().tambahBarang(b);
				
				refreshDataBarang();
				d.dispose();
				JOptionPane.showMessageDialog(this, "Barang ditambahkan!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(d, "Input ID, Harga, atau Stok tidak valid!");
			}
		});
		d.setVisible(true);
	}

    /**
     * Menangani aksi edit: memeriksa baris yang dipilih dan membuka dialog edit.
     */
    private void aksiEdit() {
        int row = tableBarang.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(this, "Pilih barang dulu!"); 
            return; 
        }
        String id = (String) modelBarang.getValueAt(row, 0);
        Barang b = Main.getListBarang().cariBarang(id);
        
        if(b != null) {
            showDialogEditBarang(b); 
        }
    }

    /**
     * Menampilkan dialog edit barang untuk mengubah atribut barang.
     *
     * @param b objek Barang yang akan diedit
     */
    private void showDialogEditBarang(Barang b) {
        JDialog d = new JDialog(this, "Edit Barang: " + b.getNama(), true);
        d.setSize(750, 450); 
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout(15, 15));
        
        // Panel utama dengan padding (pnlContent adalah JPanel, jadi bisa pakai setBorder)
        JPanel pnlContent = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // --- 1. PANEL DATA (Kiri) ---
        JPanel pnlData = new JPanel(new GridLayout(7, 2, 10, 10)); 
        
        // Field Text untuk di-edit
        JTextField txtNama = ThemeArt.createTextField(); txtNama.setText(b.getNama());
        JTextField txtHarga = ThemeArt.createTextField(); txtHarga.setText(String.valueOf(b.getHarga()));
        JTextField txtStok = ThemeArt.createTextField(); txtStok.setText(String.valueOf(b.getStok()));
        JTextField txtDeskripsi = ThemeArt.createTextField(); txtDeskripsi.setText(b.getDeskripsi());
        JTextField txtKat = ThemeArt.createTextField(); txtKat.setText(b.getKategori());
        JTextField txtPath = ThemeArt.createTextField(); txtPath.setText(b.getImagePath());
        
        pnlData.add(new JLabel(" Nama Parfum:")); pnlData.add(txtNama);
        pnlData.add(new JLabel(" Harga (Angka):")); pnlData.add(txtHarga);
        pnlData.add(new JLabel(" Stok:")); pnlData.add(txtStok);
        pnlData.add(new JLabel(" Deskripsi:")); pnlData.add(txtDeskripsi);
        pnlData.add(new JLabel(" Kategori:")); pnlData.add(txtKat);
        pnlData.add(new JLabel(" Image Path:")); pnlData.add(txtPath);
        pnlData.add(new JLabel("")); pnlData.add(new JLabel("")); 
        
        // --- 2. PANEL GAMBAR (Kanan) ---
        JPanel pnlImage = new JPanel(new BorderLayout());
        pnlImage.setBorder(BorderFactory.createTitledBorder("Preview Gambar"));
        
        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        
        // Muat dan skalakan gambar dari ImagePath
        ImageIcon productIcon = ThemeArt.scaleImage(b.getImagePath(), 250, 250); 
        if(productIcon != null) {
            lblImage.setIcon(productIcon);
        } else {
            lblImage.setText("<html><center>Gambar tidak tersedia atau<br>Path salah: " + b.getImagePath() + "</center></html>");
        }
        pnlImage.add(lblImage, BorderLayout.CENTER);
        
        // --- 3. GABUNGKAN & LOGIC SIMPAN ---
        pnlContent.add(pnlData);
        pnlContent.add(pnlImage);
        d.add(pnlContent, BorderLayout.CENTER);

        JButton btnSave = ThemeArt.createButton("SIMPAN PERUBAHAN", ThemeArt.SUCCESS);
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSouth.add(btnSave);
        d.add(pnlSouth, BorderLayout.SOUTH);
        
        btnSave.addActionListener(e -> {
            try {
                // Update objek Barang 'b' menggunakan setters
                b.setNama(txtNama.getText());
                b.setHarga(Double.parseDouble(txtHarga.getText()));
                b.setStok(Integer.parseInt(txtStok.getText()));
                b.setDeskripsi(txtDeskripsi.getText());
                b.setKategori(txtKat.getText());
                b.setImagePath(txtPath.getText()); 
                
                d.dispose();
                refreshDataBarang();
                JOptionPane.showMessageDialog(this, "Data barang berhasil diupdate!");
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(d, "Error: Harga/Stok harus angka valid.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        d.setVisible(true);
    }

	/**
	 * Menghapus barang yang dipilih dari ListBarang global.
	 */
	private void aksiHapus() {
		int row = tableBarang.getSelectedRow();
		if(row == -1) { JOptionPane.showMessageDialog(this, "Pilih barang dulu!"); return; }
		String id = (String) modelBarang.getValueAt(row, 0);
		if(JOptionPane.showConfirmDialog(this, "Hapus ID: "+id+"?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			Main.getListBarang().hapusBarang(id);
			refreshDataBarang();
		}
	}

	/**
	 * Konfirmasi transaksi: ubah status transaksi menjadi selesai dan simpan ke file.
	 */
	private void aksiKonfirmasi() {
		int row = tableTransaksi.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(this, "Pilih transaksi dulu!"); return;
		}
		String idTrx = (String) modelTransaksi.getValueAt(row, 0);
		
		for(Transaksi t : Main.getGlobalTransaksi()) {
			if(t.getIdTransaksi().equals(idTrx)) {
				admin.konfirmasiTransaksi(t); // Status berubah jadi SELESAI di memory
				
				Main.updateStatusTransaksiDiFile(); 
				
				refreshDataTransaksi();
				JOptionPane.showMessageDialog(this, "Transaksi "+idTrx+" Diterima!");
				return;
			}
		}
	}
}
