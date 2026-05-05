# Java Student Management System

一个基于 **Java Swing** 开发的学生成绩管理系统，支持用户登录、学生成绩管理、CGPA/资格判断、Course Recovery Plan、学术报告生成以及模拟邮件通知功能。项目同时提供一个浏览器版 Web Demo，用于在线展示核心成绩计算逻辑。

> This is a Java Swing desktop application for student academic result management, with an additional web demo for showcasing the core grade calculation workflow online.

---

## Live Demo

- Web Demo: https://chrisbetheking.github.io/java-student-management-system/java-grade-demo.html
- GitHub Repository: https://github.com/Chrisbetheking/java-student-management-system

---

## Project Overview

This project was built as a Java-based academic management system.  
The main desktop version is developed with **Java Swing**, while the web demo presents the core grade calculation workflow in a browser-friendly format.

The system focuses on:

- User login and role-based access
- Student academic result handling
- CGPA and eligibility checking
- Course recovery plan management
- Academic report generation
- CSV-based local data storage
- Simulated email notification
- Browser-based grade calculation demo

---

## Features

### 1. User Login System

The system includes a login module with account validation.

Main features:

- Username and password login
- SHA-256 password hashing
- Default admin account generation
- Account activation and deactivation
- Login/logout activity logging

Default admin account for local testing:

```text
Username: admin
Password: admin123
