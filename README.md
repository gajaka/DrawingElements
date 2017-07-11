# DrawingElements

Application serves for drawing elements within the canvas. Supported elements are : line , rectangle and seed with a colour  for flood-fill algorithm.

Task description:
----------------------
1. create a new canvas
2. start drawing on the canvas by issuing various commands
3. quit

At the moment, the program should support the following commands:

* ```C w h```         - Should create a new canvas of width w and height h.
* ```L x1 y1 x2 y2``` - Should create a new line from (x1,y1) to (x2,y2). Currently only horizontal or vertical lines are supported. Horizontal and vertical lines will be drawn using the 'x' character.
* ```R x1 y1 x2 y2``` - Should create a new rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2). Horizontal and vertical lines will be drawn using the 'x' character.
* ```B x y c```       - Should fill the entire area connected to (x,y) with "colour" c. The behaviour of this is the same as that of the "bucket fill" tool in paint programs.
* ```Q```             - Should quit the program.

Conceptual design:
--------------------------

Application can be seen as an ordered sequence of  mutually dependent steps ( formally called  pipeline operators ) dedicated to accomplish  tasks during the single command life-cycle.
In order to achieve this goal, a higher-kinded operator has been defined as  trait containing the |> transformation function:
```
trait PipeOperator[T,U,K] {
    def |> (t: T)(u: U): K
  }
  ```
The PipeOperator is the key point of this model. Operator has been passing through all phases of  the application workflow changing behaviour and implementation  step-by-step, from one to another phase. In other words , pipe operator  transforms itself , depending on the task that needs to be executed.
Therefore positive workflow scenario  look as the following:

pre-processing phase --> build validation pipeline operator --> validation phase --> 
build drawing pipeline operator --> invoke decorator canvas --> build decorator pipeline operator --> 
select corresponding decorator pipeline operator ( implicitly by compiler )  --> decorate canvas.
Notice that drawing pipeline operator instantiate itself as polymorphic method. 

The creation of a class that implements a specific transformation using an explicit configuration of pipeline operator is quite simple: all you need is the definition of  input types  T,U and an output type K overriding the basic definition of the |> pipeline transformation method. Here is an example of how to create decorator pipeline operator.
``` 
 abstraction :
 object Operator {
  //Higher-kinded type
  type OpType = {
    type I[X] = X // identity 
    type V[X] = Try[X] 
    type D[X] =Unit
  }
 trait PipeOperator[T,U,K] {
    def |> (t: T)(u: U): K
  }
  refinement step 1:
  sealed trait DecoratorOperator[T] extends PipeOperator[T,Canvas,OpType#I[Canvas]]
  
  refinement step 2:
  overriding the |> pipeline transformation method.
   implicit object RectangleDecorator extends DecoratorOperator[Rectangle] {
   override def |> (r:Rectangle)(canvas:Canvas):Canvas = { ... }
   ```
   Constraint: Currently application support drawing canvas  heigh and width , 40 200 respectively. These 
    upper bounds are not specified by the task. 
     
   Run DrawingElements
---------------------------------

*   Unzip ```DrawingElements.zip``` in local directory.
*   From terminal  cd to DrawingElements folder.
*   Check permissions of the sbt inside DrawingElements folder. It should be executable. 
*   Run application ```./sbt run```

TODO:
-----------
1) Tests are missing at the moment. Tests should be done in FlatSpec stile and should cover all possible workflow scenarios including all public methods defined in application.
2) Design By Contract : there are very few contracts specified in the model,  describing preconditions with no postconditions provided. For more information about formal specification and refinement calculus please refer to: https://github.com/gajaka/frama-c-pvs/



        
   
   
   
   
  
  
                                                                                                


