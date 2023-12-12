package com.sipun.wallpaperw.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sipun.wallpaperw.data.model.UnSplashPhoto
import com.sipun.wallpaperw.databinding.ItemUnsplashPhotoBinding
import javax.inject.Inject

class UnSplashAdapter @Inject constructor() : PagingDataAdapter<UnSplashPhoto , UnSplashAdapter.PhotoViewHolder>(
    PHOTO_COMPARATOR
) {

    private lateinit var binding : ItemUnsplashPhotoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder
    {
        binding = ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PhotoViewHolder()
    }

    override fun onBindViewHolder(holder: PhotoViewHolder , position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }

    override fun getItemViewType(position : Int) : Int
    {
        return position
    }

    private var onItemClickListener : ((UnSplashPhoto) -> Unit?)? = null

    fun setOnItemClickListener(listener : (UnSplashPhoto) -> Unit)
    {
        onItemClickListener = listener
    }

    inner class PhotoViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: UnSplashPhoto?) {
            binding.apply {
                if (photo != null) {
                    imageView.load(photo.urls.small)
                    Log.e("HECTOR" , "small --> "+photo.urls.small +"\n mid --> "  +photo.urls.medium +"\n large --> "+photo.urls.large +"\n ")
                } else {
                    imageView.setImageDrawable(null)
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        photo?.let { pic -> it(pic) }
                    }
                }
            }
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnSplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnSplashPhoto , newItem: UnSplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UnSplashPhoto , newItem: UnSplashPhoto): Boolean {
                return oldItem == newItem
            }
        }
    }
}