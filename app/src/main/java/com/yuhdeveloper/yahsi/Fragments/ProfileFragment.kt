package com.yuhdeveloper.yahsi.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.yuhdeveloper.yahsi.Activities.OrderActivity
import com.yuhdeveloper.yahsi.Activities.SignActivity
import com.yuhdeveloper.yahsi.Adapters.RA_Orders
import com.yuhdeveloper.yahsi.Orders
import com.yuhdeveloper.yahsi.Pojos.OrderProperties
import com.yuhdeveloper.yahsi.Pojos.Users
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.SharedPreferencesManager
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig
import com.yuhdeveloper.yahsi.Usefull.RecyclerPack.LayoutManagerConfig


class ProfileFragment : Fragment(), View.OnClickListener {

    lateinit var txt_orders: TextView
    lateinit var rec_orders: RecyclerView
    lateinit var btn_order: Button

    lateinit var txt_customerName: TextView
    lateinit var txt_email: TextView
    lateinit var txt_phone: TextView

    lateinit var txt_exit:TextView
    lateinit var _context:Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setID(view)
        setListener()

        var user: Users = SharedPreferencesManager().getUserInformation(_context)

        txt_customerName.text = user.name+" "+user.surname
        txt_email.text =user.email
        txt_phone.text =user.phone

        var orders = FirebaseConfig.getRef("Orders")

        orders.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(_context,error.message,Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var items: ArrayList<OrderProperties> = ArrayList()


                for (item in snapshot.children) {
                    if(user.role!!.toLowerCase().equals("admin")){
                        var obj: OrderProperties = item.getValue(OrderProperties::class.java)!!
                        obj.orderID = item.key
                        items.add(obj)
                    }else{
                        var obj: OrderProperties = item.getValue(OrderProperties::class.java)!!
                        obj.orderID = item.key

                        if(obj.customerID.equals(user.uid)){
                            items.add(obj)
                        }
                    }

                }
                var adapter: RA_Orders = RA_Orders(_context,items,SharedPreferencesManager().getUserInformation(_context).role!!)
                rec_orders.adapter = adapter
                rec_orders.layoutManager = LayoutManagerConfig.getLinearVertical(_context)

            }
        })
    }

    fun setListener() {
        btn_order.setOnClickListener(this)
        txt_exit.setOnClickListener(this)
    }

    fun setID(view: View) {
        txt_orders = view.findViewById(R.id.FtProfile_txt_orders)
        rec_orders = view.findViewById(R.id.FtProfile_rec_orders)
        btn_order = view.findViewById(R.id.FtProfile_btn_getOrder)

        txt_exit = view.findViewById(R.id.FtProfile_txt_exit)
        txt_phone = view.findViewById(R.id.ftUserProfile_txt_phone)
        txt_email = view.findViewById(R.id.ftUserProfile_txt_email)
        txt_customerName = view.findViewById(R.id.ftUserProfile_txt_namesurname)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            btn_order.id -> {
                var intent: Intent = Intent(context, OrderActivity::class.java)
                startActivity(intent)
            }

            txt_exit.id->{
                SharedPreferencesManager().logout(_context)
                var intent:Intent = Intent(_context,SignActivity::class.java)
                startActivity(intent)

                (context as Activity).finish()
            }
        }
    }
}