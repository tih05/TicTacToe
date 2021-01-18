package com.tikhomirov.ticktacktoe.domain.usecases.save_state

import com.tikhomirov.ticktacktoe.domain.models.GameState
import com.tikhomirov.ticktacktoe.domain.models.Move
import com.tikhomirov.ticktacktoe.domain.repositories.GameStateDataSource

class SaveGameStateUseCaseImpl(
    private val gameStateDataSource: GameStateDataSource
) : SaveGameStateUseCase {
    override fun execute(time: Long, moves: List<Move>, boardSize: Int) {
        gameStateDataSource.saveState(
            GameState(
                time = time,
                moves = moves,
                boardSize = boardSize
            )
        )
    }
}