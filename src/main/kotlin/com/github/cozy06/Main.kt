package com.github.cozy06

import com.github.cozy06.Async.Companion.async
import com.github.cozy06.BasicCommand.Companion.Break
import com.github.cozy06.BasicCommand.Companion.login
import com.github.cozy06.BasicCommand.Companion.signup
import com.github.cozy06.BasicCommand.Companion.stop
import com.github.cozy06.Logic.Companion.input
import com.github.cozy06.RunCommand.Companion.RUN

val jarList = ImportJar().loadJarIndDir("${System.getProperty("user.dir")}/plugins/")

fun main() {
    var IN: String? = ""
    var inputOK = true
    while(!Break) {
        if(inputOK) { async {
            inputOK = false
            IN = input(">> ")
            ImportJar().callClassCommand(jarList, IN)
            signup.RUN(IN)
            login.RUN(IN)
            stop.RUN(IN)
            Thread.sleep(50L)
            inputOK = true
        } }
        ImportJar().callClassPassive(jarList)
    }
}