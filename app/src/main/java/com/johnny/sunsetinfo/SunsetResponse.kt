package com.johnny.sunsetinfo

import com.google.gson.annotations.SerializedName


data class SunsetResponse(
    @SerializedName("results")
    val results: SunsetResult,
    @SerializedName("status")
    val status: String
)