package com.jmballangca.formbuilder




import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue



class FormControl(
    val name: String,
    private val initialValue: String,
    val validators: List<Validator> = emptyList()
) {
    var value by mutableStateOf(initialValue)
    var error by mutableStateOf<EValidator?>(null)

    var touched by mutableStateOf(false)
    var dirty by mutableStateOf(false)

    val valid: Boolean
        get() = error == null

    private fun validate() {
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
        touched = false
        dirty = false
    }
}