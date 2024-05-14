@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.bigcart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bigcart.ui.theme.BigCartTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class EmailActivity : ComponentActivity() {
    var auth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val email = remember { mutableStateOf("") }
            BigCartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF4F5F9)
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 26.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        Intent(applicationContext,AuthActivity::class.java).also {
                                            startActivity(it)
                                        }
                                        finish()
                                    }
                                    .padding(start = 17.dp),
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                            Text(
                                text = "Password Recovery",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(25.dp))
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 84.dp),
                            textAlign = TextAlign.Center,
                            text = "Forgot Password",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 13.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Lorem ipsum dolor sit amet, consetetur \n" +
                                    "sadipscing elitr, sed diam nonumy",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF868889)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Email(email)
                        GradientButton(email)
                    }
                }
            }
        }
    }

    @ExperimentalMaterial3Api
    @Composable
    fun Email(email: MutableState<String>) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp, top = 44.dp),
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red,
                cursorColor = Color.Black
            ),
            leadingIcon = {Icon(imageVector = Icons.Filled.Email, "Email", tint = Color(0xFF868889))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = {
                Text(
                    text = "Email Address",
                    color = Color(0xFF868889),
                    fontWeight = FontWeight.Medium
                )
            },
            value = email.value,
            onValueChange = {
                email.value = it
            }
        )
    }

    @Composable
    fun GradientButton(email: MutableState<String>) {
        Button(
            onClick = {
                Firebase.auth.sendPasswordResetEmail(email.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Check your email.",
                                Toast.LENGTH_LONG,
                            ).show()
                        }
                    }
            },
            modifier = Modifier
                .wrapContentHeight(Alignment.Bottom)
                .padding(start = 16.dp, end = 16.dp, top = 17.dp)
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
                    text = "Send link",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
