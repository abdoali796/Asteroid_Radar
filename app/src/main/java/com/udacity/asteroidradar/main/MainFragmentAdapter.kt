package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.Asteroids
import com.udacity.asteroidradar.databinding.AsteroidItmBinding


class MainFragmentAdapter(val clickListener: AsteroidListener) :
    androidx.recyclerview.widget.ListAdapter<Asteroid, MainFragmentAdapter.AsteroidVH>(
        AsteroidDiffCallback()
    ) {


    class AsteroidVH(val binding: AsteroidItmBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid, clickListener: AsteroidListener) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
//
        }

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.asteroid_itm
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidVH {
        val withDataBinding: AsteroidItmBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), AsteroidVH.LAYOUT, parent, false
        )
        return AsteroidVH(withDataBinding)

    }

    override fun onBindViewHolder(holder: AsteroidVH, position: Int) {
        val item = getItem(position)

        holder.bind(item!!, clickListener)
    }


}


class AsteroidDiffCallback :
    DiffUtil.ItemCallback<Asteroid>() {


    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem

    }
}

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroids: Asteroid) = clickListener(asteroids)
}
