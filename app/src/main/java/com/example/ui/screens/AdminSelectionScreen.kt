package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ApplicationStatus
import com.example.model.TeacherApplication
import com.example.ui.components.StatusBadge

@Composable
fun AdminSelectionScreen(
    applications: List<TeacherApplication>,
    onOpenPaymentDialog: (Long) -> Unit,
    onOpenScheduleDialog: (TeacherApplication) -> Unit,
    onOpenEvaluationDialog: (TeacherApplication) -> Unit,
    onSelectApplicationForView: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("ALL") }

    val totalCount = applications.size
    val feePaidCount = applications.count { it.isFeePaid }
    val interviewCount = applications.count { it.status == ApplicationStatus.INTERVIEW_SCHEDULED || it.status == ApplicationStatus.INTERVIEW_COMPLETED }
    val selectedCount = applications.count { it.status == ApplicationStatus.SELECTED }

    val filteredList = when (selectedFilter) {
        "FEE_PAID" -> applications.filter { it.isFeePaid }
        "INTERVIEW" -> applications.filter { it.status == ApplicationStatus.INTERVIEW_SCHEDULED || it.status == ApplicationStatus.INTERVIEW_COMPLETED }
        "SELECTED" -> applications.filter { it.status == ApplicationStatus.SELECTED }
        else -> applications
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Admin Panel Header
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "কোচিং এডমিন ও সিলেকশন বোর্ড",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "শিক্ষক আবেদন যাচাই, ইন্টারভিউ ও ৩৫,০০০৳ বেতনে নির্বাচন",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stats Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Total
                        StatBox(
                            title = "মোট আবেদন",
                            value = "$totalCount",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        // Fee Paid
                        StatBox(
                            title = "৯৯৯৳ ফি প্রাপ্ত",
                            value = "$feePaidCount",
                            color = Color(0xFFD97706),
                            modifier = Modifier.weight(1f)
                        )
                        // Interview
                        StatBox(
                            title = "ইন্টারভিউ",
                            value = "$interviewCount",
                            color = Color(0xFF9333EA),
                            modifier = Modifier.weight(1f)
                        )
                        // Selected
                        StatBox(
                            title = "নির্বাচিত (৩৫k)",
                            value = "$selectedCount",
                            color = Color(0xFF059669),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {
            // Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == "ALL",
                    onClick = { selectedFilter = "ALL" },
                    label = { Text("সকল ($totalCount)", fontSize = 11.sp) }
                )
                FilterChip(
                    selected = selectedFilter == "FEE_PAID",
                    onClick = { selectedFilter = "FEE_PAID" },
                    label = { Text("ফি সম্পন্ন ($feePaidCount)", fontSize = 11.sp) }
                )
                FilterChip(
                    selected = selectedFilter == "INTERVIEW",
                    onClick = { selectedFilter = "INTERVIEW" },
                    label = { Text("ইন্টারভিউ ($interviewCount)", fontSize = 11.sp) }
                )
                FilterChip(
                    selected = selectedFilter == "SELECTED",
                    onClick = { selectedFilter = "SELECTED" },
                    label = { Text("সিলেক্টেড ($selectedCount)", fontSize = 11.sp) }
                )
            }
        }

        if (filteredList.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "এই ক্যাটাগরিতে কোনো প্রার্থী নেই",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }
        }

        items(filteredList, key = { it.id }) { app ->
            CandidateAdminCard(
                application = app,
                onOpenPaymentDialog = { onOpenPaymentDialog(app.id) },
                onOpenScheduleDialog = { onOpenScheduleDialog(app) },
                onOpenEvaluationDialog = { onOpenEvaluationDialog(app) },
                onSelectApplicationForView = { onSelectApplicationForView(app.id) }
            )
        }
    }
}

@Composable
private fun StatBox(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = color.copy(alpha = 0.08f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = color)
            Text(text = title, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
        }
    }
}

@Composable
fun CandidateAdminCard(
    application: TeacherApplication,
    onOpenPaymentDialog: () -> Unit,
    onOpenScheduleDialog: () -> Unit,
    onOpenEvaluationDialog: () -> Unit,
    onSelectApplicationForView: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = application.fullName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "${application.applicantId} • ${application.subject}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                StatusBadge(status = application.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "যোগ্যতা: ${application.qualification} (${application.institution})",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "মোবাইল: ${application.phone} | অভিজ্ঞতা: ${application.experience}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (application.isFeePaid) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF059669),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "৯৯৯৳ ফি পরিশোধিত (${application.paymentMethod} Trx: ${application.trxId})",
                        fontSize = 10.sp,
                        color = Color(0xFF059669),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (application.interviewDate.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = Color(0xFF9333EA),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "ইন্টারভিউ: ${application.interviewDate} (${application.interviewTime})",
                        fontSize = 10.sp,
                        color = Color(0xFF9333EA),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (application.status == ApplicationStatus.SELECTED) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WorkspacePremium,
                        contentDescription = null,
                        tint = Color(0xFFD97706),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "বেতন: ৳৩৫,০০০/মাস • ব্যাচ: ${application.assignedBatch}",
                        fontSize = 11.sp,
                        color = Color(0xFF059669),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!application.isFeePaid) {
                    Button(
                        onClick = onOpenPaymentDialog,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("৯৯৯৳ ফি যাচাই", fontSize = 11.sp)
                    }
                } else {
                    OutlinedButton(
                        onClick = onOpenScheduleDialog,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (application.interviewDate.isBlank()) "শিডিউল দিন" else "শিডিউল পরিবর্তন", fontSize = 11.sp)
                    }

                    Button(
                        onClick = onOpenEvaluationDialog,
                        shape = RoundedCornerShape(8.dp),
                        colors = if (application.status == ApplicationStatus.SELECTED) {
                            ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
                        } else ButtonDefaults.buttonColors(),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.RateReview, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (application.status == ApplicationStatus.SELECTED) "সিলেকশন সম্পন্ন" else "মূল্যায়ন ও নির্বাচন", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
