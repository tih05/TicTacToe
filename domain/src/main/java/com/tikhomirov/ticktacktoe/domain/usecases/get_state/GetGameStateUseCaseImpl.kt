package com.tikhomirov.ticktacktoe.domain.usecases.get_state

import com.tikhomirov.ticktacktoe.domain.models.GameState
import com.tikhomirov.ticktacktoe.domain.repositories.GameStateDataSource

class GetGameStateUseCaseImpl(
    private val gameStateDataSource: GameStateDataSource
) : GetGameStateUseCase {
    override fun execute(): GameState? {
        return gameStateDataSource.getLastState()
    }
}