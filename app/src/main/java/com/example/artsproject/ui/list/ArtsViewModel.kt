package com.example.artsproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artsproject.data.ArtRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtsViewModel @Inject constructor(
    private val repository: ArtRepository
) : ViewModel() {


    private val _uiState = MutableLiveData<ArtsUIState>()
    val uiState: LiveData<ArtsUIState>
        get() = _uiState

    fun onLoad() {

        viewModelScope.launch{

            _uiState.postValue(
                ArtsUIState(isLoading = true, isError = false, arts = listOf())
            )

            val result = repository.getArts()

            result.fold(
                onSuccess = {
                    _uiState.postValue(
                        ArtsUIState(isLoading = false, arts = it)
                    )
                },
                onFailure = {
                    _uiState.postValue(
                        ArtsUIState(isLoading = false, isError = true)
                    )
                }
            )
        }
    }

}