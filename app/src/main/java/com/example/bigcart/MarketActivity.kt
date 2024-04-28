package com.example.bigcart

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.bigcart.favourite.FavouriteViewModel
import com.example.bigcart.food.Food
import com.example.bigcart.food.FoodViewModel
import com.example.bigcart.ui.theme.BigCartTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
class MarketActivity : ComponentActivity() {
    var auth: FirebaseAuth = Firebase.auth
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foodView: FoodViewModel by this.viewModels()
        val favouriteView: FavouriteViewModel by this.viewModels()
        token = this.resources.getString(R.string.token)
        setContent {
            foodView.fetchFood(token!!)
            favouriteView.fetchFavourite(auth.uid.toString(), token!!)
            val foods by foodView.food.observeAsState()
            val favourite by favouriteView.favourite.observeAsState()
            BigCartTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color(0xfff7f8fb)
                ) {
                    val pagerState = rememberPagerState(pageCount = { 3 })
                    val coroutineScope = rememberCoroutineScope()
                    HorizontalPager(
                        modifier = Modifier
                            .wrapContentHeight(align=Alignment.Top),
                        state = pagerState
                    ) {
                        when (it) {
                            0 -> {Home(foods)}
                            1 -> {Profile(auth)}
                            2 -> {Favourite(foods, favourite)}
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
    fun Profile(auth: FirebaseAuth) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F5F9))
        ){
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFF4F5F9))
            ){
                Box(
                    modifier = Modifier
                        .height(108.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
//                if(auth.currentUser.photoUrl != )
                Image(
                    modifier = Modifier
                        .padding(top = 33.dp)
                        .align(Alignment.Center)
                        .size(114.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "profile",
                )
            }
        }
//        Button(onClick = {
//            auth.signOut()
//            Intent(applicationContext,MainActivity::class.java).also {
//                startActivity(it)
//            }
//            finish()
//        }) {
//            Text(text="Exit")
//        }
    }

    @Composable
    fun CustomIconButton(
        imageVector: ImageVector,
        contentDescription: String,
        tab: PagerState,
        index: Int,
        paddingStart: Dp = 0.dp,
        paddingEnd: Dp = 0.dp,
        onClick: () -> Unit,
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
                tint = if (index == tab.currentPage) Color(0xFF6CC51D) else Color(0xFF868889)
            )
        }
    }

    @Composable
    fun Home(foods: Map<String, Food>?) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Search()
            Column(
                modifier = Modifier
                    .padding(top = 10.dp, start = 17.dp, end = 17.dp, bottom = 46.dp)
                    .fillMaxSize()
            ){
                if (foods == null) {
                    Poster()
                    LoadingGif()
                } else {
                    LazyColumn {
                        items(1){
                            Poster()
                        }
                        val foodArr = foods!!.values.toList()
                        items(ceil(foodArr!!.size.toDouble() / 2).toInt()) { i ->
                            Row{
                                Food_Card(
                                    foodArr[i * 2].label,
                                    foodArr[i * 2].img,
                                    Modifier.weight(1f)
                                )
                                if (i * 2 + 1 < foodArr.size) {
                                    Spacer(modifier = Modifier.width(17.dp))
                                    Food_Card(
                                        foodArr[i * 2 + 1].label,
                                        foodArr[i * 2 + 1].img,
                                        Modifier.weight(1f)
                                    )
                                }
                                else{
                                    Spacer(modifier = Modifier.width(17.dp))
                                    Box(
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(17.dp))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Food_Card(label: String, image: String?, modifier: Modifier = Modifier) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(coil.size.Size.ORIGINAL)
                .crossfade(true)
                .build()
        )
        Card(
            modifier = modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 21.dp, start = 48.dp, end = 48.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = if (painter.state is AsyncImagePainter.State.Error) painterResource(id = R.drawable.cart) else painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start=10.dp, end=10.dp),
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun Favourite(foods: Map<String, Food>?, favourite: Set<String>?){
        Column(
            modifier = Modifier
                .padding(top=17.dp, bottom=63.dp)
                .fillMaxSize()
        ) {
            if (foods == null || favourite == null) {
                LoadingGif()
            } else {
                LazyColumn(
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 46.dp)
                ) {
                    val iter = favourite.iterator()
                    items(ceil(favourite!!.size.toDouble() / 2).toInt()) { i ->
                        var food_id = iter.next()
                        Row {
                            Food_Card(
                                foods[food_id]!!.label,
                                foods[food_id]!!.img,
                                Modifier.weight(1f)
                            )
                            if (iter.hasNext()) {
                                food_id = iter.next()
                                Spacer(modifier = Modifier.width(17.dp))
                                Food_Card(
                                    foods[food_id]!!.label,
                                    foods[food_id]!!.img,
                                    Modifier.weight(1f)
                                )
                            }
                            else{
                                Spacer(modifier = Modifier.width(17.dp))
                                Box(
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(17.dp))
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Search(){
        val text = remember { mutableStateOf("") }
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
                .padding(bottom = 17.dp),
            painter = painterResource(id = R.drawable.poster),
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            contentDescription = "poster"
        )
    }

    @Composable
    fun LoadingGif(){
        val imageLoader = ImageLoader.Builder(this)
            .components{
                if(Build.VERSION.SDK_INT >= 28){
                    add(ImageDecoderDecoder.Factory())
                }
                else{
                    add(GifDecoder.Factory())
                }
            }
            .build()
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(this).data(R.drawable.loading).build(), imageLoader=imageLoader
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(60.dp),
            painter=painter,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

