package com.example.jmtask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.jmtask.BuildConfig
import com.example.jmtask.R
import com.example.jmtask.databinding.ItemMovieBinding
import com.example.jmtask.model.Result

class MovieRecyclerAdapter(
    private val data: List<Result>,
    private val context: Context,
    private val onClick: (Result) -> Unit

) : RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardView.setOnClickListener { onClick(data[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(context)
                .load(BuildConfig.IMG_BASE_URL + "/w300" + data[position].poster_path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img)

            title.text = data[position].title
            releaseDate.text = data[position].release_date
//            discount.text = "${data[position].discountPercentage}% off"
//            price.text = "MRP ₹${data[position].price}"
            rating.text = "${data[position].vote_average} ⭐"
        }
    }
}