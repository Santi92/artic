package com.example.artsproject.ui.list

import com.example.artsproject.data.dto.Art

data class ArtsUIState(
    val isLoadingMoreArt: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val arts: List<Art> = listOf(),
)