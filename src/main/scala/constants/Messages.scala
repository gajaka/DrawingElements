package constants

/**
  *<p><b>Purpose:</b> Messages holder object
  */
object Messages {
   val ENTER_COMMAND:String="enter command:"

   val TRY_AGAIN_OR_QUIT:String=
     """
       |Enter valid command or press Q[Quit].""".stripMargin

   def WRONG_INPUT_COMMAND(invalid:String):String=
     s"""|[ERROR]:Wrong input command line: $invalid.
         |Command is not well formatted or contains invalid argument(s).""".stripMargin

   def DUPLICATED_CANVAS(invalid:String):String=
     s"""|[ERROR]:Validation $invalid failed.
        |Multiple canvas instances are not allowed. """.stripMargin

  def UNEXPECTED_TYPE_AT (invalid:String):String=
    s"""[ERROR]: Unexpected type in the command list at  $invalid position.
       |
     """.stripMargin

  def INVALID_LINE_SHAPE (invalid:String):String =
    s"""|[ERROR]:Invalid dimensions for command: $invalid.
        |Line should be either horizontal or vertical and should fit canvas.""".stripMargin

  def INVALID_RECTANGLE_POSITION (invalid:String):String =
    s"""|[ERROR]:Invalid dimensions for command: $invalid.
        |Rectangle should be within the canvas.""".stripMargin

  def INVALID_BUCKET (invalid:String):String =
    s"""|[ERROR]:Invalid command: $invalid.
        |Seed should be inside canvas.""".stripMargin

  def INVALID_ELEMENT_POSITION:String =
    s"""[ERROR]:Position validation failed.
       |An element should be within the canvas.""".stripMargin

}
/**
  *<p><b>Purpose:</b> Constants holder object
  */
object Constants {
  val EMPTY:String=" "
  val HEAD:String ="head"
}
/**
  *<p><b>Purpose:</b> ElementConstants holder object
  */
object ElementConstants {
  val Blank:Char                 = ' '
  val HyphenMinusChar:Char       = '-'
  val VerticalBar:Char           = '|'
  val LineChar:Char              = 'x'
}
