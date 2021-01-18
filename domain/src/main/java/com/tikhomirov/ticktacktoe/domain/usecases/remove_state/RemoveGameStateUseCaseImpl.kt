package com.tikhomirov.ticktacktoe.domain.usecases.remove_state

import com.tikhomirov.ticktacktoe.domain.repositories.GameStateDataSource

class RemoveGameStateUseCaseImpl(
    private val gameStateDataSource: GameStateDataSource
): RemoveGameStateUseCase {
    override fun execute() {
        gameStateDataSource.removeLastState()
    }

}