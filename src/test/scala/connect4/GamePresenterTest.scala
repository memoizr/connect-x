package connect4

import connect4.Board._
import org.mockito.Matchers._
import org.mockito.Mockito._
import rx.lang.scala.schedulers.ImmediateScheduler
import rx.lang.scala.subjects.PublishSubject

class GamePresenterTest extends BaseTest {

  var view: GamePresenter.View = _
  var boardHelper:BoardHelper = _
  var presenter: GamePresenter = _
  val movesSubject = PublishSubject[String]()

  override protected def beforeEach(): Unit = {
    view = mock[GamePresenter.View]
    boardHelper = mock[BoardHelper]
    presenter = new GamePresenter(view, boardHelper, ImmediateScheduler())

    when(view.moves).thenReturn(movesSubject)
    when(boardHelper.newMove(any(), any(), any())).thenReturn((None, Board()))
  }

  "Game" should "start with Player A" in {
    presenter.start
    verify(view).showTurnOf(A)
  }

  "Players" should "take turn" in {
    presenter.start

    verify(view).showTurnOf(A)

    movesSubject.onNext("x")

    verify(view).showTurnOf(B)

    movesSubject.onNext("x")

    verify(view, times(2)).showTurnOf(A)
  }

  "Board" should "be shown before each turn" in {
    presenter.start

    verify(view).showBoard(any[Board]())

    movesSubject.onNext("x")

    verify(view, times(2)).showBoard(any[Board]())
  }

  "An appropriate new board" should "be computed at each step" in {

    when(boardHelper.genBoard).thenReturn(Board())
    presenter.start

    val boardOne: Board = Board(Column(\))
    val boardTwo: Board = Board(Column(X))

    when(boardHelper.newMove(any(), any(), any())).thenReturn((None, boardOne), (None, boardTwo))

    movesSubject.onNext("x")

    verify(boardHelper).newMove(Board(), "x", A)
    verify(view).showBoard(boardOne)

    movesSubject.onNext("x")

    verify(boardHelper).newMove(boardOne, "x", B)
    verify(view).showBoard(boardTwo)
  }

  "A winner" should "be declared" in {
    presenter.start
    val boardOne: Board = Board(Column(\))
    when(boardHelper.newMove(any(), any(), any())).thenReturn((Some(A), boardOne))

    movesSubject.onNext("x")

    verify(view).showWinner(A)
    verify(view).showBoard(boardOne)
    verify(view, never()).showTurnOf(B)
  }
}
