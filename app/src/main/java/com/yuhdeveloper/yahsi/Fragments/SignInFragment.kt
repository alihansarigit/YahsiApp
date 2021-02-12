package com.yuhdeveloper.yahsi.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.yuhdeveloper.yahsi.Activities.MainActivity
import com.yuhdeveloper.yahsi.AlertDialogPrepare
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.Pojos.Users
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.SharedPreferencesManager
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignInFragment : Fragment(), View.OnClickListener {


    lateinit var txt_signUp: TextView
    lateinit var IReplaceListener: IReplaceFragment
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var btn_signIn: Button
    lateinit var txtLayout_email: TextInputLayout
    lateinit var txtLayout_password: TextInputLayout

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
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    fun setID(view: View) {
        txt_signUp = view.findViewById(R.id.FtSignIn_txt_sign_up)
        edt_email = view.findViewById(R.id.FtSignIn_edt_email)
        edt_password = view.findViewById(R.id.FtSignIn_edt_password)
        txtLayout_email = view.findViewById(R.id.FtSignIn_txtLayout_email)
        txtLayout_password = view.findViewById(R.id.FtSignIn_txtLayout_password)
        btn_signIn = view.findViewById(R.id.FtSignIn_btn_sign_in)
        IReplaceListener = _context as IReplaceFragment

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        fDB = FirebaseDatabase.getInstance()
    }

    fun setListeners() {
        txt_signUp.setOnClickListener(this)
        btn_signIn.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            setID(view)
            setListeners()

    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {
            txt_signUp.id -> {
                IReplaceListener.replaceFragment(SignUpFragment())
            }
            btn_signIn.id -> {
                if (checkVariables()) {
                    var user: Users = Users()
                    user.email = edt_email.text.toString()
                    user.password = edt_password.text.toString()
                    signIn(user)
                }
            }
        }
    }

    private fun checkVariables(): Boolean {

        var verificate: Boolean = true
        clearErrorMessage()


        if (edt_email.text.toString().equals("")) {
            txtLayout_email.error = "Doldurulması Zorunludur!"
            verificate = false
        }

        if (edt_password.text.toString().equals("")) {
            txtLayout_password.error = "Doldurulması Zorunludur!"
            verificate = false
        } else if (edt_password.text.toString().length < 6) {
            txtLayout_password.error = "En az 6 karakter giriniz!"
            verificate = false

        }

        if (!isEmailValid(edt_email.text.toString())) {
            txtLayout_email.error = "Email Formatında Yazınız!"
            verificate = false
        }

        return verificate
    }

    fun clearErrorMessage() {
        txtLayout_email.isErrorEnabled = false
        txtLayout_password.isErrorEnabled = false
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun signIn(_user: Users) {

        btn_signIn.isEnabled = false
        fAuth!!.signInWithEmailAndPassword(_user.email!!, _user.password!!)
            .addOnSuccessListener {
                if (!fAuth!!.currentUser!!.isEmailVerified) {
                    // Email doğrulanmadı!

                    Toast.makeText(context, "E-mail Doğrulanmadı", Toast.LENGTH_LONG)
                        .show()

                    AlertDialogPrepare.verificationDialog(
                        _context,
                        fAuth!!,
                        _user,
                        fDB!!.getReference()
                    )
                } else {
                    var s = fDB!!.getReference().child("Users/" + fAuth!!.currentUser!!.uid)
                    s.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var obj: Users = dataSnapshot.getValue(Users::class.java)!!

                            SharedPreferencesManager().login(_context, obj)
                            var intent: Intent = Intent(context, MainActivity::class.java)
                            _context.startActivity(intent)

                            Toast.makeText(context, "Giriş Başarılı!", Toast.LENGTH_LONG)
                                .show()
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                            btn_signIn.isEnabled = true

                        }
                    })

                }
            }
            .addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                btn_signIn.isEnabled = true

            }
    }


    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnSuccessListener(OnSuccessListener<Void> {
            Toast.makeText(
                context,
                "Doğrulama Linki Mail Adresinize Gönderildi",
                Toast.LENGTH_LONG
            ).show()
        }).addOnFailureListener {
            Toast.makeText(
                context,
                "Doğrulama Linki Mail Adresinize Gönderilemedi!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}