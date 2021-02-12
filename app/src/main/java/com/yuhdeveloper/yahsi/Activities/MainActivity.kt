package com.yuhdeveloper.yahsi.Activities

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.yuhdeveloper.yahsi.AlertDialogPrepare
import com.yuhdeveloper.yahsi.Fragments.ProfileFragment
import com.yuhdeveloper.yahsi.Interfaces.IDeleteOrder
import com.yuhdeveloper.yahsi.Orders
import com.yuhdeveloper.yahsi.Pojos.Users
import com.yuhdeveloper.yahsi.SharedPreferencesManager
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig
import java.lang.Exception


class MainActivity : AppCompatActivity(),IDeleteOrder{


    lateinit var frm_layout: FrameLayout

    fun setID(){
        frm_layout = findViewById(R.id.ActMain_frmlayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setID()
        supportFragmentManager.beginTransaction().replace(frm_layout.id,ProfileFragment()).commit()


//        val database = FirebaseDatabase.getInstance()
//
//        var user: Users = SharedPreferencesManager().getModel(this)
//        var order: Orders =
//            Orders()
//        order.customerID = 4
//        order.orderName = "Gömlek"
//        order.orderQuantity = 43
//        order.orderDate = ServerValue.TIMESTAMP
        /*
        val myRef = database.getReference("Orders/"+order.customerID).push().setValue(order)
        var list = database.getReference("Orders/"+order.customerID)

        list.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                        var obj: Orders = postSnapshot.getValue(Orders::class.java)!!
                        Log.i("aga->", getTime(obj.orderDate as Long))

//                    if (obj.customerID == 1) {
//                        val dateFormat: DateFormat = getDateTimeInstance()
//                        val netDate = Date(obj.orderDate as Long)
//                        Log.i("aga->", dateFormat.format(netDate))
//                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        */
    }

    override fun deleteOrder(orderID: String) {

        lateinit var dialog:AlertDialog

        var yesClickListener:View.OnClickListener = View.OnClickListener {
            var ref = FirebaseConfig.getRef("Orders/"+orderID)

            try{
                ref.removeValue()
                dialog.dismiss()
            }
            catch (ex: Exception){
                Toast.makeText(this,ex.localizedMessage, Toast.LENGTH_LONG)
            }
        }
        var noClickListener:View.OnClickListener = View.OnClickListener {
          dialog.dismiss()
        }
        dialog = AlertDialogPrepare.AreYouSureAlert(this,yesClickListener,noClickListener)

    }
}