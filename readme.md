# 🧠 Graph-Based Mentor–Mentee Matching & Scheduling App

A distributed microservices-based backend system for connecting mentees with mentors based on skills, availability, and industry preferences. Built using **Spring Boot**, **Neo4j (Graph DB)**, **Redis**, and **Eureka**, the platform facilitates intelligent matchmaking, OTP-based user authentication, meeting scheduling, and automated lifecycle management of mentorship sessions.

---

## 🚀 Features

### ✅ User Authentication
- OTP-based login and registration using **Redis**.
- Role-based access control (`MENTOR`, `MENTEE`).

### 🤝 Mentor-Mentee Matching
- Dynamic matching based on **skills**, **industry**, and **experience**.
- Implemented using **Spring Data Neo4j** with relationship-based queries.

### 🗓️ Meeting Scheduling
- Mentees and mentors can view overlapping available slots.
- Meeting scheduling and rescheduling support.
- Meetings stored in Neo4j using `SCHEDULED_BY` and `HOSTING` relationships.

### 🔁 Meeting Lifecycle Automation
- A **cron job** updates meeting status from `SCHEDULED` to `COMPLETED` once the time has elapsed.

### ❌ Meeting Cancellation
- Mentees can cancel meetings; logs stored in Neo4j.

### 📊 Logging & Monitoring
- Uses **Spring AOP** to log complete events for better traceability.

### 📦 Microservices & Discovery
- Built using a microservices architecture with services for:
  - **User Authentication**
  - **Mentorship Matching**
- Registered using **Eureka** Discovery Server.

---

## 🛠 Tech Stack

| Layer              | Technology                                     |
|--------------------|------------------------------------------------|
| Language           | Java 17                                        |
| Framework          | Spring Boot 3                                  |	
| Database           | Neo4j (Graph DB)                               |
| Auth Cache         | Redis                                          |
| API Discovery      | Eureka (Netflix OSS)                           |
| Scheduling         | Spring Scheduler                               |


---

## 📁 Project Structure

mentorship-platform-backend/

├── user-service/
│ └── Handles OTP login, role-based registration

├── mentorship-matching-service/
│ └── Mentor-Mentee graph matching, meeting scheduling

├── api-gateway-service/
│ └── Routes external API calls to microservices

├── eureka-server/
│ └── Eureka server for service registry

## 🔐 Redis OTP Flow

1. User requests OTP via email.
2. OTP stored in Redis with 60s TTL. The OTP is validated against Redis using a key pattern: otp:<email>.
3. On OTP submit, Redis validates the code, verifies the user and expires the entry.

---

## 🔄 Cron Job (Auto-complete meetings)

- Runs every minute to check for meetings whose time has passed.
- Updates their status from `SCHEDULED` ➝ `COMPLETED`.


## 🧠 Mentor–Mentee Matchmaking (Graph-Based)

The system uses **Spring Data Neo4j** to intelligently connect mentees with the most relevant mentors based on their **skills** and **industry interests**.

### 🧩 How It Works

- Each user is labeled as either `:Mentor` or `:Mentee` in the Neo4j graph.
- Skills and industries are modeled as separate nodes:
  - `(:Skill {name: "Java"})`
  - `(:Industry {name: "Software"})`
- Relationships are created as:
  - `(:User)-[:HAS_SKILL]->(:Skill)`
  - `(:User)-[:INTERESTED_IN]->(:Industry)`

### ⚙️ Matching Logic

When a mentee signs up or requests mentorship:

- A custom Cypher query is executed to find all mentors who:
  - Share at least one skill
  - Share at least one industry of interest

### 📅 Meeting Scheduling

Once a mentor is matched:
- Overlapping time slots between mentor and mentee are calculated.
- Users can schedule or reschedule meetings via REST APIs.

### 📂 Directory Structure 

├── mentorship-matching-service

│   ├── controller/

│   ├── service/

│   ├── repo/

│   ├── dto/

│   ├── model/

│   ├── config/

│   └── aspect/





