package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.ApplyFormState
import com.example.ui.components.RecruitmentStepIndicator
import com.example.ui.components.SalaryHighlightCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyScreen(
    formState: ApplyFormState,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onQualificationChange: (String) -> Unit,
    onInstitutionChange: (String) -> Unit,
    onSubjectChange: (String) -> Unit,
    onExperienceChange: (String) -> Unit,
    onTargetClassChange: (String) -> Unit,
    onShiftChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var qualificationExpanded by remember { mutableStateOf(false) }
    var subjectExpanded by remember { mutableStateOf(false) }
    var experienceExpanded by remember { mutableStateOf(false) }
    var targetClassExpanded by remember { mutableStateOf(false) }
    var shiftExpanded by remember { mutableStateOf(false) }

    val qualifications = listOf(
        "B.Sc (Hons) in Mathematics",
        "M.Sc in Physics",
        "B.Sc in Chemistry",
        "B.A / M.A in English Literature",
        "B.Sc in Computer Science / ICT",
        "B.Sc in Biology / Botany / Zoology",
        "B.A (Hons) in Bengali",
        "অন্যান্য স্নাতক / স্নাতকোত্তর"
    )

    val subjects = listOf(
        "উচ্চতর গণিত (Higher Math)",
        "সাধারণ গণিত (General Math)",
        "পদার্থবিজ্ঞান (Physics)",
        "রসায়ন (Chemistry)",
        "জীববিজ্ঞান (Biology)",
        "ইংরেজি (English 1st & 2nd)",
        "বাংলা (Bangla)",
        "তথ্য ও যোগাযোগ প্রযুক্তি (ICT)"
    )

    val experiences = listOf(
        "নতুন / ফ্রেশার (যোগ্যতা ও মেধাভিত্তিক)",
        "১-২ বছর অনলাইন/অফলাইন অভিজ্ঞতা",
        "৩-৫ বছর শিক্ষকতার অভিজ্ঞতা",
        "৫+ বছর কোচিং শিক্ষকতার অভিজ্ঞতা"
    )

    val classLevels = listOf(
        "Class 9-10 ও SSC পরীক্ষার্থী",
        "HSC ও কলেজ পর্যায়",
        "বিশ্ববিদ্যালয় ভর্তি কোচিং (Admission)",
        "Class 6-8 বেসিক ফাউন্ডেশন"
    )

    val shifts = listOf(
        "সন্ধ্যা শিফট (৬:০০ - ৮:০০)",
        "সকাল শিফট (৯:০০ - ১১:০০)",
        "বিকাল শিফট (৩:০০ - ৫:০০)",
        "উইকেন্ড স্পেশাল (শুক্রবার ও শনিবার)"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Hero Image Banner
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.img_coaching_hero),
                    contentDescription = "Digital Coaching Center Hero",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xCC0A192F))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Surface(
                        color = Color(0xFFF59E0B),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "স্মার্ট ডিজিটাল ক্লাসরুম",
                            color = Color(0xFF0F172A),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "ডিজিটাল কোচিং সেন্টারে শিক্ষক নিয়োগ",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "অনলাইনে লাইভ ক্লাসের মাধ্যমে পাঠদান ও উচ্চ আয়ের সুযোগ",
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Salary and Fee Highlight
        SalaryHighlightCard()

        Spacer(modifier = Modifier.height(16.dp))

        // Process Stepper
        RecruitmentStepIndicator(currentStep = 1)

        Spacer(modifier = Modifier.height(16.dp))

        // Registration Form Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "শিক্ষক নিবন্ধন ফরম (Application Form)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "নাম নথিভুক্ত করতে ৯৯৯ টাকা ফি প্রযোজ্য হবে",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Full Name
                OutlinedTextField(
                    value = formState.fullName,
                    onValueChange = onNameChange,
                    label = { Text("আপনার পুরো নাম (Full Name) *") },
                    placeholder = { Text("যেমন: তানভীর আহমেদ") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("apply_full_name_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Phone
                OutlinedTextField(
                    value = formState.phone,
                    onValueChange = onPhoneChange,
                    label = { Text("মোবাইল নম্বর (Phone Number) *") },
                    placeholder = { Text("01XXXXXXXXX") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("apply_phone_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Email
                OutlinedTextField(
                    value = formState.email,
                    onValueChange = onEmailChange,
                    label = { Text("ইমেইল (Email Address)") },
                    placeholder = { Text("teacher@example.com") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("apply_email_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Qualification Dropdown
                ExposedDropdownMenuBox(
                    expanded = qualificationExpanded,
                    onExpandedChange = { qualificationExpanded = !qualificationExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = formState.qualification,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("সর্বশেষ শিক্ষাগত যোগ্যতা *") },
                        leadingIcon = { Icon(Icons.Default.School, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = qualificationExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = qualificationExpanded,
                        onDismissRequest = { qualificationExpanded = false }
                    ) {
                        qualifications.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    onQualificationChange(item)
                                    qualificationExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Institution
                OutlinedTextField(
                    value = formState.institution,
                    onValueChange = onInstitutionChange,
                    label = { Text("বিশ্ববিদ্যালয় / শিক্ষা প্রতিষ্ঠান *") },
                    placeholder = { Text("যেমন: ঢাকা বিশ্ববিদ্যালয়, বুয়েট, ইত্যাদি") },
                    leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("apply_institution_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Subject Dropdown
                ExposedDropdownMenuBox(
                    expanded = subjectExpanded,
                    onExpandedChange = { subjectExpanded = !subjectExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = formState.subject,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("যে বিষয়ে পড়াতে আগ্রহী (Subject) *") },
                        leadingIcon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = subjectExpanded,
                        onDismissRequest = { subjectExpanded = false }
                    ) {
                        subjects.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    onSubjectChange(item)
                                    subjectExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Experience Dropdown
                ExposedDropdownMenuBox(
                    expanded = experienceExpanded,
                    onExpandedChange = { experienceExpanded = !experienceExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = formState.experience,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("শিক্ষকতার অভিজ্ঞতা (Experience)") },
                        leadingIcon = { Icon(Icons.Default.WorkOutline, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = experienceExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = experienceExpanded,
                        onDismissRequest = { experienceExpanded = false }
                    ) {
                        experiences.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    onExperienceChange(item)
                                    experienceExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Class Level Dropdown
                ExposedDropdownMenuBox(
                    expanded = targetClassExpanded,
                    onExpandedChange = { targetClassExpanded = !targetClassExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = formState.targetClass,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("টার্গেট ক্লাস / লেভেল") },
                        leadingIcon = { Icon(Icons.Default.Groups, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = targetClassExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = targetClassExpanded,
                        onDismissRequest = { targetClassExpanded = false }
                    ) {
                        classLevels.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    onTargetClassChange(item)
                                    targetClassExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Preferred Shift Dropdown
                ExposedDropdownMenuBox(
                    expanded = shiftExpanded,
                    onExpandedChange = { shiftExpanded = !shiftExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = formState.preferredShift,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("পছন্দনীয় ক্লাস সময় (Shift)") },
                        leadingIcon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = shiftExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = shiftExpanded,
                        onDismissRequest = { shiftExpanded = false }
                    ) {
                        shifts.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    onShiftChange(item)
                                    shiftExpanded = false
                                }
                            )
                        }
                    }
                }

                if (formState.errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = formState.errorMessage,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Submit Button
                Button(
                    onClick = onSubmit,
                    enabled = !formState.isSubmitting,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("submit_registration_button")
                ) {
                    if (formState.isSubmitting) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("আবেদন জমা হচ্ছে...")
                    } else {
                        Icon(Icons.Default.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "আবেদন জমা দিন ও ৯৯৯৳ ফি পরিশোধ করুন",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
