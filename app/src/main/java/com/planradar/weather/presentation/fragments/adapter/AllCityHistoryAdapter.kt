package com.planradar.weather.presentation.fragments.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.planradar.weather.R
import com.planradar.weather.data.cache.model.CachedCity
import com.planradar.weather.data.cache.model.CachedHistoryCity
import com.planradar.weather.databinding.RecyclerItemAllCitiesBinding
import com.planradar.weather.databinding.RecyclerItemAllHistoriesBinding
import com.planradar.weather.listeners.OnItemClickedGeneric
import com.planradar.weather.utils.DateHelper
import com.planradar.weather.utils.diffutils.DiffUtilCallBack
import com.planradar.weather.utils.diffutils.ItemsDiffCallback
import com.planradar.weather.utils.diffutils.ViewBindingVH

class AllCityHistoryAdapter: RecyclerView.Adapter<ViewBindingVH<RecyclerItemAllHistoriesBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingVH<RecyclerItemAllHistoriesBinding> =
        ViewBindingVH.create(parent, RecyclerItemAllHistoriesBinding::inflate)

    private lateinit var mListener: OnItemClickedGeneric<CachedHistoryCity>
    private val callback by lazy { DiffUtilCallBack(this) }
    private val mNewList by lazy { arrayListOf<CachedHistoryCity>() }

    companion object {
        const val CITY_HISTORY_MODEL = "city_history_model"
    }
    fun setOnItemClickListener(listener: OnItemClickedGeneric<CachedHistoryCity>) { mListener = listener }

    fun submitList(list: List<CachedHistoryCity>) { updateListItems(list) }

    private fun updateListItems(list: List<CachedHistoryCity>) {
        val diffCallback = ItemsDiffCallback(mNewList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mNewList.clear()
        mNewList.addAll(list)
        diffResult.dispatchUpdatesTo(callback)
        notifyItemRangeChanged(0, list.size)
    }

    override fun onBindViewHolder(holder: ViewBindingVH<RecyclerItemAllHistoriesBinding>, position: Int) {
        val city = mNewList[position]
        holder.binding.apply {
            tvCityHistory.text = DateHelper.getDateFormatted(city.date)
            tvCityStatus.text = String.format(
                tvCityStatus.context.getString(R.string.weather_history_adapter),
                city.weather_main,
                "${city.main_temp}"
            )
            Glide.with(ivWeatherIcon.context)
                .load("https://openweathermap.org/img/w/${city.weather_icon}.png")
                .into(ivWeatherIcon)

            this.root.setOnClickListener { mListener.onItemClick(CITY_HISTORY_MODEL, city, position) }
        }
    }

    override fun getItemCount() = mNewList.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}