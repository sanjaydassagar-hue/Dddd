package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ApplicationStatus(val titleBn: String, val stepIndex: Int) {
    PENDING_PAYMENT("৯৯৯ টাকা ফি বাকি", 1),
    FEE_PAID("ফি পরিশোধিত (যাচাই সম্পন্ন)", 2),
    INTERVIEW_SCHEDULED("ইন্টারভিউ নির্ধারিত", 3),
    INTERVIEW_COMPLETED("ইন্টারভিউ সম্পন্ন (পর্যালোচনাধীন)", 3),
    SELECTED("চূড়ান্ত নির্বাচিত (মাসিক ৩৫,০০০ টাকা)", 4),
    REJECTED("অযোগ্য ঘোষিত", 0)
}

@Entity(tableName = "teacher_applications")
data class TeacherApplication(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val applicantId: String,
    val fullName: String,
    val phone: String,
    val email: String,
    val qualification: String,
    val institution: String,
    val subject: String,
    val experience: String,
    val targetClass: String,
    val preferredShift: String = "সন্ধ্যা ব্যাচ (৬:০০ - ৮:০০)",
    val registrationFee: Int = 999,
    val monthlySalary: Int = 35000,
    val isFeePaid: Boolean = false,
    val paymentMethod: String = "",
    val senderPhone: String = "",
    val trxId: String = "",
    val paymentDate: String = "",
    val status: ApplicationStatus = ApplicationStatus.PENDING_PAYMENT,
    val interviewDate: String = "",
    val interviewTime: String = "",
    val interviewMode: String = "অনলাইন লাইভ (Google Meet)",
    val interviewLink: String = "https://meet.google.com/dcc-teach-2026",
    val interviewScore: Int = 0,
    val interviewerNotes: String = "",
    val assignedBatch: String = "",
    val joiningDate: String = "",
    val appliedTimestamp: Long = System.currentTimeMillis()
)
