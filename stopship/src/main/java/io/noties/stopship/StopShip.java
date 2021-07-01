package io.noties.stopship;

import java.util.Objects;

@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class StopShip {

  public static boolean stopShip(Runnable block) {
    Objects.requireNonNull(block);
    block.run();
    return true;
  }

  public static boolean _stopShip(Runnable block) {
    return false;
  }

  private StopShip() {
  }
}
