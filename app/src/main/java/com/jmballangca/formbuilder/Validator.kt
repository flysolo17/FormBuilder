package com.jmballangca.formbuilder

import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.text.Regex


sealed class Validator {
    abstract fun isValid(value: String): Boolean
    abstract val error: EValidator
    abstract val message: String

    class Required(override val message: String = "This field is required") : Validator() {
        override fun isValid(value: String) = value.isNotBlank()
        override val error = EValidator.REQUIRED
    }

    class MinLength(val min: Int, override val message: String = "Minimum $min characters required") : Validator() {
        override fun isValid(value: String) = value.length >= min
        override val error = EValidator.MIN_LENGTH
    }

    class MaxLength(val max: Int, override val message: String = "Maximum $max characters allowed") : Validator() {
        override fun isValid(value: String) = value.length <= max
        override val error = EValidator.MAX_LENGTH
    }

    class Email(override val message: String = "Invalid email format") : Validator() {
        override fun isValid(value: String) =
            Patterns.EMAIL_ADDRESS.matcher(value).matches()

        override val error = EValidator.EMAIL
    }



    class Phone(override val message: String = "Invalid phone number") : Validator() {

        override fun isValid(value: String): Boolean =
            Patterns.PHONE.matcher(value).matches()

        override val error: EValidator = EValidator.PHONE
    }


    class Number(override val message: String = "Must be a number") : Validator() {
        override fun isValid(value: String) = value.toDoubleOrNull() != null
        override val error = EValidator.NUMBER
    }




    class Alpha(override val message: String = "Only alphabetic characters allowed") : Validator() {
        override fun isValid(value: String) = value.matches(Regex("^[a-zA-Z]+$"))
        override val error = EValidator.ALPHA
    }

    class Alphanumeric(override val message: String = "Only letters and numbers allowed") : Validator() {
        override fun isValid(value: String) = value.matches(Regex("^[a-zA-Z0-9]+$"))
        override val error = EValidator.ALPHANUMERIC
    }

    class Url(override val message: String = "Invalid URL format") : Validator() {
        override fun isValid(value: String) = Patterns.WEB_URL.matcher(value).matches()
        override val error = EValidator.URL
    }

    class Date(override val message: String = "Invalid date format") : Validator() {
        override fun isValid(value: String): Boolean =
            try {
                SimpleDateFormat("DD/MM/YYYY", Locale.getDefault()).parse(value)
                true
            } catch (e: ParseException) {
                false
            }

        override val error = EValidator.DATE
    }

    class Uppercase(override val message: String = "At least one uppercase letter required") : Validator() {
        override fun isValid(value: String) = value.any { it.isUpperCase() }
        override val error = EValidator.CONTAINS_UPPERCASE
    }
    class Lowercase(override val message: String = "At least one lowercase letter required") : Validator() {
        override fun isValid(value: String) = value.any { it.isLowerCase() }
        override val error = EValidator.CONTAINS_LOWERCASE
    }
    class Digit(
        val min: Int = 1,
        override val message: String = "At least $min digit(s) required"
    ) : Validator() {

        override fun isValid(value: String): Boolean {
            val digitCount = value.count { it.isDigit() }
            return digitCount >= min
        }

        override val error = EValidator.CONTAINS_DIGIT
    }

    class SpecialChar(override val message: String = "At least one special character required") : Validator() {
        override fun isValid(value: String) = value.any { !it.isLetterOrDigit() }
        override val error = EValidator.CONTAINS_SPECIAL_CHAR
    }

    class NoWhiteSpaces(override val message: String = "White spaces not allowed") : Validator() {
        override fun isValid(value: String) = !value.contains(" ")
        override val error = EValidator.NO_WHITE_SPACES
    }


}
