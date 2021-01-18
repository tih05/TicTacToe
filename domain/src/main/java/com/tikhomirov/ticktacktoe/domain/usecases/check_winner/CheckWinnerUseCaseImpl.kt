package com.tikhomirov.ticktacktoe.domain.usecases.check_winner

import com.tikhomirov.ticktacktoe.domain.models.GameStatus

class CheckWinnerUseCaseImpl : CheckWinnerUseCase {
    override fun execute(board: Array<Array<Int>>): GameStatus {
        var isFilled = true
        val resultsArr = Array(2 * board.size + 2) { 0 }
        for (column in board.indices) {
            for (row in board.indices) {
                isFilled = isFilled && board[column][row] != 0
                resultsArr[column] += board[column][row]
                resultsArr[board.size + row] += board[column][row]
                if (isDiagonal(column, row)) {
                    resultsArr[resultsArr.lastIndex - 1] += board[column][row]
                }
                if (isAntiDiagonal(board.size, column, row)) {
                    resultsArr[resultsArr.lastIndex] += board[column][row]
                }
            }
        }
        var result: GameStatus = GameStatus.IN_PROGRESS
        for (points in resultsArr) {
            if (points == board.size) {
                result = GameStatus.X_WON
                break;
            } else if (points == -board.size) {
                result = GameStatus.O_WON
                break;
            } else if (isFilled) {
                return GameStatus.TIE
            }
        }
        return result
    }


    private fun isDiagonal(column: Int, row: Int) = column == row
    private fun isAntiDiagonal(boardSize: Int, column: Int, row: Int) =
        column + row == boardSize - 1
}