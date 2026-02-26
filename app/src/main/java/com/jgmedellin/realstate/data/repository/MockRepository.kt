package com.jgmedellin.realstate.data.repository

import com.jgmedellin.realstate.data.model.*

object MockRepository {

    val currentUser = User(
        id = "u1",
        name = "Alex Johnson",
        email = "alex.johnson@realestate.com",
        jobTitle = "Property Manager",
        avatarUrl = "https://i.pravatar.cc/150?img=12"
    )

    val properties = listOf(
        Property(
            id = "p1",
            name = "Sunset Villa",
            address = "123 Ocean Drive, Miami, FL",
            imageUrl = "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=400",
            status = PropertyStatus.GOOD,
            type = PropertyType.HOME,
            tenant = "John Smith",
            monthlyRent = 3500.0
        ),
        Property(
            id = "p2",
            name = "Metro Tower",
            address = "456 Business Ave, NYC, NY",
            imageUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=400",
            status = PropertyStatus.URGENT,
            type = PropertyType.COMMERCIAL,
            tenant = "TechCorp Inc.",
            monthlyRent = 12000.0
        ),
        Property(
            id = "p3",
            name = "Green Park Apt",
            address = "789 Garden Ln, Chicago, IL",
            imageUrl = "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=400",
            status = PropertyStatus.OCCUPIED,
            type = PropertyType.APARTMENT,
            tenant = "Maria Garcia",
            monthlyRent = 2200.0
        ),
        Property(
            id = "p4",
            name = "Lakeside Cottage",
            address = "321 Lake Rd, Austin, TX",
            imageUrl = "https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=400",
            status = PropertyStatus.VACANT,
            type = PropertyType.HOME,
            monthlyRent = 2800.0
        ),
        Property(
            id = "p5",
            name = "Downtown Lofts",
            address = "555 Main St, Denver, CO",
            imageUrl = "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=400",
            status = PropertyStatus.MAINTENANCE,
            type = PropertyType.APARTMENT,
            tenant = "David Lee",
            monthlyRent = 1800.0
        ),
        Property(
            id = "p6",
            name = "Harbor Office",
            address = "888 Pier Blvd, Seattle, WA",
            imageUrl = "https://images.unsplash.com/photo-1497366216548-37526070297c?w=400",
            status = PropertyStatus.GOOD,
            type = PropertyType.COMMERCIAL,
            tenant = "StartupHub LLC",
            monthlyRent = 8500.0
        )
    )

    private val spaces = listOf(
        Space("s1", "Kitchen", SpaceIcon.KITCHEN, "p1"),
        Space("s2", "Bathroom", SpaceIcon.BATHROOM, "p1"),
        Space("s3", "Bedroom", SpaceIcon.BEDROOM, "p1"),
        Space("s4", "Living Room", SpaceIcon.LIVING_ROOM, "p1"),
        Space("s5", "Patio", SpaceIcon.PATIO, "p1"),
        Space("s6", "Garage", SpaceIcon.GARAGE, "p1"),
        Space("s7", "Kitchen", SpaceIcon.KITCHEN, "p2"),
        Space("s8", "Office", SpaceIcon.OFFICE, "p2"),
        Space("s9", "Storage", SpaceIcon.STORAGE, "p2"),
        Space("s10", "Kitchen", SpaceIcon.KITCHEN, "p3"),
        Space("s11", "Bathroom", SpaceIcon.BATHROOM, "p3"),
        Space("s12", "Bedroom", SpaceIcon.BEDROOM, "p3"),
        Space("s13", "Laundry", SpaceIcon.LAUNDRY, "p3"),
        Space("s14", "Kitchen", SpaceIcon.KITCHEN, "p4"),
        Space("s15", "Dining", SpaceIcon.DINING, "p4"),
        Space("s16", "Bedroom", SpaceIcon.BEDROOM, "p4"),
        Space("s17", "Office", SpaceIcon.OFFICE, "p5"),
        Space("s18", "Kitchen", SpaceIcon.KITCHEN, "p5"),
        Space("s19", "Bathroom", SpaceIcon.BATHROOM, "p5"),
        Space("s20", "Office", SpaceIcon.OFFICE, "p6"),
        Space("s21", "Storage", SpaceIcon.STORAGE, "p6")
    )

    private val assets = listOf(
        Asset("a1", "Samsung Refrigerator", "RF28R7551SR", "https://images.unsplash.com/photo-1571175443880-49e1d25b2bc5?w=200", "Kitchen", "Jan 2024", "p1"),
        Asset("a2", "LG Washer", "WM4500HBA", "https://images.unsplash.com/photo-1626806787461-102c1bfaaea1?w=200", "Laundry", "Mar 2023", "p1"),
        Asset("a3", "Carrier AC Unit", "24ACC636A003", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Living Room", "Jun 2023", "p1"),
        Asset("a4", "Bosch Dishwasher", "SHPM88Z75N", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Kitchen", "Aug 2023", "p1"),
        Asset("a5", "Rheem Water Heater", "XE50T06ST45U1", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Garage", "Nov 2022", "p1"),
        Asset("a6", "Dell Monitors", "P2722H", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Office", "Feb 2024", "p2"),
        Asset("a7", "Industrial HVAC", "Trane XR15", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Building", "Jan 2023", "p2"),
        Asset("a8", "GE Microwave", "JES1657SMSS", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Kitchen", "Sep 2023", "p3"),
        Asset("a9", "Whirlpool Dryer", "WED5000DW", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Laundry", "Apr 2023", "p3"),
        Asset("a10", "Ring Doorbell", "Pro 2", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=200", "Front Door", "Jul 2024", "p4")
    )

    val maintenanceTasks = listOf(
        MaintenanceTask("m1", "Fix leaking faucet", "Water is dripping from the kitchen faucet", "Plumbing", "Sunset Villa", TaskPriority.HIGH, "Feb 20, 2025", assignee = "Mike"),
        MaintenanceTask("m2", "Replace AC filter", "Standard quarterly filter replacement", "HVAC", "Metro Tower", TaskPriority.MEDIUM, "Feb 22, 2025", assignee = "Sarah"),
        MaintenanceTask("m3", "Paint exterior walls", "Refreshing the main entrance appearance", "General", "Green Park Apt", TaskPriority.LOW, "Mar 01, 2025", assignee = "Tom"),
        MaintenanceTask("m4", "Repair garage door", "Sensor is not detecting the remote", "Electrical", "Lakeside Cottage", TaskPriority.HIGH, "Feb 18, 2025", assignee = "Mike"),
        MaintenanceTask("m5", "Inspect fire alarms", "Testing all smoke detectors on every floor", "Safety", "Downtown Lofts", TaskPriority.MEDIUM, "Feb 25, 2025", assignee = "Sarah"),
        MaintenanceTask("m6", "Fix broken window", "Repairing the cracked pane in unit 4A", "Repair", "Harbor Office", TaskPriority.HIGH, "Feb 19, 2025", isCompleted = true, assignee = "Tom"),
        MaintenanceTask("m7", "Plumbing inspection", "Full checkup of the main water line", "Plumbing", "Sunset Villa", TaskPriority.LOW, "Mar 05, 2025", isCompleted = true, assignee = "Mike"),
        MaintenanceTask("m8", "Elevator maintenance", "Routine check for the north elevator", "Technical", "Metro Tower", TaskPriority.HIGH, "Feb 15, 2025", isCompleted = true, assignee = "Sarah")
    )

    val activityLogs = listOf(
        ActivityLog("l1", "AC Unit Serviced", "Regular maintenance completed for unit in living room", "2 hours ago", LogCategory.MAINTENANCE, "Sunset Villa"),
        ActivityLog("l2", "Rent Payment Received", "Monthly rent of \$3,500 received from John Smith", "5 hours ago", LogCategory.FINANCIAL, "Sunset Villa"),
        ActivityLog("l3", "New Tenant Application", "Application received from Sarah Wilson for Unit 3B", "1 day ago", LogCategory.TENANT, "Green Park Apt"),
        ActivityLog("l4", "Fire Safety Inspection", "Annual fire safety inspection passed", "1 day ago", LogCategory.INSPECTION, "Metro Tower"),
        ActivityLog("l5", "Lease Renewal Signed", "2-year lease renewal signed by TechCorp Inc.", "2 days ago", LogCategory.TENANT, "Metro Tower"),
        ActivityLog("l6", "Plumbing Repair", "Fixed leaking pipe in 2nd floor bathroom", "3 days ago", LogCategory.MAINTENANCE, "Downtown Lofts"),
        ActivityLog("l7", "Property Tax Payment", "Q1 property tax payment processed - \$4,200", "3 days ago", LogCategory.FINANCIAL, "Lakeside Cottage"),
        ActivityLog("l8", "Security System Update", "Upgraded to new smart lock system", "4 days ago", LogCategory.GENERAL, "Harbor Office"),
        ActivityLog("l9", "Roof Inspection", "Bi-annual roof inspection completed", "5 days ago", LogCategory.INSPECTION, "Sunset Villa"),
        ActivityLog("l10", "Insurance Renewal", "Property insurance renewed for 2025", "1 week ago", LogCategory.FINANCIAL, "Green Park Apt")
    )

    fun getPropertyById(id: String): Property? = properties.find { it.id == id }

    fun getSpacesForProperty(propertyId: String): List<Space> = spaces.filter { it.propertyId == propertyId }

    fun getAssetsForProperty(propertyId: String): List<Asset> = assets.filter { it.propertyId == propertyId }
}
