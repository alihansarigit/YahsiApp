package com.yuhdeveloper.yahsi.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuhdeveloper.yahsi.Pojos.OrderProperties
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.ViewHolders.VH_Orders

class RA_Orders(_context:Context, _items:ArrayList<OrderProperties>,_role:String):RecyclerView.Adapter<VH_Orders>() {

    var context:Context = _context
    var items:ArrayList<OrderProperties> = _items
    var role=_role

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH_Orders {
        var view: View = LayoutInflater.from(context).inflate(R.layout.custom_rec_orders,parent,false)
        return VH_Orders(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH_Orders, position: Int) {
        holder.setData(items.get(items.size-1-position),role )
    }


}