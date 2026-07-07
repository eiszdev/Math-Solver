# Math Solver

Math Solver is a Kotlin Multiplatform project focused on building a reusable math engine for solving algebra problems. The long-term goal is to support mobile apps that scan a handwritten or printed equation with the camera, convert it to text, and show a step-by-step solution.

The current priority is the independent `Math-Engine` module. Camera, OCR, and app UI integrations will be built around it later, without coupling platform-specific code to the engine.

## Current Status

`Math-Engine` currently supports a first MVP for linear equations with one variable:

- Tokenizing mathematical text.
- Parsing tokens into an expression tree.
- Pretty-printing expressions and equations.
- Solving basic linear equations.
- Handling implicit multiplication such as `2x` and `2(x + 1)`.
- Handling negative coefficients and grouped negative expressions.

Example equations covered by tests:

```text
x + 3 = 7
2x = 10
3x - 4 = x + 6
2(x + 1) = 10
(3x + 6) / 3 = 10
-x + 2 = 5
-2x + 2 = 10
-(x + 2) = 5
```

## Project Structure

```text
Math-Engine/        Kotlin Multiplatform math engine
sample/composeApp/  Sample Compose Multiplatform app
sample/iosApp/      iOS sample app host
```

## Run Tests

```bash
./gradlew :Math-Engine:check
```

On Windows:

```powershell
.\gradlew.bat :Math-Engine:check
```

## Run Sample App

- Desktop JVM: `./gradlew :sample:composeApp:run`
- Android: open the project in Android Studio and run the sample app.
- iOS: open `sample/iosApp/iosApp.xcodeproj` in Xcode and run the sample app.

## Roadmap

- Add a public `MathEngine` API with typed success and failure results.
- Replace internal generic errors with domain-specific error types.
- Add a simplification layer for expressions.
- Generate step-by-step solutions.
- Expand equation support beyond simple linear equations.
- Add OCR/camera integration in a separate platform-aware layer.
- Build polished Android and iOS apps using the engine.

## License

License to be decided before public release.
