package com.github.cozy06

import java.io.File
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader

class ImportJar {
    fun loadJarIndDir(dir: String): Array<URL>? {
        try {
            val dirFile = File(dir)

            val jarFiles = dirFile.listFiles { file ->
                file.toString().lowercase().endsWith(".jar")
            }

            if (jarFiles == null || jarFiles.isEmpty()) {
                println("Plugins Not Found...")
                return null
            }

            val urls = jarFiles.map { file ->
                file.toURI().toURL()
            }.toTypedArray()

            val loader = URLClassLoader.newInstance(urls, ClassLoader.getSystemClassLoader())

            val method: Method = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
            method.isAccessible = true

            for (url in urls) {
                try {
                    method.invoke(loader, url)
                    println("${url.file} loaded.")
                } catch (e: Exception) {
                    println("failed loading ${url.file}")
                    println(e)
                }
            }
            return urls
        } catch (e: Exception) {
            throw e
        }
    }


    fun callClassCommand(jarList: Array<URL>?, input: String?) {
        if (jarList != null) {
            for (urls in jarList) {
                val classLoader = URLClassLoader(arrayOf(urls))

                val clazz = classLoader.loadClass("server.plugin.Main")
                val obj = clazz.getDeclaredConstructor().newInstance()


                val commandMethod = clazz.getMethod("command", String::class.java)
                commandMethod.invoke(obj, input)
            }
        }
    }

    fun callClassPassive(jarList: Array<URL>?) {
        if (jarList != null) {
            for (urls in jarList) {
                val classLoader = URLClassLoader(arrayOf(urls))

                val clazz = classLoader.loadClass("server.plugin.Main")
                val obj = clazz.getDeclaredConstructor().newInstance()

                try {
                    val passiveMethod = clazz.getMethod("passive")
                    passiveMethod.invoke(obj)
                    Thread.sleep(50L)
                } catch (e: Exception) {  }
            }
        }
    }
}