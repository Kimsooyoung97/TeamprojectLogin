package com.example.goat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goat.ui.theme.GOATTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var contentColor by remember { mutableStateOf(Color.Black) }
            var backgroundColor by remember { mutableStateOf(Color.White) }

            GOATTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = backgroundColor
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val user = Firebase.auth.currentUser
                    val context = LocalContext.current
                    val auth: FirebaseAuth = FirebaseAuth.getInstance()
                    var name: String? by remember { mutableStateOf("") }
                    var email: String? by remember { mutableStateOf("") }
                    var uid: String? by remember { mutableStateOf("") }

                    user?.let {
                        name = it.displayName
                        email = it.email
                        uid = it.uid
                    }
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        scrimColor = backgroundColor,
                        drawerContent = {
                            ModalDrawerSheet(
                            ) {
                                Text(
                                    text = "개인 페이지",
                                    color = contentColor,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(12.dp)
                                )
                                Divider()
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier
                                            .padding(20.dp, 30.dp)
                                            .align(
                                                Alignment.CenterStart
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AccountCircle,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(80.dp)
                                                .align(Alignment.CenterVertically)
                                        )
                                        Spacer(modifier = Modifier.size(10.dp, 1.dp))
                                    }
                                }
                                NavigationDrawerItem(label = {
                                    Text(
                                        text = "▶  스케줄 관리", fontWeight = FontWeight.Bold
                                    )
                                },
                                    selected = false,
                                    onClick = { scope.launch { drawerState.close() } })
                            }
                        }) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CenterAlignedTopAppBar(title = {

                                Text(
                                    text = "GOAT", maxLines = 1, overflow = TextOverflow.Ellipsis
                                )


                            }, navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.apply { if (isClosed) open() else close() } } }) {
                                    Icon(
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = "Profile",
                                        modifier = Modifier.size(45.dp)
                                    )
                                }
                            })
                            NavigationBar(modifier = Modifier.align(Alignment.BottomCenter)) {
                                NavigationBarItem(selected = false, onClick = { }, icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Home, contentDescription = null
                                    )
                                }, label = { Text(text = "Home") })

                                NavigationBarItem(selected = false, onClick = { }, icon = {
                                    Icon(
                                        imageVector = Icons.Filled.MailOutline,
                                        contentDescription = null
                                    )
                                }, label = { Text(text = "Message") })
                            }
                            Column(modifier = Modifier.align(Alignment.Center)) {
                                Text(text = "$name 선생님")
                                Text(text = "이메일: $email")
                                Text(text = "사용자 UID: $uid")
                                Button(onClick = {val myIntent = Intent(context, StartActivity::class.java)
                                    context.startActivity(myIntent)
                                    auth.signOut()
                                }) {
                                    Text(text = "로그아웃 버튼")
                                }

//        auth.signOut()
//        Toast.makeText(this.auth.currentUser?.uid.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}

