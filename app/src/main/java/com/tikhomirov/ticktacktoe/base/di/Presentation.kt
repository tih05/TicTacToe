package com.tikhomirov.ticktacktoe.base.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.tikhomirov.ticktacktoe.base.ResourcesManager
import com.tikhomirov.ticktacktoe.screens.ai_field.AiFieldViewModel
import com.tikhomirov.ticktacktoe.screens.game_finished.GameFinishedViewModel
import com.tikhomirov.ticktacktoe.screens.menu.MenuViewModel
import com.tikhomirov.ticktacktoe.screens.stats.StatsViewModel
import com.tikhomirov.ticktacktoe.screens.unfinished_game.UnfinishedGameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    single { get<Cicerone<Router>>().router }
    single { Cicerone.create() }
    single { ResourcesManager(androidContext()) }


    viewModel { MenuViewModel() }
    viewModel { AiFieldViewModel() }
    viewModel { GameFinishedViewModel() }
    viewModel { UnfinishedGameViewModel() }
    viewModel { StatsViewModel() }

}