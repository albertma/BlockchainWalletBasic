package albert.ma.blockchain.walletbasic.bip39

interface WordList {
    fun getWord(index:Int):String
    fun getIndex(word:String?):Int
    fun getSpace():Char
}