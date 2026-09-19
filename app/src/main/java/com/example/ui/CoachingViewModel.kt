package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.TeacherRepository
import com.example.model.ApplicationStatus
import com.example.model.TeacherApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class NavigationTab(val labelBn: String, val labelEn: String) {
    APPLY("নিয়োগ ও আবেদন", "Recruitment"),
    STATUS("আমার আবেদন", "My Status"),
    PREPARATION("ইন্টারভিউ প্রস্তুতি", "Interview Prep"),
    ADMIN("কোচিং প্যানেল", "Selection Panel")
}

data class ApplyFormState(
    val fullName: String = "",
    val phone: String = "",
    val email: String = "",
    val qualification: String = "B.Sc (Hons) in Mathematics",
    val institution: String = "",
    val subject: String = "উচ্চতর গণিত (Higher Math)",
    val experience: String = "১-২ বছর",
    val targetClass: String = "Class 9-10 & SSC",
    val preferredShift: String = "সন্ধ্যা শিফট (৬:০০ - ৮:০০)",
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val successAppId: String? = null
)

data class PaymentState(
    val isDialogOpen: Boolean = false,
    val targetApplicationId: Long = 0,
    val selectedMethod: String = "bKash",
    val senderPhone: String = "",
    val trxId: String = "",
    val isProcessing: Boolean = false,
    val errorMessage: String? = null,
    val paymentSuccess: Boolean = false
)

data class InterviewScheduleState(
    val isDialogOpen: Boolean = false,
    val targetApplicationId: Long = 0,
    val candidateName: String = "",
    val interviewDate: String = "২৫ সেপ্টেম্বর, ২০২৬",
    val interviewTime: String = "সকাল ১১:০০ টা",
    val interviewLink: String = "https://meet.google.com/dcc-smart-board",
    val interviewMode: String = "অনলাইন (Google Meet)"
)

data class EvaluationState(
    val isDialogOpen: Boolean = false,
    val targetApplicationId: Long = 0,
    val candidateName: String = "",
    val score: Int = 85,
    val notes: String = "উপস্থাপনা সুন্দর, ধারণ ক্ষমতা চমৎকার এবং শিক্ষার্থীদের বুঝানোর দক্ষতা প্রশংসনীয়।",
    val isSelected: Boolean = true,
    val assignedBatch: String = "HSC-২০২৬ স্পেশাল ফিজিক্স ও ম্যাথ ব্যাচ",
    val joiningDate: String = "০১ অক্টোবর, ২০২৬"
)

data class InterviewTip(
    val id: Int,
    val questionBn: String,
    val categoryBn: String,
    val guidanceBn: String
)

class CoachingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TeacherRepository

    val allApplications: StateFlow<List<TeacherApplication>>
    val latestApplication: StateFlow<TeacherApplication?>

    private val _activeTab = MutableStateFlow(NavigationTab.APPLY)
    val activeTab: StateFlow<NavigationTab> = _activeTab.asStateFlow()

    private val _selectedAppId = MutableStateFlow<Long?>(null)
    val selectedAppId: StateFlow<Long?> = _selectedAppId.asStateFlow()

    private val _formState = MutableStateFlow(ApplyFormState())
    val formState: StateFlow<ApplyFormState> = _formState.asStateFlow()

    private val _paymentState = MutableStateFlow(PaymentState())
    val paymentState: StateFlow<PaymentState> = _paymentState.asStateFlow()

    private val _scheduleState = MutableStateFlow(InterviewScheduleState())
    val scheduleState: StateFlow<InterviewScheduleState> = _scheduleState.asStateFlow()

    private val _evaluationState = MutableStateFlow(EvaluationState())
    val evaluationState: StateFlow<EvaluationState> = _evaluationState.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    val interviewTips = listOf(
        InterviewTip(
            id = 1,
            questionBn = "ডিজিটাল কোচিংয়ে শিক্ষার্থীদের দৃষ্টি আকর্ষণ করার কৌশল কী?",
            categoryBn = "পদ্ধতিগত প্রশ্ন",
            guidanceBn = "ডিজিটাল পেন-ট্যাবলেট, অ্যানিমেটেড স্লাইড, বাস্তব উদাহরণ এবং প্রতি ১০ মিনিটে শিক্ষার্থীদের প্রশ্নোত্তরে যুক্ত রাখার পরিকল্পনা তুলে ধরুন।"
        ),
        InterviewTip(
            id = 2,
            questionBn = "মাসিক ৩৫,০০০ টাকা বেতনের দায়িত্ব হিসেবে আপনি কী অবদান রাখবেন?",
            categoryBn = "দায়িত্ববোধ",
            guidanceBn = "নিয়মিত লাইভ ক্লাস, সময়মতো মডেল টেস্ট খাতা মূল্যায়ন, দুর্বল শিক্ষার্থীদের বিশেষ জুম সেশন এবং ক্লাসের উচ্চমানের লেকচার শিট তৈরি।"
        ),
        InterviewTip(
            id = 3,
            questionBn = "৫ মিনিটের একটি ডেমো ক্লাস কিভাবে সাজাবেন?",
            categoryBn = "ডেমো ক্লাস",
            guidanceBn = "শুরুতে ১ মিনিটে মূল টপিকের গুরুত্ব, ৩ মিনিটে জটিল ধারণার সহজ ব্যাখ্যা এবং শেষ ১ মিনিটে দ্রুত রিভিশন ও কুইজ।"
        ),
        InterviewTip(
            id = 4,
            questionBn = "অনলাইনে দুর্বল শিক্ষার্থীদের জন্য আপনার অতিরিক্ত উদ্যোগ কী হবে?",
            categoryBn = "যত্ন ও মেন্টরিং",
            guidanceBn = "সাপ্তাহিক ডাউট-সলভিং সেশন, রেকর্ডেড ক্লাস দেখার ফলোআপ এবং অভিভাবকদের সাথে নিয়মিত অগ্রগতি পর্যালোচনা।"
        )
    )

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = TeacherRepository(database.teacherDao())

        allApplications = repository.allApplications.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        latestApplication = repository.latestApplication.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }

    fun selectTab(tab: NavigationTab) {
        _activeTab.value = tab
    }

    fun selectApplication(id: Long) {
        _selectedAppId.value = id
    }

    fun updateFormName(value: String) {
        _formState.value = _formState.value.copy(fullName = value, errorMessage = null)
    }

    fun updateFormPhone(value: String) {
        _formState.value = _formState.value.copy(phone = value, errorMessage = null)
    }

    fun updateFormEmail(value: String) {
        _formState.value = _formState.value.copy(email = value, errorMessage = null)
    }

    fun updateFormQualification(value: String) {
        _formState.value = _formState.value.copy(qualification = value)
    }

    fun updateFormInstitution(value: String) {
        _formState.value = _formState.value.copy(institution = value, errorMessage = null)
    }

    fun updateFormSubject(value: String) {
        _formState.value = _formState.value.copy(subject = value)
    }

    fun updateFormExperience(value: String) {
        _formState.value = _formState.value.copy(experience = value)
    }

    fun updateFormTargetClass(value: String) {
        _formState.value = _formState.value.copy(targetClass = value)
    }

    fun updateFormPreferredShift(value: String) {
        _formState.value = _formState.value.copy(preferredShift = value)
    }

    fun submitTeacherRegistration() {
        val state = _formState.value
        if (state.fullName.isBlank()) {
            _formState.value = state.copy(errorMessage = "অনুগ্রহ করে আপনার পুরো নাম লিখুন")
            return
        }
        if (state.phone.length < 11) {
            _formState.value = state.copy(errorMessage = "সঠিক ১১ ডিজিটের মোবাইল নম্বর দিন (যেমন: 017xxxxxxxx)")
            return
        }
        if (state.institution.isBlank()) {
            _formState.value = state.copy(errorMessage = "আপনার বিশ্ববিদ্যালয় বা কলেজের নাম লিখুন")
            return
        }

        viewModelScope.launch {
            _formState.value = state.copy(isSubmitting = true, errorMessage = null)
            val randomNum = Random.nextInt(1000, 9999)
            val applicantId = "DCC-2026-$randomNum"

            val newApp = TeacherApplication(
                applicantId = applicantId,
                fullName = state.fullName.trim(),
                phone = state.phone.trim(),
                email = state.email.trim(),
                qualification = state.qualification,
                institution = state.institution.trim(),
                subject = state.subject,
                experience = state.experience,
                targetClass = state.targetClass,
                preferredShift = state.preferredShift,
                status = ApplicationStatus.PENDING_PAYMENT
            )

            val newId = repository.submitApplication(newApp)
            _selectedAppId.value = newId
            _formState.value = ApplyFormState(successAppId = applicantId)
            _snackbarMessage.value = "আবেদন সফল! ইন্টারভিউ নিশ্চিত করতে ৯৯৯ টাকা ফি পরিশোধ করুন।"
            _activeTab.value = NavigationTab.STATUS
            openPaymentDialog(newId)
        }
    }

    fun openPaymentDialog(applicationId: Long) {
        _paymentState.value = PaymentState(
            isDialogOpen = true,
            targetApplicationId = applicationId,
            selectedMethod = "bKash",
            senderPhone = "",
            trxId = "",
            isProcessing = false
        )
    }

    fun closePaymentDialog() {
        _paymentState.value = _paymentState.value.copy(isDialogOpen = false)
    }

    fun updatePaymentMethod(method: String) {
        _paymentState.value = _paymentState.value.copy(selectedMethod = method)
    }

    fun updatePaymentSenderPhone(phone: String) {
        _paymentState.value = _paymentState.value.copy(senderPhone = phone, errorMessage = null)
    }

    fun updatePaymentTrxId(trxId: String) {
        _paymentState.value = _paymentState.value.copy(trxId = trxId, errorMessage = null)
    }

    fun processPayment() {
        val state = _paymentState.value
        if (state.senderPhone.length < 11) {
            _paymentState.value = state.copy(errorMessage = "সঠিক বিকাশ/নগদ প্রেরক নম্বর দিন")
            return
        }
        if (state.trxId.length < 6) {
            _paymentState.value = state.copy(errorMessage = "সঠিক ট্রানজেকশন আইডি (TrxID) প্রদান করুন")
            return
        }

        viewModelScope.launch {
            _paymentState.value = state.copy(isProcessing = true, errorMessage = null)
            val success = repository.payRegistrationFee(
                applicationId = state.targetApplicationId,
                method = state.selectedMethod,
                senderPhone = state.senderPhone,
                trxId = state.trxId.uppercase()
            )
            if (success) {
                _paymentState.value = state.copy(
                    isProcessing = false,
                    paymentSuccess = true,
                    isDialogOpen = false
                )
                _snackbarMessage.value = "৯৯৯ টাকা পেমেন্ট সফলভাবে গৃহীত হয়েছে! শীঘ্রই ইন্টারভিউ শিডিউল হবে।"
            } else {
                _paymentState.value = state.copy(
                    isProcessing = false,
                    errorMessage = "পেমেন্ট সম্পন্ন হতে ব্যর্থ হয়েছে। আবার চেষ্টা করুন।"
                )
            }
        }
    }

    fun openScheduleDialog(app: TeacherApplication) {
        _scheduleState.value = InterviewScheduleState(
            isDialogOpen = true,
            targetApplicationId = app.id,
            candidateName = app.fullName,
            interviewDate = if (app.interviewDate.isNotBlank()) app.interviewDate else "২৪ সেপ্টেম্বর, ২০২৬",
            interviewTime = if (app.interviewTime.isNotBlank()) app.interviewTime else "সকাল ১১:০০ টা",
            interviewLink = if (app.interviewLink.isNotBlank()) app.interviewLink else "https://meet.google.com/dcc-teach-2026",
            interviewMode = if (app.interviewMode.isNotBlank()) app.interviewMode else "অনলাইন (Google Meet)"
        )
    }

    fun closeScheduleDialog() {
        _scheduleState.value = _scheduleState.value.copy(isDialogOpen = false)
    }

    fun updateScheduleDate(date: String) {
        _scheduleState.value = _scheduleState.value.copy(interviewDate = date)
    }

    fun updateScheduleTime(time: String) {
        _scheduleState.value = _scheduleState.value.copy(interviewTime = time)
    }

    fun updateScheduleLink(link: String) {
        _scheduleState.value = _scheduleState.value.copy(interviewLink = link)
    }

    fun updateScheduleMode(mode: String) {
        _scheduleState.value = _scheduleState.value.copy(interviewMode = mode)
    }

    fun confirmScheduleInterview() {
        val state = _scheduleState.value
        viewModelScope.launch {
            repository.scheduleInterview(
                applicationId = state.targetApplicationId,
                date = state.interviewDate,
                time = state.interviewTime,
                link = state.interviewLink,
                mode = state.interviewMode
            )
            _scheduleState.value = state.copy(isDialogOpen = false)
            _snackbarMessage.value = "ইন্টারভিউ সফলভাবে শিডিউল করা হয়েছে!"
        }
    }

    fun openEvaluationDialog(app: TeacherApplication) {
        _evaluationState.value = EvaluationState(
            isDialogOpen = true,
            targetApplicationId = app.id,
            candidateName = app.fullName,
            score = if (app.interviewScore > 0) app.interviewScore else 88,
            notes = if (app.interviewerNotes.isNotBlank()) app.interviewerNotes else "বিষয়ভিত্তিক জ্ঞান ও অনলাইন ক্লাস পরিচালনা দক্ষতা অত্যন্ত সন্তোষজনক।",
            isSelected = true,
            assignedBatch = if (app.assignedBatch.isNotBlank()) app.assignedBatch else "${app.subject} এক্সক্লুসিভ লাইভ ব্যাচ",
            joiningDate = if (app.joiningDate.isNotBlank()) app.joiningDate else "০১ অক্টোবর, ২০২৬"
        )
    }

    fun closeEvaluationDialog() {
        _evaluationState.value = _evaluationState.value.copy(isDialogOpen = false)
    }

    fun updateEvaluationScore(score: Int) {
        _evaluationState.value = _evaluationState.value.copy(score = score)
    }

    fun updateEvaluationNotes(notes: String) {
        _evaluationState.value = _evaluationState.value.copy(notes = notes)
    }

    fun updateEvaluationIsSelected(isSelected: Boolean) {
        _evaluationState.value = _evaluationState.value.copy(isSelected = isSelected)
    }

    fun updateEvaluationBatch(batch: String) {
        _evaluationState.value = _evaluationState.value.copy(assignedBatch = batch)
    }

    fun updateEvaluationJoiningDate(date: String) {
        _evaluationState.value = _evaluationState.value.copy(joiningDate = date)
    }

    fun confirmCandidateEvaluation() {
        val state = _evaluationState.value
        viewModelScope.launch {
            repository.evaluateCandidate(
                applicationId = state.targetApplicationId,
                score = state.score,
                notes = state.notes,
                isSelected = state.isSelected,
                assignedBatch = state.assignedBatch,
                joiningDate = state.joiningDate
            )
            _evaluationState.value = state.copy(isDialogOpen = false)
            val msg = if (state.isSelected) {
                "অভিনন্দন! প্রার্থীকে শিক্ষক হিসেবে নির্বাচিত করা হয়েছে (মাসিক ৩৫,০০০ টাকা)!"
            } else {
                "প্রার্থীর মূল্যায়ন সংরক্ষিত হয়েছে।"
            }
            _snackbarMessage.value = msg
        }
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}
