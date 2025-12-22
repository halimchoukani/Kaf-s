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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.halimchoukani.kafs.R
import com.halimchoukani.kafs.Screen
import com.halimchoukani.kafs.data.model.Coffee
import com.halimchoukani.kafs.ui.component.LoadImage
import com.halimchoukani.kafs.viewmodel.CoffeeViewModel
import com.halimchoukani.kafs.viewmodel.UserViewModel


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    userName: String,
    viewModel: CoffeeViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    onLogout: () -> Unit = {},
    navController: NavController
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val coffees by viewModel.filteredCoffees.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSectionModern(userName, onLogout = {
            userViewModel.logout(onLogout)
        }, navController = navController)
        Spacer(modifier = Modifier.height(12.dp))
        SearchSectionModern(searchQuery, viewModel::onSearchQueryChange)
        Spacer(modifier = Modifier.height(16.dp))
        PromoBannerModern()
        Spacer(modifier = Modifier.height(16.dp))
        CategoryTabsModern(coffees,isLoading,error,selectedCategory, viewModel::onCategorySelect)
        Spacer(modifier = Modifier.height(16.dp))
        CoffeeGridModern(coffees,isLoading,error, navController, userViewModel)
    }
}

@Composable
fun HeaderSectionModern(userName: String, onLogout: () -> Unit, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Logout",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {
                    navController.navigate(Screen.Profile.route)
                }){
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }

            }
        }
    }
}

@Composable
fun SearchSectionModern(query: String, onQueryChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
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
fun CategoryTabsModern(coffees:List<Coffee>,isLoading: Boolean, error:String?,selectedCategory: String, onCategorySelect: (String) -> Unit) {

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
            val categorySet = coffees.map { it.category }.toSet()
            val tabs = listOf("All Coffee") + categorySet.toList()
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(tabs.size) { index ->
                    val item = tabs[index]
                    val isSelected = item == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else Color(0xFFEDEDED)
                            )
                            .clickable { onCategorySelect(item) }
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = item,
                            color = if (isSelected) Color.White else Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

}
@Composable
fun CoffeeGridModern(coffees:List<Coffee>,isLoading: Boolean, error:String?, navController: NavController, userViewModel: UserViewModel) {

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
                    CoffeeItem(coffee, navController, userViewModel)
                }
            }
        }
    }

}

@Composable
fun CoffeeItem(item: Coffee, navController: NavController, userViewModel: UserViewModel) {
    val isFavorite = userViewModel.isFavorite(item.id)

    Box(
        modifier = Modifier
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { 
                navController.navigate(Screen.CoffeeDetail.passCoffeeId(item.id))
            }
    ) {
        Column {
            // Image with favorite icon
            Box {
                LoadImage(
                    url = item.imageRes,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    description = item.name
                )

                // Favorite icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(bottomStart = 12.dp))
                        .background(Color.White)
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                        userViewModel.toggleFavorite(item)
                    } ) {
                        Icon(
                        painter = if (isFavorite) painterResource(R.drawable.heartfil) else painterResource(R.drawable.heart),
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    }

                }
            }

            // Coffee details
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.category,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Price and add button
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${item.price} TND",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
