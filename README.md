# 🛒 E-Commerce Website
A role-based E-Commerce platform with distinct functionality for **guests**, **customers**, **multiple staff members**, and a **single manager**. This application supports product browsing, shopping, reviews, user management, and an admin dashboard for internal operations. The project follows the **MVC architecture (Model-View-Controller)**, utilizes **OOP (Object-Oriented Programming)**, and implements **JPA (Java Persistence API)**. The user interface is built using *Bootstrap*.

---

## 👥 User Roles & Permissions

### 🔹 Guest
- Browse and search products
- Register as a customer

### 🔹 Customer
- Add products to cart and place orders
- Submit product reviews
- Customize personal profile

### 🔹 Staff
- Manage product listings (create, update, delete)
- View customer information
- Reply to customer reviews
- Customize own profile

### 🔹 Manager
- Access the manager dashboard
- All permissions granted to staff
- Manage staff accounts (create, edit, delete)
- Manage customer accounts (ban, delete, edit)
- Customize own profile

---

## 🧭 Role-Based Navigation

- **Guest** → Public pages (e.g., home, product listings, register)
- **Customer** → Redirected to the **User Dashboard** after login
- **Staff/Manager** → Redirected to the **Admin Dashboard** after login

---

## 🛠️ Features Overview

| Feature                | Guest | Customer | Staff | Manager |
|------------------------|:-----:|:--------:|:-----:|:-------:|
| Browse/Search Products | ✅    | ✅       | ✅    | ✅      |
| Register/Login         | ✅    | ✅       | ✅    | ✅      |
| Add to Cart            | ❌    | ✅       | ❌    | ❌      |
| Place Orders           | ❌    | ✅       | ❌    | ❌      |
| Manage Orders          | ❌    | ❌       | ✅    | ✅      |
| Submit Reviews         | ❌    | ✅       | ❌    | ❌      |
| Reply to Reviews       | ❌    | ❌       | ✅    | ✅      |
| Manage Products        | ❌    | ❌       | ✅    | ✅      |
| View Customers         | ❌    | ❌       | ✅    | ✅      |
| Manage Staff           | ❌    | ❌       | ❌    | ✅      |
| Manage Customers       | ❌    | ❌       | ❌    | ✅      |
| Customize Profile      | ❌    | ✅       | ✅    | ✅      |

---

## 📁 Project Screenshots

![JavaEcommerceUser](https://github.com/user-attachments/assets/a905bddd-9707-4c67-bc17-0da22b918a0c)
![JavaEcommerceAdmin](https://github.com/user-attachments/assets/30841f2f-ae79-4d52-af85-ce9107fb66e3)

