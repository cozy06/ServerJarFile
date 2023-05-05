package com.github.cozy06

import com.github.cozy06.Logic.Companion.loop

class Random {
    companion object {
        fun randomCode(length: Int): String {
            var randoms: String = ""
            loop({randoms += (0..9).random().toString()}, length)
            return randoms
        }
    }
}