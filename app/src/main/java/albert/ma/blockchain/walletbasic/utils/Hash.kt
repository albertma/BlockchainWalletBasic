package albert.ma.blockchain.walletbasic.utils

import androidx.compose.ui.unit.IntOffset
import org.spongycastle.crypto.digests.SHA256Digest
import org.spongycastle.jcajce.provider.digest.Keccak

class Hash {
    val KECCAK256_DIGEST_LENGTH:Int = 32
    companion object{

        public fun keccak256(bytes: ByteArray, offset:Int, size:Int):ByteArray{
            val kecc = Keccak.Digest256()
            kecc.update(bytes, offset, size)
            return kecc.digest()
        }

        public fun keccak256(hexInput:String){
        //    val bytes:ByteArray = HexUtils.hexStringToByteArray()
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
    }
}