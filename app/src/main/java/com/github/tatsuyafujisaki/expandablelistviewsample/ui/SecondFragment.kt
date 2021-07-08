package com.github.tatsuyafujisaki.expandablelistviewsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleExpandableListAdapter
import androidx.fragment.app.Fragment
import com.github.tatsuyafujisaki.expandablelistviewsample.databinding.FragmentSecondBinding
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.expandGroups
import com.google.android.material.snackbar.Snackbar

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val groupData = ExpandableListViewUtil.generateGroupData()
    private val childData = ExpandableListViewUtil.generateChildData(groupData.size)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = createSimpleExpandableListAdapter()
        with(binding.expandableListView) {
            setAdapter(adapter)
            expandGroups(adapter)
            setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Snackbar.make(
                    view,
                    "${groupData[groupPosition][ExpandableListViewUtil.group]} > ${childData[groupPosition][childPosition][ExpandableListViewUtil.child]}",
                    Snackbar.LENGTH_SHORT
                ).show()
                true
            }
        }
        binding.button.setOnClickListener {
            groupData.clear()
            groupData.addAll(ExpandableListViewUtil.generateGroupData())
            childData.clear()
            childData.addAll(ExpandableListViewUtil.generateChildData(groupData.size))
            binding.expandableListView.expandGroups(adapter)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createSimpleExpandableListAdapter() = SimpleExpandableListAdapter(
        requireContext(),
        groupData,
        android.R.layout.simple_expandable_list_item_1,
        arrayOf(ExpandableListViewUtil.group),
        intArrayOf(android.R.id.text1),
        childData,
        android.R.layout.simple_expandable_list_item_2,
        arrayOf(ExpandableListViewUtil.child, ExpandableListViewUtil.group),
        intArrayOf(android.R.id.text1, android.R.id.text2)
    )
}
