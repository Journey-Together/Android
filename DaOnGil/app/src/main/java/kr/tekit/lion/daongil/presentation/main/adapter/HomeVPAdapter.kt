package kr.tekit.lion.daongil.presentation.main.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemHomeVpBinding

class HomeVPAdapter(private val images: List<Drawable>) : RecyclerView.Adapter<HomeVPAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemHomeVpBinding = ItemHomeVpBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    class ViewHolder(private val binding: ItemHomeVpBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Drawable) {
            binding.itemHomeIv.setImageDrawable(image)
        }
    }
}