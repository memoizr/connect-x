package connect4

import Board._

object Board {
  type Column = List[Cell]
  val Column = List

  sealed trait Cell

  case object O extends Cell

  case object X extends Cell

  case object \ extends Cell

}

case class Board(columns: Column*) {
}