package com.task.mysugr.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BGRepository @Inject constructor(private val  bloodGlucoseDao: BloodGlucoseDao) {

    fun getBgValues() = bloodGlucoseDao.getBGValues()

    suspend fun saveBGValue(bloodGlucose: BloodGlucose) = bloodGlucoseDao.saveBGValue(bloodGlucose)

}