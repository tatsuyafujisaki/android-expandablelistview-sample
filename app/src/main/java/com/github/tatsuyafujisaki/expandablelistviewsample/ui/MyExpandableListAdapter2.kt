package com.github.tatsuyafujisaki.expandablelistviewsample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.github.tatsuyafujisaki.expandablelistviewsample.R
import com.github.tatsuyafujisaki.expandablelistviewsample.databinding.FragmentFourthBinding
import com.github.tatsuyafujisaki.expandablelistviewsample.domain.Child
import com.github.tatsuyafujisaki.expandablelistviewsample.domain.Group
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.expandGroups
import kotlin.random.Random

class MyExpandableListAdapter2(binding: FragmentFourthBinding) : BaseExpandableListAdapter() {
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
                .inflate(R.layout.expandable_list_view_group, parent, false)
        root.findViewById<TextView>(R.id.text_view)?.text = groups[groupPosition].name
        root.findViewById<ImageView>(R.id.image_view)?.setImageResource(
            if (isExpanded) {
                R.drawable.ic_baseline_keyboard_arrow_up_24
            } else {
                R.drawable.ic_baseline_keyboard_arrow_down_24
            }
        )
        return root
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        val root = convertView
            ?: LayoutInflater.from(parent.context)
                .inflate(R.layout.expandable_list_view_child, parent, false)
        root.findViewById<TextView>(R.id.text_view1)?.text = groups[groupPosition].children[childPosition].name
        root.findViewById<TextView>(R.id.text_view2)?.text = groups[groupPosition].name
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
