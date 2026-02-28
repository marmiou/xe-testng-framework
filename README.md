# XE TestNG Selenium Framework

Modern UI automation framework using **Java, Selenium, TestNG, Maven, and Allure Reports**.

Designed following **industry best practices (Page Object Model, parallel execution, CI/CD integration)**.

---

# 🚀 Tech Stack

* Java 21
* Selenium 4
* TestNG
* Maven
* Allure Reporting
* GitHub Actions (CI/CD)

---

# 📦 Project Structure

```
xe-testng-framework
│
├── pom.xml
├── testng.xml
│
├── src
│   ├── main/java/gr/xe
│   │   ├── core        → DriverFactory, Base classes
│   │   ├── pages       → Page Objects
│   │   ├── components  → Reusable UI components
│   │   └── utils       → Utilities
│   │
│   └── test/java/gr/xe/tests
│       └── Test classes
│
└── src/test/resources
    └── allure.properties
```

---

# ⚙️ Prerequisites

## Java 21

Install:

```bash
brew install openjdk@21
```

Verify:

```bash
java -version
```

---

## Maven

Install:

```bash
brew install maven
```

Verify:

```bash
mvn -version
```

---

## Browsers

Required:

* Google Chrome

Supported:

* Firefox

Optional:

* Microsoft Edge

> Microsoft Edge is supported by the framework but may require additional driver setup depending on the operating system and environment.

---

# ▶️ Run Tests

Run all tests:

```bash
mvn clean test
```

Run specific suite:

```bash
mvn test -DsuiteXmlFile=testng.xml
```
# 🧪 Headless Execution

The framework supports both **headed** and **headless** execution.

## Local execution (default)

Runs with visible browser:

```bash
mvn test
```

---

## Headless execution

Run tests in headless mode:

```bash
mvn test -Dheadless=true
```

---

## CI execution

In CI environments (GitHub Actions), headless mode is automatically enabled.

No additional configuration is required.

---

This allows flexible execution depending on the environment.



---

# 📊 Allure Report

Generate and open report:

```bash
allure serve target/allure-results
```

---

# 🌐 Multi-browser Support

Configured via:

```
testng.xml
```

Example:

```xml
<parameter name="browser" value="chrome"/>
```

Supported by the framework:

* Chrome (default)
* Firefox
* Edge (optional)

---

# ⚡ Parallel Execution

Enabled via TestNG configuration:

```xml
<suite parallel="tests" thread-count="3">
```

---

# 🤖 CI/CD

GitHub Actions pipeline:

* Executes tests automatically
* Generates Allure results
* Uploads test artifacts

---

# 🧪 Example Test

```java
@Test
public void openHomePage() {

    driver.get("https://www.xe.gr");

    assertThat(driver.getTitle())
        .isNotEmpty();

}
```

---

# 🧠 Framework Features

* Thread-safe DriverFactory
* BaseTest abstraction
* TestNG integration
* Allure reporting
* Parallel execution support
* Multi-browser support
* CI/CD ready architecture

---

# 👩‍💻 Author

**Markella Efthymiou**

QA Automation Engineer

GitHub:
https://github.com/marmiou/

---

