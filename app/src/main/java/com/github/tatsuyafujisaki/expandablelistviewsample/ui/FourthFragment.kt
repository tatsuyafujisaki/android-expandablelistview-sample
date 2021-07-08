package com.github.tatsuyafujisaki.expandablelistviewsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.tatsuyafujisaki.expandablelistviewsample.databinding.FragmentFourthBinding
import com.github.tatsuyafujisaki.expandablelistviewsample.util.ExpandableListViewUtil.expandGroups
import com.google.android.material.snackbar.Snackbar

class FourthFragment : Fragment() {
    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = MyExpandableListAdapter2(binding)
        with(binding.expandableListView) {
            setAdapter(adapter)
            expandGroups(adapter)
            setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Snackbar.make(
                    view,
                    "${adapter.groups[groupPosition].name} > ${adapter.groups[groupPosition].children[childPosition].name}",
                    Snackbar.LENGTH_SHORT
                ).show()
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
