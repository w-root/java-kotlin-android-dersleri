package com.example.kotlinileri_hilt

import javax.inject.Inject

class InterfaceImplementer @Inject constructor() : MyInterface {
    override fun printFunction(): String {
        return "Hello! My friend"
    }
}