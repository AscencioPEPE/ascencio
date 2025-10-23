# Talavera Subscriptions – Java/Spring Boot SaaS Prototype

A modular full-stack SaaS application developed for the **Talavera Solutions Full-Stack Developer Challenge**.  
The goal is to build a **localized subscription-based SaaS** (English/Spanish) using **Spring Boot**, **PostgreSQL**, and **React**, following a strict **BDD/TDD** workflow and clean architectural principles.

---

## 🧩 Overview

This project demonstrates a *vertically sliced* backend and frontend structure, featuring:

- 🔐 **Authentication** (JWT + bcrypt)  
- 🗂 **Projects module** (user-scoped CRUD with quotas)  
- 💳 **Plans & Mocked Subscriptions** (Free / Pro)  
- 🌍 **Internationalization (EN/ES)** with message bundles  
- 🧪 **BDD/TDD-first** approach using Cucumber, JUnit 5, and Mockito  
- 🐳 **Dockerized environment** (API + DB)  
- 🧱 **Layered Architecture:** Controller → Service → Repository

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Spring Boot 3.5.7 (Java 17), Spring Security, Data JPA |
| **Database** | PostgreSQL 15 |
| **Auth** | JWT (io.jsonwebtoken), bcrypt |
| **Frontend** | React 18 + TypeScript|
| **Testing** | JUnit 5, Cucumber, Mockito |

---

## 🧠 Core Features (Tier Breakdown)

### **Tier 0 — Boot & Test Harness**
- Docker Compose setup for API + DB.
- Maven project with test, lint, and format scripts.
- Initial failing `auth.feature` for BDD-first workflow.

### **Tier 1 — Auth + Projects**
- User signup/signin with JWT.
- CRUD for Projects (scoped by user).
- Free Plan: Max 3 projects.
- Localized messages (`messages_en.properties`, `messages_es.properties`).

### **Tier 2 — Plans & Mocked Subscriptions**
- `/api/plans`: Free vs Pro (localized fields).  
- `/api/subscriptions`: start, get current.  
- Mocked Stripe adapter to generate invoices.  
- Pro Plan increases quota to 10 projects.

### **Tier 3 — Cancel & UI Polish**
- Cancel subscription endpoint.
- Optimistic UI updates.
- Basic accessibility and error state review.

### 1️⃣ Prerequisites
- Java 17+
- Maven 3.9+
- Docker & Docker Compose
- Node.js 20+ (for frontend)

