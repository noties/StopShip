package io.noties.stopship.sample

import android.app.Application
import io.noties.debug.AndroidLogDebugOutput
import io.noties.debug.Debug

class App: Application() {
  override fun onCreate() {
    super.onCreate()

    Debug.init(AndroidLogDebugOutput.create(BuildConfig.DEBUG))
  }
}