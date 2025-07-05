
# Form Builder

A Form Validation library inspired by Angular form builder for Jetpack Compose.


## Installation

Step 1: Add jitpack repository to your build file

```bash
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url = uri("https://jitpack.io") }
	}
}
```

Step 2: Add the dependency

```bash
dependencies {
	implementation("com.github.flysolo17:FormBuilder:latest")
}
```
    
## Usage/Examples
Example 1 : Using standalone form controls

```kotlin
import com.jmballangca.formbuilder.FormControl
import com.jmballangca.formbuilder.Validator


@Composable
fun LoginFormExample(modifier: Modifier = Modifier) {
    val email = remember { FormControl("", listOf(Validator.Required(), Validator.Email())) }
    val password = remember { FormControl("", listOf(Validator.Required(), Validator.MinLength(6))) }

    Column(modifier = modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState ->
                email.onFocusChange(focusState.isFocused)
            },
            label = { Text("Email") },
            value = email.value,
            onValueChange = { email.set(it) },
            isError = email.dirty && email.valid == false,
            supportingText = {
                val error = email?.firstError
                 if (error != null)
                    Text(error)
            }
        )
        TextField(
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState ->
                password.onFocusChange(focusState.isFocused)
            },
            label = { Text("Password") },
            value = password.value,
            isError = password.dirty && password.valid == false,
            supportingText = {
                val first = password.firstError
                val listOfErrors = password.errors
                val lastError = password.lastError
                if (first != null) {
                    Text(first)
                }
            },
            onValueChange = { password.set(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            )
        )
    }
}

```

Example 2 : Using Form Builder to create forms

```kotlin
import com.jmballangca.formbuilder.FormBuilder
import com.jmballangca.formbuilder.FormControl
import com.jmballangca.formbuilder.Validator

class LoginFormGroup : FormBuilder(
    mapOf(
        "email" to FormControl("", listOf(Validator.Required(), Validator.Email())),
        "password" to FormControl("", listOf(Validator.Required(), Validator.MinLength(6)))
    )
)

@Composable
fun LoginFormExample(modifier: Modifier = Modifier) {

    val loginForm = remember {
        LoginFormGroup()
    }
    val email = loginForm.get("email")
    val password = loginForm.get("password")
    val valid = loginForm.valid
    Scaffold {
        Column(modifier = modifier.padding(it)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                value = email?.value ?: "",
                onValueChange = { loginForm.set("email",it) },
                isError = email?.dirty == true && email.valid == false,
                supportingText = {
                    val error = email?.firstError
                    if (error != null)
                        Text(error)
                }
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                value = password?.value ?: "",
                isError = password?.dirty == true && password.valid == false,
                supportingText = {
                    val first = password?.firstError
                    if (first != null) {
                        Text(first)
                    }
                },
                onValueChange = { loginForm.set("password",it)},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                )
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = valid,
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Login")
            }

        }
    }

}
```
## Demo

![Demo](https://raw.githubusercontent.com/flysolo17/FormBuilder/master/form.webp)

