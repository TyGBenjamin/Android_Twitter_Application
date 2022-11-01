package com.example.lib_data.util

/**
 *
 */
object Constants {
    const val BASE_URL = "http://10.0.2.2:8080/api/"
    const val TAG = "Logger"
    const val PREFERENCES = ""
    const val username: String = "Username"
    const val password = "Password"
    const val miliTimeOne: Long = 6800L
    const val miliTimeTwo: Int = 500
    const val IMAGE: String =
        "https://image.shutterstock.com/image-vector/one-piece-character-luffy-nika-600w-2210725005.jpg"

    /**
     *
     */
    fun getIdFromUrl(url: String): Int {
        val index = url.lastIndexOf('/')
        return url.substring(index + 1).toInt()
    }


}

/**
 *
 */
object ScreenConstants {
    const val loginScreen: String = "login_screen"
    const val signUpScreen: String = "signUp_screen"
    const val splashScreen: String = "splash_screen"
    const val homeScreen: String = "home_screen"
    const val dashboardScreen: String = "dashboard_screen"
    const val addPostScreen: String = "add_post_screen"
    const val viewPostScreen: String = "view_post_screen"
    const val addCommentScreen: String = "add_comment_screen"
}


