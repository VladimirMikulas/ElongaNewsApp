package com.vlamik.datatest

object DataTestResources {
    fun playerListJson(): String =
        loadJsonResource("playerList")

    private fun loadJsonResource(fileName: String) =
        javaClass.classLoader!!
            .getResource("$fileName.json")!!
            .readText()
}
