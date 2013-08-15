package com.jasoncramer.leapinstrument

import com.leapmotion.leap.{Controller, Finger, Frame, Hand, Leap,
                            Listener, FingerList, HandList}
import scala.language.implicitConversions
import scala.collection.JavaConversions._

class Instrument

/** An interface between the LeapMotion SDK and the sound synthesis
  * API.
  */
abstract class MotionInstrument(handID: Int, instrument: Instrument)
extends Listener with HasFingersListeners with HasHandListeners {

  implicit def fingerList2Seq(flist: FingerList): Seq[Finger] = flist.toSeq
  implicit def handList2Seq(hlist: HandList): Seq[Hand] = hlist.toSeq

  /** Provide a Frame update to the instrument.
    *
    * Invokes all listeners that correspond to hands
    * and fingers.
    *
    * @return True if the hand is still valid
    */
  override def onFrame(controller: Controller): Unit = {
    val frame = controller.frame
    val hands = frame.hands
    val hand = hands(0) // Just take the first hand available
    if (hand.isValid) {
      val fingers = hand.fingers
      for (handListener <- handListeners) handListener(hand)
      for (fingersListener <- fingersListeners) fingersListener(fingers)
    }
  }
}

trait HasFingersListeners {
  type FingersListener = (Seq[Finger]) => Unit
  def fingersListeners: Seq[FingersListener]
}

trait HasHandListeners {
  type HandListener = (Hand) => Unit
  def handListeners: Seq[HandListener]
}

case class MainInstrument(handId: Int, instrument: Instrument)
extends MotionInstrument(handId, instrument) {

  // Define listeners for fingers
  val fingerTipProximityTo: FingersListener = (fingers) => {
    // APPROACH 1a:
    // Naive furthest points
    //
    // The intuition here is that a sphere where the diameter
    // is defined by a line segment drawn between the two
    // points that are furthest apart.
    //
    // Since n<=5, which is relatively small, at least for now,
    // we can get away with the naive O(n^2) solution

    var maxDist = Double.MinValue

    for {
      (finger, index) <- fingers.zipWithIndex
      if index < (fingers.size - 1)
      finger2 <- fingers.drop(index + 1)
      dist = finger.tipPosition.distanceTo(finger2.tipPosition)
    } if(dist > maxDist) maxDist = dist

  }

  // Define Listeners for a hand
  val yToPitch: HandListener = (hand) => {
    val y = hand.palmPosition.getY
    // TODO: define instrument interface before we can
    //       actually do anything here
  }

  val sphereToVolume: HandListener = (hand) => {
    val radius = hand.sphereRadius
  }


  def fingersListeners: Seq[FingersListener] = Seq()
  def handListeners: Seq[HandListener] = Seq(yToPitch, sphereToVolume)
}
