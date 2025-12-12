// app/src/main/java/com/worldmates/messenger/ui/components/MessageBubble.kt
package com.worldmates.messenger.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.worldmates.messenger.data.model.Message
import com.worldmates.messenger.ui.theme.MessengerColors
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MessageBubble(
    message: Message,
    isFromMe: Boolean,
    onLongPress: (Message) -> Unit,
    onClick: (Message) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val bubbleColor = if (isFromMe) {
        MessengerColors.MessageBubbleMe
    } else {
        MessengerColors.MessageBubbleOther
    }
    
    val textColor = if (isFromMe) {
        MessengerColors.OnPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    
    val timeFormatter = remember {
        SimpleDateFormat("HH:mm", Locale.getDefault())
    }
    
    val messageTime = timeFormatter.format(Date(message.timeStamp * 1000))
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start
    ) {
        // Bubble with shadow
        Surface(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isFromMe) 16.dp else 4.dp,
                        bottomEnd = if (isFromMe) 4.dp else 16.dp
                    ),
                    clip = false
                )
                .clickable { onClick(message) }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress(message) }
                    )
                },
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isFromMe) 16.dp else 4.dp,
                bottomEnd = if (isFromMe) 4.dp else 16.dp
            ),
            color = bubbleColor,
            tonalElevation = 1.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Reply preview (if any)
                message.replyToText?.let { replyText ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(24.dp)
                                    .background(MessengerColors.PrimaryVariant)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = replyText,
                                style = MaterialTheme.typography.bodySmall,
                                color = textColor.copy(alpha = 0.8f),
                                maxLines = 2,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                
                // Sender name for groups
                if (message.senderName != null && !isFromMe) {
                    Text(
                        text = message.senderName,
                        style = MaterialTheme.typography.labelMedium,
                        color = textColor.copy(alpha = 0.8f),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                
                // Media content
                when {
                    message.mediaUrl != null -> {
                        MediaMessageContent(
                            mediaUrl = message.mediaUrl,
                            mediaType = message.mediaType,
                            duration = message.mediaDuration,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    message.text.contains("http") && message.text.matches(Regex(".*\\.(jpg|jpeg|png|gif|mp4|mov|avi|mp3|wav)$")) -> {
                        // Auto-detected media link
                        MediaMessageContent(
                            mediaUrl = message.text,
                            mediaType = null,
                            duration = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                
                // Text content
                Text(
                    text = message.decryptedText ?: message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Status row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Edited indicator
                    if (message.isEdited) {
                        Text(
                            text = "ред.",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.6f),
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    
                    // Time
                    Text(
                        text = messageTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = textColor.copy(alpha = 0.6f)
                    )
                    
                    // Read status (only for my messages)
                    if (isFromMe) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (message.isRead) Icons.Filled.DoneAll 
                                        else Icons.Filled.Done,
                            contentDescription = "Статус прочитання",
                            tint = if (message.isRead) MessengerColors.Info 
                                  else textColor.copy(alpha = 0.6f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MediaMessageContent(
    mediaUrl: String,
    mediaType: String?,
    duration: Long?,
    modifier: Modifier = Modifier
) {
    val isImage = mediaType?.contains("image") == true || 
                  mediaUrl.matches(Regex(".*\\.(jpg|jpeg|png|gif|webp)$"))
    val isVideo = mediaType?.contains("video") == true || 
                  mediaUrl.matches(Regex(".*\\.(mp4|mov|avi|webm)$"))
    val isAudio = mediaType?.contains("audio") == true || 
                  mediaUrl.matches(Regex(".*\\.(mp3|wav|ogg|m4a)$"))
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        when {
            isImage -> {
                AsyncImage(
                    model = mediaUrl,
                    contentDescription = "Зображення",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                // Image overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.1f))
                )
            }
            
            isVideo -> {
                Box(contentAlignment = Alignment.Center) {
                    // Video thumbnail placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .background(Color.Black.copy(alpha = 0.2f))
                    )
                    
                    // Play button
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = "Відтворити",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    
                    // Duration
                    duration?.let {
                        Text(
                            text = formatDuration(it),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            
            isAudio -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MessengerColors.Primary,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Filled.MusicNote,
                                contentDescription = "Аудіо",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Голосове повідомлення",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                        duration?.let {
                            Text(
                                text = formatDuration(it),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = "Відтворити",
                        tint = MessengerColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            else -> {
                // Generic file
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.InsertDriveFile,
                        contentDescription = "Файл",
                        tint = MessengerColors.Primary,
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = mediaUrl.substringAfterLast("/"),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Файл",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Icon(
                        Icons.Filled.Download,
                        contentDescription = "Завантажити",
                        tint = MessengerColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

private fun formatDuration(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}