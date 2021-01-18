package com.tikhomirov.ticktacktoe.domain.models

data class Move(
    val column: Int,
    val row: Int,
    val type: MoveType
)