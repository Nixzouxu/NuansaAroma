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

