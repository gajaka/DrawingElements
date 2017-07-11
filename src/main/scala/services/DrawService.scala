package services

import services.CanvasMaster.{recover, run}
import commands.{Command => C, DrawCanvas => DC}
import operators.Operator.{OpType, PipeOperator}

import scala.util.{Failure, Success, Try}

/**
  * <p><b>Purpose:</b> Higher-kinded trait serves for drawing elements.
  * @tparam U Draw command holder that needs to be proceed within a workflow.
  */
sealed trait ElementsDrawOperator[U<:C] extends
  PipeOperator[OpType#V[U],List[C],OpType#D[Unit]]
/**
  * <p><b>Purpose:</b> Higher-kinded trait serves for drawing elements.
  * @tparam U Draw command holder that needs to be proceed within a workflow.
  */
sealed trait CanvasDrawOperator[U] extends
  PipeOperator[OpType#I[U],List[C],OpType#D[Unit]]



object DrawService {
  /**
    * Polymorphic <b>PipeOperator</b> implementation
    * serves for drawing elements.This operator is polymorphic
    * variant of the |> operator and can be treated as |>[U <: C](..)
    * operator.
    */
  def pipeLineDrawElement[U <: C] (t: Try[U])(implicit l:List[C])=
    new ElementsDrawOperator[U] { self =>
      /**
        * Polymorphic <b>PipeOperator</b> implementation
        * serves for drawing elements.
        */
   override def |>(t: Try[U])(l: List[C]):Unit = {
    require(l.nonEmpty)
    t match {
      case Success(a) => draw(a)(l)
      case Failure(e) => recover(e.getMessage)(l)
    }
  }
    |>(t)(l)
  }

  /**
    * Polymorphic <b>PipeOperator</b> implementation
    * serves for drawing canvas.
    */
  def pipeLineDrawCanvas (t:DC)(implicit l:List[C]):Unit=
    new CanvasDrawOperator[DC] { self =>
    override def |> (t: DC)(u: List[C]): Unit = {
      require(l.isEmpty)
      draw(t)(l)
    }
    |>(t)(l)
  }

  // invoking decorator in order to decorate canvas
  // return control list to Canvas Master object
  private def draw[U <: C](t:U)(l:List[C]): Unit = {
    val l1 = l :+ t
    println(CanvasDecorator.decorate(l1) + "\n")
    run(l1)
  }
}
