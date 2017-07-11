package validators
import validators.{InvalidCommandException => ICEx}
import commands.{BucketFill => BF, Command => C, DrawCanvas => DC,
                 DrawLine => DL, DrawRectangle => DR}

import scala.util.{Failure, Success, Try}
import constants.Messages._
import constants.Constants._
import elements.{Point => P}
import operators.Operator.{OpType, PipeOperator}
/**
  * <p><b>Purpose:</b>Object used to validate <b>List[Command]</b>
  * instance checking the content and header type
  */
object ListValidator {
  /**
    * Validate list content and header
    * @return an instance of DrawCanvas otherwise Failure
    */
  def validate(implicit l:List[C]) : Try[DC] = l match {
    case (h::_) => selfValidation(h)
    case Nil  =>  Failure(ICEx(INVALID_ELEMENT_POSITION))
  }
  def selfValidation: C =>Try[DC] = {
    case dc:DC  => Success(dc)
    case _ => Failure (ICEx(UNEXPECTED_TYPE_AT(HEAD)))
  }
}

/**
  * <p><b>Purpose:</b> An extension of PipeOperator used to validate
  * U against DC
  * @tparam U Type that needs to be validated.
  */
sealed trait ValidatorOperator[U] extends PipeOperator[DC,U,(OpType)#V[U]]{
  protected def checkPrecondition: (DC,U) => Boolean
}

/**
  * <p><b>Purpose:</b>Object used to validate <b>DrawLine</b>
  * instance against <b>DrawCanvas</b> in order to check precondition
  * for an element <b>Line</b>instantiation.
  */
object DrawLineValidator extends ValidatorOperator[DL] {

  /**
    * Validate DrawLine instance with DrawCanvas
    * @return Validated object DrawLine otherwise Failure
    */
  override def |>(dc:DC)(dl:DL) : Try[DL] = (dc,dl) match {
    case (c,l) if checkPrecondition(c,l) => Success(dl)
    case invalid => Failure (ICEx(INVALID_LINE_SHAPE(invalid._2.toString)))
  }
  override def checkPrecondition:(DC,DL) => Boolean= {
    (c,l) =>
      val p1 = P(l.x1,l.y1)
      val p2 = P(l.x2,l.y2)
      (p1 *-* p2 ) &&  p1.isInArea(c.w,c.h) && p2.isInArea(c.w,c.h)
  }
}
/**
  * <p><b>Purpose:</b>Object used to validate <b>DrawRectangle</b>
  * instance against <b>DrawCanvas</b> in order to check precondition
  * for an element <b>Rectangle</b>instantiation.
  */
object DrawRectangleValidator extends ValidatorOperator[DR] {

  /**
    * Validate DrawRectangle instance with DrawCanvas
    * @return Validated object DrawRectangle otherwise Failure
    */
  override def |>(dc:DC)(dr:DR):Try[DR] = (dc,dr) match {
    case (c,r) if checkPrecondition(c,r) => Success(dr)
    case invalid => Failure (ICEx(INVALID_RECTANGLE_POSITION(invalid._2.toString)))
  }

  override def checkPrecondition:(DC,DR) => Boolean={
    (c,r) =>
      val p1 = P(r.x1,r.y1)
      val p2 = P(r.x2,r.y2)
      p1.isInArea(c.w,c.h) && p2.isInArea(c.w,c.h)
  }
}
/**
  * <p><b>Purpose:</b>Object used to validate <b>BucketFill</b>
  * instance against <b>DrawCanvas</b>in order to check precondition
  * for an element <b>BucketFill</b>instantiation.
  */
object BucketFillValidator extends ValidatorOperator[BF] {
  /**
    * Validate BucketFill instance with DrawCanvas
    * @return Validated object BucketFill otherwise Failure
    */
  override def |>(dc:DC)( bf:BF): Try[BF] = (dc,bf) match {
    case (c, f) if checkPrecondition(c,f) => Success(bf)
    case invalid => Failure(ICEx(INVALID_BUCKET(invalid._2.toString)))
  }

  override def checkPrecondition:(DC,BF) => Boolean={
    (c,f) => P(f.x, f.y).isInArea(c.w,c.h)
  }
}
