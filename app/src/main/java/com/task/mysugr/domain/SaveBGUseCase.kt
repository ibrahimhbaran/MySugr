package com.task.mysugr.domain

import com.task.mysugr.data.BGRepository
import com.task.mysugr.data.BloodGlucose
import javax.inject.Inject

class SaveBGUseCase @Inject constructor(private val bgRepository: BGRepository) {

    suspend operator fun invoke(bg: BloodGlucose) = bgRepository.saveBGValue(bg)

}