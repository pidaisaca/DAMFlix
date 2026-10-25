package com.example.damflix.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.damflix.R
import com.example.damflix.ui.components.InputField
import com.example.damflix.ui.viewmodel.MovieViewModel

@Composable
fun MovieFormScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = viewModel(),
) {
    var title by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var isWatched by remember { mutableStateOf(false) }
    var savedMessage by remember { mutableStateOf<String?>(null) }

    val statusWatched = stringResource(id = R.string.status_watched)
    val statusPending = stringResource(id = R.string.status_pending)
    val msgTemplate = stringResource(id = R.string.msg_movie_saved)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.title_register_movie),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                InputField(
                    value = title,
                    onValueChange = {
                        title = it
                        savedMessage = null
                    },
                    label = stringResource(id = R.string.label_movie_title)
                )

                InputField(
                    value = director,
                    onValueChange = {
                        director = it
                        savedMessage = null
                    },
                    label = stringResource(id = R.string.label_movie_director)
                )

                InputField(
                    value = year,
                    onValueChange = {
                        year = it
                        savedMessage = null
                    },
                    label = stringResource(id = R.string.label_release_year),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isWatched,
                        onCheckedChange = { isWatched = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isWatched) {
                            stringResource(id = R.string.label_movie_watched)
                        } else {
                            stringResource(id = R.string.label_movie_pending)
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        val statusStr = if (isWatched) statusWatched else statusPending
                        val yearText = if (year.isNotBlank()) " ($year)" else ""
                        val savedTitle = title
                        val savedDirector = director

                        viewModel.addMovie(
                            title = title,
                            director = director,
                            year = year,
                            isWatched = isWatched,
                            onSaved = {
                                savedMessage = String.format(msgTemplate, savedTitle, savedDirector, yearText, statusStr)
                            }
                        )

                        title = ""
                        director = ""
                        year = ""
                        isWatched = false
                    },
                    enabled = title.isNotBlank() && director.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.btn_save_movie))
                }

                savedMessage?.let { message ->
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieFormPreview() {
    MaterialTheme {
        MovieFormScreen()
    }
}
