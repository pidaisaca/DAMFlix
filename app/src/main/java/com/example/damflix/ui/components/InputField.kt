package com.example.damflix.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Este metodo se crea ya que se va a repetir varias veces un campo a rellenar en el formulario
// asi no se repiten tantisimas lineas de codigo
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth()
    )
}
