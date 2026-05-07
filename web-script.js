const STORAGE_KEY = "javaGradeDemoRecords";

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
    notes: "Standard pass case with stable coursework and exam performance.",
  },

  borderline: {
    studentName: "Tan Wei",
    studentId: "TP079120",
    courseName: "Database Systems",
    courseworkScore: 55,
    finalExamScore: 43,
    attendance: 76,
    semester: "Year 2 Semester 1",
    notes: "Borderline case. Total score is close to the minimum passing range.",
  },

  failed: {
    studentName: "Alex Lim",
    studentId: "TP080021",
    courseName: "Data Structures",
    courseworkScore: 38,
    finalExamScore: 34,
    attendance: 62,
    semester: "Year 1 Semester 2",
    notes: "Failed case. Recovery plan and resit suggestion should be triggered.",
  },

  excellent: {
    studentName: "Chris Wang",
    studentId: "TP081006",
    courseName: "Software Engineering",
    courseworkScore: 91,
    finalExamScore: 88,
    attendance: 96,
    semester: "Year 2 Semester 2",
    notes: "Excellent case with strong coursework and final exam performance.",
  },
};

let latestResultText = "";

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
        label: "Pass · Attendance Warning",
        type: "borderline",
        pass: true,
      };
    }

    return {
      label: "Pass",
      type: "pass",
      pass: true,
    };
  }

  if (total >= 45 && finalExamScore >= 35) {
    return {
      label: "Borderline · Resit Recommended",
      type: "borderline",
      pass: false,
    };
  }

  return {
    label: "Fail · Resit Required",
    type: "fail",
    pass: false,
  };
}

function getRecoveryPlan(total, finalExamScore, attendance) {
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

function getSystemNotes(total, courseworkScore, finalExamScore, attendance) {
  const notes = [];

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
  const recovery = getRecoveryPlan(total, finalExamScore, attendance);
  const notes = getSystemNotes(total, courseworkScore, finalExamScore, attendance);

  return {
    studentName: formData.studentName.trim(),
    studentId: formData.studentId.trim(),
    courseName: formData.courseName.trim(),
    semester: formData.semester,
    courseworkScore,
    finalExamScore,
    attendance,
    total,
    grade,
    status: status.label,
    statusType: status.type,
    pass: status.pass,
    recovery,
    notes,
    userNotes: formData.notes.trim(),
    createdAt: new Date().toISOString(),
  };
}

function renderResult(result) {
  emptyResult.classList.add("hidden");
  resultContent.classList.remove("hidden");

  resultBanner.className = `result-banner ${result.statusType}`;
  resultStatus.textContent = result.status;
  resultGrade.textContent = result.grade;

  metricCoursework.textContent = `${formatNumber(result.courseworkScore)}%`;
  metricExam.textContent = `${formatNumber(result.finalExamScore)}%`;
  metricTotal.textContent = `${formatNumber(result.total)}%`;
  metricAttendance.textContent = `${formatNumber(result.attendance)}%`;

  studentSummary.textContent =
    `${result.studentName} (${result.studentId}) completed ${result.courseName} in ${result.semester}. ` +
    `The final total score is ${formatNumber(result.total)}%, with grade ${result.grade}.`;

  recoveryPlan.textContent = result.recovery;

  systemNotes.innerHTML = "";
  result.notes.forEach((note) => {
    const li = document.createElement("li");
    li.textContent = note;
    systemNotes.appendChild(li);
  });

  latestResultText = [
    "Java Student Grade Management System - Result",
    `Student: ${result.studentName} (${result.studentId})`,
    `Course: ${result.courseName}`,
    `Semester: ${result.semester}`,
    `Coursework Score: ${formatNumber(result.courseworkScore)}%`,
    `Final Exam Score: ${formatNumber(result.finalExamScore)}%`,
    `Total Score: ${formatNumber(result.total)}%`,
    `Grade: ${result.grade}`,
    `Status: ${result.status}`,
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
    status: result.status,
    statusType: result.statusType,
  });

  saveRecords(records.slice(0, 20));
  renderRecords();
}

function renderRecords() {
  const records = getRecords();

  if (!records.length) {
    recordsBody.innerHTML = `
      <tr>
        <td colspan="6" class="table-empty">No records yet.</td>
      </tr>
    `;
    return;
  }

  recordsBody.innerHTML = records
    .map((record) => {
      const created = new Date(record.createdAt).toLocaleString();

      return `
        <tr>
          <td>${created}</td>
          <td>${escapeHtml(record.studentName)}</td>
          <td>${escapeHtml(record.courseName)}</td>
          <td>${formatNumber(record.total)}%</td>
          <td>${record.grade}</td>
          <td><span class="status-pill ${record.statusType}">${escapeHtml(record.status)}</span></td>
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
    if (field) field.value = value;
  });

  showToast("Sample loaded");
}

function resetForm() {
  gradeForm.reset();
  latestResultText = "";
  resultContent.classList.add("hidden");
  emptyResult.classList.remove("hidden");
  showToast("Form reset");
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
    showToast("No records to export");
    return;
  }

  const headers = [
    "Created Time",
    "Student Name",
    "Student ID",
    "Course",
    "Coursework Score",
    "Final Exam Score",
    "Total Score",
    "Grade",
    "Status",
  ];

  const rows = records.map((record) => [
    new Date(record.createdAt).toLocaleString(),
    record.studentName,
    record.studentId,
    record.courseName,
    formatNumber(record.courseworkScore),
    formatNumber(record.finalExamScore),
    formatNumber(record.total),
    record.grade,
    record.status,
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
  link.download = "java-grade-records.csv";
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);

  URL.revokeObjectURL(url);
  showToast("CSV exported");
}

async function copyLatestResult() {
  if (!latestResultText) {
    showToast("No result to copy");
    return;
  }

  try {
    await navigator.clipboard.writeText(latestResultText);
    showToast("Result copied");
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

    showToast("Result copied");
  }
}

gradeForm.addEventListener("submit", (event) => {
  event.preventDefault();

  const data = collectFormData();

  if (!validateScores(data)) {
    showToast("Scores must be between 0 and 100");
    return;
  }

  const result = calculateGrade(data);

  renderResult(result);
  addRecord(result);
  showToast("Grade calculated");
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

resetFormBtn.addEventListener("click", resetForm);
copyResultBtn.addEventListener("click", copyLatestResult);
exportCsvBtn.addEventListener("click", exportCsv);

clearRecordsBtn.addEventListener("click", () => {
  localStorage.removeItem(STORAGE_KEY);
  renderRecords();
  showToast("Records cleared");
});

renderRecords();
