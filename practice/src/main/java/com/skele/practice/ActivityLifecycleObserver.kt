package com.skele.practice

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class ActivityLifecycleObserver(private val lifecycleOwner: LifecycleOwner) :
    LifecycleEventObserver {

    private val TAG = "ActivityLifecycleObserver"

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> Log.d(TAG, "onCreate : ${lifecycleOwner.lifecycle.currentState}")
            Lifecycle.Event.ON_START -> Log.d(TAG, "onStart : ${lifecycleOwner.lifecycle.currentState}")
            Lifecycle.Event.ON_RESUME -> Log.d(TAG, "onResume : ${lifecycleOwner.lifecycle.currentState}")
            Lifecycle.Event.ON_PAUSE -> Log.d(TAG, "onPause : ${lifecycleOwner.lifecycle.currentState}")
            Lifecycle.Event.ON_STOP -> Log.d(TAG, "onStop : ${lifecycleOwner.lifecycle.currentState}")
            Lifecycle.Event.ON_DESTROY -> Log.d(TAG, "onDestroy : ${lifecycleOwner.lifecycle.currentState}")
            Lifecycle.Event.ON_ANY -> Log.d(TAG, "onAny : ${lifecycleOwner.lifecycle.currentState}")
            else -> Log.d(TAG, "onStateChanged: ${lifecycleOwner.lifecycle.currentState}")
        }
    }
}