package commands
import constants.Constants.EMPTY

/**
  * A marker for commands holder classes
  */
sealed trait Command

/**
  *<p>Canvas command parameters holder.
  * @param w width of Canvas
  * @param h height of Canvas
  */
case class DrawCanvas (w:Int,h:Int) extends Command {
  import io.CanvasLimits._
  require (w>0 && h>0 && w < W_MAX && h < H_MAX)
}

/**
  *<p>Line command parameters holder.
  * @param x1   line x coordinate
  * @param y1   line y coordinate
  * @param x2   line x coordinate
  * @param y2   line y coordinate
  */
case class DrawLine (x1: Int, y1: Int,
                     x2: Int, y2: Int) extends Command{
  require (x1>0 && y1>0 && x2>0 && y2>0)
}

/**
  *<p>Rectangle command parameters holder.
  * @param x1 upper left corner x coordinate
  * @param y1 upper left corner y coordinate
  * @param x2 lower right corner x coordinate
  * @param y2 lower right corner y coordinate
  */
case class DrawRectangle (x1: Int, y1: Int,
                          x2: Int, y2: Int) extends Command{
  require (x1>0 && y1>0 && x2>0 && y2>0)
}

/**
  *<p>BucketFill command parameters holder.
  * @param x seed x coordinate
  * @param y seed y coordinate
  * @param c colour
  */
case class BucketFill (x:Int, y:Int ,c:Char) extends Command{
  require (x>0 && y>0 )
}

/**
  * Quit program command holder
  */
case class Quit() extends Command

/**
  * Invalid input command holder
  * @param message reason
  */
case class InvalidCommand (message:String=EMPTY) extends Command
