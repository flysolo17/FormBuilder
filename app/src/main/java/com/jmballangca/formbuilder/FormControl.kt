package com.jmballangca.formbuilder




class FormControl(
    val name: String,
    val initialValue: String,
    val validators: List<Validator> = emptyList()
) {
    var value: String = initialValue
    var error: EValidator? = null
    var touched: Boolean = false
    var dirty: Boolean = false

    val valid: Boolean
        get() = error == null

    fun validate() {
        error = validators.firstOrNull { !it.isValid(value) }?.error
    }

    fun set(newValue: String) {
        if (newValue != value) {
            dirty = true
        }
        value = newValue
        if (touched || dirty) {
            validate()
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
