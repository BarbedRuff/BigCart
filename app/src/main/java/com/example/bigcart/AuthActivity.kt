package com.example.bigcart

import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bigcart.ui.theme.BigCartTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigCartTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                )
                {
                    Background()
                    Body()
                }
            }
        }
    }

    @Composable
    fun Background(){
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }

    @Composable
    fun Body() {
        Column(
            modifier = Modifier
                .wrapContentHeight(Alignment.Bottom)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(Color(0xFFF4F5F9))
        )
        {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 31.dp),
                text = "Welcome",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp),
                text = "Lorem ipsum dolor sit amet, consetetur \n" +
                        "sadipscing elitr, sed diam nonumy",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF868889)
            )
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 27.dp)
                    .background(Color.White, RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
            ){
                Image(
                    modifier = Modifier
                        .padding(top = 19.dp, bottom = 19.dp, start = 33.dp)
                        .width(22.dp)
                        .height(22.dp),
                    painter = painterResource(id = R.drawable.g),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                        .fillMaxWidth(),
                    text = "Continue with google",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }
            GradientButton()
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 39.dp)
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        Intent(applicationContext,LoginActivity::class.java).also {
                            startActivity(it)
                        }
                    },
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    "Already have an account ? ",
                    fontSize = 17.sp,
                    color = Color(0xFF868889),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Login",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    @Composable
    fun GradientButton() {
        Button(
            onClick = {
                Intent(applicationContext,RegActivity::class.java).also {
                    startActivity(it)
                }
            },
            modifier = Modifier
                .wrapContentHeight(Alignment.Bottom)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
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
                Image(
                    modifier = Modifier
                        .padding(top = 19.dp, bottom = 19.dp, start = 33.dp)
                        .width(26.dp)
                        .height(26.dp),
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 19.dp)
                        .fillMaxWidth(),
                    text = "Create an account",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
