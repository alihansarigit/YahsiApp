package com.yuhdeveloper.yahsi

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.yuhdeveloper.yahsi.Activities.MainActivity
import com.yuhdeveloper.yahsi.Pojos.Users
import com.yuhdeveloper.yahsi.Usefull.FirebasePack.FirebaseConfig.Companion.getTime

class AlertDialogPrepare {


    companion object {

        fun verificationDialog(
            _context: Context,
            fAuth: FirebaseAuth,
            _user: Users,
            ref: DatabaseReference
        ): AlertDialog {
            var view: View = LayoutInflater.from(_context)
                .inflate(R.layout.custom_alert_verification, null)

            var alert: AlertDialog = AlertDialog.Builder(_context).create()
            alert.setCancelable(false)

            alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            var img_close: ImageView = view.findViewById(R.id.customalertverification_img_close)
            var btn_checkVerification: Button =
                view.findViewById(R.id.customalertverification_btn_checkVerification)
            var btn_sendVerification: Button =
                view.findViewById(R.id.customalertverification_btn_sendVerification)

            img_close.setOnClickListener {
                alert.dismiss()
            }

            btn_checkVerification.setOnClickListener {
                fAuth!!.signInWithEmailAndPassword(_user.email!!, _user.password!!)
                    .addOnSuccessListener {
                        if (!fAuth!!.currentUser!!.isEmailVerified) {
                            Toast.makeText(_context, "E-mail Doğrulanmadı!", Toast.LENGTH_LONG)
                                .show()

                        } else {


                            var s = ref.child("Users/" + fAuth!!.currentUser!!.uid)
                            s.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    var obj: Users = dataSnapshot.getValue(Users::class.java)!!

                                    SharedPreferencesManager().login(_context, obj)
                                    var intent: Intent = Intent(_context, MainActivity::class.java)
                                    _context.startActivity(intent)

                                    Toast.makeText(_context, "E-mail Doğrulandı!", Toast.LENGTH_LONG)
                                        .show()
                                    alert.dismiss()
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Getting Post failed, log a message
                                    Log.w(
                                        "TAG",
                                        "loadPost:onCancelled",
                                        databaseError.toException()
                                    )
                                    // ...
                                }
                            })





                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(_context, it.message, Toast.LENGTH_LONG).show()
                    }
            }

            btn_sendVerification.setOnClickListener {
                fAuth!!.currentUser!!.sendEmailVerification()
                    .addOnSuccessListener(OnSuccessListener<Void> {
                        Toast.makeText(
                            _context,
                            "Doğrulama Linki Mail Adresinize Gönderildi",
                            Toast.LENGTH_LONG
                        ).show()
                    }).addOnFailureListener {
                    Toast.makeText(
                        _context,
                        "Doğrulama Linki Mail Adresinize Gönderilemedi!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            alert.setView(view)
            alert.show()
            return alert
        }


        fun AreYouSureAlert(_context: Context,listenerYES:View.OnClickListener,listenerNO:View.OnClickListener):AlertDialog{
            var view:View = LayoutInflater.from(_context).inflate(R.layout.custom_alert_areusure,null)
            var alert:AlertDialog = AlertDialog.Builder(_context).create()
            var btn_yes:Button = view.findViewById(R.id.alert_btn_yes)
            var btn_no:Button = view.findViewById(R.id.alert_btn_no)
            btn_yes.setOnClickListener(listenerYES)
            btn_no.setOnClickListener(listenerNO)
            alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            alert.setView(view)
            alert.show()

            return alert
        }

    }
}