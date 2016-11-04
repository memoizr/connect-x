package connect4

import java.io.{InputStream, PrintStream}

import org.mockito.Mockito._
import rx.lang.scala.schedulers.ImmediateScheduler

class GameIntegrationTest extends BaseTest {

  var in: InputStream = _
  var out: PrintStream = _
  var game: Game = _

  override protected def beforeEach(): Unit = {
    import StringOps._
    in =
      """|3
        |4
        |3
        |4
        |3
        |4
        |3
        |4
      """.stripMargin.byteInputStream
    out = mock[PrintStream]

    game = new Game(in, out, (6, 7), ImmediateScheduler())
  }


  ignore should "play a game" in {
    game.start

    verify(out, times(4)).println("Turn of A")
    verify(out, times(3)).println("Turn of B")
  }
}
