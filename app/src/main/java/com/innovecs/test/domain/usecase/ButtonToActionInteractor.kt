package com.innovecs.test.domain.usecase

import com.innovecs.test.domain.ButtonAction
import com.innovecs.test.domain.ButtonToAction
import com.innovecs.test.domain.ButtonToActionRepository
import java.util.*

class ButtonToActionInteractor(private val repository: ButtonToActionRepository) {

    suspend fun execute(): ButtonAction? {
        val actionsFromServer = repository.getActions() ?: return null
        val currentAction = getCurrentAction(actionsFromServer)
        currentAction?.let {
            repository.saveActionTime(System.currentTimeMillis(), buttonAction(it.type))
        }
        return buttonAction(currentAction?.type)
    }

    private fun getCurrentAction(list: List<ButtonToAction>): ButtonToAction? {
        val enabledActions = list.filter { it.enabled }
        val validActions = enabledActions.filter { isDayValid(it) && isCoolPeriodPassed(it) }
        //todo If two actions have the same priority, choose one at random.
        return validActions.maxByOrNull { it.priority }
    }

    private fun isDayValid(action: ButtonToAction): Boolean {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK) - 1
        return action.validDays.contains(currentDay)
    }

    private fun isCoolPeriodPassed(action: ButtonToAction): Boolean {
        val timeAction = repository.getActionTime(buttonAction(action.type)) ?: return true
        val cooledDate = Calendar.getInstance().apply {
            timeInMillis = timeAction
            add(Calendar.MILLISECOND, action.coolDown)
        }
        return cooledDate.timeInMillis < System.currentTimeMillis()
    }

    private fun buttonAction(action: String?) =
        ButtonAction.values().find { it.action == action } ?: ButtonAction.ERROR
}