package com.tikhomirov.ticktacktoe.domain.usecases.get_stats

import com.tikhomirov.ticktacktoe.domain.models.GameStatistics

interface GetStatisticsUseCase {
    fun execute(): GameStatistics
}