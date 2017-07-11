package elements

import constants.ElementConstants.{Blank, LineChar}
import operators.Operator.{OpType, PipeOperator}

/**
  * <p><b>Purpose:</b> An extension of PipeOperator used to decorate
  * canvas with type T provided
  * @tparam T Decorator Type
  */
sealed trait DecoratorOperator[T] extends PipeOperator[T,Canvas,OpType#I[Canvas]]

/**
  * <p><b>Purpose:</b>  Provides strategies for canvas decoration.
  */
object DecoratorOperator{

  /**
    * <p><b>Purpose:</b> An implicit decorator operator that serves for
    * adding line to canvas area
    */
  implicit object LineDecorator extends DecoratorOperator[Line]{
    override def |> (l:Line)(canvas:Canvas):Canvas ={
      require( l.p1.isInArea(canvas.w,canvas.h)
        && l.p2.isInArea(canvas.w,canvas.h))
      l match {
        case ln if ln.isHorizontal && ln.p1 <= ln.p2 =>
          Range(ln.p1.x,ln.p2.x+1).foreach(canvas.bitMap(ln.p1.y)(_) = LineChar )

        case ln if ln.isHorizontal && ln.p2 <= ln.p1 =>
          Range(ln.p2.x,ln.p1.x+1).foreach(canvas.bitMap(ln.p1.y)(_) = LineChar )

        case ln if ln.isVertical &&  ln.p1 <= ln.p2=>
          Range(ln.p1.y,ln.p2.y+1).foreach(canvas.bitMap(_)(ln.p1.x) = LineChar )

        case ln if ln.isVertical && ln.p2 <= ln.p1 =>
          (ln.p2.y to ln.p1.y).foreach(canvas.bitMap(_)(ln.p1.x) = LineChar )
      }
      canvas
    }
  }

  /**
    * <p><b>Purpose:</b> An implicit decorator operator that serves for
    * adding rectangle to canvas area
    */
  implicit object RectangleDecorator extends DecoratorOperator[Rectangle] {

    override def |> (r:Rectangle)(canvas:Canvas):Canvas = {
      require(r.p1.isInArea(canvas.w,canvas.h)
        && r.p2.isInArea(canvas.w,canvas.h))
      r.components.foreach(LineDecorator.|>(_)(canvas))
      canvas
    }
  }

  /**
    *  <p><b>Purpose:</b> An implicit decorator operator  that serves for
    *  flood-fill canvas modification
    */
  implicit object BucketFillDecorator extends DecoratorOperator[BucketFill] {
    /**
      * Flood-fill algorithm
      * @param bf seed - starting point
      * @param canvas  An area that needs to be decorated
      * @return flood-fill decorated canvas
      */
    override def |> (bf:BucketFill)(canvas:Canvas):Canvas = {
      val ch = canvas.bitMap(bf.p.y)(bf.p.x)
      if (ch ==Blank && bf.p.isInArea(canvas.w,canvas.h)) {
        canvas.bitMap(bf.p.y)(bf.p.x) = bf.c
        Range(0,canvas.h).foreach(_ =>
          bf.pointBucket(bf.p).foreach(p => |>(BucketFill(p,bf.c))(canvas))
        )
      }
      canvas
    }
  }
}
