package com.ahmeturunveren.movieapp.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmeturunveren.movieapp.data.model.movie.FavoriteModel
import com.ahmeturunveren.movieapp.databinding.FavoriteItemBinding
import com.ahmeturunveren.movieapp.util.diffutil.DiffUtilCallback
import com.ahmeturunveren.movieapp.util.extensions.click

class FavoriteAdapter(
    var onDeleteClick: (Int)->Unit={}
): ListAdapter<FavoriteModel, FavoriteAdapter.FavoriteViewHolder>(
    DiffUtilCallback<FavoriteModel>(
        itemsTheSame = { oldItem, newItem ->
            oldItem == newItem
        },
        contentsTheSame = { oldItem, newItem ->
            oldItem == newItem
        }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteViewHolder(
        FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FavoriteViewHolder(val binding:FavoriteItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favMovie:FavoriteModel) = with(binding){
            movie = favMovie
            executePendingBindings()
            deleteButton.click {
                onDeleteClick(favMovie.id)
            }
        }
    }
}
