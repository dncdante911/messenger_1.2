// app/src/main/java/com/worldmates/messenger/ui/profile/ProfileScreen.kt
package com.worldmates.messenger.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.worldmates.messenger.R
import com.worldmates.messenger.ui.theme.MessengerColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Профіль",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Filled.Settings, "Налаштування")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile header with gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                MessengerColors.GradientStart,
                                MessengerColors.GradientEnd
                            )
                        )
                    )
            ) {
                // Profile avatar
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.Center)
                        .offset(y = 40.dp),
                    shadowElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(R.drawable.ic_profile_placeholder),
                            contentDescription = "Аватар",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        // Edit button
                        Surface(
                            shape = CircleShape,
                            color = MessengerColors.Primary,
                            onClick = { /* TODO: Edit profile */ },
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Filled.Edit,
                                    "Редагувати",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(60.dp))
            
            // User info
            Text(
                "John Doe",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                "@johndoe",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Status
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MessengerColors.OnlineGreen.copy(alpha = 0.1f),
                border = BorderStroke(1.dp, MessengerColors.OnlineGreen.copy(alpha = 0.3f)),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MessengerColors.OnlineGreen)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "В мережі",
                        style = MaterialTheme.typography.labelMedium,
                        color = MessengerColors.OnlineGreen
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Profile actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProfileActionItem(
                    icon = Icons.Filled.QrCode,
                    title = "Мій QR-код",
                    subtitle = "Поділитися контактом"
                )
                
                ProfileActionItem(
                    icon = Icons.Filled.Star,
                    title = "Обране",
                    subtitle = "Збережені повідомлення"
                )
                
                ProfileActionItem(
                    icon = Icons.Filled.Devices,
                    title = "Пристрої",
                    subtitle = "Активні сесії"
                )
                
                ProfileActionItem(
                    icon = Icons.Filled.Security,
                    title = "Конфіденційність",
                    subtitle = "Блокування, останній раз в мережі"
                )
                
                ProfileActionItem(
                    icon = Icons.Filled.Help,
                    title = "Допомога",
                    subtitle = "Питання та підтримка"
                )
            }
        }
    }
}

@Composable
fun ProfileActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Surface(
        onClick = onClick ?: {},
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}