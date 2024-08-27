package com.example.finalapplication

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class XmlResponse(
    @Element
    val body : myXmlBody
)

@Xml(name="body")
data class myXmlBody(
    @Element
    val items : myXmlItems
)

@Xml(name="items")
data class myXmlItems(
    @Element
    val item : MutableList<myXmlItem>
)

@Xml(name="item")
data class myXmlItem(
    @PropertyElement
    val sidoNm:String?,
    @PropertyElement
    val gugunNm:String?,
    @PropertyElement
    val staNm:String?,
    @PropertyElement
    val beachKnd:String?,
    @PropertyElement
    val linkAddr:String?,
    @PropertyElement
    val linkTel:String?,
    @PropertyElement
    val beachImg:String?,
) {
    constructor() : this(null, null, null, null, null, null, null)
}
