package com.tikhomirov.ticktacktoe.domain.models

enum class GameStatus(val points: Int) {
        X_WON(1),
        O_WON(-1),
        TIE(0),
        IN_PROGRESS(Int.MAX_VALUE)
    }