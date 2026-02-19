package com.jgmedellin.realstate.ui.screens.activitylogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jgmedellin.realstate.data.model.ActivityLog
import com.jgmedellin.realstate.data.model.LogCategory
import com.jgmedellin.realstate.data.repository.MockRepository
import com.jgmedellin.realstate.ui.theme.*

@Composable
fun ActivityLogsScreen() {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Maintenance", "Tenant", "Financial", "Inspection", "General")

    val logs = remember { MockRepository.activityLogs }
    val filteredLogs = logs.filter { log ->
        selectedCategory == "All" || log.category.label == selectedCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Activity Logs",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Filter Chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            categories.take(4).forEach { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ChipSelectedBg,
                        selectedLabelColor = ChipSelectedText,
                        containerColor = ChipUnselectedBg,
                        labelColor = ChipUnselectedText
                    ),
                    border = null,
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        // Timeline List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredLogs) { log ->
                LogCard(log = log)
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun LogCard(log: ActivityLog) {
    val (icon, color) = when (log.category) {
        LogCategory.MAINTENANCE -> Icons.Filled.Build to PastelOrange
        LogCategory.TENANT -> Icons.Filled.Person to PastelBlue
        LogCategory.FINANCIAL -> Icons.Filled.AttachMoney to PastelGreen
        LogCategory.INSPECTION -> Icons.Filled.Search to PastelPurple
        LogCategory.GENERAL -> Icons.Filled.Info to PastelTeal
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Category icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = log.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = log.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = log.propertyName,
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryBlue
                    )
                    Text(
                        text = log.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                }
            }
        }
    }
}
