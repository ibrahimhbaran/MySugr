package com.task.mysugr.domain

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.task.mysugr.ui.model.BGModel
import java.util.Date
import javax.inject.Inject

class CreateBGModelUseCase @Inject constructor() {

    @SuppressLint("SimpleDateFormat")
    operator fun invoke(bg: Double): BGModel {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        return BGModel(bg, currentDate)
    }

}