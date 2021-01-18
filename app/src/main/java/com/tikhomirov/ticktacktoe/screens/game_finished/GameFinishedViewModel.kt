package com.tikhomirov.ticktacktoe.screens.game_finished

import androidx.lifecycle.MutableLiveData
import com.tikhomirov.ticktacktoe.R
import com.tikhomirov.ticktacktoe.base.BaseViewModel
import com.tikhomirov.ticktacktoe.base.navigation.Screens
import com.tikhomirov.ticktacktoe.domain.models.GameStatus

class GameFinishedViewModel : BaseViewModel() {

    companion object {
        private const val DEFAULT_BOARD_SIZE = 3
    }

    val backgroundLiveData = MutableLiveData<Int>()
    val statusTextLiveData = MutableLiveData<String>()

    private var boardSize: Int = DEFAULT_BOARD_SIZE

    fun onViewCreated(gameStatus: GameStatus, boardSize: Int) {
        this.boardSize = boardSize
        when (gameStatus) {
            GameStatus.X_WON -> {
                resourcesManager
                    .getColor(R.color.green_05ad3a)
                    .let { backgroundLiveData.postValue(it) }
                resourcesManager
                    .getString(R.string.you_win)
                    .let { statusTextLiveData.postValue(it) }

            }
            GameStatus.O_WON -> {
                resourcesManager
                    .getColor(R.color.red_ffff4444)
                    .let { backgroundLiveData.postValue(it) }
                resourcesManager
                    .getString(R.string.you_lose)
                    .let { statusTextLiveData.postValue(it) }
            }
            GameStatus.TIE -> {
                resourcesManager
                    .getColor(R.color.yellow_FFEB3B)
                    .let { backgroundLiveData.postValue(it) }
                resourcesManager
                    .getString(R.string.tie)
                    .let { statusTextLiveData.postValue(it) }
            }
            else -> null /*no-op*/
        }
    }

    fun onRetryClicked() {
        router.newRootScreen(Screens.aiField(boardSize))
    }

    fun onHomeClicked() {
        router.newRootScreen(Screens.menu())
    }

    fun onStatsClicked() {
        router.newRootScreen(Screens.stats())

    }
}