package com.innovecs.test.domain

data class ButtonToAction(
    val type: String,
    val enabled: Boolean,
    val priority: Int,
    val validDays: List<Int>,
    val coolDown: Int
)