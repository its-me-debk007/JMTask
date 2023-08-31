package com.example.jmtask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.jmtask.BuildConfig
import com.example.jmtask.R
import com.example.jmtask.databinding.ItemMovie2Binding
import com.example.jmtask.model.Result

class FavouriteMoviesRecyclerAdapter(
    private var data: List<Result>,
    private val context: Context,
    private val onClick: (Result) -> Unit

) : RecyclerView.Adapter<FavouriteMoviesRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemMovie2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardView.setOnClickListener { onClick(data[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemMovie2Binding.inflate(
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
                .load(BuildConfig.IMG_BASE_URL + "/w500" + data[position].backdrop_path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img)

            title.text = data[position].title
            overview.text = data[position].overview
            rating.text = "${data[position].vote_average.toString().substring(0, 3)} ⭐"
        }
    }

    fun updateData(newData: List<Result>) {
        data = newData
        notifyDataSetChanged()
    }
}