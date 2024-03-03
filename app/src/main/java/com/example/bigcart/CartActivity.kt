package com.example.bigcart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bigcart.ui.theme.BigCartTheme

class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigCartTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color(0xFFF4F5F9)
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        Intent(applicationContext,MarketActivity::class.java).also {
                                            startActivity(it)
                                        }
                                    }
                                    .padding(start = 17.dp, top = 31.dp, bottom = 31.dp),
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 31.dp, bottom = 31.dp),
                                text = "Shopping Cart",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(25.dp))
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 89.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Icon(
                                modifier = Modifier
                                    .size(125.dp),
                                imageVector = Icons.Outlined.ShoppingCart,
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
                        GradientButton()
                    }
                }
            }
        }
    }

    @Composable
    fun GradientButton() {
        Button(
            onClick = {
                Intent(applicationContext,MarketActivity::class.java).also {
                    startActivity(it)
                }
            },
            modifier = Modifier
                .wrapContentHeight(Alignment.Bottom)
                .padding(start = 16.dp, end = 16.dp, bottom = 57.dp)
                .shadow(
                    spotColor = Color(0xFF6CC51D),
                    shape = RoundedCornerShape(5.dp),
                    elevation = 10.dp
                ),
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
                    ),
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 19.dp)
                        .fillMaxWidth(),
                    text = "Start shopping",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}