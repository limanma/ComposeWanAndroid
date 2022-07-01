package com.example.composewanandroid

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.composewanandroid.ui.theme.Neutral0
import com.example.composewanandroid.ui.theme.Neutral2

enum class BottomType {
    ARTICLE, RECOMMEND, SEARCH
}

class BottomItem(
    val bottomType: BottomType,
    val imageVector: ImageVector,
    initialChecked: Boolean = false,
    initialColor: Color = Neutral2
) {
    var checked: Boolean by mutableStateOf(initialChecked)
    var tintColor: Color by mutableStateOf(initialColor)
}


class MainViewModel : ViewModel() {
    private val _bottomState =
        mutableListOf(
            BottomItem(
                bottomType = BottomType.ARTICLE,
                imageVector = Icons.Default.List,
                initialColor = Neutral0,
                initialChecked = true
            ),
            BottomItem(bottomType = BottomType.RECOMMEND, imageVector = Icons.Default.Send),
            BottomItem(bottomType = BottomType.SEARCH, imageVector = Icons.Default.Search),
        ).toMutableStateList()

    val bottomState: List<BottomItem>
        get() = _bottomState

    fun changeBottomItem(selectRoute: BottomItem) {
        _bottomState.forEach {
            it.checked = it.bottomType == selectRoute.bottomType
            it.tintColor = if (it.checked) Neutral0 else Neutral2
        }
    }
}
