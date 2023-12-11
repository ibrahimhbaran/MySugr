package com.task.mysugr.domain

import com.task.mysugr.data.BGRepository
import com.task.mysugr.ui.model.BGModel
import com.task.mysugr.ui.model.UnitType
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBGValuesUseCase @Inject constructor(
    private val bgRepository: BGRepository,
    private val convertToMMOLUseCase: ConvertToMMOLUseCase
) {

    // by default all bg values are set as mg/Dl in database if user has chosen mmol then we need
    // convert the value to the mmol/L on UI else use mg/Dl as default
    operator fun invoke(unitType: UnitType) = bgRepository.getBgValues().map { bloodGlucoses ->
        bloodGlucoses.map { bg ->
            val bgValue = if (unitType == UnitType.MMOL) convertToMMOLUseCase(bg.value) else bg.value
            BGModel(bgValue,bg.dateTime)
        }
    }
}