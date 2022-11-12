package com.planradar.weather.presentation.fragments.adapter
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.databinding.RecyclerItemAllCitiesBinding
import com.planradar.weather.domain.model.City
import com.planradar.weather.listeners.OnItemClickedGeneric
import com.planradar.weather.utils.diffutils.DiffUtilCallBack
import com.planradar.weather.utils.diffutils.ItemsDiffCallback
import com.planradar.weather.utils.diffutils.ViewBindingVH

class AllCityAdapter: RecyclerView.Adapter<ViewBindingVH<RecyclerItemAllCitiesBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingVH<RecyclerItemAllCitiesBinding> =
        ViewBindingVH.create(parent, RecyclerItemAllCitiesBinding::inflate)

    private lateinit var mListener: OnItemClickedGeneric<CachedCity>
    private val callback by lazy { DiffUtilCallBack(this) }
    private val mNewList by lazy { arrayListOf<CachedCity>() }

    companion object {
        const val CITY_MODEL = "city_model"
        const val CITY_INFO = "city_info"
    }
    fun setOnItemClickListener(listener: OnItemClickedGeneric<CachedCity>) { mListener = listener }

    fun submitList(list: List<CachedCity>) { updateListItems(list) }

    private fun updateListItems(list: List<CachedCity>) {
        val diffCallback = ItemsDiffCallback(mNewList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mNewList.clear()
        mNewList.addAll(list)
        diffResult.dispatchUpdatesTo(callback)
        notifyItemRangeChanged(0, list.size)
    }

    override fun onBindViewHolder(holder: ViewBindingVH<RecyclerItemAllCitiesBinding>, position: Int) {
        val city = mNewList[position]
        holder.binding.apply {
            tvCityName.text = city.name
            tvCityName.setOnClickListener { mListener.onItemClick(CITY_MODEL, city, position) }
            ivCityInfo.setOnClickListener { mListener.onItemClick(CITY_INFO, city, position) }
        }
    }

    override fun getItemCount() = mNewList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}