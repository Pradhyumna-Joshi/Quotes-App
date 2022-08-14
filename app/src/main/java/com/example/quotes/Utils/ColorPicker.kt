package com.example.quotes.Utils

object ColorPicker {

    private var colorArray  = arrayOf("#f1c40f","#c0392b","#8e44ad","#27ae60","#2c3e50","#2980b9"
    ,"#e67e22","#e74c3c","#1abc9c","#af8536","#fed361")

    private var colorIndex = 1

    fun getColor() = colorArray[colorIndex++ % colorArray.size]
}