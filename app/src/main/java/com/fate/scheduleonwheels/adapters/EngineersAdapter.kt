package com.fate.scheduleonwheels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fate.data.data.entities.Engineer
import com.fate.scheduleonwheels.databinding.ItemEngineerBinding


class EngineersAdapter() : RecyclerView.Adapter<EngineersAdapter.EngineersViewHolder>() {

    private var mItems: ArrayList<Engineer> = ArrayList(0)

    fun assignList(items: List<Engineer>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineersViewHolder {
        return EngineersViewHolder(
            ItemEngineerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: EngineersViewHolder, position: Int) {
        val item = mItems[position]
        holder.bind(item)
    }

    inner class EngineersViewHolder(private val binding: ItemEngineerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(engineerItem: Engineer) {
            binding.textViewEngineerName.text = engineerItem.name
            binding.executePendingBindings()
        }

    }
}