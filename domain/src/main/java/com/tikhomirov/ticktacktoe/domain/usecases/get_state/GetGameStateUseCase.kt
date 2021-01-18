package com.tikhomirov.ticktacktoe.domain.usecases.get_state

import com.tikhomirov.ticktacktoe.domain.models.GameState

interface GetGameStateUseCase {
    fun execute(): GameState?
}