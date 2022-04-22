package com.example.mysecond.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mysecond.R
import com.example.mysecond.data.ProfileCard

class ProfileStackViewAdapter : RecyclerView.Adapter<ProfileStackViewAdapter.ProfileStackViewHolder>() {

    var data = listOf<ProfileCard>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileStackViewHolder {
        return ProfileStackViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false) )
    }

    override fun onBindViewHolder(holder: ProfileStackViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    class ProfileStackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.item_card_name)
        val age = itemView.findViewById<TextView>(R.id.item_card_age)
        val city = itemView.findViewById<TextView>(R.id.item_card_city)
        val image = itemView.findViewById<ImageView>(R.id.item_card_image)
        fun bind(profileCard: ProfileCard) {
            name.text = profileCard.name
            age.text = profileCard.age.toString()
            city.text = profileCard.city
            image.setImageResource(profileCard.image)
        }
    }
}