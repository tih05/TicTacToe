package com.tikhomirov.ticktacktoe.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.tikhomirov.ticktacktoe.domain.fromJson
import com.tikhomirov.ticktacktoe.domain.models.GameState
import com.tikhomirov.ticktacktoe.domain.repositories.GameStateDataSource

class GameStateRepository(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : GameStateDataSource {

    companion object {
        private const val GAME_STATE = "game_state"
    }

    override fun getLastState(): GameState? {
        return sharedPreferences
            .getString(GAME_STATE, null)
            ?.let { gson.fromJson<GameState>(it) }
    }

    override fun saveState(state: GameState) {
        gson
            .toJson(state)
            .let { jsonState ->
                sharedPreferences
                    .edit {
                        putString(GAME_STATE, jsonState)
                    }
            }
    }

    override fun removeLastState() {
        sharedPreferences.edit{
            remove(GAME_STATE)
        }
    }
}