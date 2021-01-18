package com.tikhomirov.ticktacktoe.screens.unfinished_game

import com.tikhomirov.ticktacktoe.base.BaseViewModel
import com.tikhomirov.ticktacktoe.base.navigation.Screens
import com.tikhomirov.ticktacktoe.domain.usecases.remove_state.RemoveGameStateUseCase
import org.koin.core.component.inject

class UnfinishedGameViewModel : BaseViewModel() {
    private val removeGameStateUseCase: RemoveGameStateUseCase by inject()

    fun onYesClicked() {
        router.navigateTo(Screens.restoreGame())
    }

    fun onNoClicked() {
        removeGameStateUseCase.execute()
        router.navigateTo(Screens.menu(false))
    }

}