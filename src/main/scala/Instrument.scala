package com.jasoncramer.instrument

trait Instrument {
  def setFreq(freq: Double): Unit = ???
  def setVolume(vol: Double): Unit = ???
  def setVelocity(vel: Int): Unit = ???
}
