package com.task.mysugr.domain

import javax.inject.Inject

class ConvertToMMOLUseCase @Inject constructor(){

    operator fun invoke (value: Double)  = value.div(ONE_MMOL)

    companion object{
        // 1 mmol/L is 18.0182 mg/DL
        const val ONE_MMOL = 18.0182

    }
}