# QA Playwright UI Testing Framework

![GitHub Stars](https://img.shields.io/github/stars/Sidpng/QA-Playwright-UI-Framework?style=social)
![Java Version](https://img.shields.io/badge/Java-17-blue)
![Playwright](https://img.shields.io/badge/Playwright-1.44.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.8.1-orange)

A **modern, production-ready UI testing framework** built with Playwright, TestNG, and **integrated GenAI capabilities** for intelligent test scenario generation.

---

## 🎯 Purpose

This framework demonstrates **advanced UI automation** with best practices:
- ✅ Modern Playwright browser automation (faster than Selenium)
- ✅ Page Object Model (POM) for maintainable tests
- ✅ GenAI-powered test scenario generation (Claude API integration)
- ✅ Cross-browser support (Chromium, Firefox, WebKit)
- ✅ Comprehensive assertions and validations
- ✅ CI/CD ready with GitHub Actions

**Target Application:** SauceDemo (https://www.saucedemo.com)

---

## 📋 Key Features

| Feature | Description |
|---------|-------------|
| **Page Object Model** | Encapsulates page elements and interactions |
| **Base Test Class** | Reusable browser & element methods |
| **GenAI Integration** | Claude API for test scenario & flow generation |
| **Cross-browser** | Run tests on Chromium, Firefox, WebKit |
| **Smart Assertions** | Clear, specific validation checks |
| **Screenshots** | Automatic capture on test failures |
| **Flexible Config** | Centralized browser & timeout settings |
| **Accessibility Testing** | WCAG compliance scenario generation |

---

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- ANTHROPIC_API_KEY environment variable (for GenAI features)

### Installation

```bash
# Clone the repository
git clone https://github.com/Sidpng/QA-Playwright-UI-Framework.git
cd QA-Playwright-UI-Framework

# Set up your Claude API key
export ANTHROPIC_API_KEY="sk-ant-your-key-here"

# Run tests
mvn clean test
```

### Set API Key (Windows PowerShell)

```powershell
$env:ANTHROPIC_API_KEY = "sk-ant-your-key-here"
mvn clean test
```

---

## 📁 Project Structure

```
QA-Playwright-UI-Framework/
├── src/test/java/com/qa/ui/
│   ├── base/
│   │   └── BaseTest.java                     # Browser initialization & helpers
│   ├── pages/
│   │   ├── LoginPage.java                   # Login page POM
│   │   ├── InventoryPage.java               # Products page POM
│   │   └── CheckoutPage.java                # Checkout page POM
│   ├── tests/
│   │   ├── LoginTest.java                   # Login test cases
│   │   ├── InventoryTest.java               # Product browsing tests
│   │   └── E2ECheckoutTest.java             # End-to-end flow tests
│   ├── genai/
│   │   └── UITestScenarioGenerator.java     # Claude API integration
│   └── utils/
│       ├── ScreenshotUtils.java             # Screenshot utilities
│       └── ReportingUtils.java              # Report utilities
├── src/test/resources/
│   ├── config.properties                    # Configuration
│   └── testng.xml                           # Test suite config
├── docs/
│   ├── FRAMEWORK_ARCHITECTURE.md            # Detailed design
│   └── UI_TESTING_WITH_GENAI.md            # GenAI guide
├── screenshots/                             # Test screenshots
├── pom.xml
└── README.md
```

---

## 🧪 Usage Examples

### Running All Tests

```bash
mvn clean test
```

### Running Specific Test Class

```bash
mvn clean test -Dtest=LoginTest
```

### Running Specific Test Method

```bash
mvn clean test -Dtest=LoginTest#testLoginWithValidCredentials
```

### Generate Test Scenarios with GenAI

```bash
mvn exec:java -Dexec.mainClass="com.qa.ui.genai.UITestScenarioGenerator"
```

---

## 📚 Test Examples

### Example 1: Simple Login Test

```java
@Test
public void testLoginWithValidCredentials() {
    goToBaseURL();
    LoginPage loginPage = new LoginPage(page);
    
    loginPage.login("standard_user", "secret_sauce");
    
    Assert.assertTrue(loginPage.isLoginSuccessful());
}
```

### Example 2: Form Validation Test

```java
@Test
public void testLoginWithEmptyUsername() {
    goToBaseURL();
    LoginPage loginPage = new LoginPage(page);
    
    loginPage.login("", "secret_sauce");
    
    Assert.assertTrue(loginPage.isErrorMessageDisplayed());
    Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
}
```

### Example 3: GenAI-Powered Scenario Generation

```java
UITestScenarioGenerator generator = new UITestScenarioGenerator();

// Generate test scenarios for login page
String scenarios = generator.generateUITestScenarios(
    "LoginPage",
    "Username and password fields with login button"
);
System.out.println(scenarios);

// Generate user interaction flow
String flow = generator.generateUserInteractionFlow(
    "User logs in and views products"
);
System.out.println(flow);

// Generate form validation tests
String validations = generator.generateFormValidationTests(
    "username:text, password:password"
);
System.out.println(validations);
```

---

## 🤖 GenAI Features

### 1. Intelligent Test Scenario Generation

```java
String scenarios = generator.generateUITestScenarios(
    "LoginPage",
    "Contains username/password fields and login button"
);
```

Generates:
- Happy path scenarios
- Edge cases (empty fields, long inputs)
- Error scenarios (invalid credentials, locked accounts)
- Security test cases (SQL injection, XSS patterns)
- Performance considerations

### 2. User Interaction Flow Mapping

```java
String flow = generator.generateUserInteractionFlow(
    "User logs in, browses products, adds to cart, and checks out"
);
```

Provides:
- Step-by-step action sequence
- Element selectors for each step
- Expected page states
- Error recovery paths
- Validation checkpoints

### 3. Form Validation Testing

```java
String validations = generator.generateFormValidationTests(
    "email:email, password:password, age:number"
);
```

Includes:
- Valid input test cases
- Invalid input test cases
- Boundary value testing
- Special character handling
- Cross-field validation

### 4. Accessibility Testing

```java
String a11yTests = generator.generateAccessibilityTests("LoginPage");
```

Covers:
- WCAG 2.1 compliance
- Keyboard navigation
- Screen reader compatibility
- Color contrast validation
- Focus management

---

## 🔌 Page Object Model Examples

### LoginPage.java

```java
public class LoginPage {
    private final Page page;
    
    private final String USERNAME_INPUT = "#user-name";
    private final String PASSWORD_INPUT = "#password";
    private final String LOGIN_BUTTON = "#login-button";
    
    public LoginPage(Page page) {
        this.page = page;
    }
    
    public void login(String username, String password) {
        page.fill(USERNAME_INPUT, username);
        page.fill(PASSWORD_INPUT, password);
        page.click(LOGIN_BUTTON);
    }
    
    public boolean isLoginSuccessful() {
        return page.isVisible(".inventory_container");
    }
}
```

### Usage in Test

```java
LoginPage loginPage = new LoginPage(page);
loginPage.login("user", "password");
Assert.assertTrue(loginPage.isLoginSuccessful());
```

---

## 🌐 Cross-Browser Testing

### Run on Chromium (Default)

```java
protected String browserType = "chromium";
```

### Run on Firefox

```java
protected String browserType = "firefox";
```

### Run on WebKit (Safari)

```java
protected String browserType = "webkit";
```

---

## 📊 Test Reports

Tests generate execution reports in:

```
target/ExtentReports/ExtentReport.html
```

Open in browser to view:
- Test execution summary
- Pass/fail breakdown
- Detailed logs
- Screenshots
- Timing information

---

## 🛠️ Development Workflow

### Adding a New Test Page

1. Create Page Object class extending common patterns
2. Define locators as class constants
3. Create action methods encapsulating interactions

```java
public class NewPage {
    private final Page page;
    
    private final String BUTTON = "[data-test='button']";
    
    public NewPage(Page page) {
        this.page = page;
    }
    
    public void clickButton() {
        page.click(BUTTON);
    }
}
```

### Adding a New Test Case

1. Extend BaseTest class
2. Follow AAA pattern (Arrange-Act-Assert)
3. Use POM for element interactions

```java
@Test
public void testNewScenario() {
    // Arrange
    NewPage newPage = new NewPage(page);
    
    // Act
    newPage.clickButton();
    
    // Assert
    Assert.assertTrue(page.isVisible("[result]"));
}
```

---

## ✨ Latest Updates

**2026-06-22** *(Initial Release)*
- ✅ Playwright framework with TestNG
- ✅ Base test class with browser management
- ✅ LoginPage POM with comprehensive test cases
- ✅ GenAI integration for test scenario generation
- ✅ Cross-browser support (Chromium, Firefox, WebKit)
- ✅ Detailed documentation and examples
- 📝 **Note:** Currently targeting SauceDemo; multi-page POM expansion coming in v1.1

---

## 🤝 Contributing

Contributions welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

---

## 📝 License

MIT License - see [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Siddhartha Upadhyay**
- 8+ years QA Professional
- Cognizant Senior Associate – QA
- GitHub: [@Sidpng](https://github.com/Sidpng)
- Email: razor9nfs@gmail.com

---

## 📞 Support

- **Questions?** Open an issue on [GitHub Issues](https://github.com/Sidpng/QA-Playwright-UI-Framework/issues)
- **Documentation?** See [docs/](./docs/) folder
- **Ideas?** Connect on [LinkedIn](https://linkedin.com/in/sidpng)

---

**Last Updated:** June 22, 2026  
**Status:** Active & Maintained ✅

---

## 🎓 Learning Resources

- [Playwright Documentation](https://playwright.dev/java/)
- [TestNG Documentation](https://testng.org/)
- [Page Object Model](https://martinfowler.com/bliki/PageObject.html)
- [Claude API Documentation](https://docs.anthropic.com/)

---

**Happy Testing! 🚀**
