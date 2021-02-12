package com.yuhdeveloper.yahsi.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.Pojos.Users
import com.yuhdeveloper.yahsi.R
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpFragment : Fragment(), View.OnClickListener {


    lateinit var edt_name: EditText
    lateinit var edt_surname: EditText
    lateinit var edt_phone: EditText
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var edt_passwordConfirm: EditText
    lateinit var btn_sign: Button
    lateinit var img_back: ImageView

    lateinit var txtLayout_name: TextInputLayout
    lateinit var txtLayout_surname: TextInputLayout
    lateinit var txtLayout_email: TextInputLayout
    lateinit var txtLayout_password: TextInputLayout
    lateinit var txtLayout_passwordConfirm: TextInputLayout
    lateinit var txtLayout_phone: TextInputLayout

    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null


    private var fDB: FirebaseDatabase? = null

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
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setID(view)
        setListeners()
    }

    fun setID(v: View) {
        edt_name = v.findViewById(R.id.FtSignUp_edt_name)
        edt_surname = v.findViewById(R.id.FtSignUp_edt_surname)
        edt_phone = v.findViewById(R.id.FtSignUp_edt_phone)
        edt_email = v.findViewById(R.id.FtSignUp_edt_email)
        edt_password = v.findViewById(R.id.FtSignUp_edt_password)
        edt_passwordConfirm = v.findViewById(R.id.FtSignUp_edt_passwordconfirm)
        btn_sign = v.findViewById(R.id.FtSignUp_btn_sign_up)
        img_back = v.findViewById(R.id.FtSignUp_img_back)

        txtLayout_name = v.findViewById(R.id.FtSignUp_textLayout_name)
        txtLayout_surname = v.findViewById(R.id.FtSignUp_textLayout_surname)
        txtLayout_email = v.findViewById(R.id.FtSignUp_textLayout_email)
        txtLayout_password = v.findViewById(R.id.FtSignUp_textLayout_password)
        txtLayout_passwordConfirm = v.findViewById(R.id.FtSignUp_textLayout_passwordConfirm)
        txtLayout_phone = v.findViewById(R.id.FtSignUp_textLayout_phone)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        fDB = FirebaseDatabase.getInstance()

    }

    fun setListeners() {
        btn_sign.setOnClickListener(this)
        img_back.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            img_back.id -> {
                var IReplaceListener: IReplaceFragment = _context as IReplaceFragment
                IReplaceListener.replaceFragment(SignInFragment())
            }
            btn_sign.id -> {
                if(checkVariables()){
                    var user: Users = Users()
                    user.name = edt_name.text.toString()
                    user.surname = edt_surname.text.toString()
                    user.email = edt_email.text.toString()
                    user.password = edt_password.text.toString()
                    user.phone = edt_phone.text.toString()
                    user.isVerificated = false
                    user.createdAt = ServerValue.TIMESTAMP
                    user.role = "Üye"

                    signUp(user)
                }else{

                }
            }
        }


    }

    private fun signUp(user: Users) {

        btn_sign.isEnabled = false
        fAuth!!.createUserWithEmailAndPassword(user.email!!, user.password!!).addOnCompleteListener {
            if (it.isSuccessful) {
                user.uid = fAuth!!.currentUser!!.uid
                addUser(user)

                var IReplaceListener: IReplaceFragment = _context as IReplaceFragment
                IReplaceListener.replaceFragment(SignInFragment())

                Toast.makeText(context, "Kayıt Başarılı!", Toast.LENGTH_LONG).show()

//                    u.sendEmailVerification().addOnSuccessListener(OnSuccessListener<Void>{
//                        Toast.makeText(context,"Başarılı", Toast.LENGTH_LONG).show()
//                    }).addOnFailureListener {
//                        Toast.makeText(context,"Başarısız", Toast.LENGTH_LONG).show()
//                    }
            }else{
                btn_sign.isEnabled = true
            }
        }.addOnFailureListener {
            Toast.makeText(_context, it!!.message!!, Toast.LENGTH_LONG).show()
            btn_sign.isEnabled = true

        }
    }


    private fun checkVariables():Boolean {

        var verificate:Boolean = true
        clearErrorMessage()

        if (edt_name.text.toString().equals("")) {
            txtLayout_name.error = "Doldurulması Zorunludur!"
            verificate = false

        }
        if (edt_surname.text.toString().equals("")) {
            txtLayout_surname.error = "Doldurulması Zorunludur!"
            verificate = false

        }
        if (edt_email.text.toString().equals("")) {
            txtLayout_email.error = "Doldurulması Zorunludur!"
            verificate = false

        }

        if (edt_password.text.toString().equals("") && edt_passwordConfirm.text.toString()
                .equals("")
        ) {
            txtLayout_password.error = "Doldurulması Zorunludur!"
            txtLayout_passwordConfirm.error = "Doldurulması Zorunludur!"
            verificate = false

        } else if (!edt_password.text.toString().equals(edt_passwordConfirm.text.toString())) {
            txtLayout_password.error = "Şifreler Eşleşmiyor!"
            txtLayout_passwordConfirm.error = "Şifreler Eşleşmiyor!"
            verificate = false

        } else if (edt_password.text.toString().length < 6) {
            txtLayout_password.error = "En az 6 karakter giriniz!"
            verificate = false

        }


        if (edt_phone.text.toString().equals("")) {
            txtLayout_phone.error = "Doldurulması Zorunludur!"
            verificate = false

        }

        if (!isEmailValid(edt_email.text.toString())) {
            txtLayout_email.error = "Email Formatında Yazınız!"
            verificate = false
        }

        return verificate
    }

    fun clearErrorMessage() {
        txtLayout_name.isErrorEnabled = false
        txtLayout_surname.isErrorEnabled = false
        txtLayout_email.isErrorEnabled = false
        txtLayout_password.isErrorEnabled = false
        txtLayout_passwordConfirm.isErrorEnabled = false
        txtLayout_phone.isErrorEnabled = false
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }


    fun addUser(user: Users) {
        var ref_user = fDB!!.reference
        ref_user.child("Users/"+user.uid).setValue(user)
    }
}