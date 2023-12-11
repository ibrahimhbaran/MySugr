package com.task.mysugr.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.mysugr.data.BloodGlucose
import com.task.mysugr.domain.AverageBGUseCse
import com.task.mysugr.domain.ConvertToMGUseCase
import com.task.mysugr.domain.CreateBGModelUseCase
import com.task.mysugr.domain.GetBGValuesUseCase
import com.task.mysugr.domain.SaveBGUseCase
import com.task.mysugr.ui.model.BGModel
import com.task.mysugr.ui.model.UnitType
import com.task.mysugr.ui.screens.SaveBgUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val saveBGUseCase: SaveBGUseCase,
    private val createBGModelUseCase: CreateBGModelUseCase,
    private val convertToMGUseCase: ConvertToMGUseCase,
    private val getBGValuesUseCase: GetBGValuesUseCase,
    private val averageBGUseCse: AverageBGUseCse
) : ViewModel() {

    private val _saveBGUIState = MutableStateFlow(SaveBgUiState())
    val saveBgUiStateStateFlow: StateFlow<SaveBgUiState> = _saveBGUIState.asStateFlow()

    fun getAllBGValues(unitType: UnitType): Flow<List<BGModel>> = getBGValuesUseCase(unitType)

    fun getAverageBgValue(unitType: UnitType) : Flow<Double> = averageBGUseCse(unitType)

    fun saveBG(bgValue: String, unitType: UnitType) {
        try {
            if (bgValue.isEmpty()) throw IllegalArgumentException("Enter a value")
            var bg = Integer.parseInt(bgValue).toDouble()
            // we will save all values as mg/DL into our database
            if (unitType == UnitType.MMOL) {
                bg = convertToMGUseCase(bg)
            }
            val bloodGlucoseModel = createBGModelUseCase(bg)
            viewModelScope.launch {
                saveBGUseCase(
                    BloodGlucose(
                        bloodGlucoseModel.bgValue,
                        bloodGlucoseModel.dateTime
                    )
                )
            }
        } catch (e: Exception) {
            _saveBGUIState.value = SaveBgUiState(e.message.toString())
        }
    }


    fun cleanErrorMessage() {
        _saveBGUIState.value = SaveBgUiState("")
    }

}