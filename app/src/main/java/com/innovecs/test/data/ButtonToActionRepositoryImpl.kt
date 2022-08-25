package com.innovecs.test.data

import com.innovecs.test.data.network.actionApi
import com.innovecs.test.domain.ButtonAction
import com.innovecs.test.domain.ButtonToAction
import com.innovecs.test.domain.ButtonToActionRepository

class ButtonToActionRepositoryImpl : ButtonToActionRepository {
    private val actionTimeCache = mutableMapOf<ButtonAction, Long?>()

    override suspend fun getActions(): List<ButtonToAction>? {
        val response = actionApi.getActions()
        return if (response.isSuccessful) {
            response.body()?.mapper()
        } else null
    }

    override fun saveActionTime(timeInMillis: Long, buttonAction: ButtonAction) {
        actionTimeCache[buttonAction] = timeInMillis
    }

    override fun getActionTime(buttonAction: ButtonAction?) = actionTimeCache.getOrDefault(buttonAction, null)

    private fun List<ButtonToActionResponse>.mapper() =
        map { ButtonToAction(it.type, it.enabled, it.priority, it.validDays, it.coolDown) }
}