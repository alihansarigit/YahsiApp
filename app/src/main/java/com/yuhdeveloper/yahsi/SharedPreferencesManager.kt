package com.yuhdeveloper.yahsi

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mangaship.Ids
import com.yuhdeveloper.yahsi.Pojos.Users
import java.time.temporal.ValueRange


class SharedPreferencesManager {

    companion object{
         var KEY = "Login"
         var VALUE = "IsLogin"
    }


    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
    }

    fun login(context:Context,_user:Users){
        VALUE = Ids.Pref_Login.toString()
        KEY = Ids.Pref_Login.toString()
        val editor: SharedPreferences.Editor = getSharedPreferences(context).edit()
        val gson = Gson()
        val json = gson.toJson(_user)
        editor.putString(VALUE, json);

        editor.commit()
    }


    fun getUserInformation(context:Context):Users{
        VALUE = Ids.Pref_Login.toString()
        KEY = Ids.Pref_Login.toString()
        val gson = Gson()
        var user:Users? = gson.fromJson<Users>(getSharedPreferences(context).getString(VALUE,""),Users::class.java)

        if(user==null){
            user = Users()
        }
        return user!!
    }

    fun logout(context:Context){
        VALUE = Ids.Pref_Login.toString()
        KEY = Ids.Pref_Login.toString()
        var user = getUserInformation(context)

        user.password=""
        user.uid=""
        user.name=""
        user.surname=""
        user.isVerificated=false
        user.email=""
        user.phone=""
        user.createdAt=""
        user.blocked = false


        login(context,user)
    }

    fun getUid(context:Context):String{
       return getUserInformation(context).uid!!
    }
    fun setPrefLogin(context: Context, newValue: Boolean) {
        VALUE = Ids.Pref_Login.toString()
        KEY = Ids.Pref_Login.toString()
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(VALUE, newValue)
        editor.commit()
    }

    fun getPrefLogin(context: Context): Boolean {
        KEY = Ids.Pref_Login.toString()
        VALUE = Ids.Pref_Login.toString()
        return getSharedPreferences(context).getBoolean(VALUE,false)
    }

    fun setPrefUsername(context: Context, newValue: String) {
        KEY = Ids.Pref_Username.toString()
        VALUE = Ids.Pref_Username.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getPrefUsername(context: Context): String {
        KEY = Ids.Pref_Username.toString()
        VALUE = Ids.Pref_Username.toString()

        return getSharedPreferences(context).getString(VALUE,"").toString()
    }

    fun setPrefPassword(context: Context, newValue: String) {
        VALUE = Ids.Pref_Password.toString()
        KEY = Ids.Pref_Password.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getPrefPassword(context: Context): String {
        VALUE = Ids.Pref_Password.toString()
        KEY = Ids.Pref_Password.toString()

        return getSharedPreferences(context).getString(VALUE,"").toString()
    }


    fun setPrefVipDate(context: Context, newValue: String) {
        VALUE = Ids.Pref_VipDate.toString()
        KEY = Ids.Pref_VipDate.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getPrefVipDate(context: Context): String {
        VALUE = Ids.Pref_VipDate.toString()
        KEY = Ids.Pref_VipDate.toString()

        return getSharedPreferences(context).getString(VALUE,"").toString()
    }


    fun setPrefEmail(context: Context, newValue: String) {
        VALUE = Ids.Pref_Email.toString()
        KEY = Ids.Pref_Email.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getPrefEmail(context: Context): String {
        VALUE = Ids.Pref_Email.toString()
        KEY = Ids.Pref_Email.toString()
        return getSharedPreferences(context).getString(VALUE,"").toString()
    }

    fun setReward(context: Context, newValue: Int) {
        VALUE = Ids.Pref_Reward.toString()
        KEY = Ids.Pref_Reward.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putInt(VALUE, newValue)
        editor.commit()
    }

    fun getReward(context: Context): Int {
        VALUE = Ids.Pref_Reward.toString()
        KEY = Ids.Pref_Reward.toString()
        return getSharedPreferences(context).getInt(VALUE,0)
    }

    fun setToken(context: Context, newValue: String) {
        VALUE = Ids.Pref_Token.toString()
        KEY = Ids.Pref_Token.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getToken(context: Context): String {
        VALUE = Ids.Pref_Token.toString()
        KEY = Ids.Pref_Token.toString()

        return getSharedPreferences(context).getString(VALUE,"").toString()
    }

    fun setPrefName(context: Context, newValue: String) {
        VALUE = Ids.Pref_Name.toString()
        KEY = Ids.Pref_Name.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getPrefName(context: Context): String {
        VALUE = Ids.Pref_Name.toString()
        KEY = Ids.Pref_Name.toString()
        return getSharedPreferences(context).getString(VALUE,"").toString()
    }
    fun setPrefSurame(context: Context, newValue: String) {
        VALUE = Ids.Pref_Name.toString()
        KEY = Ids.Pref_Name.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getPrefSurame(context: Context): String {
        VALUE = Ids.Pref_Name.toString()
        KEY = Ids.Pref_Name.toString()
        return getSharedPreferences(context).getString(VALUE,"").toString()
    }

    fun setPrefRozet(context: Context, newValue: Int) {
        VALUE = Ids.Pref_Rozet.toString()
        KEY = Ids.Pref_Rozet.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putInt(VALUE, newValue)
        editor.commit()
    }

    fun getPrefRozet(context: Context): Int {
        VALUE = Ids.Pref_Rozet.toString()
        KEY = Ids.Pref_Rozet.toString()
        return getSharedPreferences(context).getInt(VALUE,0)
    }

    fun setPrefAvatarID(context: Context, newValue: String) {
        VALUE = Ids.Pref_avatarid.toString()
        KEY = Ids.Pref_avatarid.toString()

        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getPrefAvatarID(context: Context): String {
        VALUE = Ids.Pref_avatarid.toString()
        KEY = Ids.Pref_avatarid.toString()
        return getSharedPreferences(context).getString(VALUE,"").toString()
    }


    fun getPrefWish(context: Context): Boolean {
        KEY = Ids.Pref_wishButton.toString()
        VALUE = Ids.Pref_wishButton.toString()
        return getSharedPreferences(context).getBoolean(VALUE,true)
    }

    fun setPrefWish(context: Context, newValue: Boolean) {
        VALUE = Ids.Pref_wishButton.toString()
        KEY = Ids.Pref_wishButton.toString()
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(VALUE, newValue)
        editor.commit()
    }


    fun setUnRead(context: Context, newValue: Int) {
        VALUE = Ids.Pref_unreadmessage.toString()
        KEY = Ids.Pref_unreadmessage.toString()
        val editor = getSharedPreferences(context).edit()
        editor.putInt(VALUE, newValue)
        editor.commit()
    }

    fun getUnRead(context: Context): Int {
        KEY = Ids.Pref_unreadmessage.toString()
        VALUE = Ids.Pref_unreadmessage.toString()
        return getSharedPreferences(context).getInt(VALUE,0)
    }


    fun setSwitchTurn(context: Context, newValue: String) {
        VALUE = Ids.Pref_SwitchTurn.toString()
        KEY = Ids.Pref_SwitchTurn.toString()
        val editor = getSharedPreferences(context).edit()
        editor.putString(VALUE, newValue)
        editor.commit()
    }

    fun getSwitchTurn(context: Context): String {
        KEY = Ids.Pref_SwitchTurn.toString()
        VALUE = Ids.Pref_SwitchTurn.toString()
        return getSharedPreferences(context).getString(VALUE,"LEFT").toString()
    }
}