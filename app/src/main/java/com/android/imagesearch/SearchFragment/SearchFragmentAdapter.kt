package com.android.imagesearch.SearchFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.imagesearch.api.Document
import com.android.imagesearch.databinding.BasicBinding
import com.bumptech.glide.Glide

class SearchFragmentAdapter(private val items : ArrayList<Document>,private val context: Context) : RecyclerView
.Adapter<RecyclerView
.ViewHolder>() {

    inner class SearchViewHolder(private val binding:BasicBinding) : RecyclerView.ViewHolder
        (binding.root){
        fun bind(items:Document){
            binding.apply {
                SearchName.text = items.display_sitename
                SearchTime.text = items.datetime
                Glide.with(context)
                    .load(items.image_url)
                    .into(SearchProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BasicBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SearchViewHolder
        val Items = items[position]
        holder.bind(Items)
    }

}