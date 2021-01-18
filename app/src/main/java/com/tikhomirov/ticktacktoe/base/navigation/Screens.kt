package com.tikhomirov.ticktacktoe.base.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.tikhomirov.ticktacktoe.domain.models.GameStatus
import com.tikhomirov.ticktacktoe.screens.ai_field.AiFieldFragment
import com.tikhomirov.ticktacktoe.screens.game_finished.GameFinishedFragment
import com.tikhomirov.ticktacktoe.screens.menu.MenuFragment
import com.tikhomirov.ticktacktoe.screens.stats.StatsFragment
import com.tikhomirov.ticktacktoe.screens.unfinished_game.UnfinishedGameFragment

object Screens {
    fun menu(checkForSavedGame:Boolean = false) = FragmentScreen { MenuFragment.newInstance(checkForSavedGame) }
    fun stats() = FragmentScreen { StatsFragment.newInstance() }
    fun aiField(boardSize: Int) = FragmentScreen { AiFieldFragment.newInstance(boardSize) }
    fun restoreGame() = FragmentScreen { AiFieldFragment.newInstance(0, true) }
    fun gameFinished(gameStatus: GameStatus, boardSize: Int) =
        FragmentScreen { GameFinishedFragment.newInstance(gameStatus, boardSize) }

    fun unfinishedGame() = FragmentScreen { UnfinishedGameFragment.newInstance() }
}