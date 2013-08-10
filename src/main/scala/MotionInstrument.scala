import com.leapmotion.leap.{Finger, Frame, Hand, Leap, FingerList, HandList}
import scala.collection.JavaConversions._

class Instrument

/** An interface between the LeapMotion SDK and the sound synthesis
  * API.
  */
abstract class MotionInstrument(handID: Int, instrument: Instrument)
extends HasFingersListeners with HasHandListeners {

  /** Provide a Frame update to the instrument.
    *
    * Invokes all listeners that correspond to hands
    * and fingers.
    *
    * @return True if the hand is still valid
    */
  def update(frame: Frame): Boolean = {
    val hand = frame.hands.get(handID)
    if (hand.isValid) {
      val fingers = hand.fingers.toSeq
      for (handListener <- handListeners) handListener(hand)
      for (fingersListener <- fingersListeners) fingersListener(fingers)
      true
    } else false
  }
}

trait HasFingersListeners {
  def fingersListeners: Seq[(Seq[Finger]) => Unit]
}

trait HasHandListeners {
  def handListeners: Seq[(Hand) => Unit]
}
