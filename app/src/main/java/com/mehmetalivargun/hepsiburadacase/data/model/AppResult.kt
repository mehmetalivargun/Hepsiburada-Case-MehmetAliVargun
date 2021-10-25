package com.mehmetalivargun.hepsiburadacase.data.model

data class AppResult(
    val advisories: List<String>,
    val appletvScreenshotUrls: List<Any>,
    val artistId: Int,
    val artistName: String,
    val artistViewUrl: String,
    val artworkUrl100: String,
    val artworkUrl512: String,
    val artworkUrl60: String,
    val averageUserRating: Double,
    val averageUserRatingForCurrentVersion: Double,
    val bundleId: String,
    val contentAdvisoryRating: String,
    val currency: String,
    val currentVersionReleaseDate: String,
    val description: String,
    val features: List<String>,
    val fileSizeBytes: String,
    val formattedPrice: String,
    val genreIds: List<String>,
    val genres: List<String>,
    val ipadScreenshotUrls: List<String>,
    val isGameCenterEnabled: Boolean,
    val isVppDeviceBasedLicensingEnabled: Boolean,
    val kind: String,
    val languageCodesISO2A: List<String>,
    val minimumOsVersion: String,
    val price: Double,
    val primaryGenreId: Int,
    val primaryGenreName: String,
    val releaseDate: String,
    val releaseNotes: String,
    val screenshotUrls: List<String>,
    val sellerName: String,
    val sellerUrl: String,
    val supportedDevices: List<String>,
    val trackCensoredName: String,
    val trackContentRating: String,
    val trackId: Int,
    val trackName: String,
    val trackViewUrl: String,
    val userRatingCount: Int,
    val userRatingCountForCurrentVersion: Int,
    val version: String,
    val wrapperType: String
)