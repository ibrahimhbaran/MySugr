package com.task.mysugr.domain

import javax.inject.Inject

class ConvertToMGUseCase @Inject constructor() {

    // this will convert MMOl to MG
   operator fun invoke (value: Double)  = value.times(ONE_MMOL)

    companion object{
        // 1 mmol/L is 18.0182 mg/DL
        const val ONE_MMOL = 18.0182
    }
}