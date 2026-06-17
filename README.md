# 🩸 Blood Bank Management System

A Spring Boot REST API for managing blood bank operations.

## Tech Stack
- Java 17
- Spring Boot 3.2.0
- H2 In-Memory Database
- Spring Data JPA
- Maven

## Quick Start

### Prerequisites
- JDK 17+
- Maven 3.6+

### Run Locally
```bash
mvn spring-boot:run
```
App starts at: `http://localhost:8080`

### H2 Database Console
URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:bloodbankdb`
- Username: `sa`
- Password: *(leave blank)*

---

## API Endpoints

### 🧑 Donors
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/donors/register` | Register a new donor |
| GET | `/api/donors` | Get all donors |
| GET | `/api/donors/{id}` | Get donor by ID |
| GET | `/api/donors/{id}/donation-history` | Donation history |
| POST | `/api/donors/{id}/donate?units=1&location=Dhaka` | Record a donation |
| DELETE | `/api/donors/{id}` | Delete donor |

**Register Donor Body:**
```json
{
  "name": "Rahim Uddin",
  "email": "rahim@example.com",
  "phone": "01700000000",
  "age": 25,
  "weightKg": 65.0,
  "bloodGroup": "A+"
}
```

### 🏥 Requesters
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/requesters/register` | Register a requester |

**Register Requester Body:**
```json
{
  "name": "Dr. Karim",
  "email": "karim@hospital.com",
  "phone": "01800000000",
  "bloodGroup": "O+",
  "hospitalName": "Dhaka Medical"
}
```

### 🩸 Blood Requests
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/blood-requests/submit` | Submit a blood request |
| POST | `/api/blood-requests/{id}/approve` | Approve a request |
| POST | `/api/blood-requests/{id}/reject` | Reject a request |
| GET | `/api/blood-requests` | Get all requests |
| GET | `/api/blood-requests/pending` | Get pending requests |
| GET | `/api/blood-requests/{id}` | Get request by ID |

**Submit Request Body:**
```json
{
  "bloodGroup": "A+",
  "unitsRequested": 2,
  "patientName": "Fatema Begum",
  "patientAge": "45",
  "hospitalName": "Square Hospital",
  "doctorName": "Dr. Ahmed",
  "reason": "Surgery"
}
```

### 📦 Blood Inventory (Admin)
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/admin/inventory` | View all stock |
| PUT | `/api/admin/inventory/{bloodGroup}/add?units=10` | Add stock |
| PUT | `/api/admin/inventory/{bloodGroup}/reduce?units=5` | Reduce stock |

### 🚨 Emergency Contacts
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/emergency-contacts/add` | Add contact |
| GET | `/api/emergency-contacts` | Get all contacts |
| GET | `/api/emergency-contacts/{id}` | Get by ID |
| DELETE | `/api/emergency-contacts/{id}` | Delete contact |

---

## Deploy to Railway

1. Push code to GitHub
2. Go to [railway.app](https://railway.app)
3. New Project → Deploy from GitHub repo
4. Railway auto-detects Spring Boot — no config needed with H2
5. Done ✅
