package com.github.tatsuyafujisaki.expandablelistviewsample.util

import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import kotlin.random.Random

object ExpandableListViewUtil {
    const val group = "group"
    const val child = "child"

    fun ExpandableListView.expandGroups(adapter: ExpandableListAdapter) {
        for (i in 0 until adapter.groupCount) expandGroup(i)
    }

    fun generateGroupData() = MutableList(Random.nextInt(1, 4)) {
        mapOf(group to "Group $it")
    }

    fun generateChildData(groupCount: Int): MutableList<List<Map<String, String>>> {
        return MutableList(groupCount) { groupPosition ->
            List(Random.nextInt(1, 4)) {
                mapOf(group to "Group $groupPosition", child to "Child $it")
            }
        }
    }
}
