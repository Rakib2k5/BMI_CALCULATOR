package com.bmai.myapplication.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BMIDao {
    @Query("SELECT * FROM bmi_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<BMIEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBMI(bmi: BMIEntity)

    @Delete
    suspend fun deleteBMI(bmi: BMIEntity)

    @Query("DELETE FROM bmi_history")
    suspend fun deleteAllHistory()
}
