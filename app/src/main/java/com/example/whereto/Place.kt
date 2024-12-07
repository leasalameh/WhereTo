data class Place(
    val name: String,
    val location: String,
    val description: String,
    val rating: Double,
    val tags: List<String>,
    val openingHours: List<String>,  // Could be a list of strings like ["Monday: 08:00am - 12:00am", ...]
    val priceRange: String,
    val contactInfo: String,
    val imageUrl: String,
)