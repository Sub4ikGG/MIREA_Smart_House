# Smart House - Android

[README-RU](https://github.com/sub4ikgg)

## Android Application

Description of the mobile application, including the technologies used and its implementation.

---

## Introduction

One of the requirements for this lab work was to create an Android mobile application. The result is a functional Android app that enables users to control their smart home devices.

---

## Technology Stack

### Programming Language: Kotlin

Google’s officially recommended programming language, Kotlin, was used to develop the mobile application. It features a clean and intuitive syntax, providing all the necessary tools for tackling not just mobile development challenges, but business tasks in general.

---

### UI Framework: Jetpack Compose

As with any other mobile application, our smart home app incorporates a user interface. We opted for the native framework Jetpack Compose, developed and maintained by Google.

Key benefits:
- Accelerates UI development.
- Enables writing less code with a more intuitive structure.

Downsides:
- Startup time might be slightly longer compared to the traditional View system. However, this isn’t critical for our use case.

![image](https://github.com/user-attachments/assets/243e9ed4-1ca2-4216-852d-a8e358e7e0a2)

---

### Multithreading: Kotlin Coroutines

Each application has a main thread responsible for handling user interactions (such as clicks and animations) and rendering interfaces. Parallel or asynchronous operations require additional threads, which can be challenging to manage manually.

Kotlin Coroutines:
- Allow you to write asynchronous code as if it were synchronous.
- Delegate tasks to thread pools, simplifying management.

---

### Networking: Ktor

For network requests, we utilize the Ktor library. Written entirely using Kotlin Coroutines, it integrates efficiently into the project.

---

### Design Pattern: MVVM

We adhere to the MVVM (Model-View-ViewModel) design pattern to segregate the application into independent layers:
1. **View** – User Interface.
2. **ViewModel** – Bridges View and Model, maintaining screen state.
3. **Model** – Data source and access to remote services.

---

### Version Control System: Git

The project leverages Git and is hosted on GitHub. The repository houses the source code, which can be downloaded and executed via Android Studio.

Repository Link: [MIREA_Smart_House](https://github.com/Sub4ikGG/MIREA_Smart_House)

---

## Key Components and Services

### MainActivity

- Configures the user interface.
- Verifies device authorization at launch.
- Sets up the Edge-to-Edge display mode.


```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current.applicationContext
            val authorizationViewModel: AuthorizationViewModel = viewModel()
            val authorizationState by authorizationViewModel.state.collectAsStateWithLifecycle(
                initialValue = AuthorizationState.None
            )

            LaunchedEffect(key1 = Unit) {
                authorizationViewModel.checkAuthorization(context = context)
            }

            LaunchedEffect(key1 = authorizationState) {
                (authorizationState as? AuthorizationState.NotAuthorized)?.let {
                    if (it.message != null) {
                        Toast.makeText(
                            context,
                            "Неверные пользовательские данные",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            SmartHouseTheme {
                if (authorizationState is AuthorizationState.Authorized)
                    SmartContent()

                AnimatedVisibility(
                    visible = authorizationState !is AuthorizationState.Authorized,
                    enter = scaleIn(),
                    exit = fadeOut()
                ) {
                    AuthorizationContent(
                        isAuthorizationInProgress = authorizationState is AuthorizationState.Loading,
                        onAuthorize = { user, password ->
                            authorizationViewModel.authorize(
                                context = context,
                                user = user,
                                password = password
                            )
                        }
                    )
                }
            }
        }
    }
}
```


---

### HttpClient

`SmartHouseHttpClient`:
- A singleton class wrapping Ktor’s `HttpClient`.
- Methods:
  - `buildHttpClient` – Initializes the client.
  - `rebuildHttpClient` – Reinitializes the client.
  - `resolveHttpClient` – Retrieves the client instance.


```kotlin
object SmartHouseHttpClient {

    private var smartHouseHttpClient: HttpClient = buildHttpClient()

    private fun buildHttpClient(
        user: String? = null,
        password: String? = null
    ): HttpClient {
        val httpClient = HttpClient(CIO) {
            expectSuccess = false

            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            if (user != null && password != null)
                defaultRequest {
                    header("X-Username", user)
                    header("X-Password", password)
                }
        }

        return httpClient
    }

    fun rebuildHttpClient(user: String, password: String) {
        smartHouseHttpClient = buildHttpClient(user = user, password = password)
    }

    fun resolveHttpClient(): HttpClient = smartHouseHttpClient
}
```


---

### SmartService

Interface for interacting with smart devices. The `RemoteSmartService` implementation includes:
1. **Methods**:
   - `getLampState` – Retrieves the lamp’s status.
   - `toggleLamp` – Switches the lamp’s state.
   - `getLogs` – Obtains log records associated with the lamp.


```kotlin
interface SmartService {
    val state: Flow<LampState>

    suspend fun getLampState(stateLoading: Boolean = true)
    suspend fun toggleLamp()
    suspend fun getLogs(): ServiceLogs

    companion object {
        fun resolve(): SmartService = RemoteSmartService()
    }
}
```


---

### AuthorizationService

Interface for managing authentication. The `RemoteAuthorizationService` implementation:
- Handles authentication checks and executes login procedures.
- Stores user credentials for seamless future sign-ins without requiring data reentry.


```kotlin
interface AuthorizationService {
    val state: Flow<AuthorizationState>

    suspend fun checkAuthorization(context: Context)
    suspend fun authorize(
        context: Context,
        automatic: Boolean = false,
        user: String,
        password: String
    )

    companion object {
        fun resolve(): AuthorizationService = RemoteAuthorizationService()
    }
}
```


---

## App Interface

The app employs Jetpack Compose for declarative UI creation.

---

### AuthorizationContent

**Content**:
- Username and password fields.
- “Sign In” button.

**Process**:
- Validates entered data.
- Redirects the user to `SmartContent` following successful authentication.

---

### SmartContent

**Content**:
- List of smart devices.
- Device controls: turning lamps on/off, viewing logs.

---

## Screenshots

### Authentication interface view.

<img height="600" alt="image" src="https://github.com/user-attachments/assets/5c428e10-4c2c-418a-b8fb-3f7a25a65bf7" />
<img height="600" alt="image" src="https://github.com/user-attachments/assets/a2e9155e-3532-4c02-9763-814d077151fb" />


### Smart device control interface view
<img height="600" alt="image" src="https://github.com/user-attachments/assets/4b7ad8c7-eb1d-4f0b-a3f2-29aa36ad18b1" />
<img height="600" alt="image" src="https://github.com/user-attachments/assets/9d5e0df3-663e-49ea-9050-e7bc9c107d8a" />
<img height="600" alt="image" src="https://github.com/user-attachments/assets/50714fd0-b7da-45a6-b2bb-7a35d50dcf5f" />

---
