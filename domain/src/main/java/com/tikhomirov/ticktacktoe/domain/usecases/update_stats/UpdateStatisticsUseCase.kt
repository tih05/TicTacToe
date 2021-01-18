package com.tikhomirov.ticktacktoe.domain.usecases.update_stats

import com.tikhomirov.ticktacktoe.domain.models.GameStatus

interface UpdateStatisticsUseCase {
    fun execute(currentGameStatus: GameStatus)
}