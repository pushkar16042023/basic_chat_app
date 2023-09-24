package com.example.chat_app_gfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var userecycler : RecyclerView
    private lateinit var userlist : ArrayList<user>
    private lateinit var useradapter: useradapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbs : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.lavender))
        mAuth = FirebaseAuth.getInstance()
        mdbs = FirebaseDatabase.getInstance().getReference()
        userlist = ArrayList()
        useradapter = useradapter(this,userlist)
        userecycler = findViewById(R.id.recyclerview)
        userecycler.layoutManager = LinearLayoutManager(this)

        userecycler.adapter = useradapter
        mdbs.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postsnapshot in snapshot.children){
                    val currentuser = postsnapshot.getValue(user::class.java)
                    if(mAuth.currentUser?.uid!=currentuser?.uid){
                        userlist.add(currentuser!!)
                    }

                }
                useradapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            mAuth.signOut()
            val intent = Intent(this@MainActivity,loginactivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}