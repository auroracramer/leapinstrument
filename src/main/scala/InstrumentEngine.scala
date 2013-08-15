package com.jasoncramer.leapinstrument

import com.leapmotion.leap.{Controller, Listener}

case class Engine() {
  val instrument = MainInstrument(0, new SawtoothInstrument())
  val controller = new Controller()
  def start(): Unit = {
    instrument.start()
    controller.addListener(instrument)
  }
  def stop(): Unit = {
    instrument.stop()
  }
}
