package com.tikhomirov.ticktacktoe.domain.usecases.check_winner

import com.tikhomirov.ticktacktoe.domain.models.GameStatus

interface CheckWinnerUseCase {
    fun execute(board: Array<Array<Int>>): GameStatus
}