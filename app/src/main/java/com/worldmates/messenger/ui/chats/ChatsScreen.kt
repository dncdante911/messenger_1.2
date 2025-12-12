// app/src/main/java/com/worldmates/messenger/ui/chats/ChatsScreen.kt
package com.worldmates.messenger.ui.chats

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.worldmates.messenger.data.model.Chat
import com.worldmates.messenger.ui.components.ChatListItem
import com.worldmates.messenger.ui.components.PullToRefreshIndicator
import com.worldmates.messenger.ui.components.SearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    onChatClick: (Chat) -> Unit = {},
    onGroupClick: (com.worldmates.messenger.data.model.Group) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel = viewModel()
) {
    val chats by viewModel.chatList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }
    
    // Filter chats based on search
    val filteredChats = remember(chats, searchQuery) {
        if (searchQuery.isBlank()) {
            chats
        } else {
            chats.filter {
                it.username?.contains(searchQuery, ignoreCase = true) == true ||
                        it.lastMessage?.decryptedText?.contains(searchQuery, ignoreCase = true) == true
            }
        }
    }
    
    // Separate pinned and regular chats
    val (pinnedChats, regularChats) = remember(filteredChats) {
        filteredChats.partition { it.pinnedMessageId != null }
    }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading && chats.isEmpty()) {
                // Loading state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Завантаження чатів...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else if (error != null && chats.isEmpty()) {
                // Error state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Filled.ErrorOutline,
                        contentDescription = "Помилка",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        error ?: "Помилка завантаження",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.fetchChats() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Спробувати знову")
                    }
                }
            } else {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    // Search bar
                    item {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { searchQuery = it },
                            onSearch = { /* TODO: Implement search */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    
                    // Pinned chats section
                    if (pinnedChats.isNotEmpty()) {
                        item {
                            SectionHeader(
                                title = "Закріплені",
                                count = pinnedChats.size,
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                            )
                        }
                        
                        items(pinnedChats, key = { it.id }) { chat ->
                            ChatListItem(
                                chat = chat,
                                onClick = { onChatClick(chat) },
                                isSelected = false,
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                        
                        // Regular chats section
                        item {
                            SectionHeader(
                                title = "Всі чати",
                                count = regularChats.size,
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                            )
                        }
                    }
                    
                    // Regular chats
                    items(regularChats, key = { it.id }) { chat ->
                        ChatListItem(
                            chat = chat,
                            onClick = { onChatClick(chat) },
                            isSelected = false,
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                    
                    // Empty state
                    if (filteredChats.isEmpty() && searchQuery.isNotBlank()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 48.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Filled.SearchOff,
                                    contentDescription = "Нічого не знайдено",
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "Нічого не знайдено",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "Спробуйте інший запит",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    } else if (filteredChats.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 48.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Filled.Forum,
                                    contentDescription = "Немає чатів",
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "Немає чатів",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "Почніть нову розмову",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                    
                    // Bottom padding for FAB
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
            
            // Pull to refresh indicator
            if (isRefreshing) {
                PullToRefreshIndicator(
                    isRefreshing = isRefreshing,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ) {
            Text(
                count.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}