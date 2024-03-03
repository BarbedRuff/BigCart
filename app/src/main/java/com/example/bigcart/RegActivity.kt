package com.example.bigcart

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.bigcart.ui.theme.BigCartTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
class RegActivity : ComponentActivity() {
    var auth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            BigCartTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                )
                {
                    Background()
                    Body(email, password)
                }
            }
        }
    }

    @Composable
    fun Background() {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.reg),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }

    @Composable
    fun Body(email: MutableState<String>, password: MutableState<String>) {
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
                text = "Create account",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp),
                text = "Quickly create account",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF868889)
            )
            Email(email)
            Password(password)
            GradientButton(email, password)
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 39.dp)
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        Intent(applicationContext, LoginActivity::class.java).also {
                            startActivity(it)
                        }
                    },
                horizontalArrangement = Arrangement.Center
            ) {
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
    fun Email(email: MutableState<String>) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp, top = 26.dp),
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    "Email",
                    tint = Color(0xFF868889)
                )
            },
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
    fun Password(password: MutableState<String>) {
        val passwordVisible = remember { mutableStateOf(false) }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp, top = 5.dp),
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red,
                cursorColor = Color(0xFF868889)
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    "Password",
                    tint = Color(0xFF868889)
                )
            },
            placeholder = {
                Text(
                    text = "Password",
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF868889)
                )
            },
            value = password.value,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                password.value = it
            },
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = image, description, tint = Color(0xFF868889))
                }
            }
        )
    }

    @Composable
    fun GradientButton(email: MutableState<String>, password: MutableState<String>) {
        Button(
            onClick = {
                if(email.value != "" && password.value != "") {
                    auth.createUserWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Intent(applicationContext, MarketActivity::class.java).also {
                                    startActivity(it)
                                }
                            } else {
                                Log.d("Meow", "createUserWithEmail:failure", task.exception)
                            }
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
                    text = "Signup",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}