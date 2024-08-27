package com.example.finalapplication

data class BinfoData(val rank:Int?, val name: String?, val addr: String?)

data class PhpResponse(val result : ArrayList<BinfoData>)