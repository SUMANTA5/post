package com.sumanta.post.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumanta.post.repo.MainRepository
import com.sumanta.post.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository): ViewModel() {

    private val apiStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val apiState: StateFlow<ApiState> = apiStateFlow


    fun getPhone() = viewModelScope.launch {
        mainRepository.getPhone()
            .onStart {
                apiStateFlow.value = ApiState.Loading
            }.catch { e->
                apiStateFlow.value = ApiState.Failure(e)
            }.collect { data->
            apiStateFlow.value = ApiState.Success(data)

            }
    }


    fun setPhone(
        name: String,
        phoneNo: Long
    ) = mainRepository.setPhone(name, phoneNo)

    fun delete(
        userId: Int
    )= mainRepository.delete(userId)
}