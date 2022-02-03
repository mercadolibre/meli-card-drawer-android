package com.meli.android.carddrawer.app

object TestUtils {

    fun <T: Any> T.getDeclaredField(name: String): Any {
        return javaClass.getDeclaredField(name).let {
            it.isAccessible = true
            it.get(this) as Any
        }
    }

}