package albert.ma.blockchain.walletbasic.utils

import albert.ma.blockchain.walletbasic.bip39.MnemonicWordNumber
import albert.ma.blockchain.walletbasic.walletutils.WalletUtils
import org.junit.Before
import org.junit.Test

class WalletUtilsTest {


    @Before
    fun init(){

    }

    @Test
    fun createMnemonicWordsTest(){
        val words:List<String> = WalletUtils.createMnemonicWords(MnemonicWordNumber.TWELVE)

    }
}