package com.yuhdeveloper.yahsi.Usefull.RecyclerPack

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LayoutManagerConfig {

    companion object
    {

        fun getGrid(_context:Context,span:Int):GridLayoutManager{
        var layoutManager:GridLayoutManager = GridLayoutManager(_context,span)
        return layoutManager
    }

        fun getLinearVertical(_context:Context):LinearLayoutManager{
            var layoutManager:LinearLayoutManager = LinearLayoutManager(_context)
            layoutManager.orientation= RecyclerView.VERTICAL
            return layoutManager
        }
    }
}