package com.example.composewanandroid.article

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composewanandroid.model.Article
import com.example.composewanandroid.ui.theme.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope


@Composable
fun ArticleScreen(articleViewModel: ArticleViewModel = viewModel()) {
    val uiState = articleViewModel.uiState.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(uiState.value.refreshing),
        onRefresh = { articleViewModel.refresh() }) {
        ArticleList(uiState.value, loadMore = { articleViewModel.loadMore() })
    }
}


@Composable
fun ArticleItem(article: Article, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(8.dp), color = Ocean0)
            .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            .height(100.dp)
    ) {
        Text(text = article.title, style = MaterialTheme.typography.subtitle2, maxLines = 1)
        if (article.desc.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = article.desc, style = MaterialTheme.typography.body2, maxLines = 2)
        }
        Spacer(modifier = Modifier.height(60.dp))
        Row {
            Text(
                text = "${article.author.ifEmpty { article.shareUser }} · ${article.niceShareDate}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun ArticleList(uiState: UiState, loadMore: suspend CoroutineScope.() -> Unit, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val lastItemAchieve: Boolean by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    if (lastItemAchieve) {
        LaunchedEffect(key1 = Unit, block = loadMore)
        Log.i(TAG, "到底啦")
    }
    LazyColumn(modifier = modifier.background(MaterialTheme.colors.background), state = listState) {
        items(uiState.articleList, key = { item -> item.id }) {
            ArticleItem(it)
            Spacer(modifier = Modifier.height(4.dp))
        }
//        item {
//            LaunchedEffect(key1 = Unit, block = loadMore)
//        }
    }
}

@Preview
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        article = Article(
            title = "哈哈哈哈哈哈哈哈哈哈哈哈",
            author = "哈哈哈",
            niceShareDate = "哈哈哈",
            desc = "哈哈哈",
            id = 1,
            link = "aa",
            shareUser = "哈哈哈"
        )
    )
}