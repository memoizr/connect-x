package connect4

import connect4.Board.{O, X, \}

import Board._


class BoardHelperTest extends BaseTest {
  val boardHelper = new BoardHelper(6, 7)

  it should "accept integer input below the size of board, and update it" in {
    val (_, board) = boardHelper.newMove(boardHelper.genBoard, "2", A)
    board shouldBe Board(
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, X),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \)
    )

    val (_, newBoard) = boardHelper.newMove(board, "2", B)
    newBoard shouldBe Board(
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, O, X),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \)
    )

    val (_, newNewBoard) = boardHelper.newMove(newBoard, "3", A)
    newNewBoard shouldBe Board(
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, O, X),
      Column(\, \, \, \, \, X),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \)
    )
  }

  it should "calculate a winner in a column" in {
    val board = Board(
      Column(\, \, X, X, X, O),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, O, O),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \)
    )

    val (winner, newBoard) = boardHelper.newMove(board, "1", A)

    winner shouldBe Some(A)
  }

  it should "calculate a winner in a row" in {
    val board = Board(
      Column(\, \, \, \, \, O),
      Column(\, \, \, \, \, X),
      Column(\, \, \, \, \, X),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, X),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \)
    )

    val (winner, newBoard) = boardHelper.newMove(board, "4", A)

    winner shouldBe Some(A)
  }

  it should "calculate a winner in second diagonal" in {
    val board = Board(
      Column(\, \, \, \, \, O),
      Column(\, \, \, \, O, X),
      Column(\, \, \, O, O, O),
      Column(\, \, \, X, X, X),
      Column(\, \, \, \, \, X),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \)
    )

    val (winner, newBoard) = boardHelper.newMove(board, "4", B)

    winner shouldBe Some(B)
  }

  it should "calculate a winner in first diagonal" in {
    val board = Board(
      Column(\, \, \, X, X, O),
      Column(\, \, \, O, O, X),
      Column(\, \, \, O, O, O),
      Column(\, \, \, X, X, O),
      Column(\, \, \, \, \, X),
      Column(\, \, \, \, \, \),
      Column(\, \, \, \, \, \)
    )

    val (winner, newBoard) = boardHelper.newMove(board, "1", B)

    winner shouldBe Some(B)
  }
}
