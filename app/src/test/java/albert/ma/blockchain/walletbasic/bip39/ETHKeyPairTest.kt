package albert.ma.blockchain.walletbasic.bip39


import albert.ma.blockchain.walletbasic.key.EthereumKeypair
import org.junit.Before
import org.junit.Test

class ETHKeyPairTest {

    var ethKeyPair:EthereumKeypair? = null
    @Before
    fun setup(){

    }

    @Test
    fun privateKeyAddress(){
        val bytes: ByteArray = SecureRandomSeed.random(MnemonicWordNumber.TWELVE)
        ethKeyPair = EthereumKeypair(bytes)
        val address:String = ethKeyPair!!.getAddress()
        println("Address: $address")
    }
}