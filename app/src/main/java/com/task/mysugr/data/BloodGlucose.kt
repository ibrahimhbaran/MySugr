package com.task.mysugr.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BloodGlucose(
    val value: Double,
    val dateTime: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
