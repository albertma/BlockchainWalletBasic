package albert.ma.blockchain.walletbasic.key

import java.math.BigInteger

interface Key {
    //Get raw private key in byte array format
    fun getRawPrivateKey(): ByteArray

    //Get compressed or uncompressed raw public key in byte array format
    fun getRawPublicKey(isCompressed: Boolean): ByteArray

    //Get uncompressed raw public key in byte array format
    fun getRawPublicKey(): ByteArray

    //Get raw address in byte array format
    fun getRawAddress(): ByteArray

    //Get private key in String format
    fun getPrivateKey():String

    //Get private key in BigInteger format
    fun getBigIntegerPrivateKey(): BigInteger

    //Get public key in BigInteger format
    fun getBigIntegerPublicKey(): BigInteger

    //Get 0x address string
    fun getAddress():String

    fun sign(messageHash: ByteArray):ByteArray

}