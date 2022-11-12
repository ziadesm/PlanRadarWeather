package com.planradar.weather.data.mapper

interface EntityMapper<T, R> {
    fun mapFromEntity(entity: T): R
    fun mapToEntity(domain: R): T
}