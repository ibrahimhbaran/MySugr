package com.task.mysugr.domain

import com.task.mysugr.ui.model.UnitType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AverageBGUseCse @Inject constructor(private val getBGValuesUseCase: GetBGValuesUseCase) {

    operator fun invoke(unitType: UnitType): Flow<Double> {
        return getBGValuesUseCase(unitType).map { bbgModels ->
            bbgModels.sumOf { it.bgValue } / bbgModels.size
        }
    }
}