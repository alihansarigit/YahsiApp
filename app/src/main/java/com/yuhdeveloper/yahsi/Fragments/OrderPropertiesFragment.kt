package com.yuhdeveloper.yahsi.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ServerValue.TIMESTAMP
import com.mangaship.Ids
import com.yuhdeveloper.yahsi.Adapters.SA_OrderProperties
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.Pojos.OrderProperties
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.SharedPreferencesManager
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig
import io.grpc.Server
import java.lang.Exception

class OrderPropertiesFragment : Fragment(), View.OnClickListener {


    lateinit var bundle: Bundle
    lateinit var frm: LinearLayout
    lateinit var btn_order: Button
    lateinit var img_back: ImageView

    lateinit var spn_kumasRengi: Spinner
    lateinit var spn_kumasCinsi: Spinner
    lateinit var spn_yakaModel: Spinner
    lateinit var spn_kolBoyu: Spinner
    lateinit var spn_baski: Spinner
    lateinit var spn_cinsiyet: Spinner
    lateinit var spn_kolBantRengi: Spinner
    lateinit var spn_yakaRengi: Spinner
    lateinit var spn_dugmeRengi: Spinner
    lateinit var spn_nakis: Spinner
    lateinit var spn_cepModel: Spinner
    lateinit var spn_fermuarRengi: Spinner
    lateinit var spn_kolModeli: Spinner
    lateinit var spn_etekModeli: Spinner
    lateinit var spn_yakaCinsi: Spinner
    lateinit var edt_quantity: EditText
    lateinit var edt_companyName: EditText
    var orderType: String = ""
    var orderPicture: String = ""

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
        return inflater.inflate(R.layout.fragment_order_properties, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frm = view.findViewById(R.id.FtOrderProperties_lnr)
        orderType = bundle.getString(Ids.orderType.toString(), "")
        orderPicture = bundle.getString(Ids.orderPicture.toString(), "")
        var v: View? = null
        var inflater: LayoutInflater = LayoutInflater.from(context)

        if (orderType.equals("Basic")) {
            v = inflater.inflate(R.layout.partialview_basic, null)
        } else if (orderType.equals("Polo")) {
            v = inflater.inflate(R.layout.partialview_polo, null)
        } else if (orderType.equals("Polar")) {
            v = inflater.inflate(R.layout.partialview_polar, null)
        } else if (orderType.equals("Gömlek")) {
            v = inflater.inflate(R.layout.partialview_gomlek, null)
        }
        frm.addView(v)

//        var id:Int =0
//        var params:LinearLayout.LayoutParams = LinearLayout.LayoutParams(250, 250)
//
//        var btn_add:Button = Button(context)
//        btn_add.id = id
//        btn_add.text = "Sipariş Ver"
//        btn_add.layoutParams = params
//        btn_add.background = ContextCompat.getDrawable(_context,R.drawable.rectangle_green)
//        frm.addView(btn_add)
//        btn_add.setOnClickListener {
//
//        }

        setID(view)
        setListeners()


    }

    fun setID(view: View) {

        img_back = view.findViewById(R.id.FtOrderProperties_img_back)
        btn_order = view.findViewById(R.id.FtOrderProperties_btn_addOrder)

        if (orderType.equals("Basic")) {
            edt_companyName = view.findViewById(R.id.partial_basic_edt_CompanyName)
            spn_kumasCinsi = view.findViewById(R.id.partial_basic_spn_kumasCinsi)
            spn_kumasRengi = view.findViewById(R.id.partial_basic_spn_kumasRengi)
            spn_baski = view.findViewById(R.id.partial_basic_spn_baski)
            spn_cinsiyet = view.findViewById(R.id.partial_basic_spn_cinsiyet)
            spn_kolBoyu = view.findViewById(R.id.partial_basic_spn_kolBoyu)
            spn_yakaModel = view.findViewById(R.id.partial_basic_spn_yakaModel)
            edt_quantity = view.findViewById(R.id.partial_basic_edt_quantity)
        } else if (orderType.equals("Polo")) {
            edt_companyName = view.findViewById(R.id.partial_polo_edt_CompanyName)

            spn_kumasCinsi = view.findViewById(R.id.partial_polo_spn_kumasCinsi)
            spn_kumasRengi = view.findViewById(R.id.partial_polo_spn_kumasRengi)
            spn_nakis = view.findViewById(R.id.partial_polo_spn_nakis)
            spn_cinsiyet = view.findViewById(R.id.partial_polo_spn_cinsiyet)
            spn_kolBoyu = view.findViewById(R.id.partial_polo_spn_kolBoyu)
            spn_kolBantRengi = view.findViewById(R.id.partial_polo_spn_kolBantRengi)
            spn_yakaRengi = view.findViewById(R.id.partial_polo_spn_yakaRengi)
            spn_dugmeRengi = view.findViewById(R.id.partial_polo_spn_dugmeRengi)
            edt_quantity = view.findViewById(R.id.partial_polo_edt_quantity)

        } else if (orderType.equals("Polar")) {
            edt_companyName = view.findViewById(R.id.partial_polar_edt_CompanyName)
            spn_cepModel = view.findViewById(R.id.partial_polar_spn_cepModel)
            spn_kumasCinsi = view.findViewById(R.id.partial_polar_spn_kumasCinsi)
            spn_kumasRengi = view.findViewById(R.id.partial_polar_spn_kumasRengi)
            spn_etekModeli = view.findViewById(R.id.partial_polar_spn_etekModeli)
            spn_fermuarRengi = view.findViewById(R.id.partial_polar_spn_fermuarRengi)
            spn_nakis = view.findViewById(R.id.partial_polar_spn_nakis)
            spn_kolModeli = view.findViewById(R.id.partial_polar_spn_kolModeli)
            edt_quantity = view.findViewById(R.id.partial_polar_edt_quantity)

        } else if (orderType.equals("Gömlek")) {
            edt_companyName = view.findViewById(R.id.partial_gomlek_edt_CompanyName)
            spn_kumasCinsi = view.findViewById(R.id.partial_gomlek_spn_kumasCinsi)
            spn_kumasRengi = view.findViewById(R.id.partial_gomlek_spn_kumasRengi)
            spn_nakis = view.findViewById(R.id.partial_gomlek_spn_nakis)
            spn_cinsiyet = view.findViewById(R.id.partial_gomlek_spn_cinsiyet)
            spn_kolBoyu = view.findViewById(R.id.partial_gomlek_spn_kolBoyu)
            spn_kolBantRengi = view.findViewById(R.id.partial_gomlek_spn_kolBantRengi)
            spn_yakaRengi = view.findViewById(R.id.partial_gomlek_spn_yakaRengi)
            spn_dugmeRengi = view.findViewById(R.id.partial_gomlek_spn_dugmeRengi)
            spn_yakaCinsi = view.findViewById(R.id.partial_gomlek_spn_yakaCinsi)
            edt_quantity = view.findViewById(R.id.partial_gomlek_edt_quantity)
        }

        setData()

    }

    fun setData() {
        var lst_kumasCinsi: ArrayList<String> = ArrayList<String>()
        var lst_kumasRengi: ArrayList<String> = ArrayList<String>()
        var lst_nakis: ArrayList<String> = ArrayList<String>()
        var lst_cinsiyet: ArrayList<String> = ArrayList<String>()
        var lst_kolBoyu: ArrayList<String> = ArrayList<String>()
        var lst_kolBantRengi: ArrayList<String> = ArrayList<String>()
        var lst_yakaRengi: ArrayList<String> = ArrayList<String>()
        var lst_dugmeRengi: ArrayList<String> = ArrayList<String>()
        var lst_yakaCinsi: ArrayList<String> = ArrayList<String>()
        var lst_yakaModel: ArrayList<String> = ArrayList()
        var lst_baski: ArrayList<String> = ArrayList()
        var lst_cepModel: ArrayList<String> = ArrayList()
        var lst_fermuarRengi: ArrayList<String> = ArrayList()
        var lst_kolModeli: ArrayList<String> = ArrayList()
        var lst_etekModeli: ArrayList<String> = ArrayList()

        if (orderType.equals("Basic")) {
            lst_kumasRengi = getColorName("Kumaş Rengini Seçiniz...")

            lst_kumasCinsi.add("Kumaş Cinsini Seçiniz...")
            lst_kumasCinsi.add("Süprem")
            lst_kumasCinsi.add("Petek")
            lst_kumasCinsi.add("Elena")
            lst_kumasCinsi.add("İki İplik")
            lst_kumasCinsi.add("Üç İplik")


            lst_cinsiyet.add("Cinsiyet Seçiniz...")
            lst_cinsiyet.add("Erkek")
            lst_cinsiyet.add("Kadın")

            lst_kolBoyu.add("Kol Boyu Seçiniz...")
            lst_kolBoyu.add("Kısa Kol")
            lst_kolBoyu.add("Uzun Kol")

            lst_baski.add("Baskı Seçiniz...")
            lst_baski.add("Sağ Kol")
            lst_baski.add("Sol Kol")
            lst_baski.add("Ön")
            lst_baski.add("Arka")

            lst_yakaModel.add("Yaka Modeli Seçiniz...")
            lst_yakaModel.add("Bisiklet Yaka")
            lst_yakaModel.add("V Yaka")

        } else if (orderType.equals("Polo")) {
            lst_kumasRengi = getColorName("Kumaş Rengini Seçiniz...")
            lst_kolBantRengi = getColorName("Kol Bant Rengini Seçiniz...")
            lst_yakaRengi = getColorName("Yaka Rengini Seçiniz...")
            lst_dugmeRengi = getColorName("Düğme Rengini Seçiniz...")


            lst_kumasCinsi.add("Kumaş Cinsini Seçiniz...")
            lst_kumasCinsi.add("Süprem")
            lst_kumasCinsi.add("Petek")
            lst_kumasCinsi.add("Elena")
            lst_kumasCinsi.add("İki İplik")
            lst_kumasCinsi.add("Üç İplik")

            lst_kolBoyu.add("Kol Boyu Seçiniz...")
            lst_kolBoyu.add("Kısa Kol")
            lst_kolBoyu.add("Uzun Kol")

            lst_cinsiyet.add("Cinsiyet Seçiniz...")
            lst_cinsiyet.add("Erkek")
            lst_cinsiyet.add("Kadın")


            lst_nakis.add("Nakış Seçiniz...")
            lst_nakis.add("Ön")
            lst_nakis.add("Arka")
            lst_nakis.add("Sağ")
            lst_nakis.add("Sol")

        } else if (orderType.equals("Polar")) {
            lst_kumasRengi = getColorName("Kumaş Rengini Seçiniz...")
            lst_fermuarRengi = getColorName("Fermuar Rengini Seçiniz...")


            lst_cepModel.add("Cep Modeli Seçiniz...")
            lst_cepModel.add("Sağ-Sol Fermuar")
            lst_cepModel.add("Sağ-Sol Göğüs Fermuar")
            lst_cepModel.add("Kangru Cep")
            lst_cepModel.add("Flota Cep")


            lst_kumasCinsi.add("Kumaş Cinsini Seçiniz...")
            lst_kumasCinsi.add("Polar")
            lst_kumasCinsi.add("İki İplik")
            lst_kumasCinsi.add("Üç İplik")

            lst_kolModeli.add("Kol Modeli Seçiniz...")
            lst_kolModeli.add("Reçme")
            lst_kolModeli.add("Kaşkorse")

            lst_etekModeli.add("Etek Modeli Seçiniz...")
            lst_etekModeli.add("Reçme")
            lst_etekModeli.add("Kaşkorse")


            lst_nakis.add("Nakış Seçiniz...")
            lst_nakis.add("Ön")
            lst_nakis.add("Arka")
            lst_nakis.add("Sağ")
            lst_nakis.add("Sol")

        } else if (orderType.equals("Gömlek")) {
            lst_kumasRengi = getColorName("Kumaş Rengini Seçiniz...")
            lst_kolBantRengi = getColorName("Kol Bant Rengini Seçiniz...")
            lst_yakaRengi = getColorName("Yaka Rengini Seçiniz...")
            lst_dugmeRengi = getColorName("Düğme Rengini Seçiniz...")

            lst_kolBoyu.add("Kol Boyu Seçiniz...")
            lst_kolBoyu.add("Kısa Kol")
            lst_kolBoyu.add("Uzun Kol")

            lst_kumasCinsi.add("Kumaş Cinsini Seçiniz...")
            lst_kumasCinsi.add("Süprem")
            lst_kumasCinsi.add("Petek")
            lst_kumasCinsi.add("Elena")
            lst_kumasCinsi.add("İki İplik")
            lst_kumasCinsi.add("Üç İplik")


            lst_cinsiyet.add("Cinsiyet Seçiniz...")
            lst_cinsiyet.add("Erkek")
            lst_cinsiyet.add("Kadın")


            lst_nakis.add("Nakış Seçiniz...")
            lst_nakis.add("Ön")
            lst_nakis.add("Arka")
            lst_nakis.add("Sağ")
            lst_nakis.add("Sol")


            lst_yakaCinsi.add("Yaka Cinsi Seçiniz...")
            lst_yakaCinsi.add("Gömlek Yaka")
            lst_yakaCinsi.add("Hazırlamalı Yaka")
            lst_yakaCinsi.add("Hakim Yaka")
        }

        var adapter: SA_OrderProperties
        if (lst_baski.size > 0) {
            adapter = SA_OrderProperties(_context, lst_baski)
            spn_baski.adapter = adapter
        }
        if (lst_cepModel.size > 0) {
            adapter = SA_OrderProperties(_context, lst_cepModel)
            spn_cepModel.adapter = adapter
        }
        if (lst_cinsiyet.size > 0) {
            adapter = SA_OrderProperties(_context, lst_cinsiyet)
            spn_cinsiyet.adapter = adapter
        }
        if (lst_dugmeRengi.size > 0) {
            adapter = SA_OrderProperties(_context, lst_dugmeRengi)
            spn_dugmeRengi.adapter = adapter
        }
        if (lst_etekModeli.size > 0) {
            adapter = SA_OrderProperties(_context, lst_etekModeli)
            spn_etekModeli.adapter = adapter
        }
        if (lst_fermuarRengi.size > 0) {
            adapter = SA_OrderProperties(_context, lst_fermuarRengi)
            spn_fermuarRengi.adapter = adapter
        }
        if (lst_kolBantRengi.size > 0) {
            adapter = SA_OrderProperties(_context, lst_kolBantRengi)
            spn_kolBantRengi.adapter = adapter
        }
        if (lst_kolBoyu.size > 0) {
            adapter = SA_OrderProperties(_context, lst_kolBoyu)
            spn_kolBoyu.adapter = adapter
        }
        if (lst_kolModeli.size > 0) {
            adapter = SA_OrderProperties(_context, lst_kolModeli)
            spn_kolModeli.adapter = adapter
        }
        if (lst_kumasCinsi.size > 0) {
            adapter = SA_OrderProperties(_context, lst_kumasCinsi)
            spn_kumasCinsi.adapter = adapter
        }
        if (lst_kumasRengi.size > 0) {
            adapter = SA_OrderProperties(_context, lst_kumasRengi)
            spn_kumasRengi.adapter = adapter
        }
        if (lst_nakis.size > 0) {
            adapter = SA_OrderProperties(_context, lst_nakis)
            spn_nakis.adapter = adapter
        }
        if (lst_yakaCinsi.size > 0) {
            adapter = SA_OrderProperties(_context, lst_yakaCinsi)
            spn_yakaCinsi.adapter = adapter
        }
        if (lst_yakaModel.size > 0) {
            adapter = SA_OrderProperties(_context, lst_yakaModel)
            spn_yakaModel.adapter = adapter
        }
        if (lst_yakaRengi.size > 0) {
            adapter = SA_OrderProperties(_context, lst_yakaRengi)
            spn_yakaRengi.adapter = adapter
        }


    }

    fun setListeners() {
        img_back.setOnClickListener(this)
        btn_order.setOnClickListener(this)
    }


    fun getColorName(header: String): ArrayList<String> {
        var lst_kolBantRengi: ArrayList<String> = ArrayList()
        lst_kolBantRengi.add(header)
        lst_kolBantRengi.add("M.T.01")
        lst_kolBantRengi.add("M.T.02")
        lst_kolBantRengi.add("M.T.03")
        lst_kolBantRengi.add("M.T.04")
        lst_kolBantRengi.add("M.T.05")
        lst_kolBantRengi.add("M.T.06")
        lst_kolBantRengi.add("M.T.07")
        lst_kolBantRengi.add("M.T.08")
        lst_kolBantRengi.add("M.T.09")
        lst_kolBantRengi.add("M.T.10")
        lst_kolBantRengi.add("M.T.11")
        lst_kolBantRengi.add("M.T.12")
        lst_kolBantRengi.add("M.T.13")
        lst_kolBantRengi.add("M.T.14")
        lst_kolBantRengi.add("M.T.15")
        lst_kolBantRengi.add("M.T.16")
        lst_kolBantRengi.add("M.T.17")
        lst_kolBantRengi.add("M.T.18")
        lst_kolBantRengi.add("M.T.19")
        lst_kolBantRengi.add("M.T.20")
        lst_kolBantRengi.add("M.T.21")
        lst_kolBantRengi.add("M.T.22")
        lst_kolBantRengi.add("M.T.23")
        lst_kolBantRengi.add("M.T.24")
        lst_kolBantRengi.add("M.T.25")
        lst_kolBantRengi.add("M.T.26")
        lst_kolBantRengi.add("M.T.27")
        lst_kolBantRengi.add("M.T.28")
        lst_kolBantRengi.add("M.T.29")
        lst_kolBantRengi.add("M.T.30")
        lst_kolBantRengi.add("M.T.31")
        lst_kolBantRengi.add("M.T.32")
        lst_kolBantRengi.add("M.T.33")
        lst_kolBantRengi.add("M.T.34")
        lst_kolBantRengi.add("M.T.35")
        lst_kolBantRengi.add("M.T.36")
        lst_kolBantRengi.add("M.T.37")
        lst_kolBantRengi.add("M.T.38")
        lst_kolBantRengi.add("M.T.39")
        lst_kolBantRengi.add("M.T.40")
        lst_kolBantRengi.add("M.T.41")
        lst_kolBantRengi.add("M.T.42")
        lst_kolBantRengi.add("M.T.43")
        lst_kolBantRengi.add("M.T.44")
        lst_kolBantRengi.add("M.T.45")
        lst_kolBantRengi.add("M.T.46")
        lst_kolBantRengi.add("M.T.47")
        lst_kolBantRengi.add("M.T.48")
        lst_kolBantRengi.add("M.T.49")
        lst_kolBantRengi.add("M.T.50")

        return lst_kolBantRengi
    }

    override fun onClick(p0: View?) {
        var orderProperties
                : OrderProperties = OrderProperties()

        when (p0!!.id) {

            img_back.id -> {
                var listener: IReplaceFragment = context as IReplaceFragment
                listener.replaceFragment(OrderTypeFragment())
            }

            btn_order.id -> {
//                (edt_quantity.text.equals("") || edt_quantity.text.toString().equals("0"))

                btn_order.isEnabled = false
                if (edt_quantity.text.toString().equals("0") || edt_quantity.text.toString()
                        .equals("")
                ) {
                    btn_order.isEnabled = true
                    Toast.makeText(context, "Miktarı Belirtiniz!", Toast.LENGTH_LONG).show()

                    return
                }

                if (edt_companyName.text.toString().equals("0") || edt_companyName.text.toString()
                        .equals("")
                ) {
                    btn_order.isEnabled = true

                    Toast.makeText(context, "Firma Belirtiniz!", Toast.LENGTH_LONG).show()
                    return
                }


                when (orderType) {
                    "Polo" -> {
                        if (spn_kumasCinsi.selectedItemPosition == 0 || spn_dugmeRengi.selectedItemPosition == 0 ||
                            spn_kumasRengi.selectedItemPosition == 0 || spn_kolBoyu.selectedItemPosition == 0 ||
                            spn_cinsiyet.selectedItemPosition == 0 || spn_nakis.selectedItemPosition == 0 ||
                            spn_kolBantRengi.selectedItemPosition == 0 || spn_yakaRengi.selectedItemPosition == 0
                        ) {
                            btn_order.isEnabled = true

                            Toast.makeText(context, "Lütfen Seçim Yapınız!", Toast.LENGTH_LONG)
                                .show()
                            return
                        } else {
                            orderProperties.kumasCinsi =
                                (spn_kumasCinsi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasCinsi.selectedItemPosition
                                )
                            orderProperties.kumasRengi =
                                (spn_kumasRengi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasRengi.selectedItemPosition
                                )
                            orderProperties.cinsiyet =
                                (spn_cinsiyet.adapter as SA_OrderProperties).getItem(spn_cinsiyet.selectedItemPosition)
                            orderProperties.kolBantRengi =
                                (spn_kolBantRengi.adapter as SA_OrderProperties).getItem(
                                    spn_kolBantRengi.selectedItemPosition
                                )
                            orderProperties.dugmeRengi =
                                (spn_dugmeRengi.adapter as SA_OrderProperties).getItem(
                                    spn_dugmeRengi.selectedItemPosition
                                )
                            orderProperties.kolBoyu =
                                (spn_kolBoyu.adapter as SA_OrderProperties).getItem(spn_kolBoyu.selectedItemPosition)
                            orderProperties.nakis =
                                (spn_nakis.adapter as SA_OrderProperties).getItem(spn_nakis.selectedItemPosition)
                            orderProperties.yakaRengi =
                                (spn_yakaRengi.adapter as SA_OrderProperties).getItem(spn_yakaRengi.selectedItemPosition)
                            orderProperties.orderName = orderType
                            orderProperties.createdDate = ServerValue.TIMESTAMP
                            orderProperties.customerID =
                                SharedPreferencesManager().getUid(_context)
                            orderProperties.customerName =
                                SharedPreferencesManager().getUserInformation(_context).name
                            orderProperties.quantity = edt_quantity.text.toString().toInt()
                        }
                    }
                    "Gömlek" -> {
                        if (
                            spn_kumasCinsi.selectedItemPosition == 0 ||
                            spn_dugmeRengi.selectedItemPosition == 0 || spn_kumasRengi.selectedItemPosition == 0 ||
                            spn_cinsiyet.selectedItemPosition == 0 || spn_nakis.selectedItemPosition == 0 ||
                            spn_yakaCinsi.selectedItemPosition == 0 ||
                            spn_kolBantRengi.selectedItemPosition == 0 ||
                            spn_yakaRengi.selectedItemPosition == 0 ||
                            spn_kolBoyu.selectedItemPosition == 0
                        ) {
                            btn_order.isEnabled = true

                            Toast.makeText(context, "Lütfen Seçim Yapınız!", Toast.LENGTH_LONG)
                                .show()
                            return
                        } else {
                            orderProperties.kumasRengi =
                                (spn_kumasRengi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasRengi.selectedItemPosition
                                )
                            orderProperties.nakis =
                                (spn_nakis.adapter as SA_OrderProperties).getItem(spn_nakis.selectedItemPosition)
                            orderProperties.kumasCinsi =
                                (spn_kumasCinsi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasCinsi.selectedItemPosition
                                )
                            orderProperties.dugmeRengi =
                                (spn_dugmeRengi.adapter as SA_OrderProperties).getItem(
                                    spn_dugmeRengi.selectedItemPosition
                                )
                            orderProperties.cinsiyet =
                                (spn_cinsiyet.adapter as SA_OrderProperties).getItem(spn_cinsiyet.selectedItemPosition)
                            orderProperties.yakaCinsi =
                                (spn_yakaCinsi.adapter as SA_OrderProperties).getItem(spn_yakaCinsi.selectedItemPosition)
                            orderProperties.kolBantRengi =
                                (spn_kolBantRengi.adapter as SA_OrderProperties).getItem(
                                    spn_kolBantRengi.selectedItemPosition
                                )
                            orderProperties.yakaRengi =
                                (spn_yakaRengi.adapter as SA_OrderProperties).getItem(spn_yakaRengi.selectedItemPosition)
                            orderProperties.kolBoyu =
                                (spn_kolBoyu.adapter as SA_OrderProperties).getItem(spn_kolBoyu.selectedItemPosition)
                            orderProperties.orderName = orderType
                            orderProperties.createdDate = ServerValue.TIMESTAMP
                            orderProperties.customerID =
                                SharedPreferencesManager().getUid(_context)
                            orderProperties.customerName =
                                SharedPreferencesManager().getUserInformation(_context).name
                            orderProperties.quantity = edt_quantity.text.toString().toInt()
                        }
                    }
                    "Polar" -> {
                        if (spn_kumasRengi.selectedItemPosition == 0 ||
                            spn_kumasCinsi.selectedItemPosition == 0 ||
                            spn_cepModel.selectedItemPosition == 0 || spn_nakis.selectedItemPosition == 0 ||
                            spn_etekModeli.selectedItemPosition == 0 ||
                            spn_fermuarRengi.selectedItemPosition == 0 ||
                            spn_kolModeli.selectedItemPosition == 0
                        ) {
                            btn_order.isEnabled = true

                            Toast.makeText(context, "Lütfen Seçim Yapınız!", Toast.LENGTH_LONG)
                                .show()
                            return

                        } else {
                            orderProperties.kumasRengi =
                                (spn_kumasRengi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasRengi.selectedItemPosition
                                )
                            orderProperties.kumasCinsi =
                                (spn_kumasCinsi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasCinsi.selectedItemPosition
                                )
                            orderProperties.cepModel =
                                (spn_cepModel.adapter as SA_OrderProperties).getItem(spn_cepModel.selectedItemPosition)
                            orderProperties.fermuarRengi =
                                (spn_fermuarRengi.adapter as SA_OrderProperties).getItem(
                                    spn_fermuarRengi.selectedItemPosition
                                )
                            orderProperties.kolModeli =
                                (spn_kolModeli.adapter as SA_OrderProperties).getItem(spn_kolModeli.selectedItemPosition)
                            orderProperties.etekModeli =
                                (spn_etekModeli.adapter as SA_OrderProperties).getItem(
                                    spn_etekModeli.selectedItemPosition
                                )
                            orderProperties.nakis =
                                (spn_nakis.adapter as SA_OrderProperties).getItem(spn_nakis.selectedItemPosition)
                            orderProperties.orderName = orderType
                            orderProperties.createdDate = ServerValue.TIMESTAMP
                            orderProperties.customerID =
                                SharedPreferencesManager().getUid(_context)
                            orderProperties.customerName =
                                SharedPreferencesManager().getUserInformation(_context).name
                            orderProperties.quantity = edt_quantity.text.toString().toInt()
                        }
                    }
                    "Basic" -> {
                        if (spn_baski.selectedItemPosition == 0 ||
                            spn_kolBoyu.selectedItemPosition == 0 || spn_kumasCinsi.selectedItemPosition == 0 ||
                            spn_kumasRengi.selectedItemPosition == 0 ||
                            spn_cinsiyet.selectedItemPosition == 0 || spn_yakaModel.selectedItemPosition == 0
                        ) {
                            btn_order.isEnabled = true

                            Toast.makeText(context, "Lütfen Seçim Yapınız!", Toast.LENGTH_LONG)
                                .show()
                            return
                        } else {
                            orderProperties.baski =
                                (spn_baski.adapter as SA_OrderProperties).getItem(spn_baski.selectedItemPosition)
                            orderProperties.cinsiyet =
                                (spn_cinsiyet.adapter as SA_OrderProperties).getItem(spn_cinsiyet.selectedItemPosition)
                            orderProperties.kolBoyu =
                                (spn_kolBoyu.adapter as SA_OrderProperties).getItem(spn_kolBoyu.selectedItemPosition)
                            orderProperties.kumasCinsi =
                                (spn_kumasCinsi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasCinsi.selectedItemPosition
                                )
                            orderProperties.kumasRengi =
                                (spn_kumasRengi.adapter as SA_OrderProperties).getItem(
                                    spn_kumasRengi.selectedItemPosition
                                )
                            orderProperties.orderName = orderType
                            orderProperties.yakaModel =
                                (spn_yakaModel.adapter as SA_OrderProperties).getItem(spn_yakaModel.selectedItemPosition)
                            orderProperties.createdDate = ServerValue.TIMESTAMP
                            orderProperties.customerID =
                                SharedPreferencesManager().getUid(_context)
                            orderProperties.customerName =
                                SharedPreferencesManager().getUserInformation(_context).name
                            orderProperties.quantity = edt_quantity.text.toString().toInt()
                        }
                    }
                }


                orderProperties.orderPicture = orderPicture
                orderProperties.companyName = edt_companyName.text.toString()

                try {
                    FirebaseConfig.getRef("Orders").push().setValue(orderProperties)
                    Toast.makeText(context, "Sipariş Verildi!", Toast.LENGTH_LONG).show()

                    (context as Activity).finish()
                } catch (ex: Exception) {
                    Toast.makeText(_context, ex.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}