package albert.ma.blockchain.walletbasic.apps



import albert.ma.blockchain.walletbasic.ui.ConfirmMnemonicScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class ConfirmMnemonicActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val wordList = listOf(
                    "apple", "banana", "grape", "orange", "mango", "pear", "peach", "pineapple", "plum", "pomegranate"
            )
            ConfirmMnemonicScreen(wordList){

            }
        }
    }
}