package com.tikhomirov.ticktacktoe.domain.usecases.save_state

import com.tikhomirov.ticktacktoe.domain.models.Move

interface SaveGameStateUseCase {
    fun execute(time: Long, moves: List<Move>,boardSize:Int)
}