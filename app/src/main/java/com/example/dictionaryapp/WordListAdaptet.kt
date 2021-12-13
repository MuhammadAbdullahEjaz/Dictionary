package com.example.dictionaryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.WordItemBinding

class WordListAdaptet:RecyclerView.Adapter<WordListAdaptet.WordItemViewHolder>() {

    private var data:List<String> = emptyList()

    class WordItemViewHolder(private val binding:WordItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(word:String){
            binding.word = word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WordItemBinding.inflate(layoutInflater, parent, false)
        return WordItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordItemViewHolder, position: Int) {
        val word = data[position]
        holder.bind(word)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(dataWords:List<String>?){
        data = dataWords ?: emptyList()
        notifyDataSetChanged()
    }

}