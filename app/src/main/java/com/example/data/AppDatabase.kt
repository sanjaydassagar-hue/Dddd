package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.model.ApplicationStatus
import com.example.model.TeacherApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TeacherApplication::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teacherDao(): TeacherDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "digital_coaching_db"
                )
                .addCallback(DatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.teacherDao())
                    }
                }
            }

            suspend fun populateInitialData(dao: TeacherDao) {
                val demoList = listOf(
                    TeacherApplication(
                        applicantId = "DCC-2026-1001",
                        fullName = "তানভীর আহমেদ (Tanvir Ahmed)",
                        phone = "01711223344",
                        email = "tanvir.math@gmail.com",
                        qualification = "M.Sc in Applied Mathematics",
                        institution = "ঢাকা বিশ্ববিদ্যালয় (DU)",
                        subject = "উচ্চতর গণিত (Higher Math)",
                        experience = "৩ বছর",
                        targetClass = "HSC ও বিশ্ববিদ্যালয় ভর্তি",
                        preferredShift = "সন্ধ্যা শিফট (৬:০০ - ৮:০০)",
                        isFeePaid = true,
                        paymentMethod = "bKash",
                        senderPhone = "01711223344",
                        trxId = "BK999X7281",
                        paymentDate = "১৮ সেপ্টেম্বর, ২০২৬",
                        status = ApplicationStatus.SELECTED,
                        interviewDate = "১৯ সেপ্টেম্বর, ২০২৬",
                        interviewTime = "সকাল ১১:০০ টা",
                        interviewMode = "অনলাইন (Google Meet)",
                        interviewLink = "https://meet.google.com/dcc-math-2026",
                        interviewScore = 94,
                        interviewerNotes = "অসাধারণ উপস্থাপনা ও গণিতের সমস্যা সমাধানে অত্যন্ত পারদর্শী। স্মার্ট বোর্ডে ক্লাস নেয়ার অভিজ্ঞতা চমৎকার।",
                        assignedBatch = "HSC-2026 উচ্চতর গণিত আল্টিমেট ব্যাচ",
                        joiningDate = "০১ অক্টোবর, ২০২৬",
                        appliedTimestamp = System.currentTimeMillis() - 86400000L * 2
                    ),
                    TeacherApplication(
                        applicantId = "DCC-2026-1002",
                        fullName = "ফারহানা সুলতানা (Farhana Sultana)",
                        phone = "01988776655",
                        email = "farhana.eng@gmail.com",
                        qualification = "B.A (Hons), M.A in English Literature",
                        institution = "জাহাঙ্গীরনগর বিশ্ববিদ্যালয় (JU)",
                        subject = "ইংরেজি (English 1st & 2nd Paper)",
                        experience = "২ বছর",
                        targetClass = "Class 9-10 & SSC",
                        preferredShift = "সকাল শিফট (৯:০০ - ১১:০০)",
                        isFeePaid = true,
                        paymentMethod = "Nagad",
                        senderPhone = "01988776655",
                        trxId = "NG999A4410",
                        paymentDate = "১৯ সেপ্টেম্বর, ২০২৬",
                        status = ApplicationStatus.INTERVIEW_SCHEDULED,
                        interviewDate = "২১ সেপ্টেম্বর, ২০২৬",
                        interviewTime = "বিকাল ৪:০০ টা",
                        interviewMode = "অনলাইন (Google Meet)",
                        interviewLink = "https://meet.google.com/dcc-eng-interview",
                        interviewScore = 0,
                        interviewerNotes = "সিভি যাচাই সম্পন্ন। ইন্টারভিউ বোর্ড সদস্যগণ তৈরি আছেন।",
                        assignedBatch = "SSC ইংলিশ গ্রামার বুট ক্যাম্প",
                        joiningDate = "",
                        appliedTimestamp = System.currentTimeMillis() - 86400000L
                    )
                )
                dao.insertAll(demoList)
            }
        }
    }
}
