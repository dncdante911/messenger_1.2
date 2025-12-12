// app/src/main/java/com/worldmates/messenger/ui/components/NavigationBar.kt
package com.worldmates.messenger.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.worldmates.messenger.ui.theme.MessengerColors

sealed class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
) {
    object Chats : NavigationItem("chats", "Чати", Icons.Filled.Chat, 3)
    object Groups : NavigationItem("groups", "Групи", Icons.Filled.Groups, 1)
    object Calls : NavigationItem("calls", "Дзвінки", Icons.Filled.Call)
    object Status : NavigationItem("status", "Статуси", Icons.Filled.Visibility)
    object Settings : NavigationItem("settings", "Налаштування", Icons.Filled.Settings)
}

@Composable
fun MessengerBottomNavigation(
    currentRoute: String,
    onNavigationSelected: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem.Chats,
        NavigationItem.Groups,
        NavigationItem.Calls,
        NavigationItem.Status,
        NavigationItem.Settings
    )
    
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigationSelected(item) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount > 0) {
                                Badge {
                                    Text(
                                        text = if (item.badgeCount > 99) "99+" 
                                               else item.badgeCount.toString(),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MessengerColors.Primary,
                    selectedTextColor = MessengerColors.Primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MessengerColors.Primary.copy(alpha = 0.1f)
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}