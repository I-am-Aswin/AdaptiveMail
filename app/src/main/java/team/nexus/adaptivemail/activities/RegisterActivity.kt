package team.nexus.adaptivemail.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import team.nexus.adaptivemail.ui.theme.AdaptiveMailTheme
import team.nexus.adaptivemail.data.User
import team.nexus.adaptivemail.data.UserDBHelper
import team.nexus.adaptivemail.R

class RegisterActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDBHelper(this)
        setContent {
            RegistrationScreen(this, databaseHelper)
        }
    }
}

@Composable
fun RegistrationScreen(context: Context, databaseHelper: UserDBHelper) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4FA))
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Optional: Logo or Illustration
        Image(
            painter = painterResource(id = R.drawable.email_register),
            contentDescription = "Signup Logo",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Register",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.9f)
                .background(Color.Transparent)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
            singleLine = true
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.9f)
                .background(Color.Transparent)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
            singleLine = true
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.9f)
                .background(Color.Transparent)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
            singleLine = true
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                    val user = User(
                        id = null,
                        userName = username,
                        email = email,
                        password = password
                    )
                    databaseHelper.insertUser(user)
                    error = "User registered successfully"
                    context.startActivity(Intent(context, LoginActivity::class.java))
                } else {
                    error = "Please fill all fields"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A)),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(0.7f)
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(text = "Register", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Have an account?",
                color = Color.Gray
            )
//            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
            ) {
                Text(text = "Log in", color = Color(0xFF1E3A8A))
//                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
