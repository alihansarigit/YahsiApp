package com.yuhdeveloper.yahsi.ViewHolders

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mangaship.Ids
import com.yuhdeveloper.yahsi.Fragments.OrderPropertiesFragment
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.Pojos.OrderType
import com.yuhdeveloper.yahsi.R

class VH_OrderType(view: View): RecyclerView.ViewHolder(view) {


    var txt_orderType:TextView
    var img_orderTypePic:ImageView
    init {
        txt_orderType = view.findViewById(R.id.customrec_ordertypes_txt_orderName)
        img_orderTypePic = view.findViewById(R.id.customrec_ordertypes_img_order)
    }

    fun setData(item:OrderType){

        txt_orderType.text = item.modelName
        Glide.with(itemView.context).load(item.modelPicture).into(img_orderTypePic)

        itemView.setOnClickListener {
            var listener:IReplaceFragment = itemView.context as IReplaceFragment
            var bundle:Bundle = Bundle()
            bundle.putString(Ids.orderType.toString(),item.modelName)
            bundle.putString(Ids.orderPicture.toString(),item.modelPicture)
            var fragment:OrderPropertiesFragment = OrderPropertiesFragment()
            fragment.bundle = bundle
            listener.replaceFragment(fragment)
        }
    }
}