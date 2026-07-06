package com.bmai.myapplication.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bmai.myapplication.domain.model.BMICategory
import com.bmai.myapplication.ui.theme.*
import androidx.compose.foundation.layout.offset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    score: Double,
    weight: Double,
    height: Double,
    age: Int,
    gender: String,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    val category = BMICategory.fromScore(score)
    val animatedScore by animateFloatAsState(
        targetValue = score.toFloat(),
        animationSpec = tween(durationMillis = 1000),
        label = "ScoreAnimation"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VitalTrack", fontWeight = FontWeight.Bold, color = PrimaryBlue) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Profile */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // BMI Gauge Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                        BMIGauge(score = animatedScore, color = category.color)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = String.format("%.1f", animatedScore),
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = category.color
                            )
                            Text("BMI SCORE", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                        }
                    }

                    Surface(
                        color = category.color.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(100.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = category.color, modifier = Modifier.size(16.dp))
                            Text(category.label, color = category.color, fontWeight = FontWeight.Bold)
                        }
                    }

                    Text(
                        text = "Great Progress, Alex!", // Placeholder name
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // BMI Category Range Bar
                    Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(4.dp))) {
                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.weight(18.5f).fillMaxHeight().background(UnderweightBlue))
                            Box(modifier = Modifier.weight(6.5f).fillMaxHeight().background(HealthyGreen))
                            Box(modifier = Modifier.weight(5f).fillMaxHeight().background(OverweightYellow))
                            Box(modifier = Modifier.weight(10f).fillMaxHeight().background(ObeseRed))
                        }
                        // Indicator for current score
                        val indicatorPos = (score / 40f).coerceIn(0.0, 1.0).toFloat()
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(4.dp)
                                .align(Alignment.CenterStart)
                                .offset(x = 200.dp * indicatorPos) // This is a rough estimate, should be calculated based on width
                                .background(Color.Black, RoundedCornerShape(2.dp))
                        )
                    }

                    Text(
                        text = "Your BMI is within the optimal range. Maintaining this balance reduces the risk of heart disease and boosts energy levels.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            // Quick Actions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ActionButton(icon = Icons.Default.Bookmark, label = "Save", onClick = {})
                ActionButton(icon = Icons.Default.Share, label = "Share", onClick = {})
                ActionButton(icon = Icons.Default.PictureAsPdf, label = "PDF", onClick = {})
                ActionButton(icon = Icons.Default.Refresh, label = "Retry", onClick = onRetry)
            }

            // Recommendations
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Health Metrics", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    val bmr = if (gender == "MALE") {
                        (10 * weight) + (6.25 * height) - (5 * age) + 5
                    } else {
                        (10 * weight) + (6.25 * height) - (5 * age) - 161
                    }
                    val bodyFat = (1.20 * score) + (0.23 * age) - (10.8 * (if (gender == "MALE") 1 else 0)) - 5.4
                    
                    MetricCard(title = "Est. BMR", value = "${bmr.toInt()} kcal", modifier = Modifier.weight(1f))
                    MetricCard(title = "Est. Body Fat", value = "${String.format("%.1f", bodyFat)}%", modifier = Modifier.weight(1f))
                }
            }

            // Recommendations
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Personalized Next Steps", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                
                RecommendationCard(
                    icon = Icons.Default.FitnessCenter,
                    title = "Activity",
                    description = "Add 15 minutes of zone 2 cardio today.",
                    color = Color(0xFFE5E7FF),
                    iconColor = Color(0xFF4F46E5)
                )
                RecommendationCard(
                    icon = Icons.Default.Restaurant,
                    title = "Nutrition",
                    description = "Increase lean protein intake by 20g.",
                    color = Color(0xFFDCFCE7),
                    iconColor = Color(0xFF16A34A)
                )
                RecommendationCard(
                    icon = Icons.Default.WaterDrop,
                    title = "Hydration",
                    description = "Drink 500ml water before your next meal.",
                    color = Color(0xFFE0F2FE),
                    iconColor = Color(0xFF0284C7)
                )
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryBlue)
        }
    }
}

@Composable
fun BMIGauge(score: Float, color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 15.dp.toPx()
        val sweepAngle = (score / 40f * 360f).coerceAtMost(360f)
        
        drawCircle(
            color = Color.LightGray.copy(alpha = 0.2f),
            style = Stroke(width = strokeWidth)
        )
        
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun ActionButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Icon(icon, contentDescription = label, tint = PrimaryBlue)
        }
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun RecommendationCard(icon: ImageVector, title: String, description: String, color: Color, iconColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}
