package com.tikhomirov.ticktacktoe.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.tikhomirov.ticktacktoe.domain.models.GameStatistics
import com.tikhomirov.ticktacktoe.domain.repositories.StatisticsDataSource

class StatisticsRepository(
    private val sharedPreferences: SharedPreferences,
) : StatisticsDataSource {

    companion object {
        private const val WINS = "wins"
        private const val LOSES = "loses"
        private const val TIES = "ties"
    }

    override fun incrementLoses() {
        sharedPreferences
            .getInt(LOSES, 0)
            .let { it + 1 }
            .let { newCount ->
                sharedPreferences.edit {
                    putInt(LOSES, newCount)
                }
            }
    }

    override fun incrementWins() {
        sharedPreferences
            .getInt(WINS, 0)
            .let { it + 1 }
            .let { newCount ->
                sharedPreferences.edit {
                    putInt(WINS, newCount)
                }
            }
    }

    override fun incrementTies() {
        sharedPreferences
            .getInt(TIES, 0)
            .let { it + 1 }
            .let { newCount ->
                sharedPreferences.edit {
                    putInt(TIES, newCount)
                }
            }
    }

    override fun getGameStats(): GameStatistics {
        val wins =  sharedPreferences
            .getInt(WINS, 0)
        val ties =  sharedPreferences
            .getInt(TIES, 0)
        val loses =  sharedPreferences
            .getInt(LOSES, 0)
        return GameStatistics(wins, loses, ties)
    }
}