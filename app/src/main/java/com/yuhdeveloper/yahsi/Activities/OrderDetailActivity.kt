package com.yuhdeveloper.yahsi.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputLayout
import com.mangaship.Ids
import com.yuhdeveloper.yahsi.Pojos.OrderProperties
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig
import com.yuhdeveloper.yahsi.Usefull.JsonPackage.JsonConfig

class OrderDetailActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var order: OrderProperties


    lateinit var lnr: LinearLayout
    lateinit var img_back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)


        var orderDetails = intent.getStringExtra(Ids.orderDetail.toString())
        order = JsonConfig.convertModelFromString(orderDetails)

// TODO çıkış yap ekle
// TODO kullanıcıya göre siparişler bitti
// TODO  admine tüm siparişler gözükecek bitti
        setID()
        setListener()

    }

    fun setID() {
        lnr = findViewById(R.id.ActOrderDetail_lnr)
        img_back = findViewById(R.id.ActOrderDetail_img_back)

        control(order.orderName, "Ürün Adı")
        control(FirebaseConfig.getTime(order.createdDate.toString().toLong()), "Sipariş Tarihi")
        control(order.customerName, "Sipariş Veren")
        control(order.companyName, "Firma Adı")
        control(order.baski, "Baskı")
        control(order.cepModel, "Cep Model")
        control(order.cinsiyet, "Cinsiyet")
        control(order.dugmeRengi, "Düğme Rengi")
        control(order.etekModeli, "Etek Modeli")
        control(order.fermuarRengi, "Fermuar Rengi")
        control(order.kolBantRengi, "Kol Bant Rengi")
        control(order.kolBoyu, "Kol Boyu")
        control(order.kolModeli, "Kol Modeli")
        control(order.kumasCinsi, "Kumaş Cinsi")
        control(order.kumasRengi, "Kumaş Rengi")
        control(order.nakis, "Nakış")
        control(order.yakaCinsi, "Yaka Cinsi")
        control(order.yakaModel, "Yaka Model")
        control(order.yakaRengi, "Yaka Rengi")
//        var v: View? = null
//        var inflater: LayoutInflater = LayoutInflater.from(context)
//
//        if (order.orderName.equals("Basic")) {
//            v = inflater.inflate(R.layout.partialview_basic, null)
//        } else if (order.orderName.equals("Polo")) {
//            v = inflater.inflate(R.layout.partialview_polo, null)
//        } else if (order.orderName.equals("Polar")) {
//            v = inflater.inflate(R.layout.partialview_polar, null)
//        } else if (order.orderName.equals("Gömlek")) {
//            v = inflater.inflate(R.layout.partialview_gomlek, null)
//        }
//        lnr.addView(v)
//
//
    }

    fun control(text: String?, title: String) {
        if (text != null) {
            createEditText(text!!, title)
        }
    }

    fun createEditText(text: String, title: String) {

        var params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        params.setMargins(10, 10, 10, 5)


        var textInputLayout: TextInputLayout = TextInputLayout(this)
        textInputLayout.layoutParams = params
        textInputLayout.hint = title
        textInputLayout.layoutParams = params


        var editText: EditText = EditText(this)
        editText.setText(text)
        editText.isEnabled = false
        editText.layoutParams = params

        textInputLayout.addView(editText)

        lnr.addView(textInputLayout)
    }

    fun setListener() {

        img_back.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        finish()
    }
}