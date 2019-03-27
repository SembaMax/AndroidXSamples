package com.semba.androidsamples.ArchPagination.Data

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter

class ImageBinder {

    @BindingAdapter("imageURL")
    fun loadImage(imageView: ImageView, imageUrl: String)
    {
        imageView.setImageURI(Uri.parse(imageUrl))
    }
}