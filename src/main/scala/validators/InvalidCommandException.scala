package validators

case class InvalidCommandException (message:String) extends RuntimeException {
  override def getMessage: String = message
}

