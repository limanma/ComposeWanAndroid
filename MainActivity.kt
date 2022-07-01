package com.example.composewanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composewanandroid.article.ArticleScreen
import com.example.composewanandroid.recommend.RecommendScreen
import com.example.composewanandroid.search.SearchScreen
import com.example.composewanandroid.ui.theme.*
import okhttp3.Route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeWanAndroidTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }

    @Preview
    @Composable
    fun HomeScreen(mainViewModel: MainViewModel = viewModel()) {
        Box() {
            mainViewModel.bottomState.find { it.checked }?.let {
                when (it.bottomType) {
                    BottomType.ARTICLE -> ArticleScreen()
                    BottomType.RECOMMEND -> RecommendScreen()
                    else -> SearchScreen()
                }
            }
            BottomNavigation(
                click = { route -> mainViewModel.changeBottomItem(route) },
                modifier = Modifier.align(Alignment.BottomCenter),
                list = mainViewModel.bottomState
            )
        }
    }

    @Composable
    fun BottomNavigation(list: List<BottomItem>, click: (BottomItem) -> Unit, modifier: Modifier = Modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            list.forEach {
                BottomNavigationItem(it, it.tintColor, click, Modifier.weight(1f))
            }
        }
    }

    @Composable
    fun BottomNavigationItem(route: BottomItem, tintColor: Color, click: (BottomItem) -> Unit, modifier: Modifier = Modifier) {
        val interactionSource = remember { MutableInteractionSource() }
        IconButton(
            onClick = {}, enabled = false,
            modifier = modifier
                .background(Ocean6)
                .fillMaxHeight()
        ) {
            Icon(
                imageVector = route.imageVector,
                contentDescription = null,
                tint = tintColor,
                modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                    click(route)
                }
            )
        }
    }
}
