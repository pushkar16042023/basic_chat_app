package com.example.chat_app_gfg

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class useradapter(val context: Context, val userlist:ArrayList<user> ) : RecyclerView.Adapter<useradapter.viewholder>() {

    override fun onBindViewHolder(holder: useradapter.viewholder, position: Int) {
        val currentuser = userlist[position]
        holder.textname.text = currentuser.name
        holder.itemView.setOnClickListener{
            val intent = Intent(context,chatactivity::class.java)
            intent.putExtra("name",currentuser.name)
            intent.putExtra("uid",currentuser.uid)
            context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): useradapter.viewholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
    class viewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textname = itemView.findViewById<TextView>(R.id.text1);
    }

}