package com.tikhomirov.ticktacktoe.domain.usecases.update_stats

import com.tikhomirov.ticktacktoe.domain.models.GameStatus
import com.tikhomirov.ticktacktoe.domain.repositories.StatisticsDataSource

class UpdateStatisticsUseCaseImpl(
    private val statisticsDataSource: StatisticsDataSource
) : UpdateStatisticsUseCase {
    override fun execute(currentGameStatus: GameStatus) {
        when (currentGameStatus) {
            GameStatus.X_WON -> statisticsDataSource.incrementWins()
            GameStatus.O_WON -> statisticsDataSource.incrementLoses()
            GameStatus.TIE -> statisticsDataSource.incrementTies()
            GameStatus.IN_PROGRESS -> null /*no-op*/
        }
    }
}