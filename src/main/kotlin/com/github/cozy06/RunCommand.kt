package com.github.cozy06

import com.github.cozy06.Logic.Companion.loop

class RunCommand {
    companion object {
        fun Commands.RUN(input: String?) {
            loop({
                if (input == this.name[it]) {
                    this.action()
                }
            }, this.name.size)
        }

        fun command(vararg name: String, action: () -> Unit): Commands {
            return Commands(name.toList(), action)
        }
    }
}