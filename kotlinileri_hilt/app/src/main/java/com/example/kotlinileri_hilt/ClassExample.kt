package com.example.kotlinileri_hilt

import javax.inject.Inject

class ClassExample @Inject constructor(private val implementer: MyInterface) {

    fun myFunction() : String {
        return "Working : ${implementer.printFunction()}"
    }
}