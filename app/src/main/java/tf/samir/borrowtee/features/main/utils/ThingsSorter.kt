package tf.samir.borrowtee.features.main.utils

import tf.samir.borrowtee.viewbase.RecyclerItem

interface ThingsSorter {
    fun sort(list: List<RecyclerItem>) : List<RecyclerItem>
}

sealed class ThingSorter {
    companion object {
        fun createSorter(@ThingSortType sorter: Int? = null): ThingsSorter {
             return when (sorter) {
                 STATE_UPWARD -> StateUpwardSorter()
                 STATE_DOWNWARD -> StateDownwardSorter()
                 else -> throw IllegalStateException("At least one sorter must be used.")
             }
        }
    }

    class StateUpwardSorter : ThingsSorter {
        override fun sort(list: List<RecyclerItem>): List<RecyclerItem> =
            list.sortedBy { recyclerItem -> recyclerItem.data.state }
    }

    class StateDownwardSorter : ThingsSorter {
        override fun sort(list: List<RecyclerItem>): List<RecyclerItem> =
            list.sortedByDescending { recyclerItem -> recyclerItem.data.state }
    }
}