package com.jgmedellin.realstate.data.model

data class Property(
    val id: String,
    val name: String,
    val address: String,
    val imageUrl: String,
    val status: PropertyStatus,
    val type: PropertyType,
    val tenant: String? = null,
    val monthlyRent: Double = 0.0,
    val spaces: List<Space> = emptyList(),
    val assets: List<Asset> = emptyList()
)

enum class PropertyStatus(val label: String) {
    URGENT("Urgent"),
    GOOD("Good"),
    OCCUPIED("Occupied"),
    VACANT("Vacant"),
    MAINTENANCE("Maintenance")
}

enum class PropertyType(val label: String) {
    HOME("Home"),
    COMMERCIAL("Commercial"),
    APARTMENT("Apartment")
}

data class Space(
    val id: String,
    val name: String,
    val icon: SpaceIcon,
    val propertyId: String
)

enum class SpaceIcon(val label: String) {
    KITCHEN("Kitchen"),
    BATHROOM("Bathroom"),
    BEDROOM("Bedroom"),
    LIVING_ROOM("Living Room"),
    PATIO("Patio"),
    GARAGE("Garage"),
    OFFICE("Office"),
    LAUNDRY("Laundry"),
    DINING("Dining"),
    STORAGE("Storage")
}

data class Asset(
    val id: String,
    val name: String,
    val model: String,
    val imageUrl: String,
    val location: String,
    val purchaseDate: String,
    val propertyId: String
)

data class MaintenanceTask(
    val id: String,
    val title: String,
    val description: String = "",
    val category: String = "Maintenance",
    val propertyName: String,
    val priority: TaskPriority,
    val dueDate: String,
    val isCompleted: Boolean = false,
    val assignee: String = ""
)

enum class TaskPriority(val label: String) {
    URGENT("Urgent"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low")
}

data class ActivityLog(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: String,
    val category: LogCategory,
    val propertyName: String
)

enum class LogCategory(val label: String) {
    MAINTENANCE("Maintenance"),
    TENANT("Tenant"),
    FINANCIAL("Financial"),
    INSPECTION("Inspection"),
    GENERAL("General")
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val jobTitle: String,
    val avatarUrl: String
)
