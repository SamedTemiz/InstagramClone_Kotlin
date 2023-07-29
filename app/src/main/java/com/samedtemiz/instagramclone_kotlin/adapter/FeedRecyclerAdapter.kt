package com.samedtemiz.instagramclone_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samedtemiz.instagramclone_kotlin.databinding.RecyclerRowBinding
import com.samedtemiz.instagramclone_kotlin.model.Post
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(private val postList : ArrayList<Post>) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.txtComment.text = postList[position].comment
        holder.binding.txtEmail.text = postList[position].email

        Picasso.get().load(postList[position].downloadUrl).into(holder.binding.imgPicture)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}