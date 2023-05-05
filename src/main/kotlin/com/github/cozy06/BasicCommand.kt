package com.github.cozy06

import com.github.cozy06.File.Companion.readFile
import com.github.cozy06.File.Companion.toJson
import com.github.cozy06.File.Companion.writeAll
import com.github.cozy06.Random.Companion.randomCode
import java.io.File

class BasicCommand {
    companion object {
        var Break = false
        var logined = false

        val stop: Commands = RunCommand.command("stop", "STOP") {
            Break = true
        }

        val signup: Commands = RunCommand.command("signup", "SIGNUP") {
            var email: String = ""
            while (true) {
                if (email == "") {
                    email = Logic.input(">> EMAIL: ").toString()
                } else if (File("${System.getProperty("user.dir")}/$email.json").exists()) {
                    println(">> ID existing")
                    email = Logic.input(">> EMAIL: ").toString()
                } else {
                    break
                }
            }
            val code = randomCode(5)
            Async.async {
                Email.sendEmail(
                    email,
                    "LOGIN TO SERVER",
                    "You're CODE is '$code'",
                    "testemailcozy06@gmail.com",
                    "nnqgemtfhpnvmvbb"
                )
            }
            val accessCode = Logic.input(">> CODE: ")
            if (accessCode == code) {
                var password: String = ""
                while (true) {
                    if (password.length <= 7) {
                        if (password != "") {
                            println(">> Password should Be at lest 7 words")
                        }
                        password = Logic.inputPW(">> PW: ").toString()
                    } else {
                        break
                    }
                }
                var checkPW = ""
                while (true) {
                    if (checkPW != password) {
                        if (checkPW != "") {
                            println(">> doesn't match")
                        }
                        checkPW = Logic.inputPW(">> check PW: ").toString()
                    } else {
                        break
                    }
                }
                val EncryptionPW = Encryption.hashEncryption(password, "SHA-256")
                val userinfo: File =
                    File("${System.getProperty("user.dir")}/$email.json")
                userinfo.writeAll("{\"pw\":\"$EncryptionPW\"}")
            } else {
                println(">> $accessCode isn't your CODE")
            }
        }

        val login: Commands = RunCommand.command("login", "LOGIN") {
            val email = Logic.input(">> EMAIL: ")
            val userinfoFile: File =
                File("${System.getProperty("user.dir")}/$email.json")
            if(!userinfoFile.exists()) {
                println(">> You Should Signup First")
                return@command
            }
            var password: String = ""
            while (true) {
                if (password.length <= 7) {
                    if (password != "") {
                        println(">> Password should Be at lest 7 words")
                    }
                    password = Logic.inputPW(">> PW: ").toString()
                } else {
                    break
                }
            }
            val userinfo = userinfoFile.readFile().toJson()
            if (userinfo.getString("pw") == Encryption.hashEncryption(password, "SHA-256")) {
                logined = true
                println(">> Login Success")
            } else {
                println(">> Wrong Password")
            }
        }
    }
}