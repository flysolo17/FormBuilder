package com.jmballangca.formbuilder





class FormBuilder(private val controls: Map<String, FormControl>) {

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

    fun setValue(name: String, value: String) {
        controls[name]?.set(value)
    }

    fun onFocusChange(name: String, hasFocus: Boolean) {
        controls[name]?.onFocusChange(hasFocus)
    }
}