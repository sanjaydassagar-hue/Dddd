package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.TeacherApplication
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao {
    @Query("SELECT * FROM teacher_applications ORDER BY appliedTimestamp DESC")
    fun getAllApplications(): Flow<List<TeacherApplication>>

    @Query("SELECT * FROM teacher_applications WHERE id = :id LIMIT 1")
    fun getApplicationById(id: Long): Flow<TeacherApplication?>

    @Query("SELECT * FROM teacher_applications ORDER BY appliedTimestamp DESC LIMIT 1")
    fun getLatestApplication(): Flow<TeacherApplication?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(application: TeacherApplication): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(applications: List<TeacherApplication>)

    @Update
    suspend fun updateApplication(application: TeacherApplication)

    @Delete
    suspend fun deleteApplication(application: TeacherApplication)

    @Query("SELECT COUNT(*) FROM teacher_applications")
    suspend fun getCount(): Int
}
