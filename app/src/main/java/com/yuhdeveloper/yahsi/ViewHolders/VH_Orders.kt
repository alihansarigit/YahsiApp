package com.yuhdeveloper.yahsi.ViewHolders

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mangaship.Ids
import com.yuhdeveloper.yahsi.Activities.OrderDetailActivity
import com.yuhdeveloper.yahsi.Fragments.OrderPropertiesFragment
import com.yuhdeveloper.yahsi.Interfaces.IDeleteOrder
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.Pojos.OrderProperties
import com.yuhdeveloper.yahsi.Pojos.OrderType
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig
import com.yuhdeveloper.yahsi.Usefull.JsonPackage.JsonConfig

class VH_Orders(view: View) : RecyclerView.ViewHolder(view) {


    var txt_productName: TextView
    var txt_customerName: TextView
    var txt_orderDate: TextView
    var txt_orderDetails: TextView
    var img_productimg: ImageView
    var txt_orderQuantity: TextView

    init {
        txt_productName = view.findViewById(R.id.customrec_orders_txt_productName)
        txt_customerName = view.findViewById(R.id.customrec_orders_txt_customerName)
        txt_orderDate = view.findViewById(R.id.customrec_orders_txt_orderDate)
        txt_orderDetails = view.findViewById(R.id.customrec_orders_txt_orderDetails)
        img_productimg = view.findViewById(R.id.customrec_orders_img_productimg)
        txt_orderQuantity = view.findViewById(R.id.customrec_orders_txt_productQuantity)
    }

    fun setData(item: OrderProperties, _role: String) {

        txt_productName.text = item.orderName

        txt_customerName.text = item.customerName + " -> " + item.companyName
        txt_orderDate.text = FirebaseConfig.getTime(item.createdDate as Long)
        txt_orderQuantity.text = item.quantity.toString() + " Adet"
        txt_orderDetails.setOnClickListener {

            item.createdDate = item.createdDate.toString()

            var intent:Intent = Intent(itemView.context,OrderDetailActivity::class.java)
            intent.putExtra(Ids.orderDetail.toString(),JsonConfig.convertStringFromModel(item))
            itemView.context.startActivity(intent)

        }
        Glide.with(itemView.context).load(item.orderPicture).into(img_productimg)
//
        if (_role.toLowerCase().equals("admin")) {
            itemView.setOnLongClickListener {
                var listener:IDeleteOrder = itemView.context as IDeleteOrder
                listener.deleteOrder(item.orderID!!)

                true
            }
        }
    }
}