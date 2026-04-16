# 🏋️ Gym Management System (Full-Stack)

A professional transition of a legacy Console Java application into a modern **Full-Stack RESTful Application**.

## 🏗️ Architecture & Evolution
This project demonstrates the modernization of a monolithic script into a 3-tier architecture:

1.  **Presentation Layer:** Responsive SPA built with **HTML5, Bootstrap 5, and JavaScript (Fetch API)**.
2.  **Logic Layer:** **Spring Boot 3** REST API handling business logic and member lifecycle.
3.  **Data Layer:** **MySQL** database managed via **Spring Data JPA/Hibernate**.

### How it compares to the original Java code:
| Original Concept | New Implementation |
| :--- | :--- |
| `Scanner` Input | REST API Endpoints (@RestController) |
| `members.txt` | MySQL Relational Database |
| Custom `LinkedList` | Spring Data JPA Repositories |
| `switch-case` Menu | Tabbed Web Dashboard |

## 🛠️ Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Data JPA, MySQL Connector.
- **Frontend:** JavaScript (ES6), Bootstrap 5, CSS3.
- **Database:** MySQL 8.0.

## 🚀 Getting Started
1. Configure your MySQL credentials in `src/main/resources/application.properties`.
2. Run `GymappApplication.java` from your IDE.
3. Open `http://localhost:8080/index.html` in your browser.

## 📡 API Endpoints Summary
- `POST /api/gym/members/add` - Register new members with age validation.
- `PUT /api/gym/members/renew/{id}` - Extend membership duration.
- `PUT /api/gym/assign/{mId}/{tId}` - Link members to specific trainers.