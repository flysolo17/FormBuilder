package com.jmballangca.formbuilder





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

    fun controlErrors(): Map<String, List<String>> =
        controls.mapValues { it.value.errors }.filterValues { it.isNotEmpty() }
}
