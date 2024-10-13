package albert.ma.blockchain.walletbasic.bip39

enum class MnemonicWordNumber(val bitLength: Int) {

    THREE(27),
    SIX(54),
    NINE(72),
    TWELVE(128),
    FIFTEEN(160),
    EIGHTEEN(192),
    TWENTY_ONE(224),
    TWENTY_FOUR(256),
}