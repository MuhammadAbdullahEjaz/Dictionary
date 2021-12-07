package com.example.dictionaryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.MeaningItemBinding
import com.example.dictionaryapp.network.data.Meaning

class MeaningAdapter: RecyclerView.Adapter<MeaningAdapter.ViewHolder>() {

    private var data:List<Meaning> = emptyList()

    class ViewHolder(private val binding: MeaningItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(meanings:Meaning){
            binding.partOfSpeechV = meanings.partOfSpeech
            if(meanings.definitions.isNotEmpty()) {
                binding.definitionV = meanings.definitions.first().definition
                binding.exampleV = meanings.definitions.first().example
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MeaningItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateMeanings(meanings:List<Meaning>?){
        data = meanings ?: emptyList()
        notifyDataSetChanged()
    }
}