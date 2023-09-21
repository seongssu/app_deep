package com.android.imagesearch.SearchFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.imagesearch.R
import com.android.imagesearch.databinding.BasicBinding
import com.android.imagesearch.sharedPreferences.ImageSearchData
import com.bumptech.glide.Glide

class SearchFragmentAdapter(private val items : ArrayList<ImageSearchData>,private val context: Context) :
    RecyclerView
.Adapter<RecyclerView
.ViewHolder>() {

    interface ItemClick {
        fun onFavoritesClick(view: View, position: Int)
    }
    var itemClick:ItemClick? = null
    inner class SearchViewHolder(private val binding:BasicBinding) : RecyclerView.ViewHolder
        (binding.root){
        fun bind(items:ImageSearchData){
            binding.apply {
                SearchName.text = items.display_sitename
                SearchTime.text = items.datetime
                Glide.with(context)
                    .load(items.image_url)
                    .into(SearchProfile)

                searchHeart.setOnClickListener {
                    itemClick?.onFavoritesClick(it, position)
                }
                if(items.isLike){
                    binding.searchHeart.setImageResource(R.drawable.painted_heart)
                } else binding.searchHeart.setImageResource(R.drawable.heart)
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