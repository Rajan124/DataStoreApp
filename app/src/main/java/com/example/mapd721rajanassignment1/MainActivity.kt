package com.example.mapd721rajanassignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.*
import com.example.mapd721rajanassignment1.ui.theme.MAPD721RajanAssignment1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val dataStoreManager by lazy { DataStoreManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var id by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var courseName by remember { mutableStateOf("") }
            val studentName = "Rajan Boudel"
            val studentID = "301365245"

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Student Info", style = MaterialTheme.typography.headlineMedium)
                Text("Name: $studentName")
                Text("Student ID: $studentID")

                Spacer(modifier = Modifier.height(16.dp))

                // Input Fields
                BasicTextField(value = id, onValueChange = { id = it }, modifier = Modifier.fillMaxWidth())
                BasicTextField(value = username, onValueChange = { username = it }, modifier = Modifier.fillMaxWidth())
                BasicTextField(value = courseName, onValueChange = { courseName = it }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Button(onClick = {
                    lifecycleScope.launch {
                        dataStoreManager.saveData(id, username, courseName)
                    }
                }) {
                    Text("Store")
                }

                Button(onClick = {
                    lifecycleScope.launch {
                        val data = dataStoreManager.loadData()
                        id = data.id
                        username = data.username
                        courseName = data.courseName
                    }
                }) {
                    Text("Load")
                }

                Button(onClick = {
                    lifecycleScope.launch {
                        dataStoreManager.clearData()
                        id = ""
                        username = ""
                        courseName = ""
                    }
                }) {
                    Text("Reset")
                }
            }
        }
    }
}