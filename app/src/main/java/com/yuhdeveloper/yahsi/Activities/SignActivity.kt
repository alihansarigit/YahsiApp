package com.yuhdeveloper.yahsi.Activities

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.yuhdeveloper.yahsi.Fragments.SignInFragment
import com.yuhdeveloper.yahsi.Fragments.SignUpFragment
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.SharedPreferencesManager


class SignActivity : AppCompatActivity(), View.OnClickListener,IReplaceFragment {

    private val TAG = "SignInActivity>>"
    private var signInButton: SignInButton? = null
    private var btn: Button? = null
    private var googleApiClient: GoogleApiClient? = null
    private val RC_SIGN_IN = 1
    var name: String? = null
    var email: String? = null
    var idToken: String? = null
    var userID: String? = null
    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var authStateListener: AuthStateListener? = null


    lateinit var frm_fragmentLayout: FrameLayout
    var backPressed:Boolean = false

    lateinit var fragment:Fragment
    fun setID() {
        frm_fragmentLayout = findViewById(R.id.ActSign_frm_fragment)
    }
    fun setListeners(){
    }

    override fun replaceFragment(_fragment: Fragment){
        fragment = _fragment
        supportFragmentManager.beginTransaction().replace(frm_fragmentLayout.id,_fragment).commit()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
        }
    }

    override fun onBackPressed() {
        if( fragment is SignUpFragment){
            replaceFragment(SignInFragment())
        }else{
            if(backPressed){
                super.onBackPressed()
            }
            else{
                backPressed=true
                object: CountDownTimer(2000,1000) {
                    override fun onFinish() {
                        backPressed=false
                    }

                    override fun onTick(p0: Long) {
                    }

                }.start()
                Toast.makeText(this@SignActivity,"Çıkmak için bir daha basınız!",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        var user = SharedPreferencesManager().getUserInformation(this)


        setID()
        setListeners()

        replaceFragment(SignInFragment())

//        signInButton = findViewById(R.id.google_button)
//        btn = findViewById(R.id.google_signout)
//
//        fAuth = FirebaseAuth.getInstance()
//        fStore = FirebaseFirestore.getInstance()


//        fAuth!!.createUserWithEmailAndPassword("saribassalihan@gmail.com","alihan1111").addOnCompleteListener {
//            if(it.isSuccessful){
//                var user:FirebaseUser? = fAuth!!.currentUser
//
//                if(!user!!.isEmailVerified){
//                    user.sendEmailVerification().addOnSuccessListener(OnSuccessListener<Void>{
//                        Toast.makeText(this@SignInActivity,"Başarılı",Toast.LENGTH_LONG).show()
//                    }).addOnFailureListener {
//                        Toast.makeText(this@SignInActivity,"Başarısız",Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }

//        fAuth!!.signInWithEmailAndPassword("saribassalihan@gmail.com","alihan1111")
//            .addOnSuccessListener {
//                var user = fAuth!!.currentUser
//                if(!user!!.isEmailVerified){
//                    Toast.makeText(this@SignActivity, "E-mail Dogrulanmadı", Toast.LENGTH_LONG).show()
//                    user.sendEmailVerification().addOnSuccessListener(OnSuccessListener<Void>{
//                        Toast.makeText(this@SignActivity,"Başarılı",Toast.LENGTH_LONG).show()
//                    }).addOnFailureListener {
//                        Toast.makeText(this@SignActivity,"Başarısız",Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(this@SignActivity,"Başarısız",Toast.LENGTH_LONG).show()
//            }
    }

    private fun signUp(_email: String, _password: String) {
//        fAuth!!.createUserWithEmailAndPassword("saribassalihan@gmail.com","alihan1111").addOnCompleteListener {
//            if(it.isSuccessful){
//                var user:FirebaseUser? = fAuth!!.currentUser
//
//                if(!user!!.isEmailVerified){
//                    user.sendEmailVerification().addOnSuccessListener(OnSuccessListener<Void>{
//                        Toast.makeText(this@SignInActivity,"Başarılı",Toast.LENGTH_LONG).show()
//                    }).addOnFailureListener {
//                        Toast.makeText(this@SignInActivity,"Başarısız",Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }
    }

    private fun signIn(_email: String, _password: String) {
        fAuth!!.signInWithEmailAndPassword(_email, _password)
            .addOnSuccessListener {
                var user = fAuth!!.currentUser
                if (!user!!.isEmailVerified) {
                    Toast.makeText(this@SignActivity, "E-mail Dogrulanmadı", Toast.LENGTH_LONG)
                        .show()
                    sendEmailVerification(user)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this@SignActivity, "Başarısız", Toast.LENGTH_LONG).show()
            }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnSuccessListener(OnSuccessListener<Void> {
            Toast.makeText(
                this@SignActivity,
                "Doğrulama Linki Mail Adresinize Gönderildi",
                Toast.LENGTH_LONG
            ).show()
        }).addOnFailureListener {
            Toast.makeText(
                this@SignActivity,
                "Doğrulama Linki Mail Adresinize Gönderilemedi!",
                Toast.LENGTH_LONG
            ).show()
        }
    }



}