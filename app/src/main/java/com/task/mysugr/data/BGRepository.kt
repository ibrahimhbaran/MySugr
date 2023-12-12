package com.task.mysugr.data

import kotlinx.coroutines.flow.Flow

interface BGRepository {

    fun getBgValues(): Flow<List<BloodGlucose>>
    suspend fun saveBGValue(bloodGlucose: BloodGlucose): Long
}