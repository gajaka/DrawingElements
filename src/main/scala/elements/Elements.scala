package elements

sealed trait Element
import constants.ElementConstants._
import elements.TypeDef.Colour

object TypeDef {
  type Colour = Char
}
/**
  *  <p><b>Purpose:</b>
  * @param x bitmap x coordinate
  * @param y bitmap y coordinate
  */
case class Point(x:Int,y:Int) extends Element{
  /** <p><b>Purpose:</b>This method serves for horizontal shape line validation.
    * Note that:In this implementation extreme case has been included.
    * Line can be represented as dot in case points are equal.
    * From mathematical point of view this approach make sense
    * as special case of line definition.
    * @param that comparable point
    * @return true is two points are in horizontal position otherwise false.
    * */
  def isHorizontal(that:Point):Boolean = y == that.y


  /** <p><b>Purpose:</b>This method serves for vertical shape line validation.
    * Note that:In this implementation extreme case has been included.
    * Line can be represented as dot in case points are equal and line length is
    * equal 0. From mathematical point of view this approach make sense
    * as special case of line definition.
    * @param that comparable point
    * @return true is two points are in vertical position otherwise false.
    */
  def isVertical(that:Point):Boolean = x == that.x


  /**
    * Check if points are either in horizontal or vertical position.
    * @param that comparable point
    * @return true if points are in line position otherwise false
    */
  def *-* (that:Point) : Boolean = isHorizontal(that) || isVertical(that)


  /**
    * Check if poin is in area
    * @param w area width
    * @param h area height
    * @return true if point is inside area otherwise false
    */
  def isInArea (w:Int, h:Int):Boolean = {
    require(w > 0 && h > 0) // reduced precondition . Canvas limits are not necessary anymore.
    (x > 0 && x <= w) && (y > 0 && y <= h)
  }
  /**
    * Less that or equal function in terms of horizontal and vertical line
    * @param that comparable point
    * @return true if less than or equal works for comparable point otherwise false
    */
  def <= (that:Point):Boolean =
    (x <= that.x && (y == that.y)) ||
      (y <= that.y && ( x == that.x))

}

/**
  * <p><b>Purpose:</b> Line element
  * @param p1 line start point
  * @param p2 line end point
  */
case class Line private[elements] (p1:Point, p2:Point) extends Element {
  require(p1 *-* p2)
  def isHorizontal: Boolean = p1.isHorizontal(p2)
  def isVertical: Boolean = p1.isVertical(p2)
}
/**
  * <p><b>Purpose:</b> Rectangle element
  * @param p1 rectangle upper left corner
  * @param p2 rectangle lower right corner
  */
 case class Rectangle (p1:Point, p2:Point) extends Element {
  private[this] val p3  = Point(p2.x,p1.y)
  private[this] val p4  = Point(p1.x,p2.y)
  // rectangle components
  val components:Set[Line] = Set[Line](Line(p1,p3),Line(p4,p2),Line(p1,p4),Line(p2,p3))
}

/**
  * <p><b>Purpose:</b> BucketFill element
  * @param p seed point
  * @param c colour
  */
 case class BucketFill (p:Point, c:Colour) extends Element {
  /**
    *  Bucket around the seed point.
    * @param p Seed starting point
    * @return Set of the first points around the seed.
    */
  def pointBucket(p:Point):Seq[Point] =
    Range(p.x-1 , p.x+2).flatMap(i => Range(p.y-1 , p.y+2).map(j => Point(i,j)))
}

  abstract class Canvas (val w:Int,val h:Int) extends Element {

  val bitMap: Array[Array[Char]] = Array.ofDim[Colour](h + 2, w + 2)

  /**
    * Polymorphic  method serves for adding elements into canvas.
    * @param el An element that needs to be added into canvas object.
    * @param eo An implicit canvas operator based on polymorphic trait.
    * @tparam T An element type
    * @return Decorated canvas
    * @see elements.CanvasOperator
    */
  def add[T] (el:T)(implicit eo:DecoratorOperator[T]):Canvas = eo.|>(el)(this)

  override def toString: String = bitMap.map(_.mkString).mkString("\n")
}

/**
  * Companion object for abstraction Canvas
  */
object  Canvas {
    def apply (w: Int, h: Int): Canvas =  {
      require(w>0 && h>0)
   val canvas = new Canvas(w, h){}
    // bitMap initialization
    Range(0, canvas.h+2).foreach(x => Range(0, canvas.w+2).foreach(y => {
      (x, y) match {
        case (r, c) if r == 0 || r == h + 1 => canvas.bitMap(r)(c) = HyphenMinusChar
        case (r, c) if y == 0 || y == w + 1 => canvas.bitMap(r)(c) = VerticalBar
        case (r, c) => canvas.bitMap(r)(c) = Blank
      }
    }))
    canvas
  }
}





