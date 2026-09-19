package com.example.data

import com.example.model.ApplicationStatus
import com.example.model.TeacherApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TeacherRepository(private val teacherDao: TeacherDao) {
    val allApplications: Flow<List<TeacherApplication>> = teacherDao.getAllApplications()
    val latestApplication: Flow<TeacherApplication?> = teacherDao.getLatestApplication()

    fun getApplicationById(id: Long): Flow<TeacherApplication?> = teacherDao.getApplicationById(id)

    suspend fun submitApplication(application: TeacherApplication): Long {
        return teacherDao.insertApplication(application)
    }

    suspend fun updateApplication(application: TeacherApplication) {
        teacherDao.updateApplication(application)
    }

    suspend fun deleteApplication(application: TeacherApplication) {
        teacherDao.deleteApplication(application)
    }

    suspend fun payRegistrationFee(
        applicationId: Long,
        method: String,
        senderPhone: String,
        trxId: String
    ): Boolean {
        val app = teacherDao.getApplicationById(applicationId).firstOrNull() ?: return false
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy - hh:mm a", Locale("bn", "BD"))
        val currentDateStr = dateFormat.format(Date())

        val updated = app.copy(
            isFeePaid = true,
            paymentMethod = method,
            senderPhone = senderPhone,
            trxId = trxId,
            paymentDate = currentDateStr,
            status = ApplicationStatus.FEE_PAID
        )
        teacherDao.updateApplication(updated)
        return true
    }

    suspend fun scheduleInterview(
        applicationId: Long,
        date: String,
        time: String,
        link: String,
        mode: String
    ): Boolean {
        val app = teacherDao.getApplicationById(applicationId).firstOrNull() ?: return false
        val updated = app.copy(
            interviewDate = date,
            interviewTime = time,
            interviewLink = link,
            interviewMode = mode,
            status = ApplicationStatus.INTERVIEW_SCHEDULED
        )
        teacherDao.updateApplication(updated)
        return true
    }

    suspend fun evaluateCandidate(
        applicationId: Long,
        score: Int,
        notes: String,
        isSelected: Boolean,
        assignedBatch: String,
        joiningDate: String
    ): Boolean {
        val app = teacherDao.getApplicationById(applicationId).firstOrNull() ?: return false
        val status = if (isSelected) ApplicationStatus.SELECTED else ApplicationStatus.REJECTED
        val updated = app.copy(
            interviewScore = score,
            interviewerNotes = notes,
            status = status,
            assignedBatch = if (isSelected) assignedBatch else "",
            joiningDate = if (isSelected) joiningDate else ""
        )
        teacherDao.updateApplication(updated)
        return true
    }
}
