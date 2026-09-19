package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ApplicationStatus
import com.example.model.TeacherApplication
import com.example.ui.components.OfficialAppointmentCard
import com.example.ui.components.RecruitmentStepIndicator
import com.example.ui.components.StatusBadge

@Composable
fun StatusScreen(
    applications: List<TeacherApplication>,
    selectedAppId: Long?,
    onSelectApplication: (Long) -> Unit,
    onOpenPaymentDialog: (Long) -> Unit,
    onGoToApply: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Determine current active application
    val currentApp = if (selectedAppId != null) {
        applications.find { it.id == selectedAppId } ?: applications.firstOrNull()
    } else {
        applications.firstOrNull()
    }

    if (applications.isEmpty() || currentApp == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "কোনো আবেদন পাওয়া যায়নি",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ডিজিটাল কোচিং সেন্টারে শিক্ষক হিসেবে ৩৫,০০০ টাকা বেতনে কাজ করতে এখনই আবেদন করুন।",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onGoToApply,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("empty_state_apply_button")
                    ) {
                        Icon(Icons.Default.AddCircleOutline, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("নতুন শিক্ষক আবেদন করুন")
                    }
                }
            }
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // App Selector if multiple applications exist
        if (applications.size > 1) {
            Text(
                text = "আবেদন নির্বাচন করুন:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                applications.take(4).forEach { app ->
                    val isSelected = app.id == currentApp.id
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectApplication(app.id) },
                        label = {
                            Text(
                                text = "${app.applicantId} (${app.fullName.split(" ").first()})",
                                fontSize = 11.sp
                            )
                        },
                        leadingIcon = if (isSelected) {
                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        } else null
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Header Card with Applicant Info
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "রোল / আবেদন আইডি",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = currentApp.applicantId,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    StatusBadge(status = currentApp.status)
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("প্রার্থীর নাম:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(currentApp.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("নির্বাচিত বিষয়:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(currentApp.subject, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("মোবাইল:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(currentApp.phone, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("শিক্ষাগত যোগ্যতা:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(currentApp.qualification, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Step Progress Tracker
        RecruitmentStepIndicator(currentStep = currentApp.status.stepIndex)

        Spacer(modifier = Modifier.height(16.dp))

        // Action / Status Dependent Card
        when (currentApp.status) {
            ApplicationStatus.PENDING_PAYMENT -> {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFF59E0B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFFD97706),
                                shape = CircleShape,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Payment,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "৯৯৯ টাকা রেজিস্ট্রেশন ফি বকেয়া",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E),
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "ফি পরিশোধের পর ইন্টারভিউ শিডিউল প্রদান করা হবে",
                                    fontSize = 11.sp,
                                    color = Color(0xFFB45309)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "নিয়োগ নীতিমালা অনুযায়ী সকল প্রার্থীর প্রাথমিক যোগ্যতা যাচাই ও অনলাইন ইন্টারভিউ বোর্ড নিশ্চিত করতে ৯৯৯ টাকা ফি প্রযোজ্য।",
                            fontSize = 12.sp,
                            color = Color(0xFF78350F),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { onOpenPaymentDialog(currentApp.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("pay_fee_button")
                        ) {
                            Icon(Icons.Default.AccountBalanceWallet, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("৯৯৯৳ ফি পরিশোধ করুন (bKash / Nagad)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            ApplicationStatus.FEE_PAID -> {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF3B82F6)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFF2563EB),
                                shape = CircleShape,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "৯৯৯ টাকা ফি সফলভাবে পরিশোধিত!",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E40AF),
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "পেমেন্ট মাধ্যম: ${currentApp.paymentMethod} (TrxID: ${currentApp.trxId})",
                                    fontSize = 11.sp,
                                    color = Color(0xFF1D4ED8)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "পরবর্তী পদক্ষেপ: ইন্টারভিউ নির্ধারণ",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color(0xFF1E3A8A)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "কোচিং সেন্টারের ইন্টারভিউ প্যানেল আপনার সিভি ও বিষয়ভিত্তিক প্রোফাইল পর্যালোচনা করছেন। ২৪ ঘণ্টার মধ্যে আপনার ইন্টারভিউ তারিখ ও অনলাইন মিটিং লিংক জানানো হবে।",
                                    fontSize = 11.sp,
                                    color = Color(0xFF475569),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            ApplicationStatus.INTERVIEW_SCHEDULED -> {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5FF)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFA855F7)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFF9333EA),
                                shape = CircleShape,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Videocam,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "ইন্টারভিউ শিডিউল নির্ধারিত হয়েছে!",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6B21A8),
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "সিলেকশন সম্পন্ন হলে মাসিক ৩৫,০০০ টাকা বেতন প্রদান করা হবে",
                                    fontSize = 11.sp,
                                    color = Color(0xFF7E22CE)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.DateRange, contentDescription = null, tint = Color(0xFF7E22CE), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("তারিখ: ${currentApp.interviewDate}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Icon(Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF7E22CE), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("সময়: ${currentApp.interviewTime}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFF7E22CE), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("মাধ্যম: ${currentApp.interviewMode}", fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentApp.interviewLink))
                                    context.startActivity(intent)
                                } catch (_: Exception) {}
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9333EA)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("join_interview_button")
                        ) {
                            Icon(Icons.Default.VideoCall, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ইন্টারভিউ মিটিংয়ে যোগ দিন (Google Meet)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            ApplicationStatus.INTERVIEW_COMPLETED -> {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDFA)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF14B8A6)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFF0D9488),
                                shape = CircleShape,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.HourglassTop, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("ইন্টারভিউ সফলভাবে সম্পন্ন হয়েছে", fontWeight = FontWeight.Bold, color = Color(0xFF115E59), fontSize = 15.sp)
                                Text("ফলাফল ও চূড়ান্ত সিলেকশন প্রক্রিয়াধীন", fontSize = 11.sp, color = Color(0xFF0F766E))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "ইন্টারভিউ বোর্ডের প্রাপ্ত নম্বর ও মূল্যায়ন বিশ্লেষণ চলছে। নির্বাচিত হলে ৩৫,০০০ টাকা বেতনের চূড়ান্ত নিয়োগপত্র প্রকাশ করা হবে।",
                            fontSize = 12.sp,
                            color = Color(0xFF134E4A)
                        )
                    }
                }
            }

            ApplicationStatus.SELECTED -> {
                OfficialAppointmentCard(application = currentApp)
            }

            ApplicationStatus.REJECTED -> {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFEF4444)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("ইন্টারভিউ ফলাফল", fontWeight = FontWeight.Bold, color = Color(0xFF991B1B), fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "দুঃখিত, বর্তমান সেশনের জন্য আপনাকে নির্বাচিত করা সম্ভব হয়নি। পরবর্তী বিজ্ঞপ্তিতে পুনরায় আবেদন করার অনুরোধ করা হচ্ছে।",
                            fontSize = 12.sp,
                            color = Color(0xFF7F1D1D)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
