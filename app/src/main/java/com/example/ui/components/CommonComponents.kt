package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ApplicationStatus
import com.example.ui.theme.*

@Composable
fun StatusBadge(status: ApplicationStatus, modifier: Modifier = Modifier) {
    val (bgColor, textColor, icon) = when (status) {
        ApplicationStatus.PENDING_PAYMENT -> Triple(Color(0xFFFFF7ED), Color(0xFFC2410C), Icons.Default.PendingActions)
        ApplicationStatus.FEE_PAID -> Triple(Color(0xFFEFF6FF), Color(0xFF1D4ED8), Icons.Default.Paid)
        ApplicationStatus.INTERVIEW_SCHEDULED -> Triple(Color(0xFFFAF5FF), Color(0xFF7E22CE), Icons.Default.Event)
        ApplicationStatus.INTERVIEW_COMPLETED -> Triple(Color(0xFFF0FDFA), Color(0xFF0F766E), Icons.Default.HourglassBottom)
        ApplicationStatus.SELECTED -> Triple(Color(0xFFECFDF5), Color(0xFF047857), Icons.Default.Verified)
        ApplicationStatus.REJECTED -> Triple(Color(0xFFFEF2F2), Color(0xFFB91C1C), Icons.Default.Cancel)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, textColor.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = status.titleBn,
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RecruitmentStepIndicator(currentStep: Int, modifier: Modifier = Modifier) {
    val steps = listOf(
        "১. আবেদন",
        "২. ৯৯৯৳ ফি",
        "৩. ইন্টারভিউ",
        "৪. সিলেকশন"
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "নিয়োগ প্রক্রিয়া (৪টি সহজ ধাপ)",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                steps.forEachIndexed { index, stepName ->
                    val stepNumber = index + 1
                    val isCompleted = stepNumber < currentStep
                    val isCurrent = stepNumber == currentStep

                    val circleColor = when {
                        isCompleted -> StatusSuccess
                        isCurrent -> PrimaryBlue
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(circleColor)
                        ) {
                            if (isCompleted) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else {
                                Text(
                                    text = "$stepNumber",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stepName,
                            fontSize = 10.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SalaryHighlightCard(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFF59E0B), Color(0xFF10B981))
                ),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F172A),
                            Color(0xFF1E293B)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFFF59E0B).copy(alpha = 0.2f),
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = null,
                                    tint = Color(0xFFFBBF24),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "নিশ্চিত মাসিক বেতন",
                                color = Color(0xFF94A3B8),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "৳৩৫,০০০ টাকা / মাস",
                                color = Color(0xFFFBBF24),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Surface(
                        color = Color(0xFF10B981).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "রেজিস্ট্রেশন ফি: ৯৯৯৳",
                            color = Color(0xFF34D399),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFF334155))
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = Color(0xFF60A5FA),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "অনলাইন ও স্মার্ট ক্লাসরুম",
                            color = Color(0xFFCBD5E1),
                            fontSize = 11.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF34D399),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ইন্টারভিউ ভিত্তিক সিলেকশন",
                            color = Color(0xFFCBD5E1),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
