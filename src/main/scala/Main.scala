package com.jasoncramer.leapinstrument

import com.jsyn._
import com.jasoncramer.scsyn._

object Main extends App {
  val engine = Engine()
  engine.start()
  /*
  while(true) {
    val ln = readLine(">>> ").split(" ")
    assert(ln.length <= 2)
    ln(0) match {
      case "freq" => instr.setFreq(ln(1).toDouble)
      case "vol" => instr.setVolume(ln(1).toDouble)
      case "exit" =>
        instr.stop()
        System.exit(0)
      case _ => {}
    }
  }
  */
}

