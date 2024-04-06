package com.example.ezlotestapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ezlotestapp.databinding.DeviceListItemBinding
import com.example.ezlotestapp.domain.models.DeviceModel
import com.example.ezlotestapp.presentation.utils.getImageResourceIdByName

class DevicesAdapter(
    private val listItemEventListener: ListItemEventListener
) : ListAdapter<DeviceModel, DevicesAdapter.DeviceViewHolder>(DeviceDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            binding = DeviceListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model)
        holder.itemView.setOnClickListener {
            listItemEventListener.openDetailClick(model)
        }
        holder.itemView.setOnLongClickListener {
            listItemEventListener.onLongClickToDeleteItem(model)
            true
        }
    }

    inner class DeviceViewHolder(private val binding: DeviceListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DeviceModel) {
            with(binding) {
                val context = binding.root.context
                ivImage.setImageResource(
                    context.getImageResourceIdByName(model.imageResourceName)
                )
                tvDeviceName.text = model.deviceName
                tvSnName.text = model.platform


                ivEditImage.setOnClickListener {
                    listItemEventListener.editDeviceClick(model)
                }
            }
        }
    }

    private class DeviceDiffCallback : DiffUtil.ItemCallback<DeviceModel>() {
        override fun areItemsTheSame(oldItem: DeviceModel, newItem: DeviceModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DeviceModel, newItem: DeviceModel): Boolean {
            return oldItem == newItem
        }
    }
}