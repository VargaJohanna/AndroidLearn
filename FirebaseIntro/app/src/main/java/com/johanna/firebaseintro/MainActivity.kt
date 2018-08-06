package com.johanna.firebaseintro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

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
    }

    data class Employee (
            var name: String,
            var position: String,
            var address: String,
            var age: Int
    )
}
