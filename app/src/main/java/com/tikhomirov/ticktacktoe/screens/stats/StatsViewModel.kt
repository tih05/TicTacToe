package com.tikhomirov.ticktacktoe.screens.stats

import androidx.lifecycle.MutableLiveData
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.tikhomirov.ticktacktoe.base.BaseViewModel
import com.tikhomirov.ticktacktoe.domain.usecases.get_stats.GetStatisticsUseCase
import org.koin.core.component.inject

class StatsViewModel : BaseViewModel() {

    companion object {
        private const val WINS = "Wins"
        private const val LOSES = "Loses"
        private const val TIES = "Ties"
    }

    val statsLiveData = MutableLiveData<List<ValueDataEntry>>()

    private val getStatisticsUseCase: GetStatisticsUseCase by inject()

    fun loadStats() {
        getStatisticsUseCase
            .execute()
            .let { stats ->
                listOf(
                    ValueDataEntry(WINS, stats.wins),
                    ValueDataEntry(LOSES, stats.loses),
                    ValueDataEntry(TIES, stats.ties),
                )
            }
            .let {
                statsLiveData.postValue(it)
            }
    }


}


