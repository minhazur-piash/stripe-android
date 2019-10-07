package com.stripe.android

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale

/**
 * Utility class for common text-related operations on Stripe data coming from the server.
 */
object StripeTextUtils {

    /**
     * Swap `null` for blank text values.
     *
     * @param value an input string that may or may not be entirely whitespace
     * @return `null` if the string is entirely whitespace, otherwise the input value
     */
    @JvmStatic
    fun nullIfBlank(value: String?): String? {
        return value.takeUnless { it.isNullOrBlank() }
    }

    /**
     * A checker for whether or not the input value is entirely whitespace. This is slightly more
     * aggressive than the android TextUtils#isEmpty method, which only returns true for
     * `null` or `""`.
     *
     * @param value a possibly blank input string value
     * @return `true` if and only if the value is all whitespace, `null`, or empty
     */
    @JvmStatic
    fun isBlank(value: String?): Boolean {
        return value.isNullOrBlank()
    }

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    @JvmStatic
    fun isEmpty(str: CharSequence?): Boolean {
        return str.isNullOrEmpty()
    }

    /**
     * Converts a card number that may have spaces between the numbers into one without any spaces.
     * Note: method does not check that all characters are digits or spaces.
     *
     * @param cardNumberWithSpaces a card number, for instance "4242 4242 4242 4242"
     * @return the input number minus any spaces, for instance "4242424242424242".
     * Returns `null` if the input was `null` or all spaces.
     */
    @JvmStatic
    fun removeSpacesAndHyphens(cardNumberWithSpaces: String?): String? {
        return cardNumberWithSpaces.takeUnless { it.isNullOrBlank() }
            ?.replace("\\s|-".toRegex(), "")
    }

    /**
     * Check to see if the input number has any of the given prefixes.
     *
     * @param number the number to test
     * @param prefixes the prefixes to test against
     * @return `true` if number begins with any of the input prefixes
     */
    internal fun hasAnyPrefix(number: String?, vararg prefixes: String): Boolean {
        return prefixes.any { number?.startsWith(it) == true }
    }

    /**
     * Calculate a hash value of a String input and convert the result to a hex string.
     *
     * @param toHash a value to hash
     * @return a hexadecimal string
     */
    internal fun shaHashInput(toHash: String?): String? {
        if (toHash.isNullOrBlank()) {
            return null
        }

        return try {
            val digest = MessageDigest.getInstance("SHA-1")
            val bytes = toHash.toByteArray(StandardCharsets.UTF_8)
            digest.update(bytes, 0, bytes.size)
            bytesToHex(digest.digest())
        } catch (noSuchAlgorithm: NoSuchAlgorithmException) {
            null
        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes
            .joinToString("") { String.format("%02x", it) }
            .toUpperCase(Locale.ROOT)
    }
}
