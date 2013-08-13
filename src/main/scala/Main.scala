import de.sciss.synth._
import ugen._

object Main extends App {
  val cfg = Server.Config()
  cfg.programPath = "/usr/bin/scsynth"

  Server.run(cfg) { s =>
    TestInstrument(TestUpdateX(), TestUpdateY())
  }
}

object TestInstrument {
  def apply(x: UpdatingX, y: UpdatingY) = {
    play {
      // harmonic series
      val pitch = Vector(50, 53.86, 57.02, 59.69, 62, 64.04, 65.86, 67.51, 69.02, 71.69, 72.88, 74)
      val mousex = x.value
      val mousey = y.value
      val triggerSpacing = 0.5 / (pitch.size - 1)
      val panSpacing = 1.5 / (pitch.size - 1)
      val out = Mix.tabulate(pitch.size) { i =>
        // place trigger points from 0.25 to 0.75
        val trigger = HPZ1.kr(mousex > (0.25 + (i * triggerSpacing))).abs
        val pluck = PinkNoise.ar(Decay.kr(trigger, 0.05))
        val period = pitch(i).midicps.reciprocal
        val string = CombL.ar(pluck, period, period, 8)
        Pan2.ar(string, i * panSpacing - 0.75)
      }
      LeakDC.ar(out)
    }
  }
}

trait UpdatingX {
  def value: de.sciss.synth.ugen.MouseX
}

trait UpdatingY {
  def value: de.sciss.synth.ugen.MouseY
}

case class TestUpdateX extends UpdatingX {
  def value = MouseX(0.0, 1.0, 0.0, 0.2)
}

case class TestUpdateY extends UpdatingY{
  def value = MouseY.kr
}
