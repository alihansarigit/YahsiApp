package com.yuhdeveloper.yahsi.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.yuhdeveloper.yahsi.Adapters.RA_OrderType
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.Pojos.OrderType
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig
import com.yuhdeveloper.yahsi.Usefull.RecyclerPack.LayoutManagerConfig
import java.util.ArrayList


class OrderTypeFragment : Fragment(), View.OnClickListener {


    lateinit var rec_orderTypes: RecyclerView
    lateinit var img_back: ImageView

    lateinit var _context: Context

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_type, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setID(view)
        setListeners()
        getOrderTypes()

//        var orderType:OrderType = OrderType()
//        orderType.modelID = 1
//        orderType.modelName = "Polo"
//        orderType.modelPicture = "asd"
//
//        FirebaseConfig.addOrderType(orderType)
//
//        orderType.modelID = 2
//        orderType.modelName = "Basic"
//        orderType.modelPicture = "asd"
//
//        FirebaseConfig.addOrderType(orderType)
//        orderType.modelID = 3
//        orderType.modelName = "Polar"
//        orderType.modelPicture = "asd"
//
//        FirebaseConfig.addOrderType(orderType)
//
//        orderType.modelID = 4
//        orderType.modelName = "Gömlek"
//        orderType.modelPicture = "asd"

//        FirebaseConfig.addOrderType(orderType)
    }

    fun setID(view: View) {
        rec_orderTypes = view.findViewById(R.id.FtOrderType_rec_orderTypes)
        img_back = view.findViewById(R.id.FtOrderType_img_back)
    }

    fun setListeners() {
        img_back.setOnClickListener(this)

    }

    fun getOrderTypes() {

        var types = FirebaseConfig.getRef("Models")

        var list: ArrayList<OrderType> = ArrayList()
        types.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(_context,error.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (item in snapshot.children) {
                    var obj: OrderType = item.getValue(OrderType::class.java)!!
                    list.add(obj)
                }

                var adapter:RA_OrderType = RA_OrderType(_context,list)
                rec_orderTypes.adapter = adapter
                rec_orderTypes.layoutManager = LayoutManagerConfig.getGrid(_context,2)
            }

        })
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            img_back.id->{
                (context as Activity).finish()
            }
        }
    }


}