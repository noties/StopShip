package io.noties.stopship.sample

import android.app.Activity
import android.os.Bundle
import io.noties.debug.Debug
import io.noties.stopship._stopShip

class MainActivity : Activity() {

  fun hey() {
    Debug.i("start")

    _stopShip {
      Debug.e("printing inside stopship")
      return
    }

    Debug.i("after stopship")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    hey()

    val hey = Hey()
    hey.hey()
  }
}