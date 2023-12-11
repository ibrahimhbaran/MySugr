package com.task.mysugr.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.task.mysugr.R
import com.task.mysugr.ui.model.BGModel
import com.task.mysugr.ui.model.UnitType
import com.task.mysugr.viewmodels.MainScreenViewModel


@Composable
fun HomeScreen(
    viewModel: MainScreenViewModel = viewModel()
) {
    // regex pattern for bg input
    val pattern = remember { Regex("^\\d*\$") }

    var bgValue by remember { mutableStateOf("") }
    val radioOptions = listOf(UnitType.MG, UnitType.MMOL)
    var selectedBgUnit by remember { mutableStateOf(radioOptions[0]) }

    val saveBgUiState by viewModel.saveBgUiStateStateFlow.collectAsState()

    val bgValues by viewModel.getAllBGValues(selectedBgUnit)
        .collectAsStateWithLifecycle(initialValue = emptyList())

    val averageBGValue by viewModel.getAverageBgValue(selectedBgUnit)
        .collectAsStateWithLifecycle(initialValue = 0.0)


    Column(modifier = Modifier.padding(16.dp)) {
        ShowAverageText(averageBg = averageBGValue, unit = stringResource(id = selectedBgUnit.resId))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            text = stringResource(R.string.add_measurement),
            style = typography.titleMedium
        )

        BGRadioGroup(
            options = radioOptions,
            selectedOption = selectedBgUnit,
            onOptionSelected = { selectedBgUnit = it }
        )

        BgInput(bgValue, selectedBgUnit) {
            if (it.matches(pattern)) {
                bgValue = it
            }
        }

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = saveBgUiState.message)

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.cleanErrorMessage()
                    viewModel.saveBG(bgValue, selectedBgUnit)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Save")
            }
        }

        BGValuesList(bgValues = bgValues, stringResource(id = selectedBgUnit.resId))

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BgInput(
    bg: String,
    selectedOption: UnitType,
    onBgChange: (String) -> Unit
) {
    Row(Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = bg,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier
                .padding(vertical = 30.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
            onValueChange = onBgChange,
            label = { Text(text = stringResource(R.string.enter_your_word)) },
            isError = false,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Bold),
            text = stringResource(id = selectedOption.resId)
        )
    }
}


@Composable
private fun BGRadioGroup(
    options: List<UnitType>,
    selectedOption: UnitType,
    onOptionSelected: (UnitType) -> Unit
) {
    options.forEach { item ->
        Row(
            Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (item == selectedOption),
                onClick = { onOptionSelected(item) },
            )

            ClickableText(
                text = AnnotatedString(stringResource(id = item.resId)),
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(fontWeight = FontWeight.Bold),
                onClick = {
                    onOptionSelected(item)
                }
            )
        }
    }
}


@Composable
fun ShowAverageText(averageBg: Double, unit: String) {

    Text(
        modifier = Modifier
            .clip(shapes.medium)
            .fillMaxWidth()
            .background(colorScheme.surfaceTint)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        text = stringResource(R.string.avarageText, averageBg, unit),
        color = colorScheme.onPrimary,
        style = typography.titleLarge
    )
}

@Composable
fun BGValuesList(bgValues: List<BGModel>, unit: String) {
    LazyColumn {
        items(bgValues) {
            BGRow(bgModel = it, unit)
        }
    }

}

@Composable
fun BGRow(bgModel: BGModel, unit: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = String.format("%.2f", bgModel.bgValue) + unit)
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Text(text = bgModel.dateTime)
        }
    }
}