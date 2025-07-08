package com.jmballangca.formbuilder

import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.text.Regex

/**
 * A sealed class representing different types of form field validators.
 *
 * Each validator provides:
 * - A customizable [message] describing the validation failure. You can pass your own message when creating the validator to override the default one.
 * - An [isValid] function that checks if a given value passes the validation.
 *
 *  * ### Pre-built Validators (Version - 1.0.4):
 *   - [Validator.Required] — Ensures the field is not blank.
 *   - [Validator.MinLength] — Ensures the value meets a minimum length.
 *   - [Validator.MaxLength] — Ensures the value does not exceed a maximum length.
 *   - [Validator.Email] — Validates proper email format.
 *   - [Validator.Phone] — Validates phone number format.
 *   - [Validator.Number] — Ensures the value is a valid number.
 *   - [Validator.Alpha] — Allows only alphabetic characters.
 *   - [Validator.Alphanumeric] — Allows letters and numbers only.
 *   - [Validator.Url] — Validates URL format.
 *   - [Validator.Date] — Validates date format (DD/MM/YYYY).
 *   - [Validator.ContainsUppercase] — Ensures a minimum number of uppercase letters.
 *   - [Validator.ContainsLowercase] — Ensures a minimum number of lowercase letters.
 *   - [Validator.ContainsDigit] — Ensures a minimum number of digits.
 *   - [Validator.SpecialChar] — Ensures at least one special character.
 *   - [Validator.NoWhiteSpaces] — Ensures no white spaces.
 *
 *
 * ### Example: Creating a Custom Validator
 *
 * You can create your own custom validator by extending this class:
 *
 * ```kotlin
 * class ExactLengthValidator(
 *     private val length: Int,
 *     override val message: String = "Must be exactly $length characters"
 * ) : Validator() {
 *     override fun isValid(value: String): Boolean = value.length == length
 * }
 *
 * val formControl = FormControl(
 *     initialValue = "",
 *     validators = listOf(
 *         Validator.Required(),
 *         ExactLengthValidator(4),
 *         Validator.NoWhiteSpaces()
 *     )
 * )
 *
 * formControl.set("test")
 * formControl.validate()
 *
 * println(formControl.errors)  // Prints error messages if any validation fails
 * ```
 *
 * This allows you to easily add new validation rules tailored to your specific needs.
 */

sealed class Validator {


    abstract val message: String


    abstract fun isValid(value: String): Boolean


    class Required(override val message: String = "This field is required") : Validator() {
        override fun isValid(value: String) = value.isNotBlank()
    }


    class MinLength(val min: Int, override val message: String = "Minimum $min characters required") : Validator() {
        override fun isValid(value: String) = value.length >= min
    }


    class MaxLength(val max: Int, override val message: String = "Maximum $max characters allowed") : Validator() {
        override fun isValid(value: String) = value.length <= max
    }


    class Email(override val message: String = "Invalid email format") : Validator() {
        override fun isValid(value: String) = Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }


    class Phone(override val message: String = "Invalid phone number") : Validator() {
        override fun isValid(value: String) = Patterns.PHONE.matcher(value).matches()
    }


    class Number(override val message: String = "Must be a number") : Validator() {
        override fun isValid(value: String) = value.toDoubleOrNull() != null
    }

    class Alpha(override val message: String = "Only alphabetic characters allowed") : Validator() {
        override fun isValid(value: String) = value.matches(Regex("^[a-zA-Z]+$"))
    }


    class Alphanumeric(override val message: String = "Only letters and numbers allowed") : Validator() {
        override fun isValid(value: String) = value.matches(Regex("^[a-zA-Z0-9]+$"))
    }

    class Url(override val message: String = "Invalid URL format") : Validator() {
        override fun isValid(value: String) = Patterns.WEB_URL.matcher(value).matches()
    }


    class Date(
        val defaultFormat: String = "MM/DD/yyyy",
        override val message: String = "Invalid date format"
    ) : Validator() {
        override fun isValid(value: String): Boolean = try {
            SimpleDateFormat(defaultFormat, Locale.getDefault()).parse(value)
            true
        } catch (e: ParseException) {
            false
        }
    }

    /**
     * Validates that the value contains at least [min] uppercase letters.
     */
    class ContainsUppercase(val min: Int = 1, override val message: String = "At least $min uppercase letter required") : Validator() {
        override fun isValid(value: String) = value.count { it.isUpperCase() } >= min
    }

    /**
     * Validates that the value contains at least [min] lowercase letters.
     */
    class ContainsLowercase(val min: Int = 1, override val message: String = "At least $min lowercase letter required") : Validator() {
        override fun isValid(value: String) = value.count { it.isLowerCase() } >= min
    }

    /**
     * Validates that the value contains at least [min] digits.
     */
    class ContainsDigit(val min: Int = 1, override val message: String = "At least $min digit(s) required") : Validator() {
        override fun isValid(value: String) = value.count { it.isDigit() } >= min
    }

    /**
     * Validates that the value contains at least one special character.
     */
    class SpecialChar(override val message: String = "At least one special character required") : Validator() {
        override fun isValid(value: String) = value.any { !it.isLetterOrDigit() }
    }

    /**
     * Validates that the value does not contain any whitespace characters.
     */
    class NoWhiteSpaces(override val message: String = "White spaces not allowed") : Validator() {
        override fun isValid(value: String) = !value.contains(" ")
    }
}
