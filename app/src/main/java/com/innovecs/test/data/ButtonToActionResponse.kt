package com.innovecs.test.data

import com.google.gson.annotations.SerializedName

data class ButtonToActionResponse(
    @SerializedName("type") val type: String,
    @SerializedName("enabled") val enabled: Boolean,
    @SerializedName("priority") val priority: Int,
    @SerializedName("valid_days") val validDays: List<Int>,
    @SerializedName("cool_down") val coolDown: Int
)