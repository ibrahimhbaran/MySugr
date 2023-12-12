package com.task.mysugr.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BGRepositoryImpl @Inject constructor(private val bloodGlucoseDao: BloodGlucoseDao) :
    BGRepository {

    override fun getBgValues() = bloodGlucoseDao.getBGValues()

    override suspend fun saveBGValue(bloodGlucose: BloodGlucose): Long =
        bloodGlucoseDao.saveBGValue(bloodGlucose)

}