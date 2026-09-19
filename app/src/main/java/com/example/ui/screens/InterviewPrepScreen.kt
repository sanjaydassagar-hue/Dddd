package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.InterviewTip

@Composable
fun InterviewPrepScreen(
    tips: List<InterviewTip>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var expandedTipId by remember { mutableStateOf<Int?>(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Header Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CastForEducation,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "ইন্টারভিউ ও সিলেকশন প্রস্তুতি",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "মাসিক ৩৫,০০০ টাকা বেতনের শিক্ষক পদে নির্বাচিত হওয়ার গাইড",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Evaluation Rubric Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ইন্টারভিউ মূল্যায়ন নম্বর বিভাজন (১০০ নম্বর)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                val rubrics = listOf(
                    Triple("বিষয়ভিত্তিক দখল ও গভীরতা", "৪০ নম্বর", Color(0xFF2563EB)),
                    Triple("ডিজিটাল পেন-ট্যাবলেট ও বোর্ড প্রেজেন্টেশন", "২৫ নম্বর", Color(0xFF059669)),
                    Triple("বাচনভঙ্গি ও শিক্ষার্থী মনোযোগ আকর্ষণ", "২০ নম্বর", Color(0xFFD97706)),
                    Triple("৫ মিনিটের লাইভ ডেমো ক্লাস দক্ষতা", "১৫ নম্বর", Color(0xFF9333EA))
                )

                rubrics.forEach { (title, marks, color) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Surface(
                            color = color.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = marks,
                                color = color,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ইন্টারভিউতে জিজ্ঞাস্য গুরুত্বপূর্ণ প্রশ্নোত্তর:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        tips.forEach { tip ->
            val isExpanded = expandedTipId == tip.id
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isExpanded) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                    else MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        expandedTipId = if (isExpanded) null else tip.id
                    }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = tip.categoryBn,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tip.questionBn,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column {
                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "আদর্শ উত্তর ও টিপস:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = Color(0xFF059669)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tip.guidanceBn,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
