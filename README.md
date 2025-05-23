# 🚭 Smoking Cessation Support Platform

A full-stack web application that supports users on their journey to quit smoking. The platform provides personalized tracking, motivational tools, expert coaching, and community support to help users succeed.

---

## 📌 Table of Contents

- [Overview](#overview)
- [Core Features](#core-features)
- [Tech Stack](#tech-stack)
- [Database Design](#database-design)
- [Setup Instructions](#setup-instructions)
- [Screenshots](#screenshots)
- [Credits](#credits)

---

## 🧠 Overview

**Smoking Cessation Support Platform** is a health-tech web application designed to assist users who are trying to quit smoking. The platform offers tools for self-assessment, personalized quit plans, real-time coaching, and motivational insights.

Target users:
- 🚶 Guests (unregistered)
- 👤 Members (registered users)
- 🧑‍🏫 Coaches (support personnel)
- 🛠️ Admins (system operators)

---

## 💎 Core Features

### 👤 Guest
- View homepage with educational content
- Use addiction level and cost calculators (temporary, no login required)
- Read public blog and forum posts
- Register for a free/premium account (supporting MoMo, VNPay...)

### 🙋 Member
- Manage personal profile & notification preferences
- Create & track personalized quit-smoking plans
- Daily tracking (health, cravings, savings...)
- Receive motivational reminders (push/SMS/email)
- Interact in blog & forum (like/comment/share/anonymous)
- 1-on-1 chat and video calls with Coaches
- Rate and review coaching sessions
- Dashboard to view progress and achievements
- AI-based personalized suggestions and planning

### 🧑‍🏫 Coach
- View list of assigned members
- Conduct chat and video counseling sessions
- View member progress reports and feedback
- Share expert insights (Coach’s Tips in blog)
- Flag inappropriate forum content

### 🛠️ Admin
- Full account and RBAC management
- CMS for blog, forum, and educational content
- Manage packages, coupons, and promotions
- Define SMS/email message schedules
- View system-wide dashboards & export reports
- Monitor audit logs, backup/recovery, WAF settings
- Assign members to coaches

### 🧠 System
- Auto-assign achievement badges (e.g. 7-day smoke-free)
- Trigger push notifications and social sharing

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java + Spring Boot |
| Frontend | ReactJS / Thymeleaf (TBD) |
| Database | SQL Server |
| ORM | Spring Data JPA |
| Messaging | WebSocket + WebRTC |
| Authentication | Spring Security + JWT |
| Payment | VNPay, MoMo (integration planned) |
| AI Recommendation | (Pluggable AI logic using usage data) |
| DevOps | Docker, GitHub Actions (optional) |

---

## 🗂️ Database Design

The application follows a normalized relational schema with proper foreign keys and relationships.

> See full ERD in [`/database/erd_model.puml`](./database/erd_model.puml)  
> SQL init script: [`/database/init_schema.sql`](./database/init_schema.sql)

---
