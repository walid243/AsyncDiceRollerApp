package com.example.asyncdicerollerapp

import android.widget.ImageView
import kotlinx.coroutines.Deferred

class Player(val name: String, var dice1:ImageView, var dice2:ImageView) {
    lateinit var valueDice1: Deferred<Int>
    lateinit var valueDice2: Deferred<Int>

    suspend fun result(): Int {
        return valueDice1.await() + valueDice2.await()
    }
}