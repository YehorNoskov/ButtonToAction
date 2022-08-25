package com.innovecs.test.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovecs.test.data.ButtonToActionRepositoryImpl
import com.innovecs.test.domain.ButtonAction
import com.innovecs.test.domain.usecase.ButtonToActionInteractor
import kotlinx.coroutines.launch

class ButtonToActionViewModel : ViewModel() {

    private val interactor = ButtonToActionInteractor(ButtonToActionRepositoryImpl())

    private val _buttonAction: MutableLiveData<ButtonAction> = MutableLiveData()
    val buttonAction: LiveData<ButtonAction>
        get() = _buttonAction

    fun performActionClick(){
        viewModelScope.launch {
            _buttonAction.postValue(interactor.execute())
        }
    }
}