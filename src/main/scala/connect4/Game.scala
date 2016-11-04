package connect4

import java.io.{InputStream, PrintStream}

import rx.lang.scala.Scheduler
import rx.lang.scala.schedulers.IOScheduler

object GameApp extends App {
  new Game(System.in, System.out, (6,9), IOScheduler()).start.await()
}

class Game(in: InputStream, out: PrintStream, size: (Int, Int), scheduler: Scheduler) {
  val view = new ConsoleView(in, out)
  val gamePresenter = new GamePresenter(view, new BoardHelper(size._1, size._2), scheduler)

  def start = gamePresenter.start
}
