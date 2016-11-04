package connect4

import java.util.concurrent.CountDownLatch

import rx.lang.scala.subjects.PublishSubject
import rx.lang.scala.{Observable, Scheduler}

class GamePresenter(view: GamePresenter.View, boardHelper: BoardHelper, ioScheduler: Scheduler) {
  def start = {
    val moves = Observable
      .from(List(A, B))
      .repeat

    var board = boardHelper.genBoard
    val endSubject = PublishSubject[Unit]()
    val latch = new CountDownLatch(1)

    view
      .moves
      .repeat
      .doOnNext(println)
      .subscribeOn(ioScheduler)
      .doOnSubscribe(showMove(board, A))
      .zip(moves)
      .map { case (param, player) => {
        val result@((_, x), _) = (boardHelper.newMove(board, param, player), player)
        board = x
        result
      }
      }
      .takeUntil(endSubject)
      .doOnNext {
        case ((None, board), player) => showMove(board, player.opposite)
        case ((Some(winner), board), _) => {
          endSubject.onNext(Unit)
          latch.countDown()
          view.showBoard(board)
          view.showWinner(winner)
        }
      }
      .subscribe()

    latch
  }

  private def showMove(board: Board, player: Player): Unit = {
    view.showBoard(board)
    view.showTurnOf(player)
  }
}

object GamePresenter {

  trait View {
    def showTurnOf(player: Player): Unit

    def showBoard(board: Board): Unit

    def showWinner(player: Player): Unit

    def moves: Observable[String]
  }

}