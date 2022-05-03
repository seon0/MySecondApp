package com.example.mysecond.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mysecond.R
import com.example.mysecond.data.BoardPost

class BoardPostAdapter() :  RecyclerView.Adapter<BoardPostAdapter.BoardPostViewHolder>(){

    var data = listOf<BoardPost>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class BoardPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.post_title)
        val content = itemView.findViewById<TextView>(R.id.post_content)
        val deleteButton = itemView.findViewById<Button>(R.id.btn_delete_post)

        fun bind(boardPost: BoardPost, position: Int) {
            title.text = boardPost.title
            content.text = boardPost.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardPostViewHolder {
        return BoardPostViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false) )
    }

    override fun onBindViewHolder(holder: BoardPostViewHolder, position: Int) {
        holder.bind(data[position], position)

    }

    override fun getItemCount() = data.size

}