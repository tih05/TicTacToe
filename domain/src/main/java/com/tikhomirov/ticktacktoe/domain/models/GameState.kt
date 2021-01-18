package com.tikhomirov.ticktacktoe.domain.models

class GameState(
    val time: Long,
    val moves: List<Move>,
    val boardSize: Int
)