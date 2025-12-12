package com.halimchoukani.kafs.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.halimchoukani.kafs.R // Ensure this import is correct
import com.halimchoukani.kafs.Screen

@Composable
fun LoginScreen(
    navController: NavController
) {
    // State to hold text input values
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary) // White background
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- 1. Logo & Welcome Section ---
        Image(
            painter = painterResource(id = R.drawable.kafes_logo),
            contentDescription = "Kafés Logo",
            modifier = Modifier
                .size(120.dp) // Smaller logo than splash screen
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary // Dark Walnut
        )

        Text(
            text = "Please login to your account",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Greyish text
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // --- 2. Input Fields ---

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon",
                    tint = MaterialTheme.colorScheme.secondary // Copper Icon
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon",
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            trailingIcon = {
                val image = if (passwordVisible) painterResource(id = R.drawable.eye_open) else painterResource(id = R.drawable.visibility_off)
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image,
                        contentDescription = "Toggle Password Visibility",
                        tint = MaterialTheme.colorScheme.secondary)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        // --- 3. Forgot Password ---
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            TextButton(onClick = {  }) {
                Text(
                    text = "Forgot Password?",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        val context = LocalContext.current
        // --- 4. Login Button ---
        Button(
            onClick = {
                Firebase.auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(context,"Login Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.MainScreen.route){
                                popUpTo(0)
                            }
                        }else{
                            Toast.makeText(context,
                                task.exception?.message?:"Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Dark Walnut
                contentColor = MaterialTheme.colorScheme.onPrimary // White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 5. Sign Up Link ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account? ",
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Sign Up",
                color = MaterialTheme.colorScheme.secondary, // Copper
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(route = Screen.SignUp.route)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // KafésTheme { // Uncomment to apply your custom theme
    LoginScreen(
        navController = rememberNavController()
    )
    // }
}