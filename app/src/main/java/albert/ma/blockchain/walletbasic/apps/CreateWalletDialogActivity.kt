package albert.ma.blockchain.walletbasic.apps


import albert.ma.blockchain.walletbasic.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
class CreateWalletDialogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val input1 =  ""
        val input2 =  ""

        setContent {
                CustomDialog(this)
        }
    }

}

@Preview
@Composable
fun CustomDialog(activity: CreateWalletDialogActivity) {
    var userInput1 by remember { mutableStateOf("") }
    var userInput2 by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(20.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(fontSize = 18.sp,
             textAlign = TextAlign.Center,
             fontWeight = FontWeight.Bold,
             text = stringResource(R.string.create_wallet_title))
        Spacer(modifier = Modifier.height(10.dp))
        // 输入框 1
        TextField(
                value = userInput1,
                onValueChange = { userInput1 = it },
                label = { Text(stringResource(R.string.input_wallet_name)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Ascii, // 限制为 ASCII 字符
                        imeAction = ImeAction.Next
                ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        // 输入框 2
        TextField(
                value = userInput2,
                onValueChange = { userInput2 = it },
                label = { Text(stringResource(R.string.input_wallet_name)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Ascii, // 限制为 ASCII 字符
                        imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(10.dp))
        // 按钮区域
        Column(horizontalAlignment = Alignment.CenterHorizontally ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                Button(onClick = {
                    activity.finish()
                }) {
                    Text(stringResource(R.string.cancel))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    val name = userInput1.toString()
                    val password = userInput2.toString()
                    val letterNumber:Regex = Regex("^[0-9a-zA-Z_]+$")
                    if(!name.matches(letterNumber)){
                        Toast.makeText(activity, R.string.input_wallet_name, Toast.LENGTH_LONG).show()
                        return@Button
                    }
                    if(!password.matches(letterNumber)){
                        Toast.makeText(activity, R.string.input_wallet_name, Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    val intent = Intent(activity, CreateWalletMnemonicsActivity::class.java)
                    intent.putExtra("wallet_name", name)
                    intent.putExtra("password", password)
                    activity.startActivity(intent)
                    activity.finish()
                }) {
                    Text(stringResource(R.string.next))
                }
            }
        }

    }
}


