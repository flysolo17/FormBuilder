package com.jmballangca.formbuilder


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
            value.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

        override val error = EValidator.EMAIL
    }

    class Phone(override val message: String = "Invalid phone number") : Validator() {
        override fun isValid(value: String) = value.matches(Regex("^\\+?[0-9]{10,15}$"))
        override val error = EValidator.PHONE
    }

    class Number(override val message: String = "Must be a number") : Validator() {
        override fun isValid(value: String) = value.toDoubleOrNull() != null
        override val error = EValidator.NUMBER
    }
}
