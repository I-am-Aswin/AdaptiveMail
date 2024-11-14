package team.nexus.adaptivemail.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import team.nexus.adaptivemail.data.Email
import team.nexus.adaptivemail.data.EmailDatabaseHelper

class ViewMailActivity : ComponentActivity() {
    private lateinit var emailDatabaseHelper: EmailDatabaseHelper

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emailDatabaseHelper = EmailDatabaseHelper(this)
        setContent {
            Spacer(modifier = Modifier.height(32.dp))
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color(0xFFadbef4)
                        ),
                        title = {
                            Text(
                                text = "View Mails",
                                fontSize = 32.sp,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        modifier = Modifier.height(80.dp)
                    )
                }
            ) { paddingValues ->
                // Applying padding values to the content below the top bar
                Box(modifier = Modifier.padding(paddingValues)) {
                    val data = emailDatabaseHelper.getAllEmails()
                    Log.d("swathi", data.toString())
                    ListListScopeSample(data)
                }
            }
        }
    }
}

@Composable
fun ListListScopeSample(email: List<Email>) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            LazyColumn (
                modifier = Modifier.fillMaxWidth()
            ) {
                items(email) { email ->
                    // Adding black border and transparent background for each email
                    Column(
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                start = 16.dp,
                                bottom = 20.dp,
                                end = 16.dp // Add padding to the end (right side)
                            )

                            .border(1.dp, Color.Black) // Adding black border around each item
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Receiver_Mail: ${email.recevierMail}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Subject: ${email.subject}",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Body: ${email.body}",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
