package com.tikhomirov.ticktacktoe.domain.repositories

import com.tikhomirov.ticktacktoe.domain.models.GameState

interface GameStateDataSource {
    fun getLastState(): GameState?
    fun saveState(state:GameState)
    fun removeLastState()
}