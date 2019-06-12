package com.example.foodapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import timber.log.Timber

/**
 *  Root Activity class that helps to observe activity lifecycle avoiding boilerplate code for multiple activities
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("lifecycle").i("onCreate was called from $localClassName")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.tag("lifecycle").i("onRestart was called from $localClassName")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("lifecycle").i("onStart was called from $localClassName")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("lifecycle").i("onResume was called from $localClassName")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag("lifecycle").i("onPause was called from $localClassName")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("lifecycle").i("onStop was called from $localClassName")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("lifecycle").i("onDestroy was called from $localClassName")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Timber.tag("lifecycle").i("onSaveInstanceState was called from $localClassName")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.tag("lifecycle").i("onRestoreInstanceState was called from $localClassName")
    }
}
