package com.skele.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skele.alarm.alarm.AlarmStateStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stateStore: AlarmStateStore
) : ViewModel() {
    fun setEndTimeToNow() {
        viewModelScope.launch {
            stateStore.saveEndTime(System.currentTimeMillis())
        }
    }
}
