package connect4

import connect4.Board._


class BoardHelper(rows: Int, columns: Int) {

  def genBoard: Board = {
    val y: List[Column] = (0 until columns).map(_ => (0 until rows).map(_ => \).toList).toList
    new Board(y: _*)
  }

  def newMove(board: Board, move: String, player: Player): (Option[Player], Board) = {
    val columnIndex = move.toInt - 1
    val column = board.columns(columnIndex)
    def playerToCell(playa: Player) = playa match {
      case A => X
      case B => O
    }

    val newColumnCore: List[Cell] = playerToCell(player) :: column.dropWhile(_ == \)
    val newColumn = ((0 until (rows - newColumnCore.size)).map(_ => \) ++ newColumnCore).toList
    val y: List[Column] = board.columns.zipWithIndex.map(x => if (x._2 == columnIndex) newColumn else x._1).toList
    val newBoard = new Board(y: _*)
    (calculateWinner(newBoard), newBoard)
  }


  private def calculateWinner(board: Board): Option[Player] = {
    def match4(list: List[Cell]): Option[Cell] = list.filterNot(_ == \) match {
      case a :: b :: c :: d :: tail => if (a == b && b == c && c == d) Some(a) else match4(b :: c :: d :: tail)
      case _ => None
    }

    def findWinner(board: List[List[Cell]]): Option[Player] = board.map(match4).filterNot(_.isEmpty).headOption.map {
      case Some(X) => A
      case Some(O) => B
    }

    val transpose: List[List[Cell]] = (0 until rows).map(i => board.columns.map(_ (i)).toList).toList
    val horizWinner = findWinner(transpose)
    val vertWinner = findWinner(board.columns.toList)
    val secondDiagWinner = findWinner(fetchDiagonals(board.columns.toList, secondDiagonal))
    val firstDiagWinner = findWinner(fetchDiagonals(board.columns.toList, firstDiagonal))

    horizWinner.orElse(vertWinner).orElse(firstDiagWinner).orElse(secondDiagWinner)
  }

  private sealed trait diagonals extends ((Int, Int) => Int)

  private object firstDiagonal extends diagonals {
    override def apply(x: Int, y: Int): Int = x - y
  }

  private object secondDiagonal extends diagonals {
    override def apply(x: Int, y: Int): Int = x - (1 - y)
  }

  private def fetchDiagonals[A](list: List[List[A]], strategy: diagonals): List[List[A]] = {
    val rows = list.length
    val columns = list.head.length

    val vectorized = list.map(_.toVector).toVector

    val coords = for {
      x <- 0 until rows
      y <- 0 until columns
    } yield (x, y)

    coords
      .groupBy(strategy.tupled)
      .values
      .map(_.map { case (x, y) => vectorized(x)(y) }.toList).toList
  }
}
