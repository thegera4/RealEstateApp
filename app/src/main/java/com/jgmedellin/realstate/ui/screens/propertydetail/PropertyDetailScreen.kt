package com.jgmedellin.realstate.ui.screens.propertydetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jgmedellin.realstate.data.model.Asset
import com.jgmedellin.realstate.data.model.Space
import com.jgmedellin.realstate.data.model.SpaceIcon
import com.jgmedellin.realstate.data.repository.MockRepository
import com.jgmedellin.realstate.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(
    propertyId: String,
    onBackClick: () -> Unit,
    onViewLogs: () -> Unit
) {
    val property = remember { MockRepository.getPropertyById(propertyId) }
    val spaces = remember { MockRepository.getSpacesForProperty(propertyId) }
    val assets = remember { MockRepository.getAssetsForProperty(propertyId) }
    var searchQuery by remember { mutableStateOf("") }
    var showAddSpaceSheet by remember { mutableStateOf(false) }
    var showAddAssetSheet by remember { mutableStateOf(false) }

    val filteredAssets = assets.filter {
        searchQuery.isEmpty() ||
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.model.contains(searchQuery, ignoreCase = true)
    }

    if (property == null) return

    Box(modifier = Modifier.fillMaxSize().background(DarkBackground)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // Header Image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(property.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = property.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        DarkBackground.copy(alpha = 0.8f),
                                        DarkBackground
                                    ),
                                    startY = 100f
                                )
                            )
                    )
                    // Back button
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                            .clip(CircleShape)
                            .background(DarkBackground.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                    // Property info overlay
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = property.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        if (property.tenant != null) {
                            Text(
                                text = "Tenant: ${property.tenant}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = property.address,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTertiary
                        )
                    }
                }
            }

            // Spaces Section
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Spaces",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        IconButton(
                            onClick = { showAddSpaceSheet = true },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(PrimaryBlue.copy(alpha = 0.15f))
                                .size(36.dp)
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add Space",
                                tint = PrimaryBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        spaces.forEach { space ->
                            SpaceItem(space = space)
                        }
                    }
                }
            }

            // Installed Assets Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Installed Assets",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(
                                onClick = onViewLogs,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(DarkSurfaceVariant)
                                    .size(36.dp)
                            ) {
                                Icon(
                                    Icons.Filled.History,
                                    contentDescription = "View Logs",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(
                                onClick = { showAddAssetSheet = true },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(PrimaryBlue.copy(alpha = 0.15f))
                                    .size(36.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "Add Asset",
                                    tint = PrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    // Search
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search assets...", color = TextTertiary) },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = null, tint = TextSecondary)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = SearchBarBg,
                            unfocusedContainerColor = SearchBarBg,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )
                }
            }

            // Asset Cards
            items(filteredAssets) { asset ->
                AssetCard(
                    asset = asset,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }

        // Add Space Bottom Sheet
        if (showAddSpaceSheet) {
            AddSpaceBottomSheet(onDismiss = { showAddSpaceSheet = false })
        }

        // Add Asset Bottom Sheet
        if (showAddAssetSheet) {
            AddAssetBottomSheet(onDismiss = { showAddAssetSheet = false })
        }
    }
}

@Composable
fun SpaceItem(space: Space) {
    val pair = when (space.icon) {
        SpaceIcon.KITCHEN -> Icons.Filled.Countertops to PastelOrange
        SpaceIcon.BATHROOM -> Icons.Filled.Bathtub to PastelBlue
        SpaceIcon.BEDROOM -> Icons.Filled.Bed to PastelPurple
        SpaceIcon.LIVING_ROOM -> Icons.Filled.Weekend to PastelGreen
        SpaceIcon.PATIO -> Icons.Filled.Yard to PastelTeal
        SpaceIcon.GARAGE -> Icons.Filled.Garage to PastelPink
        SpaceIcon.OFFICE -> Icons.Filled.Computer to PastelBlue
        SpaceIcon.LAUNDRY -> Icons.Filled.LocalLaundryService to PastelOrange
        SpaceIcon.DINING -> Icons.Filled.DinnerDining to PastelGreen
        SpaceIcon.STORAGE -> Icons.Filled.Warehouse to PastelPurple
    }
    val icon = pair.first
    val color = pair.second

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(68.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = space.name,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = space.name,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            maxLines = 1
        )
    }
}

@Composable
fun AssetCard(asset: Asset, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Asset Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(asset.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = asset.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = asset.model,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Location tag
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = PrimaryBlue.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = asset.location,
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryBlue,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = asset.purchaseDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.Receipt,
                        contentDescription = "Receipt",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.History,
                        contentDescription = "Logs",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSpaceBottomSheet(onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var spaceName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf(SpaceIcon.KITCHEN) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkSurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = TextTertiary) }
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add New Space",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = spaceName,
                onValueChange = { spaceName = it },
                label = { Text("Space Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerColor,
                    focusedContainerColor = DarkSurfaceVariant,
                    unfocusedContainerColor = DarkSurfaceVariant,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = TextSecondary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Select Icon",
                style = MaterialTheme.typography.titleSmall,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Icon Grid
            val iconEntries = SpaceIcon.entries.toList()
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                iconEntries.chunked(5).forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEach { spaceIcon ->
                            val isSelected = selectedIcon == spaceIcon
                            val (icon, color) = getSpaceIconData(spaceIcon)
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedIcon = spaceIcon },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) PrimaryBlue.copy(alpha = 0.2f) else DarkSurfaceVariant,
                                border = if (isSelected) {
                                    BorderStroke(1.5.dp, PrimaryBlue)
                                } else null
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = spaceIcon.label,
                                        tint = if (isSelected) PrimaryBlue else color,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = spaceIcon.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) PrimaryBlue else TextSecondary,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                        // Fill empty spots
                        repeat(5 - row.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Space", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetBottomSheet(onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var assetName by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkSurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = TextTertiary) }
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add New Asset",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Asset Name
            OutlinedTextField(
                value = assetName,
                onValueChange = { assetName = it },
                label = { Text("Asset Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerColor,
                    focusedContainerColor = DarkSurfaceVariant,
                    unfocusedContainerColor = DarkSurfaceVariant,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = TextSecondary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Room selection
            OutlinedTextField(
                value = room,
                onValueChange = { room = it },
                label = { Text("Room") },
                leadingIcon = { Icon(Icons.Filled.MeetingRoom, contentDescription = null, tint = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerColor,
                    focusedContainerColor = DarkSurfaceVariant,
                    unfocusedContainerColor = DarkSurfaceVariant,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = TextSecondary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Purchase Date
            OutlinedTextField(
                value = purchaseDate,
                onValueChange = { purchaseDate = it },
                label = { Text("Purchase Date") },
                leadingIcon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = DividerColor,
                    focusedContainerColor = DarkSurfaceVariant,
                    unfocusedContainerColor = DarkSurfaceVariant,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedLabelColor = PrimaryBlue,
                    unfocusedLabelColor = TextSecondary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Photo upload area
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable { },
                shape = RoundedCornerShape(12.dp),
                color = DarkSurfaceVariant,
                border = BorderStroke(1.dp, DividerColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Filled.CloudUpload,
                        contentDescription = null,
                        tint = TextTertiary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Receipt or Asset Photo",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Asset", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

fun getSpaceIconData(spaceIcon: SpaceIcon): Pair<ImageVector, Color> {
    return when (spaceIcon) {
        SpaceIcon.KITCHEN -> Icons.Filled.Countertops to PastelOrange
        SpaceIcon.BATHROOM -> Icons.Filled.Bathtub to PastelBlue
        SpaceIcon.BEDROOM -> Icons.Filled.Bed to PastelPurple
        SpaceIcon.LIVING_ROOM -> Icons.Filled.Weekend to PastelGreen
        SpaceIcon.PATIO -> Icons.Filled.Yard to PastelTeal
        SpaceIcon.GARAGE -> Icons.Filled.Garage to PastelPink
        SpaceIcon.OFFICE -> Icons.Filled.Computer to PastelBlue
        SpaceIcon.LAUNDRY -> Icons.Filled.LocalLaundryService to PastelOrange
        SpaceIcon.DINING -> Icons.Filled.DinnerDining to PastelGreen
        SpaceIcon.STORAGE -> Icons.Filled.Warehouse to PastelPurple
    }
}
