package com.github.cozy06

open class Edit {
    interface EditCommand {
        fun command(input: String?)
        fun passive()
    }
}