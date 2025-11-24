<div align="center">

# 🌸 NuansaAroma

### *Where Code Meets Elegance*

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Swing](https://img.shields.io/badge/GUI-Swing-blue?style=for-the-badge)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![OOP](https://img.shields.io/badge/Paradigm-OOP-success?style=for-the-badge)]()
[![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=for-the-badge)]()

**Premium Perfume Marketplace | Built with ☕ & ❤️**

*Aplikasi desktop marketplace parfum premium yang menggabungkan elegansi desain dengan fungsionalitas modern. Dikembangkan menggunakan Java Swing dengan penerapan konsep Object-Oriented Programming (OOP) secara menyeluruh dari Encapsulation hingga Polymorphism. NuansaAroma menawarkan dua portal berbeda: Customer untuk pengalaman belanja yang intuitif, dan Admin untuk manajemen inventaris yang efisien.*

[🚀 Quick Start](#-quick-start) • [✨ Features](#-fitur-unggulan) • [👥 Team](#-the-team) • [📸 Preview](#-preview)

---

</div>

---

## 💭 The Story Behind

> *"Aroma bukan sekadar wangi—ia adalah identitas, kenangan, dan kehadiran yang tertinggal."*

NuansaAroma lahir dari sebuah pertanyaan sederhana: **Bagaimana jika pengalaman berbelanja parfum bisa semudah dan seindah wanginya?**

Ini lebih dari sekadar Final Project Praktikum PBO. Ini adalah bukti bahwa konsep *Encapsulation*, *Inheritance*, dan *Polymorphism* yang sering kita pelajari di kelas **bisa jadi sesuatu yang nyata dan berguna**. Kami mentransformasi teori jadi aplikasi desktop yang fungsional, dengan UI yang (semoga) enak dilihat dan pengalaman user yang intuitif.

---

## 👥 The Team

**Tim 12 - The Architects of Scent** 🏗️

<table>
  <tr>
    <td align="center" width="25%">
      <img src="https://via.placeholder.com/100" width="100px;" alt="Muhammad Hafidz"/><br />
      <sub><b>Muhammad Hafidz</b></sub><br />
      <i>Lead Developer & Backend Architect</i><br />
      <sup>🧠 Otak di balik Main.java & database logic</sup>
    </td>
    <td align="center" width="25%">
      <img src="https://via.placeholder.com/100" width="100px;" alt="Aulia Lutfi"/><br />
      <sub><b>Aulia Lutfi</b></sub><br />
      <i>UI/UX Designer & Frontend Engineer</i><br />
      <sup>🎨 Arsitek antarmuka ThemeArt & LoginFrame</sup>
    </td>
    <td align="center" width="25%">
      <img src="https://via.placeholder.com/100" width="100px;" alt="Zahra Rizkyna"/><br />
      <sub><b>Zahra Rizkyna</b></sub><br />
      <i>Feature Developer (Admin Side)</i><br />
      <sup>⚙️ Builder dashboard Admin & sistem konfirmasi</sup>
    </td>
    <td align="center" width="25%">
      <img src="https://via.placeholder.com/100" width="100px;" alt="Dedek Saleha"/><br />
      <sub><b>Dedek Saleha</b></sub><br />
      <i>Feature Developer (Customer Side)</i><br />
      <sup>🛒 Kreator flow belanja & integrasi Assets</sup>
    </td>
  </tr>
</table>

<div align="center">

**Status:** 🟢 Aktif | **Role Distribution:** Seimbang | **Coffee Consumed:** ☕☕☕☕ (banyak)

</div>

---

## ✨ Fitur Unggulan

### 🛍️ **Customer Portal**
```
┌─────────────────────────────────────┐
│  💳 Shopping Experience             │
├─────────────────────────────────────┤
│  ✓ Katalog Visual dengan Card      │
│  ✓ Keranjang Real-time             │
│  ✓ Multi-Payment (QRIS/Bank/COD)   │
│  ✓ Invoice Digital                 │
│  ✓ Order History Tracking          │
└─────────────────────────────────────┘
```

### 👔 **Admin Dashboard**
```
┌─────────────────────────────────────┐
│  📊 Management Panel                │
├─────────────────────────────────────┤
│  ✓ Statistik Real-time             │
│  ✓ CRUD Produk Lengkap             │
│  ✓ Order Verification              │
│  ✓ Inventory Control               │
│  ✓ Persistent Data Storage         │
└─────────────────────────────────────┘
```

---

## 🧬 Konsep OOP yang Diterapkan

Ini bukan cuma kode yang jalan—ini penerapan **SOLID principles** dan konsep PBO yang beneran:

| Konsep | Implementasi | File Terkait |
|--------|--------------|--------------|
| **Abstraction** | Abstract class `Pembayaran` & `Driver` | `model/Pembayaran.java` |
| **Polymorphism** | Berbagai metode `prosesPembayaran()` | `QRIS.java`, `Bank.java`, `COD.java` |
| **Inheritance** | `Admin` & `Customer` extends `Akun` | `model/Akun.java` |
| **Encapsulation** | Private attributes + Getter/Setter | Semua class di `model/` |
| **Collections** | `ArrayList` untuk manajemen dinamis | `Main.java` |
| **File I/O** | Persistent storage `.txt` | `database_*.txt` |

---

## 📂 Project Structure

```
NuansaAroma/
│
├── 📁 assets/                    # Image assets (M001.jpg - M020.jpg)
│   └── 🖼️  Product images
│
├── 📁 src/
│   └── 📁 nuansaaroma/
│       ├── 🧠 Main.java          # Application brain (loader & controller)
│       ├── 📁 model/             # Business logic (Barang, Akun, Transaksi)
│       ├── 📁 view/              # GUI components (Frames, Panels, Theme)
│       └── 📁 driver/            # Legacy CLI (backup logic)
│
├── 💾 database_akun.txt          # User credentials storage
├── 💾 database_transaksi.txt     # Transaction history
└── 📄 README.md                  # You are here!
```

---

## 🚀 Quick Start

### Prerequisites
```bash
☕ Java JDK 8+ 
🖥️  Terminal/CMD
📁 Project folder
```

### Installation

**1️⃣ Clone Repository**
```bash
git clone https://github.com/Nixzouxu/NuansaAroma.git
cd NuansaAroma
```

**2️⃣ Compile Source**
```bash
cd src
javac nuansaaroma/Main.java nuansaaroma/model/*.java nuansaaroma/driver/*.java nuansaaroma/view/*.java
```

**3️⃣ Run Application**
```bash
java nuansaaroma.view.StartGui
```

### 🔐 Demo Accounts

| Role | Username | Password |
|------|----------|----------|
| 👔 Admin | `admin` | `admin123` |
| 🛍️ Customer | `user` | `user123` |

*Atau buat akun baru via menu Register!*

---

## 📸 Preview

<div align="center">

### Login Screen
```
╔════════════════════════════════════╗
║     🌸 NUANSA AROMA 🌸           ║
║                                    ║
║   [ Username: __________ ]         ║
║   [ Password: ********** ]         ║
║                                    ║
║         [ LOGIN ]  [ REGISTER ]    ║
╚════════════════════════════════════╝
```

### Customer Dashboard
```
╔══════════════════════════════════════════╗
║  🛍️  KATALOG PARFUM                     ║
╠══════════════════════════════════════════╣
║  ┌──────┐  ┌──────┐  ┌──────┐           ║
║  │ 🧴  │  │ 🧴  │  │ 🧴  │           ║
║  │ M001 │  │ M002 │  │ M003 │           ║
║  │ 150K │  │ 200K │  │ 175K │           ║
║  └──────┘  └──────┘  └──────┘           ║
║                                          ║
║  🛒 Keranjang: 2 items | Total: 350K    ║
╚══════════════════════════════════════════╝
```

</div>

---

## 🛠️ Tech Stack

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=flat-square)
![OOP](https://img.shields.io/badge/OOP-Principles-success?style=flat-square)
![File I/O](https://img.shields.io/badge/Storage-File%20I%2FO-orange?style=flat-square)

</div>

---

## 📝 Development Notes

### What We Learned
- 🎯 Menerapkan konsep OOP di project real-world
- 🎨 Merancang GUI yang user-friendly dengan Swing
- 💾 Implementasi persistent storage tanpa database engine
- 🤝 Kolaborasi tim dalam pengembangan aplikasi

### Challenges Faced
- Sinkronisasi data antara view dan model
- Manajemen state untuk keranjang belanja
- Handling file I/O yang robust

### Future Improvements
- [ ] Migrasi ke database SQL
- [ ] Implementasi search & filter produk
- [ ] Payment gateway integration
- [ ] Dark mode theme

---

## 📄 License & Credits

<div align="center">

**📚 Academic Project** - Praktikum Pemrograman Berorientasi Objek 2025

Made with 💜 by **Tim 12**

*"Kode yang baik bukan cuma yang berjalan, tapi yang dibuat dengan hati."*

---

⭐ Jika project ini membantu pembelajaran kalian, jangan lupa kasih star ya!

[⬆ Back to Top](#-nuansaaroma)

</div>
