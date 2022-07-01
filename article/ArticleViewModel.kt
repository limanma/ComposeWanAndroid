package com.example.composewanandroid.article

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composewanandroid.model.Article
import com.example.composewanandroid.net.RetrofitManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import java.util.Collections.addAll

const val TAG = "ComposeWanAndroid"

data class UiState(
    val articleList: List<Article>,
    var refreshing: Boolean,
    var loading: Boolean,
    var index: Int,
)

class ArticleViewModel : ViewModel() {

    val uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState(mutableListOf(), refreshing = false, loading = false, index = 0))

    init {
        refresh()
        Log.i(TAG, "ArticleViewModel init")
    }

    fun refresh() {
        if (uiState.value.refreshing) return
        uiState.update {
            it.copy(articleList = it.articleList, loading = false, refreshing = true, index = 0)
        }
        Log.i(TAG, "refresh index:${uiState.value.index}")
        viewModelScope.launch {
            try {
                val articleResp = RetrofitManager.api().articleResp(uiState.value.index)
                uiState.update {
                    UiState(articleList = articleResp.data.datas, refreshing = false, loading = false, index = 0)
                }
            } catch (e: Exception) {
                Log.i(TAG, e.message.toString())
            }
        }
    }

    fun loadMore() {
        if (uiState.value.loading) return
        uiState.update {
            it.copy(articleList = it.articleList, loading = true, refreshing = false)
        }
        Log.i(TAG, "loadMore index:${uiState.value.index + 1}")
        viewModelScope.launch {
            val articleResp = RetrofitManager.api().articleResp(uiState.value.index + 1)
            val list = mutableListOf<Article>()
            list.addAll(uiState.value.articleList)
            list.addAll(articleResp.data.datas)
            uiState.update {
                UiState(articleList = list, refreshing = false, loading = false, index = uiState.value.index + 1)
            }
        }
    }

}