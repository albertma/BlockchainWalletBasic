package albert.ma.blockchain.walletbasic.bip32


import albert.ma.blockchain.walletbasic.key.EthereumKeypair
import albert.ma.blockchain.walletbasic.utils.Hash
import java.math.BigInteger
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec



class ExtendedKey(
    val ethereumKeyPair: EthereumKeypair,
    val chainCode: ByteArray,
    val depth: Int = 0,
    val parentFingerprint: Int = 0,
    val childNumber: Int = 0
) {

    companion object {
        private const val HMAC_SHA512 = "HmacSHA512"

        // 从种子生成主密钥
        fun fromSeed(seed: ByteArray): ExtendedKey {
            val hmac = Mac.getInstance(HMAC_SHA512)
            val key = SecretKeySpec("Bitcoin seed".toByteArray(), HMAC_SHA512)
            hmac.init(key)
            val lr = hmac.doFinal(seed)
            val l = lr.sliceArray(0 until 32)
            val r = lr.sliceArray(32 until 64)

            val privateKey = BigInteger(1, l)
            val chainCode = r

            val keyPair = EthereumKeypair(privateKey)

            return ExtendedKey(keyPair, chainCode)
        }
    }

    // 派生子密钥
    fun deriveChildKey(sequence: Int): ExtendedKey {
        val mac = Mac.getInstance(HMAC_SHA512)
        val key = SecretKeySpec(chainCode, HMAC_SHA512)
        mac.init(key)

        val extended: ByteArray = if ((sequence and 0x80000000.toInt()) == 0) {
            // 非硬化推导使用公钥
            val pub = ethereumKeyPair.getRawPublicKey()
            pub + sequence.toByteArray()
        } else {
            // 硬化推导使用私钥
            byteArrayOf(0) + ethereumKeyPair.getRawPrivateKey() + sequence.toByteArray()
        }

        val lr = mac.doFinal(extended)
        val l = lr.sliceArray(0 until 32)
        val r = lr.sliceArray(32 until 64)
        val ecSpec = ethereumKeyPair.ecSpec
        val childPrivateKey = BigInteger(1, l)
            .add(ethereumKeyPair.getBigIntegerPrivateKey())
            .mod(ecSpec.n)
        val childKeyPair = EthereumKeypair(childPrivateKey)

        return ExtendedKey(childKeyPair, r, depth + 1, getFingerPrint(), sequence)
    }

    // 获取父密钥的指纹
    fun getFingerPrint(): Int {
        val pubKey = ethereumKeyPair.getRawPublicKey()
        val hash = Hash.sha256Ripemd160(pubKey)
        val first4Bytes =  hash.sliceArray(0 until 4)
        return ByteBuffer.wrap(first4Bytes).int
    }

    // 获取公钥
    fun getPublicKey(): ByteArray {
        return ethereumKeyPair.getRawPublicKey()
    }

    // 获取私钥
    fun getPrivateKey(): ByteArray {
        return ethereumKeyPair.getRawPrivateKey()
    }

    // Helper function to convert int to ByteArray
    private fun Int.toByteArray(): ByteArray {
        return byteArrayOf(
                (this shr 24).toByte(),
                (this shr 16).toByte(),
                (this shr 8).toByte(),
                this.toByte()
        )
    }
}

