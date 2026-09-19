package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.CoachingViewModel
import com.example.ui.NavigationTab
import com.example.ui.components.EvaluateCandidateDialog
import com.example.ui.components.PaymentDialog
import com.example.ui.components.ScheduleInterviewDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    viewModel: CoachingViewModel,
    modifier: Modifier = Modifier
) {
    val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
    val allApplications by viewModel.allApplications.collectAsStateWithLifecycle()
    val selectedAppId by viewModel.selectedAppId.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val paymentState by viewModel.paymentState.collectAsStateWithLifecycle()
    val scheduleState by viewModel.scheduleState.collectAsStateWithLifecycle()
    val evaluationState by viewModel.evaluationState.collectAsStateWithLifecycle()
    val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_app_icon),
                                contentDescription = "App Icon",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "ডিজিটাল কোচিং সেন্টার",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "শিক্ষক নিয়োগ পোর্টাল • বেতন ৩৫,০০০৳",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                actions = {
                    Surface(
                        color = Color(0xFF10B981).copy(alpha = 0.15f),
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f)),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "নিয়োগ চলছে",
                                color = Color(0xFF047857),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                windowInsets = WindowInsets.navigationBars,
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                NavigationBarItem(
                    selected = activeTab == NavigationTab.APPLY,
                    onClick = { viewModel.selectTab(NavigationTab.APPLY) },
                    icon = { Icon(Icons.Default.School, contentDescription = "Apply") },
                    label = { Text("নিয়োগ ও আবেদন", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_apply")
                )
                NavigationBarItem(
                    selected = activeTab == NavigationTab.STATUS,
                    onClick = { viewModel.selectTab(NavigationTab.STATUS) },
                    icon = { Icon(Icons.Default.Badge, contentDescription = "Status") },
                    label = { Text("আমার আবেদন", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_status")
                )
                NavigationBarItem(
                    selected = activeTab == NavigationTab.PREPARATION,
                    onClick = { viewModel.selectTab(NavigationTab.PREPARATION) },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "Preparation") },
                    label = { Text("প্রস্তুতি গাইড", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_prep")
                )
                NavigationBarItem(
                    selected = activeTab == NavigationTab.ADMIN,
                    onClick = { viewModel.selectTab(NavigationTab.ADMIN) },
                    icon = { Icon(Icons.Default.FactCheck, contentDescription = "Admin") },
                    label = { Text("সিলেকশন বোর্ড", fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_admin")
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (activeTab) {
                NavigationTab.APPLY -> ApplyScreen(
                    formState = formState,
                    onNameChange = viewModel::updateFormName,
                    onPhoneChange = viewModel::updateFormPhone,
                    onEmailChange = viewModel::updateFormEmail,
                    onQualificationChange = viewModel::updateFormQualification,
                    onInstitutionChange = viewModel::updateFormInstitution,
                    onSubjectChange = viewModel::updateFormSubject,
                    onExperienceChange = viewModel::updateFormExperience,
                    onTargetClassChange = viewModel::updateFormTargetClass,
                    onShiftChange = viewModel::updateFormPreferredShift,
                    onSubmit = viewModel::submitTeacherRegistration
                )
                NavigationTab.STATUS -> StatusScreen(
                    applications = allApplications,
                    selectedAppId = selectedAppId,
                    onSelectApplication = viewModel::selectApplication,
                    onOpenPaymentDialog = viewModel::openPaymentDialog,
                    onGoToApply = { viewModel.selectTab(NavigationTab.APPLY) }
                )
                NavigationTab.PREPARATION -> InterviewPrepScreen(
                    tips = viewModel.interviewTips
                )
                NavigationTab.ADMIN -> AdminSelectionScreen(
                    applications = allApplications,
                    onOpenPaymentDialog = viewModel::openPaymentDialog,
                    onOpenScheduleDialog = viewModel::openScheduleDialog,
                    onOpenEvaluationDialog = viewModel::openEvaluationDialog,
                    onSelectApplicationForView = { id ->
                        viewModel.selectApplication(id)
                        viewModel.selectTab(NavigationTab.STATUS)
                    }
                )
            }
        }
    }

    // Payment Dialog
    PaymentDialog(
        state = paymentState,
        onMethodSelected = viewModel::updatePaymentMethod,
        onSenderPhoneChange = viewModel::updatePaymentSenderPhone,
        onTrxIdChange = viewModel::updatePaymentTrxId,
        onSubmit = viewModel::processPayment,
        onDismiss = viewModel::closePaymentDialog
    )

    // Schedule Interview Dialog
    ScheduleInterviewDialog(
        state = scheduleState,
        onDateChange = viewModel::updateScheduleDate,
        onTimeChange = viewModel::updateScheduleTime,
        onLinkChange = viewModel::updateScheduleLink,
        onModeChange = viewModel::updateScheduleMode,
        onConfirm = viewModel::confirmScheduleInterview,
        onDismiss = viewModel::closeScheduleDialog
    )

    // Evaluate Candidate Dialog
    EvaluateCandidateDialog(
        state = evaluationState,
        onScoreChange = viewModel::updateEvaluationScore,
        onNotesChange = viewModel::updateEvaluationNotes,
        onSelectedToggle = viewModel::updateEvaluationIsSelected,
        onBatchChange = viewModel::updateEvaluationBatch,
        onJoiningDateChange = viewModel::updateEvaluationJoiningDate,
        onConfirm = viewModel::confirmCandidateEvaluation,
        onDismiss = viewModel::closeEvaluationDialog
    )
}
