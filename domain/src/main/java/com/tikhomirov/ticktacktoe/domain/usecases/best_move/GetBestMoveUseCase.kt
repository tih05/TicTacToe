package com.tikhomirov.ticktacktoe.domain.usecases.best_move

import com.tikhomirov.ticktacktoe.domain.models.Move
import com.tikhomirov.ticktacktoe.domain.models.MoveType

interface GetBestMoveUseCase {
    fun execute(board: Array<Array<Int>>, computerMoveType: MoveType): Move
}