package com.jmballangca.formbuilder

import kotlin.math.log

class Sample {

    val emailControl =  FormControl(
        initialValue = "",
        validators = listOf(
            Validator.Required(),
            Validator.Email()
        )
    )
    val passwordControl = FormControl(
        initialValue = "",
        validators = listOf(
            Validator.Required(),
            Validator.MinLength(8)
        )
    )
    val loginForm = FormBuilder(
        controls = mapOf(
            "email" to emailControl,
            "password" to passwordControl
        )
    )
    val valid = loginForm.valid
    val errors = loginForm.errors
    val invalid = loginForm.invalid


}