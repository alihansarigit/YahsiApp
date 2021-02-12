package com.yuhdeveloper.yahsi.Activities

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.yuhdeveloper.yahsi.Fragments.OrderTypeFragment
import com.yuhdeveloper.yahsi.Interfaces.IDeleteOrder
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig
import java.lang.Exception


class OrderActivity : AppCompatActivity(), IReplaceFragment {

    lateinit var frm_layout: FrameLayout
    lateinit var fragment: Fragment

    fun setID() {
        frm_layout = findViewById(R.id.ActOrder_frm_layout)
    }

    fun setListeners() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setID()
        setListeners()
        replaceFragment(OrderTypeFragment())


    }

    override fun replaceFragment(_fragment: Fragment) {
        fragment = _fragment
        supportFragmentManager.beginTransaction().replace(frm_layout.id, _fragment).commit()
    }

    override fun onBackPressed() {
        if (fragment is OrderTypeFragment) {
            finish()
        } else {
            replaceFragment(OrderTypeFragment())
        }
    }


}