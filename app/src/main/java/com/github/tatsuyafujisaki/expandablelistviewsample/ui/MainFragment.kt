package com.github.tatsuyafujisaki.expandablelistviewsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleExpandableListAdapter
import androidx.fragment.app.Fragment
import com.github.tatsuyafujisaki.expandablelistviewsample.databinding.FragmentMainBinding
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.child
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.expandGroups
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.generateChildData
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.generateGroupData
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.group
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val groupData = generateGroupData()
    private val childData = generateChildData(groupData.size)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
                    "${groupData[groupPosition][group]} > ${childData[groupPosition][childPosition][child]}",
                    Snackbar.LENGTH_SHORT
                ).show()
                true
            }
        }
        binding.button.setOnClickListener {
            groupData.clear()
            groupData.addAll(generateGroupData())
            childData.clear()
            childData.addAll(generateChildData(groupData.size))
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
        arrayOf(group),
        intArrayOf(android.R.id.text1),
        childData,
        android.R.layout.simple_expandable_list_item_2,
        arrayOf(child, group),
        intArrayOf(android.R.id.text1, android.R.id.text2)
    )
}
