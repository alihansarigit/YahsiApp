package com.yuhdeveloper.yahsi

import com.google.firebase.firestore.FieldValue

class Orders( ){

    lateinit var orderName:String
    lateinit var orderState:String
//    var orderDate: Long = 0
     lateinit var orderDate: Any
//    lateinit var orderDate: MutableMap<String, Any>
     var orderQuantity:Int = 0
      var customerID:Int = 0
 }