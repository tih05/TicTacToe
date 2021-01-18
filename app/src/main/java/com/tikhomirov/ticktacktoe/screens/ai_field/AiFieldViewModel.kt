package com.tikhomirov.ticktacktoe.screens.ai_field

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import com.tikhomirov.ticktacktoe.R
import com.tikhomirov.ticktacktoe.base.BaseViewModel
import com.tikhomirov.ticktacktoe.base.navigation.Screens
import com.tikhomirov.ticktacktoe.domain.models.GameStatus
import com.tikhomirov.ticktacktoe.domain.models.Move
import com.tikhomirov.ticktacktoe.domain.models.MoveType
import com.tikhomirov.ticktacktoe.domain.usecases.best_move.GetBestMoveUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.check_winner.CheckWinnerUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.get_state.GetGameStateUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.remove_state.RemoveGameStateUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.save_state.SaveGameStateUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.update_stats.UpdateStatisticsUseCase
import org.koin.core.component.inject

class AiFieldViewModel : BaseViewModel() {

    companion object {
        private const val MAX_PLAYER = 1
        private const val MIN_PLAYER = -1
    }

    private val removeGameStateUseCase: RemoveGameStateUseCase by inject()
    private val saveGameStateUseCase: SaveGameStateUseCase by inject()
    private val getGameStateUseCase: GetGameStateUseCase by inject()
    private val getBestMoveUseCase: GetBestMoveUseCase by inject()
    private val checkWinnerUseCase: CheckWinnerUseCase by inject()
    private val updateStatisticsUseCase: UpdateStatisticsUseCase by inject()

    val moveLiveData = MutableLiveData<Move>()
    val movesSetLiveData = MutableLiveData<List<Move>>()
    val movesCountLiveData = MutableLiveData(resourcesManager.getString(R.string.moves_count, 0))
    val chronoLiveData = MutableLiveData<Boolean>()
    val chronoRestoreLiveData = MutableLiveData<Long>()
    val boardSizeLiveData = MutableLiveData<Int>()


    private var moves = mutableListOf<Move>()
    private var elapsedTime = 0L
    private var boardSize = 1
    private var computerMoveType = MoveType.O
    private lateinit var board: Array<Array<Int>>


    fun setBoardSize(boardSize: Int) {
        this.boardSize = boardSize
        board = Array(boardSize) { Array(boardSize) { 0 } }
    }

    fun onMove(move: Move) {
        if (moves.size == 0) {
            chronoLiveData.postValue(true)
        }

        board[move.column][move.row] = MAX_PLAYER
        moves.add(move)

        saveGameStateUseCase
            .execute(
                time = elapsedTime,
                moves = moves,
                boardSize = boardSize
            )

        resourcesManager
            .getString(R.string.moves_count, moves.size.div(2).plus(1))
            .let { movesCountLiveData.postValue(it) }

        checkWinner(makeAiMove = true)
    }

    fun onTick(elapsedMillis: Long) {
        elapsedTime = elapsedMillis
    }


    fun onRestartClicked() {
        board = Array(boardSize) { Array(boardSize) { 0 } }
        moves = mutableListOf<Move>()
        elapsedTime = 0
        chronoLiveData.postValue(false)
        resourcesManager
            .getString(R.string.moves_count, 0)
            .let { movesCountLiveData.postValue(it) }
        movesSetLiveData.postValue(moves)
        removeGameStateUseCase.execute()
    }

    fun onHomeClicked() {
        router.newRootScreen(Screens.menu())
    }


    private fun makeAiMove() {
        getBestMoveUseCase
            .execute(board, computerMoveType)
            .let { move ->
                board[move.column][move.row] = MIN_PLAYER
                moves.add(move)
                moveLiveData.postValue(move)
            }
        checkWinner(makeAiMove = false)
    }

    private fun checkWinner(makeAiMove: Boolean) {
        checkWinnerUseCase
            .execute(board)
            .let { gameStatus ->
                updateStatisticsUseCase.execute(gameStatus)
                if (gameStatus != GameStatus.IN_PROGRESS) {
                    router.navigateTo(Screens.gameFinished(gameStatus, boardSize))
                    chronoLiveData.postValue(false)
                } else if (makeAiMove) {
                    makeAiMove()
                }
            }
    }

    fun restoreGame() {
        getGameStateUseCase
            .execute()
            ?.let {
                setBoardSize(it.boardSize)
                boardSizeLiveData.postValue(it.boardSize)
                moves = it.moves.toMutableList()
                moves.forEach { move ->
                    board[move.column][move.row] = move.type.points
                }
                moves
                    .filter { it.type != computerMoveType }
                    .let { it.size }
                    .let {
                        resourcesManager
                            .getString(R.string.moves_count, it)
                    }
                    .let { movesCountLiveData.postValue(it) }

                movesSetLiveData.postValue(moves)
                elapsedTime = it.time
                chronoRestoreLiveData.postValue(SystemClock.elapsedRealtime() - elapsedTime)
            }
    }

}