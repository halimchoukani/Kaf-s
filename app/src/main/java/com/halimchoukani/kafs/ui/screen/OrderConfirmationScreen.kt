package com.halimchoukani.kafs.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.halimchoukani.kafs.BottomBarScreen
import com.halimchoukani.kafs.data.model.Order
import com.halimchoukani.kafs.viewmodel.UserViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val userState by userViewModel.user
    val context = LocalContext.current
    
    var address by remember { mutableStateOf(userState?.address ?: "") }
    var paymentMethod by remember { mutableStateOf("Cash on Delivery") }
    var isPlacingOrder by remember { mutableStateOf(false) }

    val cartItems = userState?.cart ?: emptyList()
    val subtotal = cartItems.sumOf { it.coffee.price * it.quantity }
    val total = subtotal + (subtotal * 0.05)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirm Order", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Address Section
            Text(text = "Shipping Address", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your delivery address") },
                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            // Payment Section
            Text(text = "Payment Method", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    listOf("Cash on Delivery", "Credit Card", "Mobile Payment").forEach { method ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = paymentMethod == method,
                                onClick = { paymentMethod = method },
                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.secondary)
                            )
                            Text(text = method, fontSize = 15.sp)
                        }
                    }
                }
            }

            // Order Summary Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Order Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    
                    OrderSummaryRow(label = "Items (${cartItems.size})", value = String.format("%.2f TND", subtotal))
                    OrderSummaryRow(label = "Tax (5%)", value = String.format("%.2f TND", subtotal * 0.05))
                    OrderSummaryRow(label = "Delivery Fee", value = "5.00 TND")
                    
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    OrderSummaryRow(label = "Total Amount", value = String.format("%.2f TND", total + 5), isTotal = true)
                }
            }

            Button(
                onClick = {
                    if (address.isEmpty()) {
                        Toast.makeText(context, "Please provide a shipping address", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    
                    isPlacingOrder = true
                    val order = Order(
                        id = UUID.randomUUID().toString(),
                        userId = userState?.id ?: "",
                        items = cartItems,
                        totalPrice = total + 5,
                        address = address,
                        paymentMethod = paymentMethod,
                        createdAt = Date()
                    )

                    userViewModel.placeOrder(
                        order = order,
                        onSuccess = {
                            isPlacingOrder = false
                            Toast.makeText(context, "Order Confirmed!", Toast.LENGTH_LONG).show()
                            navController.navigate(BottomBarScreen.Home.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        onFail = {
                            isPlacingOrder = false
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                enabled = !isPlacingOrder && cartItems.isNotEmpty()
            ) {
                if (isPlacingOrder) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Confirm Order", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun OrderSummaryRow(label: String, value: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = if (isTotal) 18.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = if (isTotal) MaterialTheme.colorScheme.primary else Color.Gray
        )
        Text(
            text = value,
            fontSize = if (isTotal) 20.sp else 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isTotal) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
        )
    }
}
