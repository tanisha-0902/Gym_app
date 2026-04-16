# 🏋️ Elite Gym Management System

A robust, full-stack web application designed to streamline gym operations. This project features a secure multi-role portal that allows administrators, trainers, and members to interact with a centralized management system through a dynamic, responsive interface.

---

## 🚀 Key Features

* **Multi-Role Authentication:** Dynamic dashboard rendering based on user roles (Admin, Trainer, Member).
* **Member Lifecycle Management:** Full CRUD operations for registering, updating, and deleting memberships.
* **Trainer Assignment:** Logic-based linking of trainers to specific members for personalized tracking.
* **Subscription Renewal:** Automated membership end-date calculation based on selected plans (Monthly, Quarterly, Yearly).
* **Secure API Communication:** Integration with Spring Security using Basic Authentication and custom CORS configurations.
* **Real-time Notifications:** Smooth user experience using Bootstrap Toasts for success and error handling.

---

## 🛠️ Tech Stack

### **Backend**
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security (Basic Auth)
* **Persistence:** Spring Data JPA / Hibernate
* **Database:** H2 (In-Memory)
* **Build Tool:** Maven

### **Frontend**
* **Logic:** Vanilla JavaScript (ES6+)
* **Styling:** Bootstrap 5.3 (CSS & Components)
* **API Client:** Fetch API with a custom Secure Wrapper for Authorization headers.

---

## 🏗️ Architecture & Security

The project follows a decoupled architecture where the frontend acts as a Single Page Application (SPA) communicating with a Java REST API. 

**Role-Based Access Control (RBAC):**
* **Admin:** Full access to member directories, trainer onboarding, and system-wide data.
* **Trainer:** Focused view for member assignments and trainer-specific login verification.
* **Member:** Personal "Self-Service" portal to view membership status and handle renewals via unique Member IDs.

---

## ⚙️ Installation & Setup

### 1. Backend Setup
1.  Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, or VS Code).
2.  Ensure Maven dependencies are loaded from `pom.xml`.
3.  Run the `GymAppApplication.java` file.
4.  The server will start at `http://localhost:8080`.

### 2. Frontend Setup
1.  Navigate to the project root directory.
2.  Open `index.html` in any modern web browser.
3.  Ensure your backend is running to allow successful API calls.

---

## 🧪 Testing the Portal

| Role | Username / ID | Password |
| :--- | :--- | :--- |
| **Admin** | `admin` | `admin123` |
| **Trainer** | *Registered Trainer ID* | *Trainer Password* |
| **Member** | *System Generated ID* | *N/A (ID Verification)* |

---

## 📝 Future Roadmap
- [ ] Implement JWT (JSON Web Tokens) for enhanced session security.
- [ ] Add a "Workout Plan" module for trainers to assign routines to members.
- [ ] Integrate a real database (MySQL/PostgreSQL) for persistent data storage.
- [ ] Add a dashboard chart for membership growth statistics.

---
