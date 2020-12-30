package tf.samir.coreexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tf.samir.coreexample.databinding.ItemViewBinding

class NameRvAdapter(private val listener: (View) -> Unit) :
    ListAdapter<String, NameRvAdapter.MyViewHolder>(StringItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    inner class MyViewHolder(private val binding: ItemViewBinding, listener: (View) -> Unit) :
            RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener(listener)
        }

        fun bind(exampleItem: String) {
            binding.apply {
                itemView.tag = exampleItem
                tvTitle.text = exampleItem
            }
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