# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ComicsDBClient is an Android app (Kotlin) for browsing the Czech comics database at comicsdb.cz. Single-module app under package `cz.kutner.comicsdb`.

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (minified + shrunk)
./gradlew assembleRelease

# Full build (compile + lint + test)
./gradlew build

# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Check for dependency updates
./gradlew dependencyUpdates --no-parallel
```

## Build Details

- **Gradle**: Kotlin DSL, version catalog at `gradle/libs.versions.toml`, configuration cache enabled
- **AGP**: 9.x with built-in Kotlin support (no separate `kotlin.android` plugin)
- **Compile/Target SDK**: 36, **Min SDK**: 24, **Java**: 17
- Version code/name derived from git (`git rev-list` count and `git describe --tags`)
- Compose enabled via `kotlin.compose` plugin
- kotlinx.serialization via `kotlin.serialization` plugin
- No KAPT, no data binding, no view binding
- R8/ProGuard enabled for release builds

## Architecture

**Single-Activity MVVM with StateFlow + Jetpack Compose + Navigation Compose**

Data flow: Composable Screen → ViewModel (StateFlow) → Retrofit Service → comicsdb.cz REST API

### ViewState pattern

All screens use `ViewState<T>` sealed class (`ui/components/ViewState.kt`) with Loading, Content, Error states. `ViewStateContainer` composable switches on the state to show the appropriate UI.

### Base classes in `abstracts/` package

- `AbstractViewModel<Data: Item>` — single item loading via `MutableStateFlow<ViewState<Data>>`
- `AbstractPagedViewModel<Data: Item>` — paginated list loading with page tracking, infinite scroll support, `isLoading` guard against duplicate loads

### Code organization

Feature-per-package: each feature (e.g. `comicsList/`, `comicsDetail/`, `authorList/`) contains its ViewModel and `*Screen.kt` composable. Shared code lives in `abstracts/`, `model/`, `network/`, `navigation/`, `ui/`, `utils/`.

- `ui/theme/` — `ComicsDBTheme`, `Color.kt` (primary=#C00000)
- `ui/components/` — `ViewState`, `ViewStateContainer`, `LoadingView`, `ErrorView`, `EmptyView`, `CoilImage`, `HtmlText`
- `navigation/` — `AppNavHost`, type-safe route classes (`Routes.kt`), `ImageCache`
- `main/` — `MainActivity` + `MainScreen` with `ModalNavigationDrawer` and screen switching

### Key libraries

| Library | Purpose |
|---------|---------|
| Compose BOM + Material 3 | UI framework and design system |
| Navigation Compose | Type-safe navigation with `@Serializable` route classes |
| Koin 4.x | DI — modules in `di/`, initialized in `ComicsDBApplication`, `koinViewModel` in Compose |
| Retrofit 3.0 + kotlinx.serialization | REST client — services in feature packages, base URL `https://comicsdb.cz` |
| OkHttp 5.x | HTTP client with 10MB cache, 120s timeouts |
| Coil 3.x + coil-compose | Image loading in Compose (`AsyncImage`) and HTML (`CoilImageGetter`) |
| Telephoto | Pinch-to-zoom image viewing (`ZoomableAsyncImage`) |
| ProfileInstaller | Baseline profile support for startup optimization |
| Timber | Logging (debug builds only) |

### Navigation

- Single-Activity architecture with `NavHost` in `AppNavHost.kt`
- Type-safe routes using `@Serializable` data classes/objects in `Routes.kt`
- Deep links via `navDeepLink<T>()`: `comicsdb.cz/comics/{id}`, `/author/{id}`, `/autor/{id}`, `/serie/{id}`
- `MainScreen` with `ModalNavigationDrawer` hosts list screens (Comics, News, Series, Authors, Classified, Forum, About)
- `DetailScaffold` composable provides consistent back-navigation top bar for detail screens
- Search with debounced text input in TopAppBar, results via `SearchScreen` with 3 tabs
- Image viewer with `HorizontalPager` + `ZoomableAsyncImage`, images passed via `ImageCache` singleton

### HTML rendering

`HtmlText` composable (`ui/components/SharedComponents.kt`) uses `AndroidView` wrapping `TextView` with `CoilImageGetter` for rendering HTML content with inline images. Used in news, forum, classified, comments, and author bio screens.

### Models

All data models are `@Serializable` (kotlinx.serialization) and `@Immutable` (Compose). They implement the `Item` marker interface. List models (e.g. `Comics`, `Series`) and detail models (e.g. `ComicsDetail`, `SeriesDetail`) are separate classes. Date fields use custom `DateSerializer`. Some models parse HTML content via `parseAsHtml()`.
