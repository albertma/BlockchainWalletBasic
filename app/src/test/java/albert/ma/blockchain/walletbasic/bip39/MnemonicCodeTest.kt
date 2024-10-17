package albert.ma.blockchain.walletbasic.bip39

import org.junit.Before
import org.junit.Test

class MnemonicCodeTest {

    var mnemonicCode:MnemonicCode? = null
    @Before
    fun setup(){
        mnemonicCode = MnemonicCode()
    }

    @Test
    fun toMnemonicTest() {
        val bytes = byteArrayOf(
                (-75).toByte(), 127.toByte(), 99.toByte(), 8.toByte(), (-2).toByte(),
                (-113).toByte(), (-42).toByte(), (-24).toByte(), (-76).toByte(), 6.toByte(),
                (-108).toByte(), 28.toByte(), (-49).toByte(), (-73).toByte(), 47.toByte(), 47.toByte()
        )
        println("bytes: ${bytes.contentToString()}")
        val words = mnemonicCode?.toMnemonic(bytes)
        val expectedList:List<String> = arrayListOf("rely", "wild", "season", "wonder", "word", "inmate", "source", "spoon", "broom", "laundry", "slim", "galaxy")
        assert(words==expectedList)
    }

    @Test
    fun toEntropyTest(){
        val expectedList:List<String> = arrayListOf("rely", "wild", "season", "wonder", "word", "inmate", "source", "spoon", "broom", "laundry", "slim", "galaxy")
        var bytesArray: ByteArray? = mnemonicCode?.toEntropy(expectedList)
        val bytesExpect = byteArrayOf(
                (-75).toByte(), 127.toByte(), 99.toByte(), 8.toByte(), (-2).toByte(),
                (-113).toByte(), (-42).toByte(), (-24).toByte(), (-76).toByte(), 6.toByte(),
                (-108).toByte(), 28.toByte(), (-49).toByte(), (-73).toByte(), 47.toByte(), 47.toByte()
        )
        println("content: ${bytesArray.contentToString()}")
        assert(bytesArray.contentEquals(bytesExpect))
    }
}