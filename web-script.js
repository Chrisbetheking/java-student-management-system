const STORAGE_KEY = "javaGradeDemoRecords";
const LANG_KEY = "javaGradeLang";

let currentLang = localStorage.getItem(LANG_KEY) || "zh";
let latestResultText = "";
let latestResult = null;

const i18n = {
  zh: {
    brand: "Java 成绩系统",
    navCalculator: "成绩计算",
    navRecords: "记录",
    navAbout: "说明",
    tagCalculation: "成绩计算",

    heroTitle: "Java 学生成绩管理系统",
    heroSubtitle: "用于展示 Java 桌面系统核心成绩计算逻辑的 Web Demo。",
    heroText:
      "本页面从 Java 学生成绩管理系统中提取核心成绩计算逻辑，用于公开展示成绩计算、通过 / 未通过判断、补考建议、本地记录和 CSV 导出功能。HR 或面试官无需本地运行 Java 桌面程序，也可以直接在浏览器中测试核心逻辑。",

    tryCalculator: "测试计算器",
    loadPass: "加载通过样例",
    sourceCode: "源码仓库",

    demoFocus: "演示重点",
    calculation: "成绩计算",
    calculationDesc: "平时成绩 + 期末成绩",
    status: "状态判断",
    statusDesc: "通过 / 未通过 / 需要补考",
    records: "本地记录",
    recordsDescShort: "LocalStorage + CSV 导出",
    projectType: "项目类型",
    projectTypeDesc: "Java 系统 + Web Demo",

    calculatorTag: "Calculator",
    calculatorTitle: "成绩计算 Demo",
    calculatorDesc:
      "可以使用样例按钮快速填充数据，也可以输入自定义成绩，测试总评分数、等级、通过状态和补考建议。",

    inputTitle: "输入信息",
    inputDesc: "学生和课程信息",
    reset: "重置",

    samplePass: "通过样例",
    sampleBorderline: "边界样例",
    sampleFailed: "未通过样例",
    sampleExcellent: "优秀样例",

    studentName: "学生姓名",
    studentNamePlaceholder: "例如：Wang Hong",
    studentId: "学生 ID",
    studentIdPlaceholder: "例如：TP078888",
    courseName: "课程名称",
    courseNamePlaceholder: "例如：Programming in Java",
    courseworkScore: "平时成绩",
    finalExamScore: "期末成绩",
    courseworkWeight: "权重：50%",
    examWeight: "权重：50%",
    attendance: "出勤率",
    attendanceNote: "仅用于风险提醒",
    semester: "学期",
    notes: "备注",
    notesPlaceholder: "可选备注",
    calculateGrade: "计算成绩",

    resultTitle: "结果",
    resultDesc: "结构化计算结果",
    copy: "复制",
    noResult: "暂无结果",
    noResultDesc: "加载样例或输入成绩后，点击计算成绩。",

    metricCourseworkLabel: "平时成绩",
    metricExamLabel: "期末成绩",
    metricTotalLabel: "总评成绩",
    metricAttendanceLabel: "出勤率",

    studentSummaryTitle: "学生摘要",
    recoveryPlanTitle: "补考 / 恢复建议",
    systemNotesTitle: "系统说明",

    recordsTag: "Records",
    recordsTitle: "本地计算记录",
    recordsDesc:
      "计算记录会使用 localStorage 保存在浏览器中，可以清空，也可以导出为 CSV 文件。",
    exportCsv: "导出 CSV",
    clearRecords: "清空记录",

    tableTime: "时间",
    tableStudent: "学生",
    tableCourse: "课程",
    tableTotal: "总分",
    tableGrade: "等级",
    tableStatus: "状态",
    noRecords: "暂无记录。",

    projectNote: "Project Note",
    technicalFocus: "Technical Focus",
    aboutTitle1: "Java 桌面系统与 Web Demo",
    aboutText1:
      "原项目主体是 Java 学生成绩管理系统。本网页不是替代 Java 桌面程序，而是用于公开展示其中最核心的成绩计算逻辑，方便通过浏览器快速查看。",
    aboutTitle2: "技术重点",
    aboutText2:
      "Demo 重点展示加权成绩计算、等级划分、通过 / 未通过判断、补考建议、本地记录和 CSV 导出。这些内容对应 Java 系统中的学生成绩管理核心逻辑。",

    footer: "Java 学生成绩管理系统 · Web Demo",
    githubLink: "GitHub 仓库 →",

    passStatus: "通过",
    passAttendanceWarning: "通过 · 出勤提醒",
    borderlineStatus: "边界情况 · 建议准备补考",
    failStatus: "未通过 · 需要补考",

    toastSample: "样例已加载",
    toastReset: "表单已重置",
    toastCalculated: "成绩已计算",
    toastCopied: "结果已复制",
    toastNoResult: "暂无可复制结果",
    toastNoRecords: "暂无可导出记录",
    toastExported: "CSV 已导出",
    toastCleared: "记录已清空",
    toastInvalid: "成绩必须在 0 到 100 之间",

    csvFileName: "java-grade-records.csv",
  },

  en: {
    brand: "Java Grade System",
    navCalculator: "Calculator",
    navRecords: "Records",
    navAbout: "About",
    tagCalculation: "Grade Calculation",

    heroTitle: "Java Student Grade Management System",
    heroSubtitle: "Web Demo for core grade calculation logic from a Java desktop system.",
    heroText:
      "This page is a public web demo extracted from the grade calculation part of a Java Student Grade Management System. It is used to show the calculation rules, pass / fail judgement, resit suggestion, local records, and CSV export without requiring reviewers to run the Java desktop application.",

    tryCalculator: "Try Calculator",
    loadPass: "Load Pass Sample",
    sourceCode: "Source Code",

    demoFocus: "Demo Focus",
    calculation: "Calculation",
    calculationDesc: "Coursework + Final Exam",
    status: "Status",
    statusDesc: "Pass / Fail / Resit Required",
    records: "Records",
    recordsDescShort: "LocalStorage + CSV Export",
    projectType: "Project Type",
    projectTypeDesc: "Java System + Web Demo",

    calculatorTag: "Calculator",
    calculatorTitle: "Grade Calculation Demo",
    calculatorDesc:
      "Use sample buttons or enter custom scores to test the grade calculation, result status, and recovery suggestion logic.",

    inputTitle: "Input",
    inputDesc: "Student and course information",
    reset: "Reset",

    samplePass: "Load Pass Sample",
    sampleBorderline: "Load Borderline Sample",
    sampleFailed: "Load Failed Sample",
    sampleExcellent: "Load Excellent Sample",

    studentName: "Student Name",
    studentNamePlaceholder: "e.g. Wang Hong",
    studentId: "Student ID",
    studentIdPlaceholder: "e.g. TP078888",
    courseName: "Course Name",
    courseNamePlaceholder: "e.g. Programming in Java",
    courseworkScore: "Coursework Score",
    finalExamScore: "Final Exam Score",
    courseworkWeight: "Weight: 50%",
    examWeight: "Weight: 50%",
    attendance: "Attendance",
    attendanceNote: "Used for warning only",
    semester: "Semester",
    notes: "Notes",
    notesPlaceholder: "Optional notes for this record",
    calculateGrade: "Calculate Grade",

    resultTitle: "Result",
    resultDesc: "Structured calculation output",
    copy: "Copy",
    noResult: "No result yet",
    noResultDesc: "Load a sample or enter scores, then click Calculate Grade.",

    metricCourseworkLabel: "Coursework",
    metricExamLabel: "Final Exam",
    metricTotalLabel: "Total Score",
    metricAttendanceLabel: "Attendance",

    studentSummaryTitle: "Student Summary",
    recoveryPlanTitle: "Recovery Plan",
    systemNotesTitle: "System Notes",

    recordsTag: "Records",
    recordsTitle: "Local Calculation Records",
    recordsDesc:
      "Calculation records are stored in the browser using localStorage. They can be cleared or exported as CSV.",
    exportCsv: "Export Records CSV",
    clearRecords: "Clear Records",

    tableTime: "Time",
    tableStudent: "Student",
    tableCourse: "Course",
    tableTotal: "Total",
    tableGrade: "Grade",
    tableStatus: "Status",
    noRecords: "No records yet.",

    projectNote: "Project Note",
    technicalFocus: "Technical Focus",
    aboutTitle1: "Java desktop system with a public web demo",
    aboutText1:
      "The original project is a Java Student Grade Management System. This web page is not intended to replace the Java desktop program. It is used to make the core grade calculation logic easier to review through a browser-based demo.",
    aboutTitle2: "Calculation, status judgement, and CSV handling",
    aboutText2:
      "The demo focuses on weighted score calculation, grade classification, pass / fail status, resit suggestion, local records, and CSV export. These parts are aligned with the Java system’s core student grade management logic.",

    footer: "Java Student Grade Management System · Web Demo",
    githubLink: "GitHub Repository →",

    passStatus: "Pass",
    passAttendanceWarning: "Pass · Attendance Warning",
    borderlineStatus: "Borderline · Resit Recommended",
    failStatus: "Fail · Resit Required",

    toastSample: "Sample loaded",
    toastReset: "Form reset",
    toastCalculated: "Grade calculated",
    toastCopied: "Result copied",
    toastNoResult: "No result to copy",
    toastNoRecords: "No records to export",
    toastExported: "CSV exported",
    toastCleared: "Records cleared",
    toastInvalid: "Scores must be between 0 and 100",

    csvFileName: "java-grade-records.csv",
  },
};

const gradeForm = document.getElementById("gradeForm");
const resetFormBtn = document.getElementById("resetForm");
const copyResultBtn = document.getElementById("copyResult");
const exportCsvBtn = document.getElementById("exportCsv");
const clearRecordsBtn = document.getElementById("clearRecords");
const recordsBody = document.getElementById("recordsBody");
const toast = document.getElementById("toast");

const emptyResult = document.getElementById("emptyResult");
const resultContent = document.getElementById("resultContent");
const resultBanner = document.getElementById("resultBanner");
const resultStatus = document.getElementById("resultStatus");
const resultGrade = document.getElementById("resultGrade");

const metricCoursework = document.getElementById("metricCoursework");
const metricExam = document.getElementById("metricExam");
const metricTotal = document.getElementById("metricTotal");
const metricAttendance = document.getElementById("metricAttendance");

const studentSummary = document.getElementById("studentSummary");
const recoveryPlan = document.getElementById("recoveryPlan");
const systemNotes = document.getElementById("systemNotes");

const sampleData = {
  pass: {
    studentName: "Wang Hong",
    studentId: "TP078888",
    courseName: "Programming in Java",
    courseworkScore: 72,
    finalExamScore: 68,
    attendance: 88,
    semester: "Year 2 Semester 1",
    notes: {
      zh: "标准通过样例，平时成绩和期末成绩表现稳定。",
      en: "Standard pass case with stable coursework and exam performance.",
    },
  },

  borderline: {
    studentName: "Tan Wei",
    studentId: "TP079120",
    courseName: "Database Systems",
    courseworkScore: 55,
    finalExamScore: 43,
    attendance: 76,
    semester: "Year 2 Semester 1",
    notes: {
      zh: "边界样例，总评成绩接近最低通过范围。",
      en: "Borderline case. Total score is close to the minimum passing range.",
    },
  },

  failed: {
    studentName: "Alex Lim",
    studentId: "TP080021",
    courseName: "Data Structures",
    courseworkScore: 38,
    finalExamScore: 34,
    attendance: 62,
    semester: "Year 1 Semester 2",
    notes: {
      zh: "未通过样例，会触发补考和恢复建议。",
      en: "Failed case. Recovery plan and resit suggestion should be triggered.",
    },
  },

  excellent: {
    studentName: "Chris Wang",
    studentId: "TP081006",
    courseName: "Software Engineering",
    courseworkScore: 91,
    finalExamScore: 88,
    attendance: 96,
    semester: "Year 2 Semester 2",
    notes: {
      zh: "优秀样例，平时成绩和期末成绩都处于较高水平。",
      en: "Excellent case with strong coursework and final exam performance.",
    },
  },
};

function t(key) {
  return i18n[currentLang][key] || key;
}

function setLanguage(lang) {
  currentLang = lang;
  localStorage.setItem(LANG_KEY, lang);
  document.documentElement.lang = lang === "zh" ? "zh-CN" : "en";

  document.querySelectorAll("[data-i18n]").forEach((element) => {
    const key = element.dataset.i18n;
    element.textContent = t(key);
  });

  document.querySelectorAll("[data-i18n-placeholder]").forEach((element) => {
    const key = element.dataset.i18nPlaceholder;
    element.placeholder = t(key);
  });

  const langToggle = document.getElementById("langToggle");
  if (langToggle) {
    langToggle.textContent = lang === "zh" ? "English" : "中文";
  }

  document.title =
    lang === "zh"
      ? "Java 学生成绩管理系统"
      : "Java Student Grade Management System";

  if (latestResult) {
    renderResult(latestResult, false);
  }

  renderRecords();
}

function showToast(message) {
  if (!toast) return;

  toast.textContent = message;
  toast.classList.add("show");

  window.clearTimeout(showToast.timer);
  showToast.timer = window.setTimeout(() => {
    toast.classList.remove("show");
  }, 1800);
}

function getRecords() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY)) || [];
  } catch (error) {
    return [];
  }
}

function saveRecords(records) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(records));
}

function formatNumber(value) {
  return Number(value).toFixed(1);
}

function getLetterGrade(total) {
  if (total >= 85) return "A";
  if (total >= 75) return "B+";
  if (total >= 65) return "B";
  if (total >= 55) return "C+";
  if (total >= 50) return "C";
  if (total >= 40) return "D";
  return "F";
}

function getStatus(total, finalExamScore, attendance) {
  if (total >= 50 && finalExamScore >= 40) {
    if (attendance < 70) {
      return {
        key: "passAttendanceWarning",
        type: "borderline",
        pass: true,
      };
    }

    return {
      key: "passStatus",
      type: "pass",
      pass: true,
    };
  }

  if (total >= 45 || finalExamScore >= 35) {
    return {
      key: "borderlineStatus",
      type: "borderline",
      pass: false,
    };
  }

  return {
    key: "failStatus",
    type: "fail",
    pass: false,
  };
}

function getRecoveryPlan(result) {
  const { total, finalExamScore, attendance } = result;

  if (currentLang === "zh") {
    if (total >= 50 && finalExamScore >= 40 && attendance >= 70) {
      return "无需补考。建议继续保持平时成绩质量，并在后续课程中关注期末复习节奏。";
    }

    if (total >= 50 && attendance < 70) {
      return "成绩达到通过要求，但出勤率低于建议范围。建议关注出勤情况，避免影响后续课程表现。";
    }

    if (total >= 45 || finalExamScore >= 35) {
      return "结果处于边界范围。建议复盘薄弱考核部分，针对性准备补考或恢复性评估。";
    }

    return "需要补考。建议先定位失分模块，重新整理课程基础内容，并在下一次评估前制定恢复计划。";
  }

  if (total >= 50 && finalExamScore >= 40 && attendance >= 70) {
    return "No resit is required. Continue tracking coursework quality and final exam preparation for future modules.";
  }

  if (total >= 50 && attendance < 70) {
    return "The student passed the score requirement, but attendance is below the recommended level. Attendance follow-up is suggested.";
  }

  if (total >= 45 || finalExamScore >= 35) {
    return "Borderline result. Review weak assessment components, arrange targeted revision, and prepare for possible recovery or resit assessment.";
  }

  return "Resit is required. Suggested actions include reviewing failed components, rebuilding coursework understanding, and preparing a recovery plan before the next assessment attempt.";
}

function getSystemNotes(result) {
  const { total, courseworkScore, finalExamScore, attendance } = result;
  const notes = [];

  if (currentLang === "zh") {
    notes.push("总评成绩按 50% 平时成绩 + 50% 期末成绩进行计算。");

    if (courseworkScore < 40) {
      notes.push("平时成绩低于 40，建议复盘作业或阶段性考核内容。");
    }

    if (finalExamScore < 40) {
      notes.push("期末成绩低于 40，可能需要补考或重点准备恢复性评估。");
    }

    if (attendance < 70) {
      notes.push("出勤率低于 70%，触发出勤提醒。");
    }

    if (total >= 85) {
      notes.push("成绩处于优秀区间，整体表现较稳定。");
    }

    if (notes.length === 1) {
      notes.push("根据当前 Demo 规则，暂未发现明显风险信号。");
    }

    return notes;
  }

  notes.push("Final total score is calculated with 50% coursework and 50% final exam weighting.");

  if (courseworkScore < 40) {
    notes.push("Coursework score is below 40. Coursework review is recommended.");
  }

  if (finalExamScore < 40) {
    notes.push("Final exam score is below 40. Resit or exam recovery preparation may be required.");
  }

  if (attendance < 70) {
    notes.push("Attendance is below 70%. Attendance warning is triggered.");
  }

  if (total >= 85) {
    notes.push("Excellent performance. Student is in the top grade range for this module.");
  }

  if (notes.length === 1) {
    notes.push("No major risk signal found based on the current demo rules.");
  }

  return notes;
}

function calculateGrade(formData) {
  const courseworkScore = Number(formData.courseworkScore);
  const finalExamScore = Number(formData.finalExamScore);
  const attendance = Number(formData.attendance);

  const total = courseworkScore * 0.5 + finalExamScore * 0.5;
  const grade = getLetterGrade(total);
  const status = getStatus(total, finalExamScore, attendance);

  const result = {
    studentName: formData.studentName.trim(),
    studentId: formData.studentId.trim(),
    courseName: formData.courseName.trim(),
    semester: formData.semester,
    courseworkScore,
    finalExamScore,
    attendance,
    total,
    grade,
    statusKey: status.key,
    statusType: status.type,
    pass: status.pass,
    userNotes: formData.notes.trim(),
    createdAt: new Date().toISOString(),
  };

  result.recovery = getRecoveryPlan(result);
  result.notes = getSystemNotes(result);

  return result;
}

function renderResult(result, updateLatestText = true) {
  latestResult = result;

  emptyResult.classList.add("hidden");
  resultContent.classList.remove("hidden");

  result.recovery = getRecoveryPlan(result);
  result.notes = getSystemNotes(result);

  resultBanner.className = `result-banner ${result.statusType}`;
  resultStatus.textContent = t(result.statusKey);
  resultGrade.textContent = result.grade;

  metricCoursework.textContent = `${formatNumber(result.courseworkScore)}%`;
  metricExam.textContent = `${formatNumber(result.finalExamScore)}%`;
  metricTotal.textContent = `${formatNumber(result.total)}%`;
  metricAttendance.textContent = `${formatNumber(result.attendance)}%`;

  if (currentLang === "zh") {
    studentSummary.textContent =
      `${result.studentName}（${result.studentId}）在 ${result.semester} 修读 ${result.courseName}。` +
      `最终总评成绩为 ${formatNumber(result.total)}%，等级为 ${result.grade}，状态为 ${t(result.statusKey)}。`;
  } else {
    studentSummary.textContent =
      `${result.studentName} (${result.studentId}) completed ${result.courseName} in ${result.semester}. ` +
      `The final total score is ${formatNumber(result.total)}%, with grade ${result.grade} and status ${t(result.statusKey)}.`;
  }

  recoveryPlan.textContent = result.recovery;

  systemNotes.innerHTML = "";
  result.notes.forEach((note) => {
    const li = document.createElement("li");
    li.textContent = note;
    systemNotes.appendChild(li);
  });

  if (updateLatestText) {
    latestResultText = buildResultText(result);
  } else {
    latestResultText = buildResultText(result);
  }
}

function buildResultText(result) {
  if (currentLang === "zh") {
    return [
      "Java 学生成绩管理系统 - 计算结果",
      `学生：${result.studentName}（${result.studentId}）`,
      `课程：${result.courseName}`,
      `学期：${result.semester}`,
      `平时成绩：${formatNumber(result.courseworkScore)}%`,
      `期末成绩：${formatNumber(result.finalExamScore)}%`,
      `总评成绩：${formatNumber(result.total)}%`,
      `等级：${result.grade}`,
      `状态：${t(result.statusKey)}`,
      `建议：${result.recovery}`,
      `说明：${result.notes.join(" | ")}`,
    ].join("\n");
  }

  return [
    "Java Student Grade Management System - Result",
    `Student: ${result.studentName} (${result.studentId})`,
    `Course: ${result.courseName}`,
    `Semester: ${result.semester}`,
    `Coursework Score: ${formatNumber(result.courseworkScore)}%`,
    `Final Exam Score: ${formatNumber(result.finalExamScore)}%`,
    `Total Score: ${formatNumber(result.total)}%`,
    `Grade: ${result.grade}`,
    `Status: ${t(result.statusKey)}`,
    `Recovery Plan: ${result.recovery}`,
    `Notes: ${result.notes.join(" | ")}`,
  ].join("\n");
}

function addRecord(result) {
  const records = getRecords();

  records.unshift({
    createdAt: result.createdAt,
    studentName: result.studentName,
    studentId: result.studentId,
    courseName: result.courseName,
    courseworkScore: result.courseworkScore,
    finalExamScore: result.finalExamScore,
    total: result.total,
    grade: result.grade,
    statusKey: result.statusKey,
    statusType: result.statusType,
  });

  saveRecords(records.slice(0, 20));
  renderRecords();
}

function renderRecords() {
  if (!recordsBody) return;

  const records = getRecords();

  if (!records.length) {
    recordsBody.innerHTML = `
      <tr>
        <td colspan="6" class="table-empty">${t("noRecords")}</td>
      </tr>
    `;
    return;
  }

  recordsBody.innerHTML = records
    .map((record) => {
      const created = new Date(record.createdAt).toLocaleString();
      const statusLabel = record.statusKey ? t(record.statusKey) : record.status || "-";

      return `
        <tr>
          <td>${created}</td>
          <td>${escapeHtml(record.studentName)}</td>
          <td>${escapeHtml(record.courseName)}</td>
          <td>${formatNumber(record.total)}%</td>
          <td>${record.grade}</td>
          <td><span class="status-pill ${record.statusType}">${escapeHtml(statusLabel)}</span></td>
        </tr>
      `;
    })
    .join("");
}

function fillSample(type) {
  const sample = sampleData[type];
  if (!sample) return;

  Object.entries(sample).forEach(([key, value]) => {
    const field = document.getElementById(key);
    if (!field) return;

    if (key === "notes") {
      field.value = value[currentLang];
    } else {
      field.value = value;
    }
  });

  showToast(t("toastSample"));
}

function resetForm() {
  gradeForm.reset();
  latestResultText = "";
  latestResult = null;
  resultContent.classList.add("hidden");
  emptyResult.classList.remove("hidden");
  showToast(t("toastReset"));
}

function escapeHtml(value) {
  return String(value)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

function collectFormData() {
  const formData = new FormData(gradeForm);

  return {
    studentName: formData.get("studentName") || "",
    studentId: formData.get("studentId") || "",
    courseName: formData.get("courseName") || "",
    courseworkScore: formData.get("courseworkScore") || 0,
    finalExamScore: formData.get("finalExamScore") || 0,
    attendance: formData.get("attendance") || 0,
    semester: formData.get("semester") || "",
    notes: formData.get("notes") || "",
  };
}

function validateScores(data) {
  const scoreFields = ["courseworkScore", "finalExamScore", "attendance"];

  for (const field of scoreFields) {
    const value = Number(data[field]);

    if (Number.isNaN(value) || value < 0 || value > 100) {
      return false;
    }
  }

  return true;
}

function exportCsv() {
  const records = getRecords();

  if (!records.length) {
    showToast(t("toastNoRecords"));
    return;
  }

  const headers =
    currentLang === "zh"
      ? ["时间", "学生姓名", "学生ID", "课程", "平时成绩", "期末成绩", "总评成绩", "等级", "状态"]
      : ["Created Time", "Student Name", "Student ID", "Course", "Coursework Score", "Final Exam Score", "Total Score", "Grade", "Status"];

  const rows = records.map((record) => [
    new Date(record.createdAt).toLocaleString(),
    record.studentName,
    record.studentId,
    record.courseName,
    formatNumber(record.courseworkScore),
    formatNumber(record.finalExamScore),
    formatNumber(record.total),
    record.grade,
    record.statusKey ? t(record.statusKey) : record.status || "-",
  ]);

  const csv = [headers, ...rows]
    .map((row) =>
      row
        .map((cell) => `"${String(cell).replaceAll('"', '""')}"`)
        .join(",")
    )
    .join("\n");

  const blob = new Blob([csv], {
    type: "text/csv;charset=utf-8;",
  });

  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");

  link.href = url;
  link.download = t("csvFileName");
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);

  URL.revokeObjectURL(url);
  showToast(t("toastExported"));
}

async function copyLatestResult() {
  if (!latestResultText) {
    showToast(t("toastNoResult"));
    return;
  }

  try {
    await navigator.clipboard.writeText(latestResultText);
    showToast(t("toastCopied"));
  } catch (error) {
    const textarea = document.createElement("textarea");
    textarea.value = latestResultText;
    textarea.setAttribute("readonly", "");
    textarea.style.position = "absolute";
    textarea.style.left = "-9999px";

    document.body.appendChild(textarea);
    textarea.select();
    document.execCommand("copy");
    textarea.remove();

    showToast(t("toastCopied"));
  }
}

gradeForm.addEventListener("submit", (event) => {
  event.preventDefault();

  const data = collectFormData();

  if (!validateScores(data)) {
    showToast(t("toastInvalid"));
    return;
  }

  const result = calculateGrade(data);

  renderResult(result);
  addRecord(result);
  showToast(t("toastCalculated"));
});

document.querySelectorAll(".sample-btn").forEach((button) => {
  button.addEventListener("click", () => {
    fillSample(button.dataset.sample);
  });
});

document.getElementById("loadPassTop")?.addEventListener("click", () => {
  fillSample("pass");
  document.getElementById("calculator")?.scrollIntoView({
    behavior: "smooth",
  });
});

document.getElementById("langToggle")?.addEventListener("click", () => {
  setLanguage(currentLang === "zh" ? "en" : "zh");
});

resetFormBtn.addEventListener("click", resetForm);
copyResultBtn.addEventListener("click", copyLatestResult);
exportCsvBtn.addEventListener("click", exportCsv);

clearRecordsBtn.addEventListener("click", () => {
  localStorage.removeItem(STORAGE_KEY);
  renderRecords();
  showToast(t("toastCleared"));
});

setLanguage(currentLang);
renderRecords();
