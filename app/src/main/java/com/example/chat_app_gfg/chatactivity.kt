package com.example.chat_app_gfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chatactivity : AppCompatActivity() {
    private lateinit var messagebox :EditText
    private lateinit var sendimage:ImageView
    private lateinit var chatrecycler : RecyclerView
    private lateinit var messageadapter: messageadapter
    private lateinit var messagelist :ArrayList<Message>
    private lateinit var db :DatabaseReference
    var senderoom:String? = null
    var receiveroom:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)


        val name = intent.getStringExtra("name")
        val recieveruid = intent.getStringExtra("uid")
        db = FirebaseDatabase.getInstance().getReference()
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        senderoom = recieveruid + senderuid
        receiveroom = senderuid + recieveruid


        supportActionBar?.title=name

        messagebox = findViewById(R.id.messagebox)
        sendimage = findViewById(R.id.sendimage)
        chatrecycler = findViewById(R.id.chatrecycler)
        messagelist = ArrayList()
        messageadapter = messageadapter(this,messagelist)
        chatrecycler.layoutManager = LinearLayoutManager(this)
        chatrecycler.adapter = messageadapter
        db.child("chats").child(senderoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messagelist.clear()
                for(i in snapshot.children){
                    val message = i.getValue(Message::class.java)
                    messagelist.add(message!!)
                }
                messageadapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        sendimage.setOnClickListener{
            val message = messagebox.text.toString()
            val messageobject = Message(message,senderuid)
            db.child("chats").child(senderoom!!).child("messages").push().setValue(messageobject).addOnSuccessListener {
                db.child("chats").child(receiveroom!!).child("messages").push().setValue(messageobject)
            }
            messagebox.setText("")


        }
    }
}