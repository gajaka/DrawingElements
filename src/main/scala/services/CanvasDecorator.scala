package services
import commands.{BucketFill => BF, Command => C, DrawCanvas => DC,
                 DrawLine => DL, DrawRectangle => DR}
import elements.{BucketFill, Canvas, Line, Rectangle, Point => P}

/**
  * <p><b>Purpose:</b>Object used to create and decorate
  * an element <b>Canvas</b>
  * */
object CanvasDecorator {
  /**
    * Transform <b>List[Command]</b>  to corresponding
    * elements of type <b>Element</b> adding them into Canvas instance.
    * @return Canvas decorated instance.
    * @see elements.Element
    */
  val decorate: PartialFunction[List[C],Canvas]= {
    case (h:DC)::Nil => Canvas(h.w,h.h)
    case (h:DC)::t =>
      val canvas = Canvas(h.w,h.h)
      t.foreach {
        case c: DL => canvas.add(Line(P(c.x1, c.y1), P(c.x2, c.y2)))
        case c: DR => canvas.add(Rectangle(P(c.x1, c.y1), P(c.x2, c.y2)))
        case c: BF => canvas.add(BucketFill(P(c.x, c.y), c.c))
        case _ => canvas
      }
      canvas
  }
}
