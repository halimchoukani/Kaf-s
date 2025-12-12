package com.halimchoukani.kafs.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.halimchoukani.kafs.R
import com.halimchoukani.kafs.data.model.Coffee
import com.halimchoukani.kafs.ui.component.BottomNavigationBar
import com.halimchoukani.kafs.ui.component.LoadImage
import com.halimchoukani.kafs.viewmodel.CoffeeViewModel


@Composable
fun HomeScreen(paddingValues: PaddingValues,userName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSectionModern(userName)
        Spacer(modifier = Modifier.height(12.dp))
        SearchSectionModern()
        Spacer(modifier = Modifier.height(16.dp))
        PromoBannerModern()
        Spacer(modifier = Modifier.height(16.dp))
        CategoryTabsModern()
        Spacer(modifier = Modifier.height(16.dp))
        CoffeeGridModern()
    }
}

@Composable
fun HeaderSectionModern(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hello, $userName 👋",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "What coffee are we brewing today?",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Profile",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SearchSectionModern() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search coffee") },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        )

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
fun PromoBannerModern() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFFFFA726))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = "Special Promo",
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0xFFE55A4F), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Buy One\nGet One FREE",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun CategoryTabsModern() {
    val tabs = listOf("All Coffee", "Machiatto", "Latte", "Americano")
    var selected by remember { mutableStateOf(0) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(tabs.size) { index ->
            val item = tabs[index]
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (index == selected) MaterialTheme.colorScheme.primary
                        else Color(0xFFEDEDED)
                    )
                    .clickable { selected = index }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = item,
                    color = if (index == selected) Color.White else Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
@Composable
fun CoffeeGridModern(viewModel: CoffeeViewModel = viewModel()) {
    val coffees by viewModel.coffees.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    when {
        isLoading -> {
            Text("Loading...", modifier = Modifier.padding(16.dp))
        }

        error != null -> {
            Text(
                text = error ?: "",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {
            LazyVerticalGrid( columns = GridCells.Fixed(2), modifier = Modifier
                .padding(16.dp)
                .height(800.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp) ){


                items(coffees) { coffee ->
                    CoffeeItemModern(coffee)
                }
            }
        }
    }

}

@Composable
fun CoffeeItemModern(item: Coffee) {
    Box(
        modifier = Modifier
            .clickable { }
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Column {
            Box {
//                LoadImage(url=item.imageRes,modifier = Modifier
//                    .height(160.dp)
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)), description = item.name)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.heart), //fav icon
                        contentDescription = "Favorite",
                        tint = Color.Red
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(item.category, fontSize = 12.sp, color = Color.Gray)
            }
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(item.price.toString(), fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
