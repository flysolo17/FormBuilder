package com.jmballangca.formbuilder


/**
 * FormBuilder is a simple utility class for managing and validating a group of form controls.
 *
 * ### Features:
 * - Check if the entire form is valid or invalid.
 * - Access all form values and error messages.
 * - Set, get, and reset control values.
 * - Validate all controls at once.
 *
 * ### Properties:
 * - [valid] — True if all controls are valid.
 * - [invalid] — True if at least one control is invalid.
 * - [errors] — A flat list of all error messages.
 * - [values] — A map of control names to their current values.
 *
 * ### Common Methods:
 * - [group] — Returns all form controls.
 * - [resetAll] — Resets all controls to initial values.
 * - [set] — Updates a control’s value.
 * - [get] — Retrieves a specific control.
 * - [onFocusChange] — Notifies a control about focus change.
 * - [getValueOrEmpty] — Gets a control’s value or empty string.
 * - [errors] — Returns all control errors as a map.
 * - [validate] — Validates all controls.
 * - [getError] — Returns the last error of a control.
 *
 * @param controls A map of form control names to their corresponding [FormControl] instances.
 */
open class FormBuilder(private val controls: Map<String, FormControl>) {

    val valid: Boolean
        get() = controls.all { it.value.valid }

    val invalid: Boolean
        get() = !valid

    val errors: List<String>
        get() = controls.values.flatMap { it.errors }

    val values: Map<String, String>
        get() = controls.mapValues { it.value.value }

    fun group(): Map<String, FormControl> = controls

    fun resetAll() {
        controls.values.forEach { it.reset() }
    }

    fun set(name: String, value: String) {
        controls[name]?.set(value)
    }

    fun get(name: String) : FormControl ? {
        return controls[name]
    }

    fun onFocusChange(name: String, hasFocus: Boolean) {
        controls[name]?.onFocusChange(hasFocus)
    }

    fun getValueOrEmpty(name: String): String = controls[name]?.value ?: ""

    fun errors(): Map<String, List<String>> =
        controls.mapValues { it.value.errors }.filterValues { it.isNotEmpty() }

    fun validate() {
        controls.forEach { it.value.validate() }
    }

    fun getError(name: String): String? = controls[name]?.lastError

}
