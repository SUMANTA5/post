package com.sumanta.post.repo

import com.sumanta.post.model.Phone
import com.sumanta.post.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiService: ApiService){


    fun getPhone(): Flow<List<Phone>> = flow {
        emit(apiService.getPhone())
    }.flowOn(Dispatchers.IO)

    fun setPhone(
        name: String,
        phoneNo: Long
    ): Flow<Phone> = flow {
        emit(apiService.setPhone(name, phoneNo))
    }.flowOn(Dispatchers.IO)
}