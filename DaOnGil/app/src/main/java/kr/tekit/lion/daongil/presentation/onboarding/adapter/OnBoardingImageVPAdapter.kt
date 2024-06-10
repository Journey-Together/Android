package kr.tekit.lion.daongil.presentation.onboarding.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.tekit.lion.daongil.databinding.ItemHomeVpBinding
import kr.tekit.lion.daongil.databinding.ItemOnboardingVpBinding
import kr.tekit.lion.daongil.presentation.main.adapter.HomeVPAdapter

class OnBoardingVPAdapter(
    private val images: List<Drawable>,
) :  RecyclerView.Adapter<OnBoardingVPAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding : ItemOnboardingVpBinding = ItemOnboardingVpBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    class ImageViewHolder(private val binding: ItemOnboardingVpBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Drawable) {
            binding.itemOnboardingIv.setImageDrawable(image)
        }
    }
}