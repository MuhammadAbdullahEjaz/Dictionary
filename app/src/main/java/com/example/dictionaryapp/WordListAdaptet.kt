package com.example.dictionaryapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.WordItemBinding
import com.example.dictionaryapp.utils.OnItemClickListener

class WordListAdaptet(private val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<WordListAdaptet.WordItemViewHolder>() {

    private var data:List<String> = emptyList()

    class WordItemViewHolder(private val binding:WordItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(word:String, onClick: OnItemClickListener, pos:Int){
            binding.word = word
            itemView.setOnClickListener{
                onClick.onClick(word)
            }
//            if (pos%2 == 0){
//                itemView.setBackgroundColor(Color.parseColor("#FFEEEEEE"))
//            }else{
//                itemView.setBackgroundColor(Color.parseColor("#00000000"))
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WordItemBinding.inflate(layoutInflater, parent, false)
        return WordItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordItemViewHolder, position: Int) {
        val word = data[position]
        holder.bind(word, onItemClickListener, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(dataWords:List<String>?){
        data = dataWords ?: emptyList()
        notifyDataSetChanged()
    }

}