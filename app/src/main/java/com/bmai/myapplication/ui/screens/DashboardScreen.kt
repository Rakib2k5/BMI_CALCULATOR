package com.bmai.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bmai.myapplication.ui.theme.PrimaryBlue

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.bmai.myapplication.domain.model.BMIResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: BMIViewModel = hiltViewModel()) {
    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Notifications, null) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Settings, null) }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                MetricOverviewCard()
            }
            item {
                TrendsCard()
            }
            item {
                QuickActionsCard()
            }
            item {
                GoalProgressCard()
            }
            
            items(history.size) { index ->
                HistoryItem(history[index])
            }
        }
    }
}

@Composable
fun HistoryItem(result: BMIResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(result.category.color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = String.format("%.1f", result.score),
                    color = result.category.color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(result.category.label, fontWeight = FontWeight.Bold)
                Text("${result.weight} kg • ${result.height} cm", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Text(
                text = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(result.timestamp),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MetricOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("YOUR RESULT", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                Surface(color = Color(0xFFDCFCE7), shape = RoundedCornerShape(8.dp)) {
                    Text("Healthy", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = Color(0xFF16A34A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Text("22.5", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
            
            // Range Bar
            Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(4.dp))) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(18.5f).fillMaxHeight().background(Color(0xFF3B82F6)))
                    Box(modifier = Modifier.weight(6.5f).fillMaxHeight().background(Color(0xFF22C55E)))
                    Box(modifier = Modifier.weight(5f).fillMaxHeight().background(Color(0xFFEAB308)))
                    Box(modifier = Modifier.weight(10f).fillMaxHeight().background(Color(0xFFEF4444)))
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("15.0", fontSize = 10.sp, color = Color.Gray)
                Text("18.5", fontSize = 10.sp, color = Color.Gray)
                Text("25.0", fontSize = 10.sp, color = Color.Gray)
                Text("30.0", fontSize = 10.sp, color = Color.Gray)
                Text("40.0", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun TrendsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Trends", fontWeight = FontWeight.Bold)
                Text("+2.1% IMPROVEMENT", color = Color(0xFF16A34A), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Text("BMI History - Last 6 Mo.", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            
            // Mock Chart
            Row(modifier = Modifier.fillMaxWidth().height(100.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Bottom) {
                listOf(0.4f, 0.5f, 0.6f, 0.8f, 0.9f, 1f).forEach { height ->
                    Box(modifier = Modifier.weight(1f).fillMaxHeight(height).background(PrimaryBlue.copy(alpha = height), RoundedCornerShape(4.dp)))
                }
            }
        }
    }
}

@Composable
fun QuickActionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Quick Actions", color = Color.White, fontWeight = FontWeight.Bold)
            QuickActionItem(Icons.Default.Bookmark, "Save to Profile")
            QuickActionItem(Icons.Default.Download, "Download PDF Report")
            QuickActionItem(Icons.Default.Refresh, "Recalculate")
        }
    }
}

@Composable
fun QuickActionItem(icon: ImageVector, label: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp),
        onClick = {}
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.White, fontSize = 14.sp)
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun GoalProgressCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Goal Progress", fontWeight = FontWeight.Bold)
                Text("84% Reach", color = Color(0xFF16A34A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            LinearProgressIndicator(
                progress = { 0.84f },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = Color(0xFF22C55E),
                trackColor = Color.LightGray.copy(alpha = 0.2f),
                strokeCap = StrokeCap.Round
            )
            TextButton(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("View Detailed Dashboard", color = PrimaryBlue)
            }
        }
    }
}
