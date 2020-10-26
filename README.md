# Swordfish IV

![Swordfish logo](https://www.maxprograms.com/images/swordfish_s.png)

An advanced CAT (Computer Aided Translation) tool based on XLIFF Standard that supports MS Office, DITA, HTML and other document formats.

Swordfish uses TM (Translation Memory) and MT (Machine Translation). Supports segment filtering, terminology, customization and more.

#### Swordfish IV Running on macOS

<a href="https://www.maxprograms.com/tutorials/TranslateFile.mp4"><img src="https://www.maxprograms.com/images/translateFile.png"></a>

## Licenses

Swordfish is available in two modes:

- Personal Use of Source Code
- Yearly Subscriptions

### Personal Use of Source Code

Source code of Swordfish is free for personal use. Anyone can download the source code, compile, modify and use it at no cost in compliance with the accompanying license terms.

You can subscribe to [Maxprograms Support](https://groups.io/g/maxprograms/) at Groups.io and request peer assistance for the source code version there.

### Subscriptions

This project covers Swordfish IV and is in early development stage. Subscriptions are not available for purchase yet.

Free installers for **Swordfish IV Preview 6** are available at [https://www.maxprograms.com/downloads/preview.html](https://www.maxprograms.com/downloads/preview.html).


## Related Projects

- [OpenXLIFF Filters](https://github.com/rmraya/OpenXLIFF)

## Requirements

- JDK 11 or newer is required for compiling and building. Get it from [AdoptOpenJDK](https://adoptopenjdk.net/).
- Apache Ant 1.10.7 or newer. Get it from [https://ant.apache.org/](https://ant.apache.org/)
- Node.js 12.16.0 LTS or newer. Get it from [https://nodejs.org/](https://nodejs.org/)

## Building

- Checkout this repository.
- Point your `JAVA_HOME` environment variable to JDK 11
- Run `ant` to compile the Java code
- Run `npm install` to download and install NodeJS dependencies
- Run `npm start` to launch Swordfish

### Steps for building

``` bash
  git clone https://github.com/rmraya/Swordfish.git
  cd Swordfish
  ant
  npm install
  npm start
```

Compile once and then simply run `npm start` to start Swordfish
