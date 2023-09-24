package com.example.chat_app_gfg

import android.content.Context

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth


class messageadapter(val context: Context, val messagelist:ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val item_sent = 2;
    val item_recieve =1;




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.recievechat,parent,false)
            return recieveviewholder(view)

        }
        else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sendchat,parent,false)
            return sentviewholder(view)
        }


    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage = messagelist[position]
        if(holder.javaClass == sentviewholder::class.java){

            val viewholder = holder as sentviewholder
            holder.sendmessage.text = currentmessage.message
        }
        else{
            val viewholder = holder as recieveviewholder
            holder.recievemessage.text =currentmessage.message

        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage = messagelist[position]
        if(FirebaseAuth.getInstance().currentUser?.uid!!.equals(currentmessage.sender)){
            return item_sent
        }
        else{
            return item_recieve
        }
    }

    override fun getItemCount(): Int {
        return messagelist.size
    }

    class sentviewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val sendmessage = itemView.findViewById<TextView>(R.id.sendmessage)

    }
    class recieveviewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val recievemessage = itemView.findViewById<TextView>(R.id.recievemessage)

    }
}