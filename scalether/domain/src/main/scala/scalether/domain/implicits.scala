package scalether.domain

import java.math.BigInteger

import scala.language.implicitConversions

object implicits {
  implicit def byteToBigInteger(i: Byte): BigInteger = BigInteger.valueOf(i)

  implicit def intToBigInteger(i: Int): BigInteger = BigInteger.valueOf(i)

  implicit def longToBigInteger(i: Long): BigInteger = BigInteger.valueOf(i)

  implicit def stringToBigInteger(s: String): BigInteger = {
    if (s == null || s.isEmpty) {
      null
    } else if (s.startsWith("0x")) {
      new BigInteger(s.substring(2), 16)
    } else {
      new BigInteger(s)
    }
  }

  implicit def stringToAddress(s: String): Address = Address(s)

  implicit def stringToWord(s: String): Word = Word(s)
}
