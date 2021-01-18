package com.tikhomirov.ticktacktoe.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tikhomirov.ticktacktoe.domain.models.Move
import com.tikhomirov.ticktacktoe.domain.models.MoveType

infix fun Pair<Int, Int>.withMove(that: MoveType): Move = Move(this.first, this.second, that)

inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)