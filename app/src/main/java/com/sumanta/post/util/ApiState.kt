package com.sumanta.post.util

import com.sumanta.post.model.Phone

sealed class ApiState {
    class Success(val data: List<Phone>) : ApiState()
    object Loading : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Empty : ApiState()

}
