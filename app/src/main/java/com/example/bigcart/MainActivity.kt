package com.example.bigcart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    private val pageTitle = listOf(
        "Welcome to\nBig Cart",
        "Buy Quality \n" + "Dairy Products",
        "Buy Premium\n" + "Quality Fruits",
        "Get Discounts \n" + "On All Products"
    )
    private val backgroundsList = listOf(
        R.drawable.title1,
        R.drawable.title2,
        R.drawable.title3,
        R.drawable.title4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var auth: FirebaseAuth =  Firebase.auth
        if(auth.currentUser != null) {
            Intent(applicationContext,MarketActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }
        setContent {
            MainPreview()
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun MainPreview() {
        val pagerState = rememberPagerState(pageCount = { 4 })
        BigCartTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                ) { page ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        BackgroundImage(page)
                        IndicatorButton(page, pagerState)
                        MainText(pageNumber = page)
                    }
                }
            }
        }
    }

    @Composable
    fun BackgroundImage(pageNumber: Int) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = backgroundsList[pageNumber]),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }

    @Composable
    fun MainText(
        mediumText: String = "Lorem ipsum dolor sit amet, consetetur \nsadipscing elitr, sed diam nonumy",
        pageNumber: Int,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                pageTitle[pageNumber],
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 70.dp),
                textAlign = TextAlign.Center
            )
            Text(
                mediumText,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 17.dp),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color(0xFF868889)
            )
        }
    }

    @Composable
    fun IndicatorButton(pageNumber: Int, pagerState: PagerState) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Indicator(pageNumber)
            GradientButton(pageNumber, pagerState)
        }
    }

    @Composable
    fun GradientButton(
        pageNumber: Int,
        pagerState: PagerState
    ) {
        val coroutineScope = rememberCoroutineScope()
        Button(
            onClick = {
                if (pageNumber ==  3) {
                    Intent(applicationContext,AuthActivity::class.java).also {
                        startActivity(it)
                    }
                }
                else{
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pageNumber + 1)
                    }
                }
            },
            modifier = Modifier
                .wrapContentHeight(Alignment.Bottom)
                .padding(start = 17.dp, end = 17.dp, bottom = 57.dp)
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
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "Get started",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(vertical = 19.dp)
                )
            }
        }
    }

    @Composable
    fun Indicator(pageNumber: Int) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.Bottom)
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0..3) {
                    if (i == pageNumber) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF6CC51D))
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFDCDCDC))
                        )
                    }
                    if (i < 3) {
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }
        }
    }
}
