package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.TeacherApplication
import com.example.ui.EvaluationState
import com.example.ui.InterviewScheduleState
import com.example.ui.PaymentState

@Composable
fun PaymentDialog(
    state: PaymentState,
    onMethodSelected: (String) -> Unit,
    onSenderPhoneChange: (String) -> Unit,
    onTrxIdChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!state.isDialogOpen) return

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Payment,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "রেজিস্ট্রেশন ফি পরিশোধ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "নির্ধারিত ফি: ৯৯৯ টাকা",
                                color = Color(0xFFD97706),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Instruction Box
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "পেমেন্ট নির্দেশনা (MFS মার্চেন্ট):",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "১. আপনার বিকাশ/নগদ/রকেট অ্যাপ থেকে 'Send Money' অথবা 'Make Payment' অপশনে যান।\n" +
                                    "২. ডিজিটাল কোচিং সেন্টারের নম্বর: 01700-123456 দিন।\n" +
                                    "৩. টাকার পরিমাণ: ৯৯৯ টাকা।\n" +
                                    "৪. পেমেন্ট শেষে প্রাপ্ত TrxID ও আপনার প্রেরক নম্বরটি নিচে লিখুন।",
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "পেমেন্ট মাধ্যম বেছে নিন:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val methods = listOf("bKash", "Nagad", "Rocket")
                    methods.forEach { method ->
                        val isSelected = state.selectedMethod == method
                        OutlinedButton(
                            onClick = { onMethodSelected(method) },
                            shape = RoundedCornerShape(10.dp),
                            colors = if (isSelected) {
                                ButtonDefaults.outlinedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                )
                            } else ButtonDefaults.outlinedButtonColors(),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = Brush.linearGradient(
                                    listOf(
                                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                    )
                                )
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = method,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = state.senderPhone,
                    onValueChange = onSenderPhoneChange,
                    label = { Text("প্রেরক মোবাইল নম্বর (১১ ডিজিট)") },
                    placeholder = { Text("01XXXXXXXXX") },
                    leadingIcon = { Icon(Icons.Default.PhoneAndroid, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("payment_sender_phone_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.trxId,
                    onValueChange = onTrxIdChange,
                    label = { Text("ট্রানজেকশন আইডি (TrxID)") },
                    placeholder = { Text("যেমন: BK999X7281") },
                    leadingIcon = { Icon(Icons.Default.Receipt, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("payment_trx_id_input")
                )

                if (state.errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onSubmit,
                    enabled = !state.isProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("confirm_payment_button")
                ) {
                    if (state.isProcessing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("যাচাই করা হচ্ছে...")
                    } else {
                        Icon(Icons.Default.VerifiedUser, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("৯৯৯৳ ফি নিশ্চিত করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleInterviewDialog(
    state: InterviewScheduleState,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onLinkChange: (String) -> Unit,
    onModeChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!state.isDialogOpen) return

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "ইন্টারভিউ শিডিউল করুন",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "প্রার্থী: ${state.candidateName}",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 13.sp
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = state.interviewDate,
                    onValueChange = onDateChange,
                    label = { Text("ইন্টারভিউ তারিখ") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.interviewTime,
                    onValueChange = onTimeChange,
                    label = { Text("ইন্টারভিউ সময়") },
                    leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.interviewMode,
                    onValueChange = onModeChange,
                    label = { Text("ইন্টারভিউ মাধ্যম") },
                    placeholder = { Text("অনলাইন (Google Meet) / স্টুডিও রুম ২") },
                    leadingIcon = { Icon(Icons.Default.Videocam, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.interviewLink,
                    onValueChange = onLinkChange,
                    label = { Text("মিটিং লিংক / স্থান") },
                    leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("শিডিউল সংরক্ষণ করুন")
                }
            }
        }
    }
}

@Composable
fun EvaluateCandidateDialog(
    state: EvaluationState,
    onScoreChange: (Int) -> Unit,
    onNotesChange: (String) -> Unit,
    onSelectedToggle: (Boolean) -> Unit,
    onBatchChange: (String) -> Unit,
    onJoiningDateChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!state.isDialogOpen) return

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "ইন্টারভিউ মূল্যায়ন ও সিলেকশন",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "প্রার্থী: ${state.candidateName}",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 13.sp
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "ইন্টারভিউ প্রাপ্ত নম্বর: ${state.score} / ১০০",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
                Slider(
                    value = state.score.toFloat(),
                    onValueChange = { onScoreChange(it.toInt()) },
                    valueRange = 0f..100f,
                    steps = 19
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectedToggle(!state.isSelected) }
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = state.isSelected,
                        onCheckedChange = onSelectedToggle
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "শিক্ষক হিসেবে নির্বাচন করুন (মাসিক ৩৫,০০০ টাকা)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (state.isSelected) Color(0xFF047857) else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "নির্বাচিত হলে অফিশিয়াল অ্যাপয়েন্টমেন্ট লেটার তৈরি হবে",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (state.isSelected) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = state.assignedBatch,
                        onValueChange = onBatchChange,
                        label = { Text("বরাদ্দকৃত ব্যাচ / কোর্স") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = state.joiningDate,
                        onValueChange = onJoiningDateChange,
                        label = { Text("যোগদানের তারিখ") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = state.notes,
                    onValueChange = onNotesChange,
                    label = { Text("ইন্টারভিউয়ার মূল্যায়ন ও মন্তব্য") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onConfirm,
                    colors = if (state.isSelected) {
                        ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
                    } else ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = if (state.isSelected) Icons.Default.Verified else Icons.Default.Save,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (state.isSelected) "সিলেকশন সম্পন্ন করুন (৩৫,০০০৳ বেতন)" else "মূল্যায়ন সংরক্ষণ করুন",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun OfficialAppointmentCard(
    application: TeacherApplication,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF10B981)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFF059669),
                        shape = CircleShape,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "অফিসিয়াল শিক্ষক নিয়োগপত্র",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF065F46),
                            fontSize = 15.sp
                        )
                        Text(
                            text = "ডিজিটাল কোচিং সেন্টার • নিয়োগপত্র ২০২৬",
                            fontSize = 11.sp,
                            color = Color(0xFF047857)
                        )
                    }
                }

                Surface(
                    color = Color(0xFF10B981).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "APPROVED",
                        color = Color(0xFF047857),
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFA7F3D0))
            Spacer(modifier = Modifier.height(12.dp))

            // Salary Box
            Surface(
                color = Color(0xFFDCFCE7),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "নির্ধারিত মাসিক বেতন:",
                            fontSize = 12.sp,
                            color = Color(0xFF166534)
                        )
                        Text(
                            text = "৳৩৫,০০০ টাকা / মাস",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF14532D)
                        )
                    }
                    Surface(
                        color = Color(0xFF15803D),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "প্রতি মাসের ১-৫ তারিখে পরিশোধ",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("শিক্ষকের নাম:", fontSize = 11.sp, color = Color(0xFF166534))
                    Text(application.fullName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF065F46))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("বিষয়:", fontSize = 11.sp, color = Color(0xFF166534))
                    Text(application.subject, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF065F46))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("বরাদ্দকৃত ব্যাচ:", fontSize = 11.sp, color = Color(0xFF166534))
                    Text(
                        if (application.assignedBatch.isNotBlank()) application.assignedBatch else "HSC ও SSC মাস্টার ব্যাচ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color(0xFF065F46)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("যোগদানের তারিখ:", fontSize = 11.sp, color = Color(0xFF166534))
                    Text(
                        if (application.joiningDate.isNotBlank()) application.joiningDate else "০১ অক্টোবর, ২০২৬",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color(0xFF065F46)
                    )
                }
            }

            if (application.interviewerNotes.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color.White.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "মূল্যায়ন মন্তব্য: \"${application.interviewerNotes}\"",
                        fontSize = 11.sp,
                        color = Color(0xFF166534),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
