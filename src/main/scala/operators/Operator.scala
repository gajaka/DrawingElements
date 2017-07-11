package operators

import scala.util.Try

object Operator {
  //Higher-kinded type
  type OpType = {
    type I[X] = X // identity - decorator output
    type V[X] = Try[X] // validator output
    type D[X] =Unit // draw output
  }
  /**
    * <p>Parametrized pipe operator for processing data within a workflow with
    * a T and U as input types and K as an output type.</p>
    */
  trait PipeOperator[T,U,K] {
    def |> (t: T)(u: U): K
  }
}



