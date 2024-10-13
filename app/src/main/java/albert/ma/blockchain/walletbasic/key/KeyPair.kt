package albert.ma.blockchain.walletbasic.key

import org.spongycastle.asn1.sec.SECNamedCurves
import org.spongycastle.asn1.x9.X9ECParameters
import org.spongycastle.crypto.params.ECDomainParameters
import org.spongycastle.math.ec.ECPoint
import org.spongycastle.util.Arrays
import java.math.BigInteger
import java.security.SecureRandom
import kotlin.math.max
import kotlin.math.min

class KeyPair(priv:BigInteger, compressed:Boolean): Key{

    val SECURERANDOM:SecureRandom = SecureRandom()
    val CURVE:X9ECParameters = SECNamedCurves.getByName("secp256k1")
    val DOMAIN:ECDomainParameters = ECDomainParameters(CURVE.curve, CURVE.g, CURVE.n, CURVE.h)
    val LARGEST_PRIVATE_KEY = BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16)

    var priv:BigInteger ?= null
    var pub: ByteArray ?= null
    var pubComp: ByteArray ?= null
    var compressed: Boolean ?= null
    init{
        this.priv = priv
        this.compressed = compressed
        val multiply: ECPoint = CURVE.g.multiply(priv)
        pub = multiply.getEncoded(false)
        pubComp = multiply.getEncoded(true)
    }

    override fun getRawPrivateKey(): ByteArray {
        var p:ByteArray = priv!!.toByteArray()
        if (p.size != 32){
            val tmp:ByteArray = ByteArray(32)
            System.arraycopy(p, max(0, p.size - 32), tmp, max(0, 32 - p.size), min(32, p.size))
            p = tmp
        }
        return p
    }

    override fun getRawPublicKey(isCompressed: Boolean): ByteArray {
        return if(isCompressed){
            Arrays.clone(pubComp)
        }else{
            Arrays.clone(pub)
        }
    }

    override fun getRawPublicKey(): ByteArray {
        return getRawPublicKey(true)
    }

    override fun getRawAddress(): ByteArray {
        //return Hash.hash160(pubComp)
        TODO("Not yet implemented")
    }

    override fun getPrivateKey(): String {
        TODO("Not yet implemented")
    }

    override fun getBigIntegerPrivateKey(): BigInteger {
        TODO("Not yet implemented")
    }

    override fun getBigIntegerPublicKey(): BigInteger {
        TODO("Not yet implemented")
    }

    override fun getAddress(): String {
        TODO("Not yet implemented")
    }

    override fun isCompressed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun <T> sign(messageHash: ByteArray): T {
        TODO("Not yet implemented")
    }
}