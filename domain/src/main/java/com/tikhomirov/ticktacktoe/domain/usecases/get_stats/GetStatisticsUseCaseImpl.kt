package com.tikhomirov.ticktacktoe.domain.usecases.get_stats

import com.tikhomirov.ticktacktoe.domain.models.GameStatistics
import com.tikhomirov.ticktacktoe.domain.repositories.StatisticsDataSource

class GetStatisticsUseCaseImpl(
    private val statisticsDataSource: StatisticsDataSource
) : GetStatisticsUseCase {
    override fun execute(): GameStatistics {
        return statisticsDataSource.getGameStats()
    }
}