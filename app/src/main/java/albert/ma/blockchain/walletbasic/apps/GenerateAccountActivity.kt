package albert.ma.blockchain.walletbasic.apps


import albert.ma.blockchain.walletbasic.R
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GenerateMnemonicActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE,
        )

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 这里可以自定义返回键的行为，禁用返回键操作
                moveTaskToBack(true) // 直接将当前任务移到后台，而不是销毁活动
            }
        })
        setContent {
            NewMnemonicScreen()
        }
    }


}

@Composable
fun NewMnemonicScreen() {
    val mnemonicWords = listOf("apple", "banana", "cat", "dog", "elephant", "fish", "grape", "house", "ice", "jungle", "kite", "lion")

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
                text = stringResource(R.string.backup_your_mnemonic),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
        )

        // Mnemonic Words
        Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            mnemonicWords.chunked(4).forEach { rowWords ->
                Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowWords.forEach { word ->
                        Text(text = word, fontSize = 22.sp, color = Color.Red, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Description
        Text(
                text = stringResource(R.string.backup_mnemonic_tips),
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
        )

        // Next Button
        Button(
                onClick = {
                    // Handle navigation to the next Activity
                    // val intent = Intent(context, NextActivity::class.java)
                    // context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
        ) {
            Text(text = "Next")
        }
    }
}