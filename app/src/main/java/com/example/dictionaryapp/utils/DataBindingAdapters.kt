package com.example.dictionaryapp.utils

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.WordAdapter
import com.example.dictionaryapp.network.data.Meaning
import com.example.dictionaryapp.network.data.Word

@BindingAdapter("android:visibility")
fun setVisibility(view:RelativeLayout, visibility:Boolean){
    if(visibility) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}

@BindingAdapter("android:visibility")
fun setVisibility(view:TextView, visibility:Boolean){
    if(visibility) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}

@BindingAdapter("android:visibility")
fun setVisibility(view:RecyclerView, visibility:Boolean){
    if(visibility) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}

@BindingAdapter("android:visibility")
fun setVisibility(view:LinearLayout, visibility:Boolean){
    if(visibility) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}

@BindingAdapter("android:data")
fun setData(recyclerView: RecyclerView, data:Word?){
    if(recyclerView.adapter == null){
        val adapter = WordAdapter()
        adapter.updateMeanings(data)
        recyclerView.adapter = adapter
    }else{
        val adapter = recyclerView.adapter as WordAdapter
        adapter.updateMeanings(data)
    }
}