package albert.ma.blockchain.walletbasic.bip39


import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.xor
import kotlin.math.ceil
import kotlin.math.pow

class PBKDF2SHA512 {

    companion object{
        fun derive(P:String, S:String, c:Int, dkLen:Int):ByteArray{
            val baos = ByteArrayOutputStream()

            try {
                val hLen = 20

                if (dkLen > ((2.0.pow(32.0) - 1) * hLen)) {
                    throw IllegalArgumentException("derived key too long")
                } else {
                    val l = ceil(dkLen.toDouble() / hLen.toDouble()).toInt()

                    for (i in 1..l) {
                        val T = F(P, S, c, i)
                        baos.write(T)
                    }
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            val baDerived = ByteArray(dkLen)
            System.arraycopy(baos.toByteArray(), 0, baDerived, 0, baDerived.size)

            return baDerived
        }

        @Throws(Exception::class)
        private fun F(P: String, S: String, c: Int, i: Int): ByteArray? {
            var U_LAST: ByteArray? = null
            var U_XOR: ByteArray? = null

            val key = SecretKeySpec(P.toByteArray(Charsets.UTF_8), "HmacSHA512")
            val mac = Mac.getInstance(key.algorithm)
            mac.init(key)

            for (j in 0 until c) {
                if (j == 0) {
                    val baS = S.toByteArray(Charsets.UTF_8)
                    val baI = INT(i)
                    val baU = ByteArray(baS.size + baI.size)

                    System.arraycopy(baS, 0, baU, 0, baS.size)
                    System.arraycopy(baI, 0, baU, baS.size, baI.size)

                    U_XOR = mac.doFinal(baU)
                    U_LAST = U_XOR
                    mac.reset()
                } else {
                    val baU = mac.doFinal(U_LAST)
                    mac.reset()

                    for (k in U_XOR!!.indices) {
                        U_XOR[k] = (U_XOR[k] xor baU[k])
                    }

                    U_LAST = baU
                }
            }

            return U_XOR
        }

        private fun INT(i: Int): ByteArray {
            val bb = ByteBuffer.allocate(4)
            bb.order(ByteOrder.BIG_ENDIAN)
            bb.putInt(i)
            return bb.array()
        }

    }
}