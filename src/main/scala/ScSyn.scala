package com.jasoncramer

import com.jsyn._
import com.jsyn.unitgen.{SawtoothOscillator, LineOut, Delay}

object scsyn {
  object SawtoothOscillator {
    def apply(): SawtoothOscillator = new SawtoothOscillator()
  }

  object LineOut {
    def apply(): LineOut = new LineOut()
  }

  object Delay {
    def apply(): Delay = new Delay()
  }
}
