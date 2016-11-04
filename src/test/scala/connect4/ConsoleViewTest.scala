package connect4

import java.io.{ByteArrayInputStream, InputStream, PrintStream}

import org.mockito.Mockito._
import rx.lang.scala.observers.TestSubscriber


class ConsoleViewTest extends BaseTest {

  var in: InputStream = _
  var out: PrintStream = _
  var view: ConsoleView = _

  override protected def beforeEach(): Unit = {
    val x: PrintStream = System.out
    val y: InputStream = System.in
    in = mock[InputStream]
    out = mock[PrintStream]
    view = new ConsoleView(in, out)
  }

  it should "print the turn of the player" in {
    view.showTurnOf(A)

    verify(out).println("Turn of A")

    view.showTurnOf(B)
    verify(out).println("Turn of B")
  }

  it should "show the board" in {
    import Board._
    val board = Board(
      Column(\, \, \, X),
      Column(\, \, X, O),
      Column(\, \, X, X),
      Column(\, \, \, \),
      Column(\, \, \, \)
    )

    view.showBoard(board)

    verify(out, times(2)).println("| | | | | |")
    verify(out).println("| |X|X| | |")
    verify(out).println("|X|O|X| | |")
    verify(out).println("|1|2|3|4|5|")
  }

  it should "show a winner" in {
    view.showWinner(A)
    verify(out).println("A wins!")

    view.showWinner(B)
    verify(out).println("B wins!")
  }

  it should "get input from user" in {
    import StringOps._

    val testSubscriber = TestSubscriber[String]()
    val y = "3".byteInputStream
    view = new ConsoleView(y, out)
    view.moves.subscribe(testSubscriber)

    testSubscriber.assertValues("3")
  }
}




