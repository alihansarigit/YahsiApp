package com.yuhdeveloper.yahsi.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.yuhdeveloper.yahsi.Pojos.OrderProperties
import com.yuhdeveloper.yahsi.R

class SA_OrderProperties(context: Context, items: ArrayList<String>) : BaseAdapter() {

    var context: Context = context
    var items: ArrayList<String> = items
    var _parent: ViewGroup? = null

    lateinit var firstView: View

    init {
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (position != 0) {

            var view: View? = null
            view = LayoutInflater.from(context)
                .inflate(R.layout.custom_spn_items, parent, false)
            var txtView:TextView = view!!.findViewById(R.id.spn_text)
            txtView.text = items.get(position)

            return view
        } else {
            return getFirstView(parent,position)
        }

    }


    fun getFirstView(parent: ViewGroup?,position:Int): View {
        firstView = LayoutInflater.from(context)
            .inflate(R.layout.custom_spn_header, parent, false)

        var txt_header:TextView = firstView.findViewById(R.id.spn_text)
        txt_header.text = items.get(position)
        return firstView
    }


    override fun getItem(position: Int): String {
        return items.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    override fun getCount(): Int {
        return items.size
    }



    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getDropDownView(position, convertView, parent)
    }
}