package com.github.cozy06

data class CommandLogic(
    val name: List<String>,
    val action: () -> Unit
)

typealias Commands = CommandLogic