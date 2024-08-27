package com.example.joyceapplication


import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

data class myJsonItems(val RSTR_NM:String, val AREA_NM:String, val FOOD_IMG_URL:String)
data class myJsonBody(val items: MutableList<myJsonItems>)
data class myJsonResponse(val body: myJsonBody)
data class JsonResponse(val response : myJsonResponse)