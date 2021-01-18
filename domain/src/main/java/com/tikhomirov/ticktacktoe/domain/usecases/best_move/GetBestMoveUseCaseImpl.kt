package com.tikhomirov.ticktacktoe.domain.usecases.best_move

import com.tikhomirov.ticktacktoe.domain.models.GameStatus
import com.tikhomirov.ticktacktoe.domain.models.Move
import com.tikhomirov.ticktacktoe.domain.models.MoveType
import com.tikhomirov.ticktacktoe.domain.usecases.check_winner.CheckWinnerUseCase
import com.tikhomirov.ticktacktoe.domain.withMove

class GetBestMoveUseCaseImpl(
    private val checkWinnerUseCase: CheckWinnerUseCase
) : GetBestMoveUseCase {
    companion object {
        private const val MAX_PLAYER = 1
        private const val MIN_PLAYER = -1
        private const val EMPTY = 0
    }

    private var alpha = Int.MIN_VALUE
    private var beta = Int.MAX_VALUE

    override fun execute(board: Array<Array<Int>>, computerMoveType: MoveType): Move {
        var bestScore = Int.MAX_VALUE
        var move: Move? = null
        for (column in board.indices) {
            for (row in board.indices) {
                if (board[column][row] == 0) {
                    board[column][row] = MIN_PLAYER
                    val score = miniMax(board, true)
                    if (score < bestScore) {
                        bestScore = score
                        move = column to row withMove computerMoveType
                    }
                    board[column][row] = EMPTY
                    alpha = Int.MIN_VALUE
                    beta = Int.MAX_VALUE
                }
            }
        }
        return move!!
    }

    private fun miniMax(board: Array<Array<Int>>, isMaximizing: Boolean): Int {
        val result = checkWinnerUseCase.execute(board)
        if (result != GameStatus.IN_PROGRESS) {
            return result.points
        }

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            mainLoop@ for (column in board.indices) {
                for (row in board.indices) {
                    if (board[column][row] == EMPTY) {
                        board[column][row] = MAX_PLAYER
                        val score = miniMax(board, false)
                        board[column][row] = EMPTY
                        bestScore = maxOf(score, bestScore)
                        alpha = maxOf(alpha, bestScore)
                        if (beta <= alpha) {
                            break@mainLoop
                        }
                    }
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            mainLoop@ for (column in board.indices) {
                for (row in board.indices) {
                    if (board[column][row] == EMPTY) {
                        board[column][row] = MIN_PLAYER
                        val score = miniMax(board, true)
                        board[column][row] = EMPTY
                        bestScore = minOf(score, bestScore)
                        beta = minOf(beta, bestScore)
                        if (beta <= alpha) {
                            break@mainLoop
                        }
                    }
                }
            }
            return bestScore
        }
    }
}