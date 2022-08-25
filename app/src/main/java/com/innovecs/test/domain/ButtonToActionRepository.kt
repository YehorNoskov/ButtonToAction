package com.innovecs.test.domain

interface ButtonToActionRepository {
    suspend fun getActions(): List<ButtonToAction>?

    fun saveActionTime(timeInMillis: Long, buttonAction: ButtonAction)

    fun getActionTime(buttonAction: ButtonAction?): Long?
}