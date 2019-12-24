package util.util

 val Int_DIGIT_RANGE = 1..Int.SIZE_BITS

fun numberOfBitWithSign(number: Int): Int {
    var x = Math.abs(number)
    var n = 0
    do {
        n++
        x = x shr 1
    } while (x != 0)
    return n + 1  //+1 for sign bit
}
fun Int.getOpsiteValue()=this xor 1

fun Int.numberOfBitWithOutSign()= numberOfBitWithSign(this)-1

infix fun Int.findDigit(n: Int): Int = when (this and (1 shl n - 1)) {
    0 -> 0
    else -> 1
}

