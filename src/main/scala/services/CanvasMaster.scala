package services

import commands.{Quit, BucketFill => BF, Command => C, DrawCanvas => DC,
                 DrawLine => DL, DrawRectangle => DR, InvalidCommand => IC}
import constants.Messages._
import io.{CommandResolver, Parser}
import validators.{BucketFillValidator => BFV, DrawLineValidator => DLV,
                    DrawRectangleValidator => DRV, ListValidator => ListV}
import constants.Constants.EMPTY
import scala.util.{Failure, Success}
import services.DrawService._

/**
  * <p><b>Purpose:</b> Master object serves for controlling the
  * life-cycle of an application.
  */
object CanvasMaster {
  /**
    *
    * @param l  list containing elements
    *          that needs to be created within the canvas
    */
  def run (implicit l: List[C] = Nil) {

    print(ENTER_COMMAND)

    val CommandResolver(command) =
      Parser(scala.io.StdIn.readLine().trim)

    command match {
      // draw canvas command
      case t:DC if l.isEmpty => pipeLineDrawCanvas(t)
      case t:DC if l.nonEmpty => recover(DUPLICATED_CANVAS(t.toString))
      // draw line command
      case t:DL =>
        ListV.validate match {
          case Success(c) => pipeLineDrawElement[DL](DLV.|>(c)(t))
          case Failure(e) =>  recover(e.getMessage)
        }
      // draw rectangle command
      case t:DR =>
        ListV.validate match {
          case Success(c) => pipeLineDrawElement[DR](DRV.|>(c)(t))
          case Failure(e) =>  recover(e.getMessage)
        }
      // bucketFill command
      case t: BF =>
        ListV.validate match {
          case Success(c) => pipeLineDrawElement[BF](BFV.|>(c)(t))
          case Failure(e) =>  recover(e.getMessage)
        }
      // quit command
      case Quit() => sys.exit(0)
      // invalid command
      case IC(m) => recover(m)

    }
  }

  /**
    * Helper method serves for recovering application in case
    * of user exception or some wrong command input.
    * @param message user message
    * @param l  list containing elements that needs
    *          to be created within the canvas.
    */
  def recover ( message:String=EMPTY)(implicit l: List[C] = Nil){
    println((message +  TRY_AGAIN_OR_QUIT).stripMargin)
    run(l)
  }
}

object RunCanvas extends App {
  CanvasMaster.run()
}