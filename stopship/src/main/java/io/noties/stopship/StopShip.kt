@file:Suppress("unused")

package io.noties.stopship

inline fun stopShip(block: () -> Unit) {
  block()
}

// no op
@Suppress("FunctionName")
inline fun _stopShip(@Suppress("UNUSED_PARAMETER") block: () -> Unit) = Unit