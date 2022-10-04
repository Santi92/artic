package com.example.artsproject.ui.list

import com.example.artsproject.data.dto.Art

class ArtsUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val arts: List<Art> = listOf(),
)