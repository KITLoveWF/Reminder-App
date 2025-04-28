## 👨‍💻 Nhóm thực hiện

- Mai Đức Kiên - MSSV: 22110046
- Lê Văn Việt Hoàng - MSSV: 22110027


# 📅 Reminder App

**Reminder App** là một ứng dụng Android đơn giản được lập trình bằng **Kotlin**, sử dụng **SQLite** để lưu trữ dữ liệu và áp dụng mô hình **MVC (Model-View-Controller)** để thiết kế phần mềm.

## 🚀 Tính năng chính

- Thêm, chỉnh sửa và xoá các ghi chú nhắc nhở.
- Lưu trữ dữ liệu ghi chú trên thiết bị bằng SQLite.
- Giao diện đơn giản, dễ sử dụng.

## 🛠 Công nghệ sử dụng

- **Ngôn ngữ lập trình:** Kotlin
- **Cơ sở dữ liệu:** SQLite (qua lớp SQLiteOpenHelper)
- **Mô hình kiến trúc:** MVC (Model - View - Controller)
- **IDE:** Android Studio

## 🧩 Cấu trúc thư mục (theo MVC)

```plaintext
ReminderApp/
├── model/         # Quản lý dữ liệu, database (SQLite)
│   └── Reminder.kt
│   └── ReminderDatabaseHelper.kt
├── view/          # Giao diện người dùng (Activity, Layout XML)
│   └── MainActivity.kt
│   └── activity_main.xml
├── controller/    # Xử lý logic giữa Model và View
│   └── ReminderController.kt
├── utils/         # Các tiện ích (tuỳ chọn nếu cần)
├── README.md
└── ...
```

## 🏗 Thiết kế theo mô hình MVC

- **Model**: 
  - Định nghĩa đối tượng Reminder.
  - Xử lý các thao tác CRUD (Create, Read, Update, Delete) với SQLite.

- **View**:
  - Các màn hình giao diện người dùng (Activity, Fragment, XML Layout).
  - Hiển thị dữ liệu và nhận tương tác từ người dùng.

- **Controller**:
  - Xử lý logic nghiệp vụ.
  - Điều phối dữ liệu giữa Model và View.

## ⚙️ Hướng dẫn cài đặt

1. Clone project:
    ```bash
    git clone https://github.com/yourusername/Reminder-App.git
    ```
2. Mở bằng Android Studio.
3. Sync Gradle và chạy project trên thiết bị Android hoặc emulator.

## 📸 Một số hình ảnh (tuỳ thêm sau)

*(Thêm ảnh screenshot nếu bạn muốn nhé)*


