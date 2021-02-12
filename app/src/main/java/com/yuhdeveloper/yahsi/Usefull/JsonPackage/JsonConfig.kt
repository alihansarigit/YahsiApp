package com.yuhdeveloper.yahsi.Usefull.JsonPackage

import com.google.gson.Gson
import com.yuhdeveloper.yahsi.Pojos.OrderProperties
import com.yuhdeveloper.yahsi.Pojos.Users
import com.yuhdeveloper.yahsi.SharedPreferencesManager

class JsonConfig {


    companion object{

        fun convertStringFromModel(orderProperties: OrderProperties):String{
            val gson = Gson()
            val json = gson.toJson(orderProperties)

            return json
        }

        fun convertModelFromString(json:String):OrderProperties{
            val gson = Gson()
            val orderDetails: OrderProperties = gson.fromJson<OrderProperties>(json, OrderProperties::class.java)

            return orderDetails
        }
    }
}