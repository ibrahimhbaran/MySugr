package com.task.mysugr.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class BGRepositoryTest{

    @Test
    fun bgRepositoryGetBGValuesTest() = runTest{
        val fakeDao = FakeBloodGlucoseDao()
        val repository = BGRepositoryImpl(fakeDao)
        val bgItem = repository.getBgValues().first()[0]
        assertEquals(BG_VALUE, bgItem.value)
        assertEquals(BG_SAVE_DATE_TIME,bgItem.dateTime)

    }

    @Test
    fun bgRepositorySaveBGValueTest() = runTest {
        val fakeDao = FakeBloodGlucoseDao()
        val repository = BGRepositoryImpl(fakeDao)
        val saveBGSuccessValue = repository.saveBGValue(BloodGlucose(BG_VALUE, BG_SAVE_DATE_TIME))
        assertEquals(BG_SAVE_VALUE, saveBGSuccessValue)

    }

    class FakeBloodGlucoseDao(): BloodGlucoseDao{

        override suspend fun saveBGValue(bloodGlucose: BloodGlucose): Long {
            return BG_SAVE_VALUE
        }

        override fun getBGValues(): Flow<List<BloodGlucose>> {
            return flow {
                emit( listOf<BloodGlucose>(BloodGlucose(BG_VALUE, BG_SAVE_DATE_TIME)))
            }
        }

    }

    companion object{
        const val BG_VALUE:Double = 60.0
        const val BG_SAVE_DATE_TIME:String = "12/12/2023"
        const val BG_SAVE_VALUE: Long = 1
    }

}

