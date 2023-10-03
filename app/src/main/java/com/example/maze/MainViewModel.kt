package com.example.maze

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    val mazeFlow = MutableStateFlow(Maze(ROWS, COLUMNS))

    companion object {
        private const val ROWS = 20
        private const val COLUMNS = 20
    }
}