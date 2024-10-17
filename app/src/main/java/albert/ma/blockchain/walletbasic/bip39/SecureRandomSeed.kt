package albert.ma.blockchain.walletbasic.bip39

import java.security.SecureRandom
import java.util.Random

class SecureRandomSeed {
    companion object{
        fun random( mnemonicWordNumber: MnemonicWordNumber):ByteArray{
            return random(mnemonicWordNumber, SecureRandom())
        }

        private fun random(mnemonicWordNumber: MnemonicWordNumber, random: Random):ByteArray{
            val randomSeed = ByteArray(mnemonicWordNumber.byteLength())
            random.nextBytes(randomSeed)
            return randomSeed
        }
    }
}