package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Digital Coaching", appName)
  }

  @Test
  fun `verify teacher registration fee and monthly salary defaults`() {
    val app = com.example.model.TeacherApplication(
      applicantId = "DCC-2026-TEST",
      fullName = "আহমেদ হাসান",
      phone = "01700000000",
      email = "ahmed@test.com",
      qualification = "B.Sc in Mathematics",
      institution = "DU",
      subject = "উচ্চতর গণিত",
      experience = "২ বছর",
      targetClass = "SSC"
    )
    assertEquals(999, app.registrationFee)
    assertEquals(35000, app.monthlySalary)
    assertEquals(com.example.model.ApplicationStatus.PENDING_PAYMENT, app.status)
  }
}
