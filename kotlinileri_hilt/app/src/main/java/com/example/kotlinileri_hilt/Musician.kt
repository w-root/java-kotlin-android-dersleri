package com.example.kotlinileri_hilt

import javax.inject.Inject

class Musician
    @Inject
    constructor(instrument : Instrument,band: Band)  {

    fun sing(){
        println("Working")
    }
}