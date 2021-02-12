package com.yuhdeveloper.yahsi.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.yuhdeveloper.yahsi.AlertDialogPrepare
import com.yuhdeveloper.yahsi.Interfaces.IReplaceFragment
import com.yuhdeveloper.yahsi.Pojos.Users
import com.yuhdeveloper.yahsi.R
import com.yuhdeveloper.yahsi.SharedPreferencesManager
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig

class SplashScreenActivity : AppCompatActivity() {

    private var fAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null
    private var fDB: FirebaseDatabase? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        object:CountDownTimer(2000,1000){
            override fun onFinish() {
                setID()

                var user:Users = SharedPreferencesManager().getUserInformation(this@SplashScreenActivity)

                if( !user.email.equals("") && !user.password.equals("") &&
                    user.email!=null && user.password!=null)
                {
                    signIn(user!!)
                }else{
                    var intent: Intent = Intent(this@SplashScreenActivity, SignActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }

            override fun onTick(p0: Long) {
            }

        }.start()

    }

    fun setID() {
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        fDB = FirebaseConfig.db
    }
    private fun signIn(_user: Users) {

        fAuth!!.signInWithEmailAndPassword(_user.email!!, _user.password!!)
            .addOnSuccessListener {
                if (!fAuth!!.currentUser!!.isEmailVerified) {
                    // Email doğrulanmadı!

                    Toast.makeText(this, "E-mail Doğrulanmadı", Toast.LENGTH_LONG)
                        .show()

                    AlertDialogPrepare.verificationDialog(
                        this,
                        fAuth!!,
                        _user,
                        fDB!!.getReference()
                    )
                } else {
                    var s = fDB!!.getReference().child("Users/" + fAuth!!.currentUser!!.uid)
                    s.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var obj: Users = dataSnapshot.getValue(Users::class.java)!!

                            SharedPreferencesManager().login(this@SplashScreenActivity, obj)
                            var intent: Intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this@SplashScreenActivity, "Giriş Başarılı!", Toast.LENGTH_LONG)
                                .show()
                            finish()

                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(this@SplashScreenActivity, "DatabaseError:"+databaseError.message, Toast.LENGTH_LONG).show()

                            var intent: Intent = Intent(this@SplashScreenActivity, SignActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                    })

                }
            }
            .addOnFailureListener {
                Toast.makeText(this@SplashScreenActivity, it.message, Toast.LENGTH_LONG).show()
                var intent: Intent = Intent(this@SplashScreenActivity, SignActivity::class.java)
                startActivity(intent)
            }
    }

}