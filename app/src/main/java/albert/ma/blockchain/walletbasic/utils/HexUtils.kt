package albert.ma.blockchain.walletbasic.utils

class HexUtils {
    val HEX_PREFIX:String = "0x"
    companion object{
        fun cleanHexPrefix(input:String){

        }

        fun containHexPrefix(input: String):Boolean{
            return input.length > 1 && input[0] == '0' && input[1] == 'x';
        }

       // fun toHexString(input: ByteArray, )
    }

}