package albert.ma.blockchain.walletbasic.bip39

import albert.ma.blockchain.walletbasic.utils.Hash
import androidx.collection.emptyLongSet

class MnemonicCode {

    private var wordList: WordList = English.INSTANCE

    companion object{
        private const val PBKDF2_ROUNDS: Int = 2048
        fun toSeed(words: List<String>, passphase:String):ByteArray{
            val builder = StringBuilder()
            for(i in words.indices) {
                builder.append(words[i])
                if(i < words.size - 1) {
                    builder.append(" ")
                }
            }

            val pass = builder.toString()
            val salt = "mnemonic$passphase"

            val seed = PBKDF2SHA512.derive(pass, salt, PBKDF2_ROUNDS, 64)
            return seed
        }

        fun bytesToBits(data: ByteArray):BooleanArray{
            val bits = BooleanArray(data.size * 8)
            for (i in data.indices) {
                for (j in 0 until 8) {
                    bits[(i * 8) + j] = (data[i].toInt() and (1 shl (7 - j))) != 0
                }
            }
            return bits

        }
    }
    fun toMnemonic(entropy: ByteArray):ArrayList<String>{
        // We take initial entropy of ENT bits and compute its
        // checksum by taking first ENT / 32 bits of its SHA256 hash.

        val hash = Hash.sha256(entropy)
        val hashBits = bytesToBits(hash)

        val entropyBits = bytesToBits(entropy)
        val checksumLengthBits = entropyBits.size / 32

        // We append these bits to the end of the initial entropy.
        val concatBits = BooleanArray(entropyBits.size + checksumLengthBits)
        System.arraycopy(entropyBits, 0, concatBits, 0, entropyBits.size)
        System.arraycopy(hashBits, 0, concatBits, entropyBits.size, checksumLengthBits)

        // Next we take these concatenated bits and split them into
        // groups of 11 bits. Each group encodes number from 0-2047
        // which is a position in a wordlist. We convert numbers into
        // words and use joined words as mnemonic sentence.

        val words = ArrayList<String>()
        val nwords = concatBits.size / 11
        for (i in 0 until nwords) {
            var index = 0
            for (j in 0 until 11) {
                index = index shl 1
                if (concatBits[(i * 11) + j]) {
                    index = index or 0x1
                }
            }
            words.add(this.wordList.getWord(index))
        }

        return words
    }
}