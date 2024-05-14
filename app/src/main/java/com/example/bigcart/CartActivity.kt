package com.example.bigcart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.bigcart.food.Food
import com.example.bigcart.ui.theme.BigCartTheme
import kotlin.math.ceil

class CartActivity : ComponentActivity() {
    private var foods: MutableMap<String, Food>? = null
    private lateinit var cart: SnapshotStateMap<String, Int>
    private var sendedCart = mutableMapOf<String, Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foods = (intent.extras?.getSerializable("foods") as? MutableMap<String, Food>)
        setContent {
            val initialCart =
                intent.extras?.getSerializable("cart") as? MutableMap<String, Int> ?: mutableMapOf()
            cart = remember { mutableStateMapOf(*initialCart.toList().toTypedArray()) }
            sendedCart = initialCart
            BigCartTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color(0xFFF4F5F9)
                ) {
                    if (cart.isEmpty() || foods == null) {
                        Column(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Head()
                            Empty()
                        }
                    } else {
                        Box {
                            Column(modifier=Modifier.padding(bottom = 150.dp)){
                                Head()
                                LazyColumn(
                                    modifier = Modifier.padding(top = 17.dp, start = 17.dp, end=17.dp)
                                ) {
                                    items(ceil(cart.keys.toList().size.toDouble() / 2).toInt()) { i ->
                                        Row {
                                            var foodId = cart.keys.toList()[i * 2]
                                            Food_Card(
                                                foodId,
                                                foods!![foodId]!!.label,
                                                foods!![foodId]!!.img,
                                                foods!![foodId]!!.price,
                                                Modifier.weight(1f)
                                            )
                                            if (i * 2 + 1 < cart.keys.toList().size) {
                                                foodId = cart.keys.toList()[i * 2 + 1]
                                                Spacer(modifier = Modifier.width(17.dp))
                                                Food_Card(
                                                    foodId,
                                                    foods!![foodId]!!.label,
                                                    foods!![foodId]!!.img,
                                                    foods!![foodId]!!.price,
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
                            var totalPrice: Double = 0.toDouble()
                            for(i in cart){
                                totalPrice += (foods!![i.key]?.price ?: 0.toDouble()) * i.value
                            }
                            Column(
                                modifier=Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .align(Alignment.BottomCenter)
                            ){
                                Row(modifier=Modifier.padding(start=17.dp, top=10.dp, end=17.dp)){
                                    Text(
                                        modifier=Modifier.weight(1f),
                                        text="Total",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text="$${String.format("%.2f", totalPrice)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                                GradientButton(
                                    text="Checkout",
                                    {
                                        startActivity(
                                            Intent(Intent.ACTION_VIEW).also{
                                                it.setData(Uri.parse("https://github.com/BarbedRuff"))
                                            }
                                        )
                                    },
                                    Modifier
                                        .wrapContentHeight(Alignment.Bottom)
                                        .padding(start = 16.dp, end = 16.dp, top=16.dp, bottom = 36.dp)
                                        .shadow(
                                            spotColor = Color(0xFF6CC51D),
                                            shape = RoundedCornerShape(5.dp),
                                            elevation = 10.dp
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun finish() {
        val intent = this.intent
        intent.putExtra("cart", HashMap(sendedCart))
        setResult(RESULT_OK, intent)
        super.finish()
    }

    @Composable
    fun Head() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                modifier = Modifier
                    .clickable {
                        finish()
                    }
                    .padding(start = 17.dp, top = 15.dp, bottom = 15.dp),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
            )
            Text(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp),
                text = "Shopping Cart",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(25.dp))
        }
    }

    @Composable
    fun Empty() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 89.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(125.dp),
                imageVector = Icons.Outlined.ShoppingBag,
                contentDescription = "Cart",
                tint = Color(0xFF6CC51D)
            )
            Text(
                modifier = Modifier
                    .padding(top = 36.dp),
                text = "Your cart is empty !",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                modifier = Modifier
                    .padding(top = 16.dp),
                text = "You will get a response within\n" +
                        "a few minutes.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF868889),
                fontWeight = FontWeight.Medium,
            )
        }
        GradientButton(
            text="Start shopping",
            { finish() },
            Modifier
                .wrapContentHeight(Alignment.Bottom)
                .padding(start = 16.dp, end = 16.dp, bottom = 57.dp)
                .shadow(
                    spotColor = Color(0xFF6CC51D),
                    shape = RoundedCornerShape(5.dp),
                    elevation = 10.dp
                )
            )
    }

    @Composable
    fun Food_Card(
        foodId: String,
        label: String,
        image: String?,
        price: Double,
        modifier: Modifier = Modifier
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(Size.ORIGINAL)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp, start = 48.dp, end = 48.dp),
            ) {
                Image(
                    alignment = Alignment.Center,
                    painter = if (painter.state is AsyncImagePainter.State.Error) painterResource(id = R.drawable.cart) else painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
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
                        cart[foodId] = if ((cart[foodId]!! - 1) > 0) cart[foodId]!! - 1 else 0
                        sendedCart[foodId] =
                            if ((sendedCart[foodId]!! - 1) > 0) sendedCart[foodId]!! - 1 else 0
                        if (cart[foodId] == 0)
                            cart.remove(foodId)
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
                IconButton(modifier = Modifier
                    .size(25.dp)
                    .padding(end = 19.dp)
                    .weight(0.25f)
                    .fillMaxSize(), onClick = {
                    cart[foodId] = cart[foodId]!! + 1
                    sendedCart[foodId] = sendedCart[foodId]!! + 1
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

    @Composable
    fun GradientButton(text: String, onClick: () -> Unit, modifier: Modifier) {
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFFAEDC81), Color(0xFF6CC51D))
                        )
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 19.dp)
                        .fillMaxWidth(),
                    text = text,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}