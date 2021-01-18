package com.tikhomirov.ticktacktoe.base.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tikhomirov.ticktacktoe.domain.usecases.best_move.GetBestMoveUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.best_move.GetBestMoveUseCaseImpl
import com.tikhomirov.ticktacktoe.domain.usecases.check_winner.CheckWinnerUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.check_winner.CheckWinnerUseCaseImpl
import com.tikhomirov.ticktacktoe.domain.usecases.get_state.GetGameStateUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.get_state.GetGameStateUseCaseImpl
import com.tikhomirov.ticktacktoe.domain.usecases.get_stats.GetStatisticsUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.get_stats.GetStatisticsUseCaseImpl
import com.tikhomirov.ticktacktoe.domain.usecases.remove_state.RemoveGameStateUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.remove_state.RemoveGameStateUseCaseImpl
import com.tikhomirov.ticktacktoe.domain.usecases.save_state.SaveGameStateUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.save_state.SaveGameStateUseCaseImpl
import com.tikhomirov.ticktacktoe.domain.usecases.update_stats.UpdateStatisticsUseCase
import com.tikhomirov.ticktacktoe.domain.usecases.update_stats.UpdateStatisticsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<Gson> { GsonBuilder().create() }

    factory<SaveGameStateUseCase> { SaveGameStateUseCaseImpl(gameStateDataSource = get()) }
    factory<GetGameStateUseCase> { GetGameStateUseCaseImpl(gameStateDataSource = get()) }
    factory<RemoveGameStateUseCase> { RemoveGameStateUseCaseImpl(gameStateDataSource = get()) }
    factory<CheckWinnerUseCase> { CheckWinnerUseCaseImpl() }
    factory<GetBestMoveUseCase> { GetBestMoveUseCaseImpl(checkWinnerUseCase = get()) }
    factory<UpdateStatisticsUseCase> { UpdateStatisticsUseCaseImpl(statisticsDataSource = get()) }
    factory<GetStatisticsUseCase> { GetStatisticsUseCaseImpl(statisticsDataSource = get()) }
}