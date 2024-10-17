package albert.ma.blockchain.walletbasic.utils

import org.spongycastle.crypto.digests.RIPEMD160Digest
import org.spongycastle.crypto.digests.SHA256Digest
import org.spongycastle.jcajce.provider.digest.Keccak
import java.security.MessageDigest

class Hash {

    companion object{
        val KECCAK256_DIGEST_LENGTH:Int = 32

        val RIPEMD160_DIGEST_LENGTH: Int = 20

        fun keccak256(bytes: ByteArray, offset:Int, size:Int):ByteArray{
            val kecc = Keccak.Digest256()
            kecc.update(bytes, offset, size)
            return kecc.digest()
        }

        fun keccak256(hexInput:String): ByteArray {
            val bytes:ByteArray = HexUtils.hexStringToByteArray(hexInput)
            return keccak256(bytes, 0, bytes.size)
        }

        fun sha256(bytes: ByteArray): ByteArray{
            return sha256(bytes, 0, bytes.size)
        }

        fun sha256(bytes: ByteArray, offset: Int, size: Int): ByteArray{
            val sha256Digest = SHA256Digest()
            sha256Digest.update(bytes, offset, size)
            val sha256 = ByteArray(32)
            sha256Digest.doFinal(sha256, 0)
            return sha256
        }

        fun hash160(bytes: ByteArray): ByteArray{
            return ripemd160(sha256(bytes))
        }

        fun ripemd160(bytes: ByteArray): ByteArray{
            val ripe:RIPEMD160Digest = RIPEMD160Digest()
            ripe.update(bytes, 0, bytes.size)
            val hash160:ByteArray = ByteArray(RIPEMD160_DIGEST_LENGTH)
            ripe.doFinal(hash160, 0)
            return hash160
        }

        fun doubleSha256(bytes: ByteArray):ByteArray{
            return doubleSha256(bytes, 0, bytes.size)
        }

        fun doubleSha256(bytes: ByteArray, offset: Int, size: Int):ByteArray{
            val sha256:MessageDigest = MessageDigest.getInstance("SHA-256")
            sha256.update(bytes, offset, size)
            val a: ByteArray = sha256.digest()
            return sha256.digest(a)
        }

        fun sha256Ripemd160(pubKey: ByteArray): ByteArray {
            // Step 1: Perform SHA-256 hashing
            val sha256Digest = MessageDigest.getInstance("SHA-256")
            val sha256Hash = sha256Digest.digest(pubKey)

            // Step 2: Perform RIPEMD-160 hashing on the result of SHA-256
            val ripemd160Digest = MessageDigest.getInstance("RIPEMD160")
            val ripemd160Hash = ripemd160Digest.digest(sha256Hash)

            return ripemd160Hash
        }
    }
}