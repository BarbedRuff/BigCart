package com.example.bigcart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bigcart.ui.theme.BigCartTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class MarketActivity : ComponentActivity() {
    var auth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var auth: FirebaseAuth = Firebase.auth
        setContent {
            BigCartTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color.White
                ) {
                    val pagerState = rememberPagerState(pageCount = { 3 })
                    val coroutineScope = rememberCoroutineScope()
                    HorizontalPager(
                        modifier = Modifier
                            .wrapContentHeight(align=Alignment.Top),
                        state = pagerState
                    ) {
                        when (it) {
                            0 -> {Home()}
                            1 -> {Text(text = "Person")}
                            2 -> {Text(text = "Favourite")}
                        }
                    }
                    Row(
                        modifier = Modifier
                            .wrapContentHeight(Alignment.Bottom)
                            .fillMaxWidth()
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        CustomIconButton(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Home",
                            paddingStart = 10.dp,
                            index = 0,
                            tab = pagerState,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage( 0)
                                }
                            }
                        )
                        CustomIconButton(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Person",
                            index = 1,
                            tab = pagerState,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage( 1)
                                }
                            }
                        )
                        CustomIconButton(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "<3",
                            index = 2,
                            tab = pagerState,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage( 2)
                                }
                            }
                        )
                        CustomIconButton(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Cart",
                            index = 3,
                            paddingEnd = 10.dp,
                            tab = pagerState,
                            onClick = {
                                startActivity(
                                    Intent(this@MarketActivity, CartActivity::class.java)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    @Composable
    fun CustomIconButton(
        imageVector: ImageVector,
        contentDescription: String,
        tab: PagerState,
        index: Int,
        paddingStart: Dp = 0.dp,
        paddingEnd: Dp = 0.dp,
        onClick: () -> Unit
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(60.dp)
                .padding(start = paddingStart, end = paddingEnd)
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = if (index == tab.currentPage) Color.Black else Color(0xFF868889)
            )
        }
    }

    @Composable
    fun Home(){
        Column(

        ){
            Search()
            Poster()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Search(){
        var text = remember { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp, top = 14.dp),
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFF4F5F9),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red,
                cursorColor = Color.Black
            ),
            leadingIcon = {Icon(imageVector = Icons.Filled.Search, "Search", tint = Color(0xFF868889))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = {
                Text(
                    text = "Search keywords..",
                    color = Color(0xFF868889),
                    fontWeight = FontWeight.Medium
                )
            },
            value = text.value,
            onValueChange = { text.value = it }
        )
    }

    @Composable
    fun Poster(){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 17.dp, end = 17.dp),
            painter = painterResource(id = R.drawable.poster),
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            contentDescription = "poster"
        )
    }
}

