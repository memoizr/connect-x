package connect4

sealed trait Player {
  def opposite = this match {
    case A => B
    case B => A
  }
}

case object A extends Player

case object B extends Player
