package com.yuhdeveloper.yahsi.Usefull.FirebasePack

import com.google.firebase.database.*
import com.yuhdeveloper.yahsi.Pojos.OrderType
import java.text.DateFormat
import java.util.*

class FirebaseConfig() {


    companion object {
        var db: FirebaseDatabase = FirebaseDatabase.getInstance()

        fun getTime(timeStamp: Long): String {
            val dateFormat: DateFormat = DateFormat.getDateTimeInstance()
            val netDate = Date(timeStamp)
            return dateFormat.format(netDate)
        }

        fun getRef(path: String): DatabaseReference {
            return db.getReference(path)
        }


        fun addOrderType(orderType: OrderType) {
            db.getReference("Models").push().setValue(orderType)
        }


//        fun getOrderTypes() {
//
//            var types = db.getReference("Models")
//
//            var list: ArrayList<OrderType> = ArrayList()
//            types.addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    for (item in snapshot.children) {
//                        var obj: OrderType = item.getValue(OrderType::class.java)!!
//                        list.add(obj)
//                    }
//                }
//
//            })
//        }
    }

}