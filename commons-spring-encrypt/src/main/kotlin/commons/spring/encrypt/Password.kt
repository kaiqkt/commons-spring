package commons.spring.encrypt

import java.math.BigInteger
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.experimental.or
import kotlin.experimental.xor

private const val KEY_LENGTH = 256
private const val SALT_SIZE = 16
private const val HEX_BITS = 16
private const val KEY_FACTORY_INSTANCE = "PBKDF2WithHmacSHA1"
private const val SECURE_RANDOM = "SHA1PRNG"

//Encrypt the raw password
public fun String.encrypt(): Pair<String, String> {
    val arrayPassword = this.toCharArray()
    val salt = generateSalt()

    val hash = encode(arrayPassword, salt)

    //hash salt
    return Pair(toHex(hash), toHex(salt))
}

//Compare the actual secured password with a raw password
public fun String.compareWith(hash: String, salt: String): Boolean {
    val hex = fromHex(hash)
    val hashOriginalPassword = encode(
        this.toCharArray(),
        fromHex(salt)
    )

    var diff = hex.size.toByte() xor hashOriginalPassword.size.toByte()

    hex.forEachIndexed { index, byte ->
        kotlin.run {
            if (index > hashOriginalPassword.size) return false

            val diffTemp = (byte xor hashOriginalPassword[index])
            diff = diff or diffTemp
        }
    }

    return diff.toInt() == 0
}


private fun encode(arrayPassword: CharArray, salt: ByteArray): ByteArray {
    val keySpec = PBEKeySpec(arrayPassword, salt, 10000, KEY_LENGTH)
    val skf = SecretKeyFactory.getInstance(KEY_FACTORY_INSTANCE)

    return skf.generateSecret(keySpec).encoded
}

private fun generateSalt(): ByteArray {
    val secureRandom = SecureRandom.getInstance(SECURE_RANDOM)
    val salt = ByteArray(SALT_SIZE)
    secureRandom.nextBytes(salt)

    return salt
}

private fun toHex(array: ByteArray): String {
    val bigInteger = BigInteger(1, array)
    val stringHex = bigInteger.toString(HEX_BITS)
    val paddingLength = (array.size * 2) - stringHex.length

    return if (paddingLength > 0) {
        String.format("%0" + paddingLength + "d", 0) + stringHex
    } else {
        stringHex
    }
}

private fun fromHex(hex: String): ByteArray {
    val bytes = ByteArray(hex.length / 2)
    for (i in bytes.indices) {
        bytes[i] = (Integer.parseInt(hex.substring(2 * i, 2 * i + 2), HEX_BITS)).toByte()
    }

    return bytes
}