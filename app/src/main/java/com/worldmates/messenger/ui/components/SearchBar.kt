// app/src/main/java/com/worldmates/messenger/ui/components/SearchBar.kt
package com.worldmates.messenger.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.worldmates.messenger.ui.theme.MessengerColors

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit = {},
    placeholder: String = "Пошук чатів та користувачів",
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var isActive by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = if (isActive) MaterialTheme.colorScheme.surface
               else MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = if (isActive) 2.dp else 0.dp,
        shadowElevation = if (isActive) 4.dp else 0.dp,
        onClick = {
            isActive = true
            focusRequester.requestFocus()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Search icon
            Icon(
                Icons.Default.Search,
                contentDescription = "Пошук",
                tint = if (isActive) MaterialTheme.colorScheme.primary
                      else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Text field
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(query)
                        keyboardController?.hide()
                    }
                ),
                textStyle = MaterialTheme.typography.bodyMedium
            )
            
            // Clear button (when there's text)
            AnimatedVisibility(
                visible = query.isNotEmpty(),
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                IconButton(
                    onClick = {
                        onQueryChange("")
                        focusRequester.requestFocus()
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Очистити",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            
            // Filter button (optional)
            if (isActive && query.isEmpty()) {
                IconButton(
                    onClick = { /* TODO: Open filters */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = "Фільтри",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
    
    // Handle focus changes
    LaunchedEffect(isActive) {
        if (isActive) {
            focusRequester.requestFocus()
        }
    }
}