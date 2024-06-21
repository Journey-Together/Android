package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemListSearchAreaBinding
import kr.tekit.lion.daongil.databinding.ItemListSearchCategoryBinding
import kr.tekit.lion.daongil.databinding.ItemListSearchPlaceBinding
import kr.tekit.lion.daongil.presentation.main.model.AreaModel
import kr.tekit.lion.daongil.presentation.main.model.CategoryModel
import kr.tekit.lion.daongil.presentation.main.model.ListSearchUIModel
import kr.tekit.lion.daongil.presentation.main.model.PlaceModel

class ListSearchAdapter(
    private val onSelectArea: (String) -> Unit,
    private val onSelectSigungu: (String) -> Unit,
    private val onClickSearchButton: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<ListSearchUIModel> = mutableListOf()
    private val areaList: MutableList<String> = mutableListOf()
    private val sigunguList: MutableList<String> = mutableListOf()

    fun submitList(newData: List<ListSearchUIModel>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    fun submitAreaList(areas: List<String>) {
        areaList.addAll(areas)
    }

    fun submitSigunguList(list: List<String>) {
        sigunguList.clear()
        sigunguList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is CategoryModel -> R.layout.item_list_search_category
            is PlaceModel -> R.layout.item_list_search_place
            is AreaModel -> R.layout.item_list_search_area
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.item_list_search_category -> ListSearchCategoryViewHolder(
                ItemListSearchCategoryBinding.bind(v)
            )

            R.layout.item_list_search_place -> ListSearchPlaceViewHolder(
                ItemListSearchPlaceBinding.bind(v)
            )

            else -> ListSearchAreaViewHolder(
                ItemListSearchAreaBinding.bind(v),
                onSelectArea,
                onSelectSigungu,
                onClickSearchButton
            )
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is ListSearchCategoryViewHolder -> holder.bind(item as CategoryModel)
            is ListSearchAreaViewHolder -> holder.bind(areaList, sigunguList)
            is ListSearchPlaceViewHolder -> holder.bind(item as PlaceModel)
        }
    }

    class ListSearchCategoryViewHolder(private val binding: ItemListSearchCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryModel) {
            // 데이터 바인딩 로직 추가
        }
    }

    class ListSearchAreaViewHolder(
        private val binding: ItemListSearchAreaBinding,
        private val onSelectArea: (String) -> Unit,
        private val onSelectSigungu: (String) -> Unit,
        private val onClickSearchButton: () -> Unit,
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(areaList: List<String>, sigunguList: List<String>) {
            with(binding) {
                val areaAdapter = ArrayAdapter(
                    root.context,
                    android.R.layout.simple_list_item_1,
                    areaList
                )
                selectedArea.setAdapter(areaAdapter)
                selectedArea.doAfterTextChanged {
                    if (it != null) {
                        detailAreaSelectLayout.visibility = View.VISIBLE
                        detailSelectedArea.text = null
                        onSelectArea(it.toString())
                    }
                }
                val sigunguAdapter = ArrayAdapter(
                    root.context,
                    android.R.layout.simple_list_item_1,
                    sigunguList
                )
                detailSelectedArea.setAdapter(sigunguAdapter)
                detailSelectedArea.doAfterTextChanged {
                    if (it != null) {
                        onSelectSigungu(it.toString())
                    }
                }

                btnSearch.setOnClickListener {
                    onClickSearchButton()
                }
            }
        }
    }

    class ListSearchPlaceViewHolder(private val binding: ItemListSearchPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaceModel) {
            with(binding) {
                tvAddr.text = item.placeAddr
                tvName.text = item.placeName

                Glide.with(binding.thumbnailImg.context)
                    .load(item.placeImg)
                    .error(R.drawable.empty_view)
                    .into(thumbnailImg)
                binding.root.setOnClickListener {
                    //onClick.invoke(absoluteAdapterPosition)
                }

                val disabilityList = item.disability

                val disabilityRVAdapter = DisabilityRVAdapter(disabilityList)
                touristBigDisabilityRv.adapter = disabilityRVAdapter
                touristBigDisabilityRv.layoutManager = LinearLayoutManager(
                    root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }
}
