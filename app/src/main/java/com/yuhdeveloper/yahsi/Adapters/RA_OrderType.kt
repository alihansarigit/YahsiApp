package com.yuhdeveloper.yahsi.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuhdeveloper.yahsi.Pojos.OrderType
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.ViewHolders.VH_OrderType
import kotlinx.android.synthetic.main.fragment_profile.*

class RA_OrderType(_context:Context,_items:ArrayList<OrderType>):RecyclerView.Adapter<VH_OrderType>() {

    var context:Context = _context
    var items:ArrayList<OrderType> = _items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH_OrderType {
        var view: View = LayoutInflater.from(context).inflate(R.layout.custom_rec_ordertypes,parent,false)
        return VH_OrderType(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH_OrderType, position: Int) {

        holder.setData(items.get(position))
    }


}