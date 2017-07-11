package io

import commands.{Quit, BucketFill => BF, Command => C, DrawCanvas => DC,
                 DrawLine => DL, DrawRectangle => DR, InvalidCommand => EC}

import constants.Messages.WRONG_INPUT_COMMAND
import io.IOCommands._

/**
  * <p><b>Purpose:</b> Canvas height / width upper bounds.
  */
object CanvasLimits {
  val  H_MAX = 40
  val  W_MAX = 200
}

case class Parser (l: String)

/**
  *<p><b>Purpose:</b> Command resolver object used to create pre-processing
  * draw commands from Regex objects. Extractor pattern implementation.
  */
object CommandResolver{
  import CanvasLimits._
  /**
    * An unapply extractor.
    * Note that: PartialFunction.condOpt is used in order to avoid
    * writing the default case of an unapply. Contract has been guaranteed
    * by guards conditions.
    * @param target takes an <b>Any</b>
    * @return an Option which is the type of the parameter it extracts.
    */
  def unapply (target: Any): Option[C] =
    PartialFunction.condOpt(target) {
      case Parser(Q()) => Quit()

      case Parser(C(w, h)) if w.toInt > 0 && h.toInt > 0 &&
        w.toInt < W_MAX && h.toInt < H_MAX => DC(w.toInt, h.toInt)

      case Parser(L(x1, y1, x2, y2)) if x1.toInt>0 &&  y1.toInt>0
        && x2.toInt>0 && y2.toInt > 0 =>
        DL(x1.toInt, y1.toInt,x2.toInt, y2.toInt)

      case Parser(R(x1, y1, x2, y2)) if x1.toInt>0 &&  y1.toInt>0
        && x2.toInt>0 && y2.toInt > 0 =>
        DR(x1.toInt, y1.toInt,x2.toInt, y2.toInt)

      case Parser(B(x, y, c)) if x.toInt>0 && y.toInt>0 =>
        BF(x.toInt,y.toInt, c(0))

      case Parser(invalid) =>
        EC(WRONG_INPUT_COMMAND(invalid))
    }

}
