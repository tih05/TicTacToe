package com.tikhomirov.ticktacktoe.screens.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tikhomirov.ticktacktoe.R
import com.tikhomirov.ticktacktoe.base.BaseViewModel
import com.tikhomirov.ticktacktoe.base.navigation.Screens
import com.tikhomirov.ticktacktoe.domain.models.Move
import com.tikhomirov.ticktacktoe.domain.models.MoveType
import com.tikhomirov.ticktacktoe.domain.usecases.get_state.GetGameStateUseCase
import com.tikhomirov.ticktacktoe.domain.withMove
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import kotlin.random.Random

class MenuViewModel : BaseViewModel() {

    companion object {
        private const val MIN_SIZE = 3
        private const val MAX_SIZE = 10
    }


    val fieldSizeLiveData = MutableLiveData(3)
    val fieldSizeTextLiveData = MutableLiveData("3x3")
    val movesLiveData = MutableLiveData(generateMovesDataSet())

    private val getGameStateUseCase: GetGameStateUseCase by inject()

    fun onAiClicked() {
        fieldSizeLiveData.value
            ?.let {
                router.navigateTo(Screens.aiField(it))
            }

    }

    fun onMinusClicked() {
        fieldSizeLiveData.value
            ?.takeIf { currentSize -> currentSize - 1 >= MIN_SIZE }
            ?.let { currentSize ->
                fieldSizeLiveData.postValue(currentSize - 1)
                movesLiveData.postValue(generateMovesDataSet())
                resourcesManager
                    .getString(R.string.field_size, currentSize - 1, currentSize - 1)
                    .let { fieldSizeTextLiveData.postValue(it) }
            }
    }

    fun onPlusClicked() {
        fieldSizeLiveData.value
            ?.takeIf { currentSize -> currentSize + 1 <= MAX_SIZE }
            ?.let { currentSize ->
                fieldSizeLiveData.postValue(currentSize + 1)
                movesLiveData.postValue(generateMovesDataSet())
                resourcesManager
                    .getString(R.string.field_size, currentSize + 1, currentSize + 1)
                    .let { fieldSizeTextLiveData.postValue(it) }
            }
    }

    fun onStatsClicked() {
        router.navigateTo(Screens.stats())
    }


    fun checkForSavedGame() {
        getGameStateUseCase
            .execute()
            ?.let {
                router.navigateTo(Screens.unfinishedGame())
            }
    }

    private fun generateMovesDataSet(): List<Move> {
        val moves = mutableListOf<Move>()
        fieldSizeLiveData.value
            ?.let { currentSize ->
                val amount = Random.nextInt(currentSize, currentSize * currentSize)
                for (i in 0..amount) {
                    val column = Random.nextInt(0, currentSize)
                    val row = Random.nextInt(0, currentSize)
                    val move = Random.nextBoolean()
                        .takeIf { it }
                        ?.let { MoveType.X }
                        ?: MoveType.O
                    moves
                        .firstOrNull { it.column == column && it.row == row }
                        ?: moves.add(column to row withMove move)
                }
            }
        return moves
    }

}


