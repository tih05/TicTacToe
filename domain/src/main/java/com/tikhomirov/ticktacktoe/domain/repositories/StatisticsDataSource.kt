package com.tikhomirov.ticktacktoe.domain.repositories

import com.tikhomirov.ticktacktoe.domain.models.GameStatistics

interface StatisticsDataSource {
    fun incrementLoses()
    fun incrementWins()
    fun incrementTies()
    fun getGameStats(): GameStatistics
}