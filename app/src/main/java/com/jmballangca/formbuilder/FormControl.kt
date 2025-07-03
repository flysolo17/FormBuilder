package com.jmballangca.formbuilder




import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FormControl(
    private val initialValue: String,
    val validators: List<Validator> = emptyList()
) {
    var value by mutableStateOf(initialValue)
    var error by mutableStateOf<EValidator?>(null)
    var errors by mutableStateOf<List<String>>(emptyList())
    val lastError by derivedStateOf { errors.lastOrNull() }
    val firstError by derivedStateOf { errors.firstOrNull() }

    var touched by mutableStateOf(false)
    var dirty by mutableStateOf(false)

    val valid: Boolean
        get() = errors.isEmpty()

    private fun validate() {
        errors = validators.filterNot { it.isValid(value) }.map { it.message }
        error = validators.firstOrNull { !it.isValid(value) }?.error
    }

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

    fun onFocusChange(hasFocus: Boolean) {
        if (!hasFocus && !touched) {
            touched = true
            validate()
        }
    }

    fun reset() {
        value = initialValue
        error = null
        errors = emptyList()
        touched = false
        dirty = false
    }
}