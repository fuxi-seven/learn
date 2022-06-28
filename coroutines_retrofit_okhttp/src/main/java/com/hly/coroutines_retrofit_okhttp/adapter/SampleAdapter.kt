package com.hly.coroutines_retrofit_okhttp.adapter

import JsonBean
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hly.coroutines_retrofit_okhttp.R

class SampleAdapter (val context: Context, var jsonBean: JsonBean?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_imageview, p0,false))
    }

    override fun getItemCount(): Int {
        return if (jsonBean?.data!!.isNotEmpty()) jsonBean?.data!!.size else 0
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as? MyViewHolder)?.imageView?.let { Glide.with(context).load(jsonBean?.data!![p1].imagePath).into(it) }
    }


    class MyViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
        var imageView: ImageView? = null

        init {
            imageView = parentView.findViewById(R.id.image)
        }
    }
}