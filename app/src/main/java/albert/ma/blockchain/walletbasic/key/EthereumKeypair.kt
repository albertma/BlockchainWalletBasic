package albert.ma.blockchain.walletbasic.key

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.math.BigInteger
import java.security.*
import java.security.spec.ECPrivateKeySpec
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
import org.bouncycastle.jce.spec.ECNamedCurveSpec
import org.bouncycastle.util.encoders.Hex
import java.security.interfaces.ECPrivateKey
import java.security.KeyPair
import java.security.Security


class EthereumKeypair(privateKeyBigInt: BigInteger):Key{

    val ecSpec: ECNamedCurveParameterSpec
    private val ecPrivateKey: ECPrivateKey
    private val ecPublicKey: PublicKey
    private val ecKeyPair: KeyPair

    init {
        // 将 ByteArray 转换为 BigInteger
        Security.addProvider(BouncyCastleProvider())


        // 获取 secp256k1 曲线参数
        ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
        val curveSpec = ECNamedCurveSpec("secp256k1", ecSpec.curve, ecSpec.g, ecSpec.n, ecSpec.h)

        // 使用私钥生成密钥对
        val keyFactory = KeyFactory.getInstance("EC", "BC")
        val privateKeySpec = ECPrivateKeySpec(privateKeyBigInt, curveSpec)
        ecPrivateKey = keyFactory.generatePrivate(privateKeySpec) as ECPrivateKey
        val keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC")
        keyPairGenerator.initialize(ecSpec)

        // 生成密钥对
        ecKeyPair = keyPairGenerator.genKeyPair()
        ecPublicKey = ecKeyPair.public
    }
    constructor(privateKeyBytes: ByteArray) : this(BigInteger(1, privateKeyBytes)) {

    }

    override fun getRawPrivateKey(): ByteArray = ecPrivateKey.s.toByteArray()

    override fun getRawPublicKey(isCompressed: Boolean): ByteArray {
        val x = (ecPublicKey as java.security.interfaces.ECPublicKey).w.affineX
        val y = (ecPublicKey as java.security.interfaces.ECPublicKey).w.affineY

        return if (isCompressed) {
            // 压缩格式，前缀是 0x02 或 0x03
            val prefix = if (y.testBit(0)) 0x03.toByte() else 0x02.toByte()
            byteArrayOf(prefix) + x.toByteArray()
        } else {
            // 非压缩格式，前缀是 0x04
            byteArrayOf(0x04.toByte()) + x.toByteArray() + y.toByteArray()
        }
    }

    override fun getRawPublicKey(): ByteArray = getRawPublicKey(isCompressed = false)

    override fun getRawAddress(): ByteArray {
        val pubKeyHash = MessageDigest.getInstance("KECCAK-256").digest(getRawPublicKey().copyOfRange(1, 65))

        return pubKeyHash.copyOfRange(12, 32) // Ethereum 地址是公钥哈希的最后20个字节
    }

    override fun getPrivateKey(): String = ecPrivateKey.s.toString(16)

    override fun getBigIntegerPrivateKey(): BigInteger = ecPrivateKey.s

    override fun getBigIntegerPublicKey(): BigInteger {
        return (ecPublicKey as java.security.interfaces.ECPublicKey).w.affineX // 这里只返回X坐标
    }

    override fun getAddress(): String = "0x" + Hex.toHexString(getRawAddress())


    override fun sign(messageHash: ByteArray):ByteArray{
        val signature = Signature.getInstance("SHA256withECDSA", "BC")
        signature.initSign(ecKeyPair.private)

        signature.update(messageHash, 0, messageHash.size)//update(messageHash)
        return signature.sign()
    }

}


