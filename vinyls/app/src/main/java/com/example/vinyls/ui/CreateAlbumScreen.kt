package com.example.vinyls.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vinyls.model.AlbumToCreate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlbumScreen(
    navController: NavController,
) {
    val backgroundColor = Color(0xFF1A1625)
    val cardColor = Color(0xFF2A1A3E)
    val purpleAccent = Color(0xFF7B3FF2)
    val borderColor = Color(0xFF4A3A5E)

    // Estados del formulario
    var coverImageUrl by remember { mutableStateOf("") }
    var albumTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var releaseYear by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("Rock") }
    var selectedRecord by remember { mutableStateOf("EMI") }
    var showGenreDropdown by remember { mutableStateOf(false) }
    var showRecordLabelDropdown by remember { mutableStateOf(false) }

    val genres = listOf("Classical", "Salsa", "Rock", "Folk")
    var recordlabels = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Album",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {  navController?.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {

            Text(
                "Cover Image",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = coverImageUrl,
                onValueChange = { coverImageUrl = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "e.g. https://example.com/image.jpg",
                        color = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = purpleAccent,
                    unfocusedBorderColor = borderColor,
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )


            Spacer(modifier = Modifier.height(20.dp))

            // Album Title Field
            Text(
                "Album Title",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = albumTitle,
                onValueChange = { albumTitle = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "e.g., Rumours",
                        color = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = purpleAccent,
                    unfocusedBorderColor = borderColor,
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Description
            Text(
                "Description",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "e.g., Great Album",
                        color = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = purpleAccent,
                    unfocusedBorderColor = borderColor,
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Genre and Release Year Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Genre Dropdown
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Genre",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    ExposedDropdownMenuBox(
                        expanded = showGenreDropdown,
                        onExpandedChange = { showGenreDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = selectedGenre,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = purpleAccent,
                                unfocusedBorderColor = borderColor,
                                focusedContainerColor = cardColor,
                                unfocusedContainerColor = cardColor
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        ExposedDropdownMenu(
                            expanded = showGenreDropdown,
                            onDismissRequest = { showGenreDropdown = false },
                            modifier = Modifier.background(cardColor)
                        ) {
                            genres.forEach { genre ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            genre,
                                            color = Color.White
                                        )
                                    },
                                    onClick = {
                                        selectedGenre = genre
                                        showGenreDropdown = false
                                    },
                                    colors = MenuDefaults.itemColors(
                                        textColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }

                // Release Year Field
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Release Year",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        value = releaseYear,
                        onValueChange = {
                            if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                                releaseYear = it
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "e.g., 1977",
                                color = Color.Gray
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = purpleAccent,
                            unfocusedBorderColor = borderColor,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Record Label Dropdown
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Record Label",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(5.dp))

                ExposedDropdownMenuBox(
                    expanded = showRecordLabelDropdown,
                    onExpandedChange = { showRecordLabelDropdown = it }
                ) {
                    OutlinedTextField(
                        value = selectedRecord,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = purpleAccent,
                            unfocusedBorderColor = borderColor,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    ExposedDropdownMenu(
                        expanded = showRecordLabelDropdown,
                        onDismissRequest = { showRecordLabelDropdown = false },
                        modifier = Modifier.background(cardColor)
                    ) {
                        recordlabels.forEach { record ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        record,
                                        color = Color.White
                                    )
                                },
                                onClick = {
                                    selectedRecord = record
                                    showRecordLabelDropdown = false
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Save Button
            Button(
                onClick = {
                    if (albumTitle.isNotBlank() && selectedRecord.isNotBlank() &&
                        releaseYear.isNotBlank() && coverImageUrl.isNotBlank()) {
                        val album = AlbumToCreate(
                            name = albumTitle,
                            genre = selectedGenre,
                            releaseDate = releaseYear,
                            cover = coverImageUrl,
                            description = description,
                            recordLabel = selectedRecord,
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purpleAccent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                enabled = albumTitle.isNotBlank() && description.isNotBlank() &&
                        releaseYear.isNotBlank() && coverImageUrl.isNotBlank()
            ) {
                Text(
                    "Save Album",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}


// ========================================
// 3. IMAGE URL DIALOG
// ========================================
//@Composable
//fun ImageUrlDialog(
//    currentUrl: String,
//    onDismiss: () -> Unit,
//    onConfirm: (String) -> Unit
//) {
//    var urlText by remember { mutableStateOf(currentUrl) }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                "Enter Image URL",
//                color = Color.White,
//                fontWeight = FontWeight.Bold
//            )
//        },
//        text = {
//            Column {
//                Text(
//                    "Paste the URL of the album cover image:",
//                    color = Color.Gray,
//                    fontSize = 14.sp
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                OutlinedTextField(
//                    value = urlText,
//                    onValueChange = { urlText = it },
//                    modifier = Modifier.fillMaxWidth(),
//                    placeholder = {
//                        Text(
//                            "https://example.com/image.jpg",
//                            color = Color.Gray
//                        )
//                    },
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedTextColor = Color.White,
//                        unfocusedTextColor = Color.White,
//                        focusedBorderColor = Color(0xFF7B3FF2),
//                        unfocusedBorderColor = Color(0xFF4A3A5E)
//                    ),
//                    singleLine = false,
//                    maxLines = 3
//                )
//            }
//        },
//        confirmButton = {
//            TextButton(
//                onClick = { onConfirm(urlText) },
//                enabled = urlText.isNotBlank()
//            ) {
//                Text("Confirm", color = Color(0xFF7B3FF2))
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel", color = Color.Gray)
//            }
//        },
//        containerColor = Color(0xFF2A1A3E),
//        textContentColor = Color.White
//    )
//}