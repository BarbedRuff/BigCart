package com.example.bigcart

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import kotlin.math.ceil


@Suppress("DEPRECATION")
@OptIn(ExperimentalFoundationApi::class)
class MarketActivity : ComponentActivity() {
    private var auth: FirebaseAuth = Firebase.auth
    private var token: String? = null
    private lateinit var cart: MutableMap<String, Int>
    private val requestC = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foodView: FoodViewModel by this.viewModels()
        val favouriteView: FavouriteViewModel by this.viewModels()
        token = this.resources.getString(R.string.token)
        setContent {
            cart = remember { mutableStateMapOf() }
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
                            .wrapContentHeight(align = Alignment.Top),
                        state = pagerState
                    ) {
                        when (it) {
                            0 -> {
                                Home(foods, favourite, favouriteView)
                            }

                            1 -> {
                                Profile(auth)
                            }

                            2 -> {
                                Favourite(foods, favourite, favouriteView)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .wrapContentHeight(Alignment.Bottom)
                            .fillMaxWidth()
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomIconButton(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Home",
                            paddingStart = 10.dp,
                            index = 0,
                            tab = pagerState,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0)
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
                                    pagerState.animateScrollToPage(1)
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
                                    pagerState.animateScrollToPage(2)
                                }
                            }
                        )
                        CustomIconButton(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = "Cart",
                            index = 3,
                            paddingEnd = 10.dp,
                            tab = pagerState,
                            onClick = {
                                startActivityForResult(
                                    Intent(this@MarketActivity, CartActivity::class.java)
                                        .also {
                                            it.putExtra("cart", HashMap(cart))
                                            it.putExtra(
                                                "foods",
                                                if (foods != null) HashMap(foods) else null
                                            )
                                        },
                                    requestC
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TestMePls", "Exit1")
        if (resultCode == RESULT_OK) {
            Log.d("TestMePls", "Exit")
            when (requestCode) {
                requestC -> {
                    var newCart =
                        (data!!.extras?.getSerializable("cart") as? MutableMap<String, Int>)!!
                    for (i in newCart) {
                        if (i.value == 0) {
                            cart.remove(i.key)
                        } else {
                            cart[i.key] = i.value
                        }
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
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFF4F5F9))
            ) {
                Box(
                    modifier = Modifier
                        .height(108.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
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
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    auth.signOut()
                    Intent(applicationContext, MainActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }) {
                Text(text = "Exit")
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
    fun Home(
        foods: Map<String, Food>?,
        favourite: MutableSet<String>?,
        favoriteView: FavouriteViewModel
    ) {
        val text = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Search(text)
            Column(
                modifier = Modifier
                    .padding(top = 10.dp, start = 17.dp, end = 17.dp, bottom = 46.dp)
                    .fillMaxSize()
            ) {
                if (foods == null || favourite == null) {
                    Poster()
                    LoadingGif()
                } else {
                    LazyColumn {
                        items(1) {
                            Poster()
                        }
                        val keyArr = foods.keys.toList().filter{ foods[it]?.label?.contains(text.value, true) ?: false }
                        items(ceil(keyArr.size.toDouble() / 2).toInt()) { i ->
                            Row {
                                Food_Card(
                                    keyArr[i * 2],
                                    foods[keyArr[i * 2]]!!.label,
                                    foods[keyArr[i * 2]]!!.img,
                                    foods[keyArr[i * 2]]!!.price,
                                    favoriteView,
                                    Modifier.weight(1f)
                                )
                                if (i * 2 + 1 < keyArr.size) {
                                    Spacer(modifier = Modifier.width(17.dp))
                                    Food_Card(
                                        keyArr[i * 2 + 1],
                                        foods[keyArr[i * 2 + 1]]!!.label,
                                        foods[keyArr[i * 2 + 1]]!!.img,
                                        foods[keyArr[i * 2 + 1]]!!.price,
                                        favoriteView,
                                        Modifier.weight(1f)
                                    )
                                } else {
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
    fun Food_Card(
        foodId: String,
        label: String,
        image: String?,
        price: Double,
        favoriteView: FavouriteViewModel,
        modifier: Modifier = Modifier
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(coil.size.Size.ORIGINAL)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.cart),
        )
        Card(
            modifier = modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 9.dp, end = 9.dp)
                    .size(25.dp),
                onClick = {
                    if (favoriteView.contains(foodId)) {
                        favoriteView.removeFavourite(foodId, token!!)
                    } else {
                        favoriteView.addFavourite(foodId, auth.uid.toString(), token!!)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = if (favoriteView.contains(foodId)) Color(0xFF6CC51D) else Color(
                        0xFF868889
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp),
            ) {
                Image(
                    alignment = Alignment.Center,
                    painter = if (painter.state is AsyncImagePainter.State.Error) painterResource(id = R.drawable.cart) else painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = "$$price",
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Color(0xFF6CC51D),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .background(Color(0xFFEBEBEB))
                    .fillMaxWidth()
                    .height(1.dp)
            )
            if (cart[foodId] == null || cart[foodId] == 0) {
                Button(
                    onClick = {
                        cart[foodId] = 1
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    ),
                    shape = RectangleShape
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp)
                                .padding(end = 9.dp),
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = null,
                            tint = Color(0xFF6CC51D)
                        )
                        Text(
                            text = "Add to cart",
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 11.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(25.dp)
                            .padding(start = 19.dp)
                            .weight(0.25f)
                            .fillMaxSize(),
                        onClick = {
                            cart[foodId] = cart[foodId]!! - 1
                            if (cart[foodId] == 0) {
                                cart.remove(foodId)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = null,
                            tint = Color(0xFF6CC51D)
                        )
                    }
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = cart[foodId].toString(),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    IconButton(
                        modifier = Modifier
                            .size(25.dp)
                            .padding(end = 19.dp)
                            .weight(0.25f)
                            .fillMaxSize(),
                        onClick = {
                            cart[foodId] = cart[foodId]!! + 1
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = Color(0xFF6CC51D)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Favourite(
        foods: Map<String, Food>?,
        favourite: MutableSet<String>?,
        favoriteView: FavouriteViewModel
    ) {
        Column(
            modifier = Modifier
                .padding(top = 17.dp)
                .fillMaxSize()
        ) {
            if (foods == null || favourite == null) {
                LoadingGif()
            } else {
                LazyColumn(
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 46.dp)
                ) {
                    val favouriteList = favourite.toList()
                    items(ceil(favourite.size.toDouble() / 2).toInt()) { i ->
                        Row {
                            val foodId = favouriteList[i * 2]
                            Food_Card(
                                foodId,
                                foods[foodId]!!.label,
                                foods[foodId]!!.img,
                                foods[foodId]!!.price,
                                favoriteView,
                                Modifier.weight(1f),
                            )
                            if (i * 2 + 1 < favouriteList.size) {
                                val foodId = favouriteList[i * 2 + 1]
                                Spacer(modifier = Modifier.width(17.dp))
                                Food_Card(
                                    foodId,
                                    foods[foodId]!!.label,
                                    foods[foodId]!!.img,
                                    foods[foodId]!!.price,
                                    favoriteView,
                                    Modifier.weight(1f)
                                )
                            } else {
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
    fun Search(text: MutableState<String>) {
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    "Search",
                    tint = Color(0xFF868889)
                )
            },
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
    fun Poster() {
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
    fun LoadingGif() {
        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(this).data(R.drawable.loading).build(), imageLoader = imageLoader
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(60.dp),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

