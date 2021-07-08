package com.github.tatsuyafujisaki.expandablelistviewsample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import com.github.tatsuyafujisaki.expandablelistviewsample.databinding.FragmentThirdBinding
import com.github.tatsuyafujisaki.expandablelistviewsample.domain.Child
import com.github.tatsuyafujisaki.expandablelistviewsample.domain.Group
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.expandGroups
import kotlin.random.Random

class MyExpandableListAdapter(binding: FragmentThirdBinding) : BaseExpandableListAdapter() {
    val groups = generateGroups()

    init {
        binding.button.setOnClickListener {
            groups.clear()
            groups.addAll(generateGroups())
            binding.expandableListView.expandGroups(this)
            notifyDataSetChanged()
        }
    }

    override fun getGroupCount() = groups.size
    override fun getChildrenCount(groupPosition: Int) = groups[groupPosition].children.size
    override fun getGroup(groupPosition: Int) = groups[groupPosition]
    override fun getChild(groupPosition: Int, childPosition: Int) = groups[groupPosition].children[childPosition]
    override fun getGroupId(groupPosition: Int) = groups[groupPosition].id
    override fun getChildId(groupPosition: Int, childPosition: Int) = groups[groupPosition].children[childPosition].id
    override fun hasStableIds() = true

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val root = convertView
            ?: LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        root.findViewById<TextView>(android.R.id.text1)?.text = groups[groupPosition].name
        return root
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        val root = convertView
            ?: LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_expandable_list_item_2, parent, false)
        root.findViewById<TextView>(android.R.id.text1)?.text = groups[groupPosition].children[childPosition].name
        root.findViewById<TextView>(android.R.id.text2)?.text = groups[groupPosition].name
        return root
    }

    /**
     * If false, [ExpandableListView.setOnChildClickListener] will not be invoked.
     */
    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    private fun generateGroups() = MutableList(Random.nextInt(1, 4)) { groupPosition ->
        Group(groupPosition.toLong(),
            "Group $groupPosition",
            List(Random.nextInt(1, 4)) {
                Child(it.toLong(), "Child $it")
            }
        )
    }
}
