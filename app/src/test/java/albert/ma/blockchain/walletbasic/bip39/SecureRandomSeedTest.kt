package albert.ma.blockchain.walletbasic.bip39

import org.junit.Test

class SecureRandomSeedTest {

    @Test
    fun randomTest(){
        val bytes = SecureRandomSeed.random(MnemonicWordNumber.TWELVE)
        println("random bytes: ${bytes.contentToString()}")
    }
}