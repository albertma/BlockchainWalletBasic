package albert.ma.blockchain.walletbasic.bip32

import albert.ma.blockchain.walletbasic.key.KeyPair
import org.spongycastle.asn1.sec.SECNamedCurves
import org.spongycastle.asn1.x9.X9ECParameters
import org.spongycastle.crypto.KeyParser
import java.math.BigInteger

import java.security.Key

import java.security.SecureRandom
import java.util.Arrays
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

val Random: SecureRandom = SecureRandom()
val Curve: X9ECParameters = SECNamedCurves.getByName("secp256k1")
val BitcoinSeed: ByteArray = "Bitcoin seed".toByteArray()

class ExtendedKey {
    val master: Key? = null
    val chainCode: ByteArray? = null
    val depth: Int? = null
    val parent: Int? = null
    val sequence: Int? = null

    companion object{
        fun create(seed: ByteArray): ExtendedKey?{
            val algorithm = "HmacSHA512"
            val mac = Mac.getInstance(algorithm)
            val seedKey: SecretKey = SecretKeySpec(BitcoinSeed, algorithm)
            mac.init(seedKey)
            val lr = mac.doFinal(seed)
            val l = Arrays.copyOfRange(lr, 0, 32)
            val r = Arrays.copyOfRange(lr, 32, 64)
            val m: BigInteger = BigInteger(1, l)
            if(m >= Curve.n){
                return null
            }
            val keyPair: KeyPair = KeyPair(l, true)

            return null
        }
    }
}