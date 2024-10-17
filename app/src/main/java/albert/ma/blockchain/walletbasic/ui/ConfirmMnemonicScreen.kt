package albert.ma.blockchain.walletbasic.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmMnemonicScreen(
    wordList: List<String>, // Full word list for suggestions
    onConfirm: (List<String>) -> Unit // Callback for confirming entered words
) {
    var mnemonicWords by remember { mutableStateOf(List(12) { "" }) } // 12-word mnemonic input
    val focusRequester = remember { FocusRequester() }

    LazyColumn (modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text(text = "Confirm Your Mnemonic", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
        }
        // Loop to create 12 text fields for mnemonic input
        items(mnemonicWords.size){index->
            MnemonicInputField(
                    index = index,
                    word = mnemonicWords[index],
                    wordList = wordList,
                    onWordChange = { updatedWord ->
                        mnemonicWords = mnemonicWords.toMutableList().apply {
                            this[index] = updatedWord
                        }
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp)) // Push the confirm button to the bottom
            Button(
                    onClick = { onConfirm(mnemonicWords) },
                    modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Confirm")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MnemonicInputField(
    index: Int,
    word: String,
    wordList: List<String>,
    onWordChange: (String) -> Unit
) {
    var inputText by remember { mutableStateOf(word) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val filteredSuggestions = wordList.filter { it.startsWith(inputText, ignoreCase = true) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Use ExposedDropdownMenuBox to handle dropdown and TextField together
    ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = {
                isDropdownExpanded = !isDropdownExpanded
            }
    ) {
        OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    isDropdownExpanded = filteredSuggestions.isNotEmpty()
                    onWordChange(it)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text("Mnemonic Word #${index+1}") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
                modifier = Modifier.heightIn(max = 150.dp)
        ) {
            filteredSuggestions.forEach { suggestion ->
                DropdownMenuItem(
                        text = { Text(suggestion) },
                        onClick = {
                            inputText = suggestion
                            isDropdownExpanded = false
                            onWordChange(suggestion)
                        }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ConfirmMnemonicScreenPreview() {
    val wordList = listOf(
            "apple", "banana", "grape", "orange", "mango", "pear", "peach", "pineapple", "plum", "pomegranate"
    )
    ConfirmMnemonicScreen(wordList = wordList) {
        // Handle mnemonic confirmation
    }
}

