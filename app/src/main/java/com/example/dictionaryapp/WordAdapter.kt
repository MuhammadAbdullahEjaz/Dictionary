package com.example.dictionaryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.HeadItemBinding
import com.example.dictionaryapp.databinding.MeaningItemBinding
import com.example.dictionaryapp.network.data.Meaning
import com.example.dictionaryapp.network.data.Word

class WordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<Meaning> = emptyList()
    private var word: Word? = null

    private val HEADER_ITEM = 1
    private val MEANING_ITEM = 2


    class HeaderHolder(private val binding: HeadItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word?) {
            binding.result = word
        }
    }

    class MeaningHolder(private val binding: MeaningItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meanings: Meaning) {
            binding.partOfSpeechV = meanings.partOfSpeech
            if (meanings.definitions.isNotEmpty()) {
                binding.definitionV = meanings.definitions.first().definition
                binding.exampleV = meanings.definitions.first().example
                if (meanings.definitions.first().synonyms.isEmpty() && meanings.definitions.first().antonyms.isEmpty()) {
                    binding.synantData = null
                } else {
                    val synAntData = mapOf<String, List<String>>(
                        Pair("synonyms", meanings.definitions.first().synonyms),
                        Pair("antonyms", meanings.definitions.first().antonyms)
                    )
                    binding.synantData = synAntData
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == MEANING_ITEM) {
            val binding = MeaningItemBinding.inflate(layoutInflater, parent, false)
            return MeaningHolder(binding)
        } else {
            val binding = HeadItemBinding.inflate(layoutInflater, parent, false)
            return HeaderHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == MEANING_ITEM) {
            val view = holder as MeaningHolder
            val item = data[position - 1]
            view.bind(item)
        } else {
            val view = holder as HeaderHolder
            view.bind(word)
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return HEADER_ITEM
        } else {
            return MEANING_ITEM
        }
    }

    fun updateMeanings(wordData: Word?) {
        if (wordData != null) {
            word = wordData
            data = wordData.meanings ?: emptyList()
            notifyDataSetChanged()
        }
    }
}