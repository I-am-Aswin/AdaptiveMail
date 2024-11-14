package team.nexus.adaptivemail.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.nexus.adaptivemail.data.Email
import team.nexus.adaptivemail.data.EmailDatabaseHelper

class SendMailActivity : ComponentActivity() {
    private lateinit var databaseHelper: EmailDatabaseHelper
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = EmailDatabaseHelper(this)
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
                                text = "Send Mail",
                                fontSize = 32.sp,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                                textAlign = TextAlign.Center
                            )
                        },
                        modifier = Modifier.height(80.dp)
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    OpenEmailer(this, databaseHelper)
                }
            }
        }
    }
}

@Composable
fun OpenEmailer(context: BoxScope, databaseHelper: EmailDatabaseHelper) {

    var recevierMail by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 55.dp, bottom = 25.dp, start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Receiver Email-Id",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = recevierMail,
            onValueChange = { recevierMail = it },
            label = { Text(text = "Email address") },
            placeholder = { Text(text = "abc@gmail.com") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Black) // Added black border
                .background(Color.Transparent), // Transparent background
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Mail Subject",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text(text = "Subject") },
            placeholder = { Text(text = "Subject") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Black) // Added black border
                .background(Color.Transparent), // Transparent background
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Mail Body",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = body,
            onValueChange = { body = it },
            label = { Text(text = "Body") },
            placeholder = { Text(text = "Body") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(200.dp) // Increase the height to make it larger
                .border(1.dp, Color.Black) // Added black border
                .background(Color.Transparent), // Transparent background
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = false
        )


        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (recevierMail.isNotEmpty() && subject.isNotEmpty() && body.isNotEmpty()) {
                    val email = Email(
                        id = null,
                        senderMail = null,
                        recevierMail = recevierMail,
                        subject = subject,
                        body = body
                    )
                    databaseHelper.insertEmail(email)
                    error = "Mail Saved"
                    scope.launch {
                        delay(1500)
                        ctx.startActivity(Intent(ctx, ViewMailActivity::class.java))
                    }
                } else {
                    error = "Please fill all fields"
                }

//                if (recevierMail.isNotEmpty() && subject.isNotEmpty() && body.isNotEmpty()) {
//                    val email = Email(
//                        id = null,
//                        senderMail = null,
//                        recevierMail = recevierMail,
//                        subject = subject,
//                        body = body
//                    )
//                    databaseHelper.insertEmail(email)
//
//                    // Show the snackbar message
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Mail Sent Successfully!")
//                        delay(1500) // Delay before redirecting
//                        ctx.startActivity(Intent(ctx, ViewMailActivity::class.java))
//                    }
//                } else {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Please fill all fields")
//                    }
//                }

//                val i = Intent(Intent.ACTION_SEND)
//                val emailAddress = arrayOf(recevierMail)
//                i.putExtra(Intent.EXTRA_EMAIL, emailAddress)
//                i.putExtra(Intent.EXTRA_SUBJECT, subject)
//                i.putExtra(Intent.EXTRA_TEXT, body)
//
//                i.setType("message/rfc822")
//                ctx.startActivity(Intent.createChooser(i, "Choose an Email client : "))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFd3e5ef))
        ) {
            Text(
                text = "Send Email",
                modifier = Modifier.padding(10.dp),
                color = Color.Black,
                fontSize = 15.sp
            )
        }

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
