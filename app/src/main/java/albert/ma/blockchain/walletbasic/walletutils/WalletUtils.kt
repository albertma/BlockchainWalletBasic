package albert.ma.blockchain.walletbasic.walletutils

import albert.ma.blockchain.walletbasic.bip32.ExtendedKey
import albert.ma.blockchain.walletbasic.bip39.MnemonicCode
import albert.ma.blockchain.walletbasic.bip39.MnemonicWordNumber
import albert.ma.blockchain.walletbasic.bip39.SecureRandomSeed

data class Wallet(val words:List<String>, val keystore:String, val address:String, val privateKey:String){}

class WalletUtils {
    companion object{
        fun createMnemonicWords(mnemonicWordNumber: MnemonicWordNumber):List<String>{
            val random = SecureRandomSeed.random(mnemonicWordNumber)
            val mnemonicCode = MnemonicCode()
            val mnemonicWords:List<String> =  mnemonicCode.toMnemonic(random)
            return mnemonicWords
        }

        fun createWallet(password:String, onCreateCallback: (Wallet?)->Unit) {
            if(onCreateCallback != null && password.isEmpty()){
                onCreateCallback(null)
                return
            }
            val random = SecureRandomSeed.random(MnemonicWordNumber.TWELVE)
            val mnemonicCode = MnemonicCode()
            val mnemonicsWords:List<String>  = mnemonicCode.toMnemonic(random)
            if(mnemonicsWords.isEmpty()){
                onCreateCallback(null)
            }
            createWallet(mnemonicsWords, password, onCreateCallback)
        }

        fun createWallet(mnemonicWords:List<String>, password: String, onCreateCallback: (Wallet) -> Unit){

        }

        private fun createWalletInner(mnemonicWords: List<String>, password: String): Wallet? {
            val seed:ByteArray = MnemonicCode.toSeed(mnemonicWords, password)
            //val extendedKey:ExtendedKey = ExtendedKey.create(seed)
            return null
        }
    }

}

