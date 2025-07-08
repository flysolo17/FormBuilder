package com.jmballangca.formbuilder




import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * FormControl manages the state, validation, and error handling for a single form field.
 *
 * ### Features:
 * - Tracks value changes, focus state, dirty state, and touched state.
 * - Runs validation rules and keeps error messages.
 * - Provides helpers for showing errors based on interaction.
 *
 * ### Properties:
 * - [value] — The current value of the form control.
 * - [error] — True if the control has any validation error.
 * - [errors] — List of error messages.
 * - [lastError] — The most recent error message, or null if none.
 * - [firstError] — The first error message, or null if none.
 * - [hasFocus] — True if the control currently has focus.
 * - [touched] — True if the control has been focused and blurred at least once.
 * - [dirty] — True if the control's value has changed from the initial value.
 * - [isDirtyUnfocusedError] — True if the control is dirty, not focused, and has an error.
 * - [valid] — True if there are no validation errors.
 *
 * ### Common Methods:
 * - [validate] — Validates the current value against all validators.
 * - [set] — Updates the value and triggers validation if needed.
 * - [onFocusChange] — Updates focus state and triggers validation on blur.
 * - [reset] — Resets the control to its initial state.
 *
 * @param initialValue The initial value of the form control.
 * @param validators A list of [Validator] objects used to validate the value.
 */
class FormControl(
    private val initialValue: String,
    val validators: List<Validator> = emptyList()
) {
    /** Current value of the form control. */
    var value by mutableStateOf(initialValue)

    /** True if the control has validation errors. */
    var error by mutableStateOf(false)

    /** List of error messages after validation. */
    var errors by mutableStateOf<List<String>>(emptyList())

    /** The last error message, or null if there are none. */
    val lastError by derivedStateOf { errors.lastOrNull() }

    /** The first error message, or null if there are none. */
    val firstError by derivedStateOf { errors.firstOrNull() }

    /** True if the control currently has focus. */
    var hasFocus by mutableStateOf(false)

    /** True if the control has been interacted with (blurred at least once). */
    var touched by mutableStateOf(false)

    /** True if the value has changed from the initial value. */
    var dirty by mutableStateOf(false)

    /**
     * True if the control is dirty, not focused, and has a validation error.
     * Useful for showing validation messages only after interaction.
     */
    val isDirtyUnfocusedError by derivedStateOf {
        (dirty && !hasFocus) && error
    }

    /** True if there are no validation errors. */
    val valid: Boolean
        get() = errors.isEmpty()

    /**
     * Validates the current value against all provided validators.
     * Updates the [errors] list and [error] flag accordingly.
     */
    fun validate() {
        errors = validators.filterNot { it.isValid(value) }.map { it.message }
        error = validators.any { !it.isValid(value) }
    }

    /**
     * Updates the control's value and optionally triggers validation.
     *
     * @param newValue The new value to set.
     */
    fun set(newValue: String) {
        if (newValue != value) {
            dirty = true
            value = newValue
            if (touched || dirty) {
                validate()
            }
        } else {
            value = newValue
        }
    }

    /**
     * Updates the control's focus state and triggers validation when losing focus for the first time.
     *
     * @param hasFocus True if the control has focus, false otherwise.
     */
    fun onFocusChange(hasFocus: Boolean) {
        this.hasFocus = hasFocus

        if (!hasFocus && !touched) {
            touched = true
            validate()
        }
    }

    /**
     * Resets the control to its initial state, clearing value, errors, and state flags.
     */
    fun reset() {
        value = initialValue
        error = false
        errors = emptyList()
        touched = false
        dirty = false
    }
}
