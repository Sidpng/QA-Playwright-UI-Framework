# Playwright UI Testing Framework - Architecture & Design

**Author:** Siddhartha Upadhyay  
**Version:** 1.0  
**Last Updated:** June 2026

---

## 📋 Overview

This framework is designed for **modern UI automation** using Playwright + TestNG, with integrated **GenAI capabilities** for intelligent test scenario generation and coverage analysis.

### Key Principles:
- **Page Object Model (POM)** for maintainable test code
- **Reusable Base Test Class** with common browser operations
- **GenAI-Powered Test Scenario Generation** using Claude API
- **Cross-browser Support** (Chromium, Firefox, WebKit/Safari)
- **Comprehensive Reporting** with screenshots and detailed logs

---

## 🏗️ Project Structure

```
QA-Playwright-UI-Framework/
├── src/
│   ├── test/
│   │   ├── java/com/qa/ui/
│   │   │   ├── base/
│   │   │   │   └── BaseTest.java                 # Browser setup & common methods
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.java               # Login page POM
│   │   │   │   ├── InventoryPage.java           # Products page POM
│   │   │   │   └── CheckoutPage.java            # Checkout page POM
│   │   │   ├── tests/
│   │   │   │   ├── LoginTest.java               # Login test cases
│   │   │   │   ├── InventoryTest.java           # Product test cases
│   │   │   │   └── E2ECheckoutTest.java         # End-to-end tests
│   │   │   ├── genai/
│   │   │   │   └── UITestScenarioGenerator.java # Claude API integration
│   │   │   └── utils/
│   │   │       ├── ScreenshotUtils.java         # Screenshot utilities
│   │   │       └── ReportingUtils.java          # Reporting helper
│   │   └── resources/
│   │       ├── config.properties                # Global configuration
│   │       └── testng.xml                       # Test suite configuration
├── docs/
│   ├── UI_TESTING_WITH_GENAI.md                 # GenAI integration guide
│   └── PAGE_OBJECT_MODEL_GUIDE.md               # POM best practices
├── screenshots/                                  # Test screenshots
├── pom.xml                                       # Maven dependencies
└── README.md
```

---

## 🔧 Core Components

### 1. **BaseTest.java** - Browser Setup & Common Methods

Handles Playwright browser initialization and lifecycle:

```java
@BeforeClass
public void setup() {
    // Launches browser (chromium by default)
    // Creates browser context and page
    // Sets viewport size
}

@AfterClass
public void tearDown() {
    // Closes page, context, and browser
    // Cleans up resources
}
```

**Available Helper Methods:**
- `navigateTo(url)` - Navigate to specific URL
- `goToBaseURL()` - Navigate to configured base URL
- `clickElement(selector)` - Click element by CSS selector
- `fillInput(selector, text)` - Fill text input
- `getText(selector)` - Get element text
- `waitForElement(selector, timeout)` - Wait for element visibility
- `isElementVisible(selector)` - Check if element is visible
- `getCurrentURL()` - Get current page URL
- `executeScript(script)` - Execute JavaScript

---

### 2. **Page Object Model (POM)**

Encapsulates page elements and interactions:

```java
public class LoginPage {
    private final Page page;
    
    // Locators
    private final String USERNAME_INPUT = "#user-name";
    private final String PASSWORD_INPUT = "#password";
    private final String LOGIN_BUTTON = "#login-button";
    
    public LoginPage(Page page) {
        this.page = page;
    }
    
    // Action methods
    public void login(String username, String password) {
        page.fill(USERNAME_INPUT, username);
        page.fill(PASSWORD_INPUT, password);
        page.click(LOGIN_BUTTON);
    }
}
```

**Benefits:**
- ✅ Centralized locator management
- ✅ Reduced code duplication
- ✅ Easier maintenance (update once, affects all tests)
- ✅ Clear separation between test logic and UI interactions

---

### 3. **GenAI Test Scenario Generator**

Intelligently generates test scenarios for UI testing:

```java
UITestScenarioGenerator generator = new UITestScenarioGenerator();

// Generate test scenarios
String scenarios = generator.generateUITestScenarios(
    "LoginPage",
    "Login page with username/password fields and login button"
);

// Generate user interaction flows
String flow = generator.generateUserInteractionFlow(
    "User logs in with valid credentials"
);

// Generate form validation tests
String validations = generator.generateFormValidationTests(
    "username:text, password:password"
);
```

**Capabilities:**
- ✅ Happy path scenario generation
- ✅ Error scenario identification
- ✅ Form validation test cases
- ✅ User interaction flow mapping
- ✅ Accessibility test suggestions
- ✅ Cross-browser scenario planning

---

### 4. **Test Organization**

Tests follow the **AAA Pattern** (Arrange-Act-Assert):

```java
@Test
public void testLoginWithValidCredentials() {
    // Arrange
    LoginPage loginPage = new LoginPage(page);
    String username = "standard_user";
    String password = "secret_sauce";
    
    // Act
    loginPage.login(username, password);
    
    // Assert
    Assert.assertTrue(loginPage.isLoginSuccessful());
}
```

---

## 🤖 GenAI Integration - How It Works

### Step 1: Page Definition

Define the page structure and elements:

```
LoginPage has:
- Username text input (#user-name)
- Password text input (#password)
- Login button (#login-button)
- Error message container ([data-test='error'])
```

### Step 2: GenAI Scenario Generation

Use UITestScenarioGenerator to identify all important test scenarios:

```java
String scenarios = generator.generateUITestScenarios(
    "LoginPage",
    "Page contains username/password fields and login button"
);
```

**Output includes:**
- Valid login with correct credentials
- Invalid login with wrong password
- Missing username error handling
- Missing password error handling
- Locked account scenarios
- SQL injection attempt handling
- Long input field testing

### Step 3: Implement Test Cases

Implement the suggested scenarios in your test class:

```java
@Test
public void testLoginWithValidCredentials() { ... }

@Test
public void testLoginWithInvalidPassword() { ... }

@Test
public void testLoginWithEmptyUsername() { ... }
```

### Step 4: GenAI Assertion Enhancement

Use UITestScenarioGenerator to optimize assertions:

```java
String assertions = generator.generateUIAssertions(
    "Error message box on login page"
);
// Output: Suggestions for visibility, text content, styling checks
```

### Step 5: Accessibility Testing

Generate WCAG compliance test cases:

```java
String a11yTests = generator.generateAccessibilityTests("LoginPage");
// Output: Keyboard navigation, screen reader, color contrast tests
```

---

## 📊 Browser Configuration

### Supported Browsers

| Browser | Type | Notes |
|---------|------|-------|
| Chromium | Default | Best performance, most widely tested |
| Firefox | Alternative | Good for cross-browser coverage |
| WebKit | Alternative | Safari-compatible testing |

### Configuration in BaseTest

```java
protected String browserType = "chromium"; // Change to firefox or webkit

@BeforeClass
public void setup() {
    // Launches selected browser
    // All subsequent operations use this browser
}
```

---

## 🚀 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn clean test -Dtest=LoginTest
```

### Run Specific Test Method
```bash
mvn clean test -Dtest=LoginTest#testLoginWithValidCredentials
```

### Run on Specific Browser
```bash
# Currently defined in BaseTest; extend to use system property
mvn clean test -Dbrowser=firefox
```

### Generate GenAI Scenarios
```bash
mvn exec:java -Dexec.mainClass="com.qa.ui.genai.UITestScenarioGenerator"
```

---

## 📈 Best Practices Implemented

1. **Page Object Model**: Encapsulates UI element locators and interactions
2. **Separation of Concerns**: Test logic separated from element locators
3. **DRY Principle**: Reusable helper methods in BaseTest
4. **AAA Pattern**: Clear Arrange-Act-Assert structure
5. **Meaningful Assertions**: Clear, specific validation checks
6. **Screenshot Capability**: Capture failures for debugging
7. **GenAI-Augmented Coverage**: Automated scenario discovery
8. **Cross-browser Support**: Tests can run on multiple browsers

---

## 🎓 Example: Writing a New Test

### Step 1: Create Page Object

```java
public class ProductsPage {
    private final Page page;
    private final String ADD_TO_CART_BTN = "[data-test='add-to-cart-sauce-labs-backpack']";
    private final String CART_ICON = "[data-test='shopping-cart-link']";
    
    public ProductsPage(Page page) {
        this.page = page;
    }
    
    public void addProductToCart() {
        page.click(ADD_TO_CART_BTN);
    }
    
    public void openCart() {
        page.click(CART_ICON);
    }
}
```

### Step 2: Write Test Case

```java
@Test
public void testAddProductToCart() {
    // Arrange
    LoginPage loginPage = new LoginPage(page);
    loginPage.login("standard_user", "secret_sauce");
    ProductsPage productsPage = new ProductsPage(page);
    
    // Act
    productsPage.addProductToCart();
    productsPage.openCart();
    
    // Assert
    Assert.assertTrue(page.isVisible("[data-test='cart-contents-container']"));
}
```

### Step 3: Enhance with GenAI

```java
// Ask GenAI for additional scenarios
UITestScenarioGenerator generator = new UITestScenarioGenerator();
String additionalScenarios = generator.generateUITestScenarios(
    "ProductsPage",
    "Page with product listings and add-to-cart functionality"
);
// Implement additional test cases based on suggestions
```

---

## 🔧 Advanced Features

### Cross-browser Execution

Extend framework to run same tests on multiple browsers:

```java
@DataProvider
public Object[][] browsers() {
    return new Object[][] {
        { "chromium" },
        { "firefox" },
        { "webkit" }
    };
}

@Test(dataProvider = "browsers")
public void testOnMultipleBrowsers(String browser) {
    browserType = browser;
    setup();
    // Test execution
    tearDown();
}
```

### Parallel Execution

Configure testng.xml for parallel execution:

```xml
<suite name="UI Tests" parallel="methods" thread-count="4">
    <!-- Test definitions -->
</suite>
```

---

## 📞 Support

For framework enhancements or issues, refer to the [GitHub Issues](https://github.com/Sidpng/QA-Playwright-UI-Framework/issues) section.

---

**Last Reviewed:** June 22, 2026  
**Maintained By:** Siddhartha Upadhyay
