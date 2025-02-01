package com.example.mapd721rajanassignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mapd721rajanassignment1.ui.theme.MAPD721RajanAssignment1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val dataStoreManager by lazy { DataStoreManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val studentName = "Rajan Boudel"
            val studentID = "301365245"

            var id by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var courseName by remember { mutableStateOf("") }
            var storedData by remember { mutableStateOf("") } // This updates only on "Load" click
            val scope = rememberCoroutineScope()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFE3F2FD) // Light Blue Background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Student Info",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0) // Dark Blue Color
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input Fields
                    CustomTextField(value = id, label = "Student ID") { id = it }
                    CustomTextField(value = username, label = "Username") { username = it }
                    CustomTextField(value = courseName, label = "Course Name") { courseName = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons
                    ButtonRow(
                        onSave = {
                            scope.launch {
                                dataStoreManager.saveData(id, username, courseName)
                                id = "" // Clear input fields after storing
                                username = ""
                                courseName = ""
                                storedData = "" // Do not display stored data immediately
                            }
                        },
                        onLoad = {
                            scope.launch {
                                val data = dataStoreManager.loadData()
                                id = data.id
                                username = data.username
                                courseName = data.courseName
                                storedData = "Student Info:\nID: ${data.id}\nUsername: ${data.username}\nCourse: ${data.courseName}"
                            }
                        },
                        onReset = {
                            scope.launch {
                                dataStoreManager.clearData()
                                id = ""
                                username = ""
                                courseName = ""
                                storedData = "" // Clear storedData on reset
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Display Loaded Data ONLY when "Load" is clicked
                    if (storedData.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = storedData,
                                modifier = Modifier.padding(16.dp),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f)) // Push name & ID to bottom

                    // Name & ID at Bottom
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0D47A1), shape = RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = studentName, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = "ID: $studentID", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

// Custom TextField with Styling
@Composable
fun CustomTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    )
}

// Buttons Row
@Composable
fun ButtonRow(onSave: () -> Unit, onLoad: () -> Unit, onReset: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onSave, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF42A5F5))) {
            Text("Store")
        }
        Button(onClick = onLoad, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A))) {
            Text("Load")
        }
        Button(onClick = onReset, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350))) {
            Text("Reset")
        }
    }
}