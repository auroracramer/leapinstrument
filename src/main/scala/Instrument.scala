package com.jasoncramer.leapinstrument

import com.jsyn._
import com.jsyn.unitgen.UnitOscillator
import com.jasoncramer.scsyn._


class Instrument(osc: UnitOscillator) {
  val synth = JSyn.createSynthesizer()
  val lineOut = LineOut()
  synth.add(osc)
  synth.add(lineOut)
  osc.output.connect(0, lineOut.input, 0)
  osc.output.connect(0, lineOut.input, 1)
  osc.amplitude.set(0.1)

  def start(): Unit = {
    lineOut.start()
    synth.start()
  }

  def stop(): Unit = {
    lineOut.stop()
    synth.stop()
  }

  def setFreq(freq: Double): Unit = osc.frequency.set(freq)
  def setVolume(vol: Double): Unit = osc.amplitude.set(vol)
}

case class SawtoothInstrument() extends Instrument(SawtoothOscillator())
