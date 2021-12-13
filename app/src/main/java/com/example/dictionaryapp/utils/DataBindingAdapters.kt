package com.example.dictionaryapp.utils

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.SynonymAntonymsListAdapter
import com.example.dictionaryapp.WordAdapter
import com.example.dictionaryapp.WordListAdaptet
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

@BindingAdapter("android:visibility")
fun setVisibility(view:ExpandableListView, visibility:Boolean){
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

@BindingAdapter("android:wordListAdapter")
fun setWordAdapter(recyclerView: RecyclerView, data:List<String>?){
    if(recyclerView.adapter == null){
        val adapter = WordListAdaptet()
        adapter.updateData(data)
        recyclerView.adapter = adapter
    }else{
        val adapter = recyclerView.adapter as WordListAdaptet
        adapter.updateData(data)
    }
}

@BindingAdapter("android:data")
fun setData(expandableListView: ExpandableListView, data:Map<String,List<String>>?) {
    if (data != null) {
        val adapter = SynonymAntonymsListAdapter(data)
        expandableListView.setAdapter(adapter)
        expandableListView.layoutParams.height = 150
        expandableListView.setOnGroupExpandListener {
            var height = 0

            for(i in 0..expandableListView.childCount){
                height += 10
                height += expandableListView.dividerHeight
            }
            expandableListView.layoutParams.height = (height+6)*10
        }

        expandableListView.setOnGroupCollapseListener {
            expandableListView.layoutParams.height = 150
        }

    }
}