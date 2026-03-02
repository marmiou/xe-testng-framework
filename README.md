# XE TestNG Selenium Framework

Modern UI automation framework using Java, Selenium, TestNG, Maven, and Allure Reports.

Designed following industry best practices (Page Object Model, parallel execution, CI/CD integration).

---

# 🚀 Tech Stack

Java 21  
Selenium 4  
TestNG  
Maven  
Allure Reporting  
GitHub Actions (CI/CD)  

---

# 📦 Project Structure

```
xe-testng-framework
│
├── LICENSE
├── pom.xml
├── testng.xml
│
├── src
│   ├── main/java/gr/xe
│   │   ├── core
│   │   │   └── DriverFactory.java
│   │   │
│   │   ├── pages
│   │   │   ├── BasePage.java
│   │   │   ├── HomePage.java
│   │   │   └── SearchResultsPage.java
│   │   │
│   │   ├── components
│   │   │
│   │   └── utils
│   │
│   └── test
│       ├── java/gr/xe
│       │   ├── core
│       │   │   └── BaseTest.java
│       │   │
│       │   └── tests
│       │       └── RentSearchTest.java
│       │
│       └── resources
│           └── allure.properties
│
└── README.md
```

---

# ⚙️ Prerequisites

## Java 21

Install:

```
brew install openjdk@21
```

Verify:

```
java -version
```

---

## Maven

Install:

```
brew install maven
```

Verify:

```
mvn -version
```

---

## Browsers

Required:

Google Chrome  

Supported:

Firefox  

Optional:

Microsoft Edge  

---

# ▶️ Run Tests

Run all tests:

```
mvn clean test
```

Run specific suite:

```
mvn test -DsuiteXmlFile=testng.xml
```

---

# 🧪 Headless Execution

Run in headless mode:

```
mvn test -Dheadless=true
```

In CI environments, headless mode is enabled automatically.

---

# 📊 Allure Report

## Run locally

```
allure serve target/allure-results
```

---

## View CI Allure Report

GitHub Actions:

https://github.com/marmiou/xe-testng-framework/actions

Steps:

1. Click **Pages build and deployment**
2. Open latest run
3. Click report link

Direct report link:

https://marmiou.github.io/xe-testng-framework/

---

# 🌐 Multi-browser Support

Configured via:

testng.xml

Example:

```
<parameter name="browser" value="chrome"/>
```

Supported:

Chrome  
Firefox  
Edge  

---

# ⚡ Parallel Execution

```
<suite parallel="tests" thread-count="3">
```

---

# 🤖 CI/CD

GitHub Actions pipeline:

• Runs tests automatically  
• Uses headless mode  
• Generates Allure report  
• Publishes report to GitHub Pages  

Pipeline:

https://github.com/marmiou/xe-testng-framework/actions

Published report:

https://marmiou.github.io/xe-testng-framework/

---

# 🧪 Example Test

```java
@Test
public void openHomePage(){

    driver.get("https://www.xe.gr");

    assertThat(driver.getTitle())
        .isNotEmpty();

}
```

---

# 🧠 Framework Features

Thread-safe DriverFactory  
Page Object Model  
TestNG integration  
Allure reporting  
Parallel execution  
Multi-browser support  
CI/CD ready  

---

# 👩‍💻 Author

Markella Efthymiou  

QA Automation Engineer  

GitHub:  

https://github.com/marmiou/
