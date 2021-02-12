package com.yuhdeveloper.yahsi

import android.content.Context

class UserPreferences(context: Context) {

    var context: Context = context


    fun login(
        password: String,
        email: String,
        token: String,
        name:String,
        surname: String
    ) {
        var pref: SharedPreferencesManager = SharedPreferencesManager()
        pref.setPrefLogin(context, true)
        pref.setPrefEmail(context, email)
        pref.setToken(context, token)

    }

    fun logout() {
        var pref: SharedPreferencesManager = SharedPreferencesManager()
        pref.setPrefPassword(context, "")
        pref.setPrefLogin(context, false)
        pref.setPrefEmail(context, "")
        pref.setToken(context, "")
        pref.setPrefRozet(context, 0)
        pref.setPrefAvatarID(context, "")
        pref.setUnRead(context, 0)
    }

    fun checkLogin(): Boolean {
        var pref: SharedPreferencesManager = SharedPreferencesManager()
        return pref.getPrefLogin(context)
    }

    fun getEmail(): String {
        var pref: SharedPreferencesManager = SharedPreferencesManager()
        return pref.getPrefEmail(context)
    }

    fun getPassword(): String {
        var pref: SharedPreferencesManager = SharedPreferencesManager()
        return pref.getPrefPassword(context)
    }
}
