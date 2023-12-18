package com.example.lab7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder> () {
    private var users = listOf<User>() // список пользователей

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    fun setData(userList: List<User>) {
        users = userList
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Связывание данных с элементами на формочке
        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.tv_id).text = user.id.toString()
            itemView.findViewById<TextView>(R.id.tv_login).text = user.login
            /*val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(password.text.toString().toByteArray())
            toListIntent.putExtra("passwordHash", hash.toString())*/
            itemView.findViewById<TextView>(R.id.tv_pass).text = user.password
        }
    }

}