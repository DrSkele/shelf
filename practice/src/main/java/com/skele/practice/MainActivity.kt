package com.skele.practice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skele.practice.ui.theme.MyAppTheme

private const val TAG = "ActivityPractice"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityLifecycleObserver(this)
        Log.d(TAG, "onCreate")
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart : ${this.lifecycle.currentState}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume : ${this.lifecycle.currentState}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause : ${this.lifecycle.currentState}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop : ${this.lifecycle.currentState}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart : ${this.lifecycle.currentState}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy : ${this.lifecycle.currentState}")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAppTheme {
        Greeting("Android")
    }
}