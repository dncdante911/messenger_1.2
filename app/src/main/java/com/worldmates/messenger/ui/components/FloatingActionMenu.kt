// app/src/main/java/com/worldmates/messenger/ui/components/FloatingActionMenu.kt
package com.worldmates.messenger.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.worldmates.messenger.ui.theme.MessengerColors

@Composable
fun FloatingActionMenu(
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        // Sub-actions
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(bottom = 80.dp, end = 8.dp)
            ) {
                FloatingActionItem(
                    icon = Icons.Filled.QrCodeScanner,
                    label = "QR-код",
                    color = MessengerColors.Success,
                    onClick = { /* TODO: Scan QR */ }
                )
                
                FloatingActionItem(
                    icon = Icons.Filled.GroupAdd,
                    label = "Нова група",
                    color = MessengerColors.Tertiary,
                    onClick = { /* TODO: Create group */ }
                )
                
                FloatingActionItem(
                    icon = Icons.Filled.Contacts,
                    label = "Новий контакт",
                    color = MessengerColors.Info,
                    onClick = { /* TODO: Add contact */ }
                )
            }
        }
        
        // Main FAB
        ExtendedFloatingActionButton(
            onClick = { expanded = !expanded },
            icon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.Edit,
                    contentDescription = if (expanded) "Закрити" else "Новий чат"
                )
            },
            text = {
                Text(if (expanded) "Закрити" else "Новий чат")
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun FloatingActionItem(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        // Label
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
        
        // Icon button
        Surface(
            shape = CircleShape,
            color = color,
            contentColor = Color.White,
            tonalElevation = 4.dp,
            modifier = Modifier.size(48.dp),
            onClick = onClick
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}