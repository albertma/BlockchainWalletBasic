package albert.ma.blockchain.walletbasic.ui;


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import kotlin.Unit;

@Composable
fun MyCreateWalletDialog( onDismiss: () -> Unit) {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Input your data")

                // Input field 1
                TextField(
                        value = text1,
                        onValueChange = { text1 = it },
                        label = { Text("Input 1") }
                )

                // Input field 2
                TextField(
                        value = text2,
                        onValueChange = { text2 = it },
                        label = { Text("Input 2") }
                )

                // Buttons row
                Row {
                    Button(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        // Action for button 2
                        // Process text1 and text2
                        onDismiss()
                    }) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}