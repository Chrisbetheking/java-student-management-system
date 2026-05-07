# Java Student Grade Management System  
# Java 学生成绩管理系统

A Java desktop grade management system with a public web demo for the core grade calculation logic.  
一个基于 Java 的学生成绩管理系统，并额外提供 Web Demo 用于展示核心成绩计算逻辑。

Live Demo / 在线演示：  
https://chrisbetheking.github.io/java-student-management-system/java-grade-demo.html

GitHub Repository / 源码仓库：  
https://github.com/Chrisbetheking/java-student-management-system

---

## Project Overview / 项目简介

This project is a Java-based student grade management system. It focuses on student information handling, grade calculation, pass / fail judgement, resit filtering, and basic CSV-based data management.

本项目是一个基于 Java 的学生成绩管理系统，主要覆盖学生信息管理、成绩计算、通过 / 未通过判断、补考筛选以及基础 CSV 数据处理等功能。

The web demo is included to make the core grade calculation logic easier to review. Reviewers can test the calculation rules directly in the browser without running the Java desktop application locally.

项目额外提供 Web Demo，方便 HR 或面试官无需本地运行 Java 桌面程序，也能直接在浏览器中测试成绩计算、结果判断和记录导出逻辑。

---

## Project Positioning / 项目定位

The Java desktop system is the main project.  
Java 桌面端系统是本项目主体。

The web demo is a public presentation layer for the core grade calculation logic.  
Web Demo 是为了公开展示核心成绩计算逻辑而整理出的演示入口。

It demonstrates:

- coursework and final exam score input
- weighted total score calculation
- grade classification
- pass / fail status judgement
- resit or recovery suggestion
- local calculation records
- CSV export

主要展示内容包括：

- 平时成绩与期末成绩输入
- 加权总评成绩计算
- 等级划分
- 通过 / 未通过状态判断
- 补考或恢复建议
- 本地计算记录
- CSV 导出

---

## Features / 功能

### Java System / Java 系统部分

- Student information management  
  学生信息管理

- Course and grade data handling  
  课程与成绩数据处理

- Grade calculation  
  成绩计算

- Pass / fail judgement  
  通过 / 未通过判断

- Resit list filtering  
  补考名单筛选

- CSV-based data processing  
  基于 CSV 的数据处理

- Basic desktop system structure using Java OOP  
  基于 Java 面向对象思想的基础桌面系统结构

### Web Demo / 网页演示部分

- Four quick sample cases  
  四个快速测试样例：

  - Pass Sample / 通过样例
  - Borderline Sample / 边界样例
  - Failed Sample / 未通过样例
  - Excellent Sample / 优秀样例

- Structured result cards  
  结构化结果卡片

- Recovery plan suggestion  
  恢复 / 补考建议

- LocalStorage calculation records  
  基于 LocalStorage 的本地计算记录

- Export records as CSV  
  导出记录为 CSV 文件

- Clear local records  
  清空本地记录

- Responsive layout for desktop and mobile  
  桌面端与移动端响应式布局

---

## Tech Stack / 技术栈

- Java
- Java Swing
- Object-Oriented Programming / 面向对象编程
- CSV data handling / CSV 数据处理
- HTML
- CSS
- JavaScript
- LocalStorage
- GitHub Pages

---

## Web Demo Logic / Web Demo 计算逻辑

The web demo uses a simple weighted score rule:  
Web Demo 使用简化的加权成绩规则：

```text
Final Total Score = Coursework Score × 50% + Final Exam Score × 50%
总评成绩 = 平时成绩 × 50% + 期末成绩 × 50%
```

Grade classification / 等级划分：

```text
A   : 85 and above / 85 分及以上
B+  : 75 - 84
B   : 65 - 74
C+  : 55 - 64
C   : 50 - 54
D   : 40 - 49
F   : below 40 / 40 分以下
```

Status judgement / 状态判断：

```text
Pass / 通过:
Total score >= 50 and final exam score >= 40
总评成绩 >= 50 且期末成绩 >= 40

Pass with Attendance Warning / 通过但出勤警告:
Total score >= 50, final exam score >= 40, but attendance < 70
总评成绩 >= 50，期末成绩 >= 40，但出勤率 < 70

Borderline / Resit Recommended / 边界情况，建议补考准备:
Total score >= 45 or final exam score >= 35, but does not fully meet pass rule
总评成绩 >= 45 或期末成绩 >= 35，但未完全满足通过条件

Fail / Resit Required / 未通过，需要补考:
Result does not meet the above conditions
不满足以上条件
```

The rule is designed for public demo purposes and may not represent the full academic policy of any institution.  
以上规则用于公开 Demo 展示，可能不代表任何学校的完整成绩政策。

---

## File Structure / 文件结构

```text
java-student-management-system/
├── java-grade-demo.html
├── web-style.css
├── web-script.js
├── README.md
└── Java source files / Java 项目源码文件
```

---

## Why the Web Demo Exists / 为什么加入 Web Demo

The original project is a Java desktop system. However, reviewers may not always have time to clone and run the Java application locally.

原项目主体是 Java 桌面端系统，但 HR 或面试官不一定会下载并本地运行 Java 程序。

The web demo solves this by showing the most important logic through a browser:

- score input
- grade calculation
- pass / fail judgement
- recovery suggestion
- record export

因此 Web Demo 用浏览器展示最核心的逻辑：

- 成绩输入
- 总评计算
- 通过 / 未通过判断
- 补考或恢复建议
- 记录导出

This makes the project easier to review during internship applications and portfolio evaluation.  
这样可以降低项目查看成本，也方便在实习申请和作品集展示中快速说明项目功能。

---

## Notes / 说明

- The web demo does not replace the Java desktop system.  
  Web Demo 不替代 Java 桌面端系统，只用于展示核心计算逻辑。

- Local records are stored in the browser using LocalStorage.  
  本地记录通过浏览器 LocalStorage 保存。

- CSV export is generated from local calculation records.  
  CSV 导出基于本地计算记录生成。

- The calculation rules are simplified for public demonstration.  
  成绩计算规则为公开演示版本，进行了简化处理。

- No backend or database is required for the web demo.  
  Web Demo 不需要后端或数据库支持。

---

## Future Improvements / 后续可扩展方向

Possible improvements include:  
后续可以继续扩展：

- Connect the web demo with a backend API  
  将 Web Demo 接入后端 API

- Add database storage for student records  
  为学生记录增加数据库存储

- Add authentication and user roles  
  增加登录认证和用户角色

- Improve Java desktop UI structure  
  优化 Java 桌面端界面结构

- Add unit tests for calculation logic  
  为成绩计算逻辑增加单元测试

- Support import and export of larger CSV files  
  支持更大规模的 CSV 导入与导出

- Add more detailed academic recovery rules  
  增加更细化的补考 / 恢复规则
