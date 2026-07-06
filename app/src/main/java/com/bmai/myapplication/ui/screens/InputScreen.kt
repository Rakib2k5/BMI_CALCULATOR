package com.bmai.myapplication.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bmai.myapplication.domain.model.Gender
import com.bmai.myapplication.ui.theme.PrimaryBlue
import com.bmai.myapplication.ui.theme.SecondaryBlue
import com.bmai.myapplication.util.UnitConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    viewModel: InputViewModel = hiltViewModel(),
    onCalculate: (Double, Double, Double, Int, String) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("VitalTrack", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text("Know Your Body", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Input your vital statistics for a clinical-grade health assessment.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            // Gender Selection
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("BIOLOGICAL SEX", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    GenderCard(
                        label = "Male",
                        isSelected = uiState.gender == Gender.MALE,
                        onClick = { viewModel.setGender(Gender.MALE) },
                        modifier = Modifier.weight(1f)
                    )
                    GenderCard(
                        label = "Female",
                        isSelected = uiState.gender == Gender.FEMALE,
                        onClick = { viewModel.setGender(Gender.FEMALE) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Age Stepper
            InfoCard(title = "AGE") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var ageText by remember(uiState.age) { mutableStateOf(uiState.age.toString()) }
                    TextField(
                        value = ageText,
                        onValueChange = {
                            if (it.all { c -> c.isDigit() }) {
                                ageText = it
                                it.toIntOrNull()?.let { newAge -> viewModel.setAge(newAge) }
                            }
                        },
                        textStyle = MaterialTheme.typography.headlineMedium.copy(color = PrimaryBlue, fontWeight = FontWeight.Bold),
                        modifier = Modifier.width(150.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = PrimaryBlue,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        suffix = { Text("YEARS") }
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { if (uiState.age > 1) viewModel.setAge(uiState.age - 1) }) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease")
                        }
                        IconButton(onClick = { if (uiState.age < 120) viewModel.setAge(uiState.age + 1) }) {
                            Icon(Icons.Default.Add, contentDescription = "Increase")
                        }
                    }
                }
            }

            // Height Slider
            InfoCard(title = "HEIGHT") {
                val isMetric = uiState.heightUnit == HeightUnit.CM
                val heightCm = uiState.heightCm
                
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        if (isMetric) {
                            var heightText by remember(heightCm) { mutableStateOf(heightCm.toInt().toString()) }
                            TextField(
                                value = heightText,
                                onValueChange = {
                                    if (it.all { c -> c.isDigit() }) {
                                        heightText = it
                                        it.toFloatOrNull()?.let { newHeight -> viewModel.setHeightCm(newHeight) }
                                    }
                                },
                                textStyle = MaterialTheme.typography.headlineMedium.copy(color = PrimaryBlue, fontWeight = FontWeight.Bold),
                                modifier = Modifier.width(150.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = PrimaryBlue,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                suffix = { Text("CM") }
                            )
                        } else {
                            val (ft, inch) = UnitConverter.cmToFeetInches(heightCm)
                            Text(
                                text = "$ft ft $inch in",
                                style = MaterialTheme.typography.headlineMedium,
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        UnitToggle(
                            isMetric = isMetric,
                            metricLabel = "CM",
                            imperialLabel = "FT",
                            onToggle = { viewModel.toggleHeightUnit() }
                        )
                    }

                    val sliderValue = if (isMetric) heightCm else heightCm / 2.54f
                    val animatedSliderValue by animateFloatAsState(targetValue = sliderValue, label = "HeightSlider")
                    
                    Slider(
                        value = animatedSliderValue,
                        onValueChange = { 
                            if (isMetric) viewModel.setHeightCm(it) 
                            else viewModel.setHeightCm(it * 2.54f)
                        },
                        valueRange = if (isMetric) 100f..250f else 39.37f..98.42f, // 100cm to 250cm in inches
                        colors = SliderDefaults.colors(thumbColor = PrimaryBlue, activeTrackColor = PrimaryBlue)
                    )
                }
            }

            // Weight Slider
            InfoCard(title = "WEIGHT") {
                val isMetric = uiState.weightUnit == WeightUnit.KG
                val weightKg = uiState.weightKg
                val displayWeight = if (isMetric) weightKg else UnitConverter.kgToLbs(weightKg)

                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        var weightText by remember(displayWeight) { mutableStateOf(String.format("%.1f", displayWeight)) }
                        TextField(
                            value = weightText,
                            onValueChange = {
                                if (it.isEmpty() || it.toDoubleOrNull() != null || it == "." || it.endsWith(".")) {
                                    weightText = it
                                    it.toFloatOrNull()?.let { newVal ->
                                        if (isMetric) viewModel.setWeightKg(newVal)
                                        else viewModel.setWeightFromLbs(newVal)
                                    }
                                }
                            },
                            textStyle = MaterialTheme.typography.headlineMedium.copy(color = PrimaryBlue, fontWeight = FontWeight.Bold),
                            modifier = Modifier.width(150.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = PrimaryBlue,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            suffix = { Text(if (isMetric) "KG" else "LB") }
                        )
                        UnitToggle(
                            isMetric = isMetric,
                            metricLabel = "KG",
                            imperialLabel = "LB",
                            onToggle = { viewModel.toggleWeightUnit() }
                        )
                    }

                    val animatedWeightValue by animateFloatAsState(targetValue = displayWeight, label = "WeightSlider")
                    
                    Slider(
                        value = animatedWeightValue,
                        onValueChange = { 
                            if (isMetric) viewModel.setWeightKg(it) 
                            else viewModel.setWeightFromLbs(it)
                        },
                        valueRange = if (isMetric) 30f..250f else 66f..550f,
                        colors = SliderDefaults.colors(thumbColor = PrimaryBlue, activeTrackColor = PrimaryBlue)
                    )
                }
            }

            Button(
                onClick = {
                    val score = viewModel.calculateBmi()
                    onCalculate(score, uiState.weightKg.toDouble(), uiState.heightCm.toDouble(), uiState.age, uiState.gender.name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Calculate BMI", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            // VitalTrack Pro Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue)
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("★", color = Color.White, fontSize = 24.sp)
                    }
                    Column {
                        Text("VitalTrack Pro", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Track your progress and sync with Health Connect for 24/7 monitoring.", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun GenderCard(label: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SecondaryBlue else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(if (isSelected) PrimaryBlue else Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(if (label == "Male") "♂" else "♀", color = if (isSelected) Color.White else Color.Gray, fontSize = 24.sp)
            }
            Text(label, fontWeight = FontWeight.Bold, color = if (isSelected) PrimaryBlue else Color.Gray)
        }
    }
}

@Composable
fun InfoCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = Color.Gray, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

@Composable
fun UnitToggle(
    isMetric: Boolean,
    metricLabel: String,
    imperialLabel: String,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        Text(
            metricLabel,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isMetric) Color.White else Color.Transparent)
                .clickable { onToggle(true) }
                .padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isMetric) PrimaryBlue else Color.Gray
        )
        Text(
            imperialLabel,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (!isMetric) Color.White else Color.Transparent)
                .clickable { onToggle(false) }
                .padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (!isMetric) PrimaryBlue else Color.Gray
        )
    }
}
