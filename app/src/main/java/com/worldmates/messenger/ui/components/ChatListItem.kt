// app/src/main/java/com/worldmates/messenger/ui/components/ChatListItem.kt
package com.worldmates.messenger.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.worldmates.messenger.data.model.Chat
import com.worldmates.messenger.ui.theme.MessengerColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListItem(
    chat: Chat,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val dateFormatter = remember {
        SimpleDateFormat("HH:mm", Locale.getDefault())
    }
    
    val lastMessageTime = chat.lastMessage?.let {
        dateFormatter.format(Date(it.timeStamp * 1000))
    } ?: ""
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                if (isSelected) MessengerColors.Primary.copy(alpha = 0.1f) 
                else Color.Transparent
            ),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Аватар с индикаторами
            ChatAvatar(
                imageUrl = chat.avatarUrl,
                name = chat.username ?: "",
                isOnline = chat.lastActivity != null && (System.currentTimeMillis() / 1000 - chat.lastActivity!!) < 300,
                isGroup = chat.isGroup,
                unreadCount = chat.unreadCount,
                modifier = Modifier.size(56.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Информация о чате
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = chat.username ?: "Unknown",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Text(
                        text = lastMessageTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Текст последнего сообщения или статус
                    val lastMessageText = if (chat.lastMessage != null) {
                        when {
                            chat.lastMessage!!.mediaUrl != null -> {
                                val icon = when {
                                    chat.lastMessage!!.mediaType?.contains("image") == true -> "🖼️"
                                    chat.lastMessage!!.mediaType?.contains("video") == true -> "🎬"
                                    chat.lastMessage!!.mediaType?.contains("audio") == true -> "🎵"
                                    else -> "📎"
                                }
                                "$icon Медіа"
                            }
                            chat.lastMessage!!.decryptedText?.isNotEmpty() == true -> 
                                chat.lastMessage!!.decryptedText!!
                            else -> "Немає повідомлень"
                        }
                    } else {
                        "Немає повідомлень"
                    }
                    
                    Text(
                        text = lastMessageText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Индикаторы
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Непрочитанные сообщения
                        if (chat.unreadCount > 0) {
                            Surface(
                                shape = CircleShape,
                                color = MessengerColors.Primary,
                                modifier = Modifier.size(20.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (chat.unreadCount > 99) "99+" else chat.unreadCount.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MessengerColors.OnPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        
                        // Закрепленный чат
                        if (chat.pinnedMessageId != null) {
                            Icon(
                                Icons.Filled.PushPin,
                                contentDescription = "Закріплено",
                                tint = MessengerColors.Warning,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatAvatar(
    imageUrl: String?,
    name: String,
    isOnline: Boolean = false,
    isGroup: Boolean = false,
    unreadCount: Int = 0,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Основной аватар
        Surface(
            shape = CircleShape,
            color = if (isGroup) MessengerColors.Tertiary.copy(alpha = 0.2f) 
                   else MessengerColors.Primary.copy(alpha = 0.2f),
            border = BorderStroke(
                width = 2.dp,
                color = if (isOnline) MessengerColors.Success 
                       else MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (imageUrl != null && imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Инициалы
                    val initials = name.split(" ")
                        .take(2)
                        .map { it.firstOrNull()?.uppercaseChar() ?: '?' }
                        .joinToString("")
                    
                    Text(
                        text = if (isGroup) "👥" else initials,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isGroup) MessengerColors.Tertiary 
                               else MessengerColors.Primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Иконка группы поверх
                if (isGroup) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(MessengerColors.Tertiary)
                            .padding(3.dp)
                    ) {
                        Icon(
                            Icons.Filled.Groups,
                            contentDescription = "Група",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
        
        // Индикатор онлайн в правом нижнем углу
        if (isOnline && !isGroup) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MessengerColors.Success)
                )
            }
        }
    }
}