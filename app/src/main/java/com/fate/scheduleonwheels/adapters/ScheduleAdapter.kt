package com.fate.scheduleonwheels.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fate.data.data.entities.Schedule
import com.fate.scheduleonwheels.databinding.ItemScheduleBinding
import com.fate.scheduleonwheels.utils.CommonUtils


@SuppressLint("SetTextI18n")
class ScheduleAdapter() : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private var mItems: ArrayList<Schedule> = ArrayList(0)

    fun assignList(items: List<Schedule>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            ItemScheduleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = mItems[position]
        holder.bind(item)
    }

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(scheduleItem: Schedule) {

            binding.apply {

                textViewDate.text = "${CommonUtils.getDayName(scheduleItem.date)}  ${scheduleItem.week}"
                if(!scheduleItem.engineersSupporting.isNullOrEmpty() && scheduleItem.engineersSupporting.size >1){
                    textViewDayShiftEngineerName.text = scheduleItem.engineersSupporting[0].name
                    textViewDayShiftType.text = "${scheduleItem.listShifts[0]} Shift"
                    textViewNightShiftEngineerName.text = scheduleItem.engineersSupporting[1].name
                    textViewNightShiftType.text = "${scheduleItem.listShifts[1]} Shift"
                }

                executePendingBindings()
            }

        }

    }
}