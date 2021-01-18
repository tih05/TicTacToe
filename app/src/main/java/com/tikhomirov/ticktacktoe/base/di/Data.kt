package com.tikhomirov.ticktacktoe.base.di

import android.content.Context
import com.tikhomirov.ticktacktoe.data.repositories.GameStateRepository
import com.tikhomirov.ticktacktoe.data.repositories.StatisticsRepository
import com.tikhomirov.ticktacktoe.domain.repositories.GameStateDataSource
import com.tikhomirov.ticktacktoe.domain.repositories.StatisticsDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { androidContext().getSharedPreferences("TicTacToe", Context.MODE_PRIVATE) }

    single<GameStateDataSource> { GameStateRepository(sharedPreferences = get(), gson = get()) }
    single<StatisticsDataSource> { StatisticsRepository(sharedPreferences = get()) }
}