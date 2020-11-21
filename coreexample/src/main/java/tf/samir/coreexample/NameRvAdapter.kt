package tf.samir.coreexample

import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_view.view.*

class NameRvAdapter(private val listener: (View) -> Unit) :
    ListAdapter<String, NameRvAdapter.MyViewHolder>(StringItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(inflate(parent.context, R.layout.item_view, parent), listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    inner class MyViewHolder(override val containerView: View, listener: (View) -> Unit) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        init {
            itemView.setOnClickListener(listener)
        }

        fun bind(exampleItem: String) =
            with(itemView) {
                itemView.tag = exampleItem
                tvTitle.text = exampleItem
            }

    }

    internal class StringItemCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

}