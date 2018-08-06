package com.johanna.firebaseintro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebaseDB = FirebaseDatabase.getInstance()
        val databaseRef = firebaseDB.getReference("messages").push()


        val employee = Employee(
                "Andor Kotkodacs",
                "Android Developer",
                "1 Rainbow Avenue",
                29 )

        // Write
        databaseRef.setValue(employee)

        // Read
        databaseRef.addValueEventListener(object : ValueEventListener{
           override fun onDataChange(dataSnapshot: DataSnapshot?) {
               if(dataSnapshot != null) {
                   val value = dataSnapshot.value as HashMap<String, Any>
                   Log.d("VALUE", value.get("name").toString())
               }
           }
            override fun onCancelled(error: DatabaseError?) {
                if(error != null) {
                    Log.d("ERROR", error.message)
                }
            }
        })
        // Sign in
        mAuth.signInWithEmailAndPassword("hanna@me.com", "password")
                .addOnCompleteListener{
                    task: Task<AuthResult> ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Signed in successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Signed in unsuccessful", Toast.LENGTH_SHORT).show()

                    }
                }
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth.currentUser

        if(currentUser != null) {
            Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    data class Employee (
            var name: String,
            var position: String,
            var address: String,
            var age: Int
    )
}
