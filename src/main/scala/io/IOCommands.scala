package io

import scala.util.matching.Regex

/**
  * <p><b>Purpose:</b>Regexp matching object for input commands.
  * (?i) : Starts case-insensitive mode
  * \\s  : Matches whitespace.
  * \\d  : Matches digits.
  * +    : Indicates one or more characters
  * ()   : Creates a group of characters which will be separated in the matches
  */
object IOCommands {
  val C:Regex = "(?i)c\\s+(\\d+)\\s+(\\d+)".r // canvas input command
  val L:Regex = "(?i)l\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)".r // line input command
  val R:Regex = "(?i)r\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)".r // rectangle input command
  val B:Regex = "(?i)b\\s+(\\d+)\\s+(\\d+)\\s+(.)".r // bucketFill input command
  val Q:Regex = "(?i)q".r // quit input command
}