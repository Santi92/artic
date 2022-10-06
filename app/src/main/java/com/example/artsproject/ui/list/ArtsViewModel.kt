package com.example.artsproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artsproject.data.ArtRepository
import com.example.artsproject.data.dto.Art

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

            val result = repository.getArts(1.toString())

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

    private var currentPage = 2

    fun onLoadNext() {


        if(currentPage > 0){
            viewModelScope.launch{

                _uiState.postValue(
                    _uiState.value!!.copy(isLoadingMoreArt = true, isError = false)
                )

                val result = repository.getArts(currentPage.toString())

                result.fold(
                    onSuccess = {result->

                        if(result.isEmpty()){
                            currentPage = 0
                        }else {
                            currentPage ++
                        }

                        val list = _uiState.value!!.arts

                        _uiState.postValue(
                            _uiState.value!!.copy(isLoadingMoreArt = false, isError = false, arts = list + result )
                        )
                    },
                    onFailure = {
                        _uiState.postValue(
                            ArtsUIState(isLoadingMoreArt = false, isError = true)
                        )
                    }
                )
            }
        }


    }

}