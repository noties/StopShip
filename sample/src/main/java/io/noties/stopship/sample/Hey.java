package io.noties.stopship.sample;

import io.noties.debug.Debug;

import static io.noties.stopship.StopShip._stopShip;
import static io.noties.stopship.StopShip.stopShip;

class Hey {

  void hey() {

    Debug.i("before");

    if (_stopShip(() -> {
      Debug.i("inside stopship");
    })) return;

    Debug.i("after stopship");
  }

  void hey2() {
stopShip(() -> {

});
  }
}
