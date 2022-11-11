package com.planradar.weather.listeners

interface OnItemClickedGeneric<T> {
    fun onItemClick(type: String, data: T, position: Int)
}