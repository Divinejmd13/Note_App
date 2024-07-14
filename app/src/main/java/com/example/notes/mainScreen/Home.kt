package com.example.notes.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.notes.R
import com.example.notes.database.Note
import com.example.notes.database.NoteViewModel
import com.example.notes.signin.UserData

class Home {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun NotesScreen( userData: UserData?, viewModel: NoteViewModel,onlogout:()->Unit) {

        val notes by viewModel.notes.collectAsState()
        var expanded by remember { mutableStateOf(false) }
        var profileExpand by remember { mutableStateOf(false) }

        var grid by remember { mutableStateOf(false) }

        var noteOpen by remember { mutableStateOf(false) }
        var noteBeingEdited by remember { mutableStateOf<Note?>(null) }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { },
                    containerColor = Color(0xFF6FC0F1),
                    contentColor = Color.Black

                ) {

                    IconButton(onClick = { noteOpen = !noteOpen }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Localized description",

                            )
                    }

                }
            },
            topBar = {
                CenterAlignedTopAppBar(

                    navigationIcon = {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF6FC0F1),
                                contentColor = Color.Black
                            )
                        ) {
                            IconButton(onClick = {
                                profileExpand=true
                            }) {
                                if (userData?.profilePictureUrl != null) {
                                    AsyncImage(
                                        model = userData.profilePictureUrl,
                                        contentDescription = "Profile picture",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                DropdownMenu(
                                    expanded = profileExpand,
                                    onDismissRequest = { profileExpand = false }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 10.dp, horizontal = 15.dp)
                                            .clip(shape = RoundedCornerShape(3.dp))
                                    ) {
                                        if (userData != null) {
                                            userData.username?.let {
                                                Text(
                                                    text = it,
                                                    color = Color(0xff1a1b2d),
                                                    lineHeight = 1.6.em,
                                                    style = TextStyle(
                                                        fontSize = 18.sp
                                                    ),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(5.dp))
                                        if (userData != null) {
                                            userData.email?.let {
                                                Text(
                                                    text = it,
                                                    color = Color(0xff535763),
                                                    lineHeight = 1.em,
                                                    style = TextStyle(
                                                        fontSize = 15.sp,
                                                        fontWeight = FontWeight.Medium
                                                    ),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                )
                                            }
                                        }
                                    }
                                    DropdownMenuItem(
                                        text = { Text("Logout") },
                                        onClick = {
                                            onlogout()
                                            profileExpand = false
                                        }
                                    )

                                }
                            }
                        }
                    },

                    actions = {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF6FC0F1),
                                contentColor = Color.Black
                            )
                        ) {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More"
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("List") },
                                    onClick = {
                                        grid = false
                                        expanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Grid") },
                                    onClick = {
                                        grid = true
                                        expanded = false
                                    }
                                )
                            }
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.LightGray,
                    ),
                    title = {

                        Text(
                            modifier = Modifier.padding(15.dp),
                            text = "Notes",
                            color = Color(0xFF000000),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic
                        )

                    }
                )
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF), // Start color
                                Color(0xFF2F92CF) // End color
                            )
                        )
                    )
            ) {

                if (noteOpen) {
                    OpenNote(
                        onDismissRequest = { noteOpen = !noteOpen },
                        onSaveNote = { title, content ->
                            if (noteBeingEdited == null) {
                                viewModel.insert(
                                    Note(
                                        title = title,
                                        content = content
                                    )
                                )

                            } else {
                                viewModel.update(
                                    noteBeingEdited!!.copy(
                                        title = title,
                                        content = content
                                    )
                                )
                                noteBeingEdited = null

                            }
                            noteOpen = false
                        },
                        noteBeingEdited?.title ?: "",
                        noteBeingEdited?.content ?: "",
                    )
                }
                if (notes.isEmpty()) {
                    Text(
                        text = "Please Add the Note ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)

                            .offset(y = 0.dp, x = (0).dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .offset(y = 150.dp, x = -100.dp)
                            .size(200.dp)
                    )

                } else {
                    if (grid) {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Adaptive(200.dp),
                            verticalItemSpacing = 4.dp,
                            horizontalArrangement = Arrangement.spacedBy(4.dp), contentPadding = it,
//                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(notes) { note ->
                                NoteCard(note = note, onClick = {
                                    noteBeingEdited = note
                                    noteOpen = !noteOpen
                                }, onDelete = { viewModel.delete(note) })

                            }
                        }
                    } else {
                        LazyColumn(
                        ) {
                            items(notes) { note ->
                                NoteCard(note = note, onClick = {
                                    noteBeingEdited = note
                                    noteOpen = !noteOpen
                                }, onDelete = { viewModel.delete(note) })

                            }
                        }
                    }

                }


            }
        }


    }
}