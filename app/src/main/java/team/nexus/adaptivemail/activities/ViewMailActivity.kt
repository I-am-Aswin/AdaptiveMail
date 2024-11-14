package team.nexus.adaptivemail.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                                textAlign = TextAlign.Center
                            )
                        },
                        modifier = Modifier.height(80.dp)
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            // Start SendMailActivity
                            startActivity(Intent(this@ViewMailActivity, SendMailActivity::class.java))
                        },
//                        modifier = Modifier
//                            .padding(16.dp), // Add padding to place button in lower right
////                            .align(Alignment.BottomEnd),
//                        containerColor = Color(0xFF1E3A8A),

                        containerColor = Color(0xFF1E3A8A),
                        modifier = Modifier
                            .padding(16.dp)
                            .height(50.dp)
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Text("Compose Email", color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp))
                    }
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
fun ListListScopeSample(emails: List<Email>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        item {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Center horizontally with padding
            ) {
                items(emails) { email ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // Take full width of the LazyColumn
                            .padding(vertical = 10.dp)
                            .border(1.dp, Color.Black)
                            .padding(16.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .width(300.dp) // Set a fixed width for uniformity
                                .padding(horizontal = 10.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = "Receiver Email: ${email.recevierMail}",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp),
                                maxLines = 1,
                                softWrap = true // Enable text wrapping
                            )
                            Text(
                                text = "Subject: ${email.subject}",
                                modifier = Modifier.padding(bottom = 8.dp),
                                maxLines = 1,
                                softWrap = true
                            )
                            Text(
                                text = "Body: ${email.body}",
                                maxLines = 3, // Limit number of lines if body text is long
                                softWrap = true
                            )
                        }
                    }
                }
            }
        }
    }
}
