package com.example.goat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth

class StartActivity : ComponentActivity() {
    // Firebase
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.White) {
                // 로그인 시도
                viewModel.tryLogin(this)

                // ...

                Column {
                    Text(text = "로그인 확인중", fontSize = 30.sp)

                    Button(onClick = {
                        // 여기에 로그인 버튼을 눌렀을 때 실행될 로직을 추가합니다.
                        if (auth.currentUser != null) {
                            // 이미 로그인되어 있으면 MainActivity로 이동
                            startActivity(Intent(this@StartActivity, MainActivity::class.java))
                        } else {
                            // 로그인되어 있지 않으면 LoginActivity로 이동
                            startActivity(Intent(this@StartActivity, LoginActivity::class.java))
                        }
                    }) {
                        Text(text = "로그인 버튼")
                    }
                }
            }
        }
    }
}
