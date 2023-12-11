package com.task.mysugr.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodGlucoseDao {

    @Insert
    suspend fun saveBGValue( bloodGlucose: BloodGlucose): Long

    @Query("SELECT * FROM bloodglucose ORDER by dateTime DESC")
    fun getBGValues() : Flow<List<BloodGlucose>>
}