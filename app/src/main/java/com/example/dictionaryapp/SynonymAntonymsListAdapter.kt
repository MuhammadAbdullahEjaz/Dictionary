package com.example.dictionaryapp

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.example.dictionaryapp.databinding.SynantHeaderBinding
import com.example.dictionaryapp.databinding.SynantItemBinding

class SynonymAntonymsListAdapter(private val data:Map<String,List<String>>): ExpandableListAdapter {


    override fun registerDataSetObserver(observer: DataSetObserver?) {
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getGroupCount(): Int {
        return data.keys.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val parent = data[data.keys.elementAt(groupPosition)]
        return parent!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return data.keys.elementAt(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val parent = data[data.keys.elementAt(groupPosition)]
        return parent!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val groupData = getGroup(groupPosition)
        val layoutinflater = LayoutInflater.from(parent?.context)
        val binding = SynantHeaderBinding.inflate(layoutinflater)
        if (getChildrenCount(groupPosition) == 0) {
            binding.text = null
        }else{
            binding.text = groupData as String
        }
        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val childData = getChild(groupPosition, childPosition)
        val layoutinflater = LayoutInflater.from(parent?.context)
        val binding = SynantItemBinding.inflate(layoutinflater)
        binding.text = childData as String
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    override fun onGroupExpanded(groupPosition: Int) {
    }

    override fun onGroupCollapsed(groupPosition: Int) {
    }

    override fun getCombinedChildId(groupId: Long, childId: Long): Long {
        return 0
    }

    override fun getCombinedGroupId(groupId: Long): Long {
        return 0
    }
}