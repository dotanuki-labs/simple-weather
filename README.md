# Android Bootstrap
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Maintainability](https://api.codeclimate.com/v1/badges/42704b7b56bbdba33b99/maintainability)](https://codeclimate.com/github/dotanuki-labs/android-bootstrap/maintainability)
[![Main](https://github.com/dotanuki-labs/simple-weather/actions/workflows/main.yml/badge.svg)](https://github.com/dotanuki-labs/simple-weather/actions/workflows/main.yml)

> An Android App for demo purposes

## About

> TODO

Here you will find a codebase powered by tests in all semantic levels (unit, integration, acceptance)
as well an architectural design that promotes discipline over state, using Kotlin Coroutines +
Kotlin Flows as foundation for a pragmatic unidirectional dataflow implementation.

## Project Overview

> TODO

## Implementation Highlights

- **OkHttp** + **Retrofit** for networking
- **Kotlinx.Serialization** for Json handling
- **Kotlinx.Coroutines** for asynchrounous processing
- Unidirectional data-flow driven by **Kotlin Flows**
- Manual dependencies/instances management at runtime driven by **Kodein**
- No annotations processors (therefore no `kapt`)
- No Fragments

## Building and Running

If you want a simple run over some task of the CI pipeline, just use the companion script

> TODO

Also note that local builds **require JDK11**, since this project compiles against Java11 bytecode.

In addition to that, this project requires
[latest stable release of Android Studio](https://developer.android.com/studio/releases),
ie, 7.x.z (Artic Fox) or newer.

## Author

Coded by Ubiratan Soares (follow me on [Twitter](https://twitter.com/ubiratanfsoares))

## License

```
The MIT License (MIT)

Copyright (c) 2022 Dotanuki Labs

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
