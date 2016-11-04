package connect4

import java.io.{InputStream, PrintStream}
import java.util.Scanner

import rx.lang.scala.Observable
import rx.schedulers.Schedulers

class ConsoleView(in: InputStream, out: PrintStream) extends GamePresenter.View {

  import Board._

  override def showTurnOf(player: Player): Unit = out.println(s"Turn of ${
    player match {
      case A => "A"
      case B => "B"
    }
  }")

  override def showBoard(board: Board): Unit = {
    val rows = board.columns.head.size
    val transpose = (0 until rows).map(i => board.columns.map(column => column(i)))
    val mappedTranspose = transpose.map(_.map {
      case X => "X"
      case O => "O"
      case \ => " "
    }.mkString("|", "|", "|"))

    mappedTranspose.foreach(out.println)
    val pointers = board.columns.indices.map(_ + 1).mkString("|", "|", "|")
    out.println(pointers)
  }

  override def showWinner(player: Player): Unit = out.println(s"${
    player match {
      case A => "A"
      case B => "B"
    }
  } wins!")

  override def moves: Observable[String] = {
    Observable { observer =>
      val scanner = new Scanner(in)
      if (scanner.hasNextLine) {
        observer.onNext(scanner.nextLine())
      }
      observer.onCompleted()
    }
  }
}
