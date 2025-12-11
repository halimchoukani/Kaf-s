package com.halimchoukani.kafs.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.halimchoukani.kafs.R
import com.halimchoukani.kafs.ui.component.BottomNavigationBar


@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSection()
        SearchSection()
        PromoBanner()
        CategoryTabs()
        CoffeeGrid()
    }
}

@Composable
fun HeaderSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hello, Alex",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun SearchSection() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search coffee") },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(14.dp)),
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
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun PromoBanner() {
    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {

        Text(
            text = "Promo",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(12.dp)
                .background(
                    Color(0xFFE55A4F),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )

        Text(
            text = "Buy one get\none FREE",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}
@Composable
fun CategoryTabs() {
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
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (index == selected) MaterialTheme.colorScheme.primary
                        else Color(0xFFEDEDED)
                    )
                    .clickable { selected = index }
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Text(
                    text = item,
                    color = if (index == selected) Color.White else Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

}
data class Coffee(
    val name: String,
    val type: String,
    val price: String,
    val img: Int
)

private val coffees = listOf(
    Coffee("Caffe Mocha", "Deep Foam", "$ 4.53", R.drawable.homefill),
    Coffee("Caffe Mocha", "Deep Foam", "$ 4.53", R.drawable.homefill),
    Coffee("Flat White", "Espresso", "$ 3.53", R.drawable.homefill)
)

@Composable
fun CoffeeGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .height(800.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(coffees) { item ->
            CoffeeItem(item)
        }
    }
}

@Composable
fun CoffeeItem(item: Coffee) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(item.img),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(12.dp)) {
            Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(item.type, fontSize = 12.sp, color = Color.Gray)
        }

        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(item.price, fontWeight = FontWeight.Bold)

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text("+", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}


