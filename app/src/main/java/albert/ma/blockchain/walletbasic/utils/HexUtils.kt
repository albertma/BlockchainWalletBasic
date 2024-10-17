package albert.ma.blockchain.walletbasic.utils

class HexUtils {

    companion object{
        val HEX_PREFIX:String = "0x"
        fun cleanHexPrefix(input:String):String{
            return if(containHexPrefix(input)) {
                input.substring(2)
            }else{
                input
            }
        }

        fun containHexPrefix(input: String):Boolean{
            return input.length > 1 && input[0] == '0' && input[1] == 'x';
        }

        fun toHexString(input: ByteArray, offset: Int, length: Int, withPrefix: Boolean): String {
            val stringBuilder = StringBuilder()
            if (withPrefix) {
                stringBuilder.append(HexUtils.HEX_PREFIX)
            }
            for (i in offset until (offset + length)) {
                stringBuilder.append(String.format("%02x", input[i].toInt() and 0xFF))
            }
            return stringBuilder.toString()
        }

        fun hexStringToByteArray(input: String): ByteArray {
            val cleanInput = cleanHexPrefix(input)
            val len = cleanInput.length

            if (len == 0) {
                return byteArrayOf()
            }

            val data: ByteArray
            var startIdx: Int

            if (len % 2 != 0) {
                data = ByteArray((len / 2) + 1)
                data[0] = Character.digit(cleanInput[0], 16).toByte()
                startIdx = 1
            } else {
                data = ByteArray(len / 2)
                startIdx = 0
            }

            for (i in startIdx until len step 2) {
                data[(i + 1) / 2] = ((Character.digit(cleanInput[i], 16) shl 4) +
                        Character.digit(cleanInput[i + 1], 16)).toByte()
            }

            return data
        }
    }

}