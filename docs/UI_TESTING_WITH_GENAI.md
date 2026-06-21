# UI Testing with GenAI - Complete Guide

**Version:** 1.0  
**Author:** Siddhartha Upadhyay  
**Date:** June 2026

---

## 🎯 What is GenAI-Augmented UI Testing?

Traditional UI testing requires manual planning of test scenarios. GenAI-Augmented UI testing uses **Claude AI** to:

✅ **Intelligently generate user interaction flows** you might have overlooked  
✅ **Identify form validation edge cases** automatically  
✅ **Suggest accessibility test cases** for WCAG compliance  
✅ **Recommend assertion points** for comprehensive validation  
✅ **Generate cross-browser scenarios** systematically  

---

## 🚀 Getting Started

### 1. Set Up Claude API Access

```bash
# Export your API key (Linux/Mac)
export ANTHROPIC_API_KEY="sk-ant-..."

# Or for Windows PowerShell:
$env:ANTHROPIC_API_KEY = "sk-ant-..."
```

### 2. Verify Anthropic SDK in pom.xml

Already included:

```xml
<dependency>
    <groupId>com.anthropic</groupId>
    <artifactId>anthropic-sdk-java</artifactId>
    <version>0.2.0</version>
</dependency>
```

### 3. Run Test Scenario Generator

```bash
mvn exec:java -Dexec.mainClass="com.qa.ui.genai.UITestScenarioGenerator"
```

---

## 📚 Usage Examples

### Example 1: Generate Test Scenarios for Login Page

**Input:**
```java
UITestScenarioGenerator generator = new UITestScenarioGenerator();

String scenarios = generator.generateUITestScenarios(
    "LoginPage",
    "Contains username input, password input, login button, and error message display area"
);

System.out.println(scenarios);
```

**GenAI Output:**
```
1. Happy Path - Valid Login
   - User enters valid username and password
   - Clicks login button
   - Expected: Redirects to inventory/home page
   - Priority: Critical

2. Invalid Credentials
   - User enters correct username but wrong password
   - Clicks login button
   - Expected: Error message appears, user stays on login page
   - Priority: High

3. Empty Username
   - User leaves username empty, enters password
   - Clicks login button
   - Expected: "Username is required" error displays
   - Priority: High

4. Empty Password
   - User enters username, leaves password empty
   - Clicks login button
   - Expected: "Password is required" error displays
   - Priority: High

5. Locked Account
   - User enters credentials for locked account
   - Clicks login button
   - Expected: "User has been locked out" message appears
   - Priority: Medium

6. SQL Injection Attempt
   - User enters "' OR '1'='1" as username
   - Clicks login button
   - Expected: Handled securely, displays error or normal behavior
   - Priority: High (Security)

7. Very Long Input
   - User enters 500+ character string in username field
   - Clicks login button
   - Expected: Input validated or truncated properly
   - Priority: Medium

8. Special Characters
   - User enters special characters (!@#$%^&*) in password
   - Clicks login button
   - Expected: Handled properly
   - Priority: Medium

9. Whitespace Handling
   - User enters " " (spaces) in username/password
   - Clicks login button
   - Expected: Treated as invalid
   - Priority: Medium

10. Rapid Successive Logins
    - User rapidly clicks login button multiple times
    - Expected: Graceful handling, no double-submit
    - Priority: Medium
```

---

### Example 2: Generate User Interaction Flow

**Input:**
```java
String userFlow = generator.generateUserInteractionFlow(
    "Customer logs in, browses products, adds items to cart, and completes checkout"
);
```

**Output:**
```
Step 1: Navigate to application | Load login page | N/A
Step 2: Enter valid username | Username field populated | #user-name
Step 3: Enter valid password | Password field populated | #password
Step 4: Click login button | Redirected to products page | #login-button
Step 5: Wait for products to load | Products visible | .inventory_item
Step 6: Scroll through product list | View all available products | (scroll action)
Step 7: Click add-to-cart for item 1 | Item added, button text changes | [data-test='add-to-cart']
Step 8: Click add-to-cart for item 2 | Item added | [data-test='add-to-cart']
Step 9: Click shopping cart icon | Cart page displayed | [data-test='shopping-cart-link']
Step 10: Verify cart contains 2 items | Item count shows 2 | (assertion)
Step 11: Click checkout button | Checkout form appears | [data-test='checkout']
Step 12: Fill shipping info | Form fields populated | (multiple selectors)
Step 13: Click continue | Shipping validated, review page shown | (button)
Step 14: Click finish | Order confirmation displayed | (button)
Step 15: Verify success message | "Thank you for your order" message visible | (assertion)
```

---

### Example 3: Generate Form Validation Tests

**Input:**
```java
String validationTests = generator.generateFormValidationTests(
    "username:text, password:password, email:email, age:number"
);
```

**Output:**
```
Field: username
Valid Cases:
- Single character: 'a'
- Alphanumeric: 'user123'
- With dots: 'john.doe'
- With underscores: 'john_doe'
- Max length (if defined): 'aaa...aaa' (255 chars)

Invalid Cases:
- Empty string: ''
- Special characters: '@username!', '#user'
- Only spaces: '   '
- Leading/trailing spaces: ' username '

Edge Cases:
- Unicode characters: 'useräöü'
- Emojis: 'user😀'
- Very long string (1000+ chars)

---

Field: email
Valid Cases:
- Standard format: 'user@example.com'
- With dots: 'john.doe@example.com'
- With numbers: 'user123@example.com'
- Subdomain: 'user@mail.example.com'
- Plus addressing: 'user+tag@example.com'

Invalid Cases:
- No @ symbol: 'user.example.com'
- No domain: 'user@'
- No local part: '@example.com'
- Spaces: 'user @example.com'
- Double dots: 'user..doe@example.com'

Edge Cases:
- Very long email (250+ chars)
- International domains: 'user@例え.jp'
- Internationalized local part: 'использователь@example.com'

---

Field: password
Valid Cases:
- Alphanumeric: 'Password123'
- With special chars: 'P@ssw0rd!'
- Mixed case: 'PaSsWoRd'
- Min length: 'Pass1' (if min=5)

Invalid Cases:
- Too short: 'Pass1' (if min=8)
- Only lowercase: 'password'
- Only uppercase: 'PASSWORD'
- Only numbers: '123456'
- Only special chars: '!@#$%^'

Edge Cases:
- Max length exceeded: 'P@ss...' (1000+ chars)
- Whitespace: 'Pass word123'
- Control characters
- Null bytes
```

---

### Example 4: Generate Accessibility Test Cases

**Input:**
```java
String a11yTests = generator.generateAccessibilityTests("LoginPage");
```

**Output:**
```
Test: Keyboard Navigation Tab Order | WCAG 2.1 2.1.1 | Verify with Tab key | Username → Password → Login button → Focus visible
Test: Focus Indicator Visibility | WCAG 2.1 2.4.7 | Visual inspection | All interactive elements show focus ring
Test: Label Association | WCAG 2.1 1.3.1 | Check HTML labels | All inputs have <label> with for= attribute
Test: Error Messages Announced | WCAG 2.1 3.3.1 | Screen reader testing | Error messages read aloud
Test: Color Not Only Means | WCAG 2.1 1.4.1 | Visual inspection | Error state indicated beyond red color
Test: Button Click Size | WCAG 2.1 2.5.5 | Measure clickable area | Minimum 44x44 pixels
Test: Text Contrast Ratio | WCAG 2.1 1.4.3 | Color analyzer tool | Minimum 4.5:1 for normal text
Test: Skip Link Present | WCAG 2.1 2.4.1 | Keyboard inspection | Skip to main content link available
Test: Input Instructions | WCAG 2.1 3.3.2 | Check form labels | Password requirements clearly stated
Test: Error Recovery | WCAG 2.1 3.3.4 | Test functionality | User can correct and resubmit
```

---

## 🎓 Complete Workflow Example

### Scenario: Test a Checkout Page Form

#### Step 1: Define Page Structure

```
Checkout Page includes:
- Billing Address Form (Street, City, State, ZIP)
- Credit Card Fields (Card Number, Expiry, CVV)
- Shipping Method Selector (Standard, Express, Overnight)
- Place Order Button
- Promo Code Input (Optional)
```

#### Step 2: Use GenAI to Generate Initial Scenarios

```java
UITestScenarioGenerator generator = new UITestScenarioGenerator();

String scenarios = generator.generateUITestScenarios(
    "CheckoutPage",
    "Billing form with address fields, credit card input, shipping options, and place order button"
);

System.out.println(scenarios);
```

#### Step 3: Implement Core Test Cases

Based on GenAI output, create CheckoutTest.java:

```java
@Test
public void testCompleteCheckoutWithValidData() {
    // Valid billing address, credit card, and shipping method
}

@Test
public void testCheckoutWithMissingZipCode() {
    // Should show validation error
}

@Test
public void testCheckoutWithInvalidCardNumber() {
    // Should show card validation error
}

@Test
public void testCheckoutWithExpiredCard() {
    // Should show expiration error
}

@Test
public void testApplyValidPromoCode() {
    // Should apply discount
}

@Test
public void testApplyInvalidPromoCode() {
    // Should show error
}
```

#### Step 4: Generate User Interaction Flow

```java
String checkoutFlow = generator.generateUserInteractionFlow(
    "User enters billing address, selects shipping method, enters card details, and completes order"
);

System.out.println(checkoutFlow);
// Output: Step-by-step sequence with expected results
```

#### Step 5: Generate Form Validation Tests

```java
String formValidations = generator.generateFormValidationTests(
    "street:text, city:text, zip:number, cardNumber:text, expiry:text, cvv:number"
);

System.out.println(formValidations);
// Output: Comprehensive validation scenarios for each field
```

#### Step 6: Enhance with Accessibility Tests

```java
String a11yChecks = generator.generateAccessibilityTests("CheckoutPage");

System.out.println(a11yChecks);
// Output: WCAG compliance test cases
```

---

## 💡 Pro Tips for GenAI Integration

### Tip 1: Be Specific with Page Descriptions

**Good:**
```
"Login page containing: username text input (#user-name), password text input (#password), 
login button (#login-button), error message container ([data-test='error']), 
remember me checkbox (optional)"
```

**Less Good:**
```
"Login page"
```

### Tip 2: Use Iterative Refinement

```java
// First pass: Get overall test scenarios
String scenarios = generator.generateUITestScenarios("LoginPage", description);

// Second pass: Deep dive into form validation
String validations = generator.generateFormValidationTests("username:text, password:password");

// Third pass: Add accessibility coverage
String a11y = generator.generateAccessibilityTests("LoginPage");

// Implement all suggested cases
```

### Tip 3: Document the GenAI Source

```java
@Test(description = "GenAI identified: Email with max length may cause issues")
public void testEmailFieldMaxLength() {
    // This test was suggested by Claude API analysis
    String longEmail = "a".repeat(255) + "@example.com";
    loginPage.enterEmail(longEmail);
    loginPage.clickSubmit();
    // Assert based on expected API response
}
```

### Tip 4: Validate Against Actual Behavior

GenAI is smart but not perfect. Always test:
- ✅ Run suggested tests against actual application
- ✅ Compare expected vs actual behavior
- ✅ Adjust test cases if application behaves differently
- ✅ Document any discrepancies

### Tip 5: Cross-Reference with Requirements

```java
// Ensure GenAI suggestions align with actual requirements
// If spec says "max 20 chars" but GenAI suggests 255, verify the spec
```

---

## 🔍 Troubleshooting

### Issue: GenAI API Key Not Found

```bash
# Verify environment variable
echo $ANTHROPIC_API_KEY  # Linux/Mac
echo $env:ANTHROPIC_API_KEY  # PowerShell

# Re-export if needed
export ANTHROPIC_API_KEY="sk-ant-your-key"
mvn exec:java -Dexec.mainClass="com.qa.ui.genai.UITestScenarioGenerator"
```

### Issue: Slow Scenario Generation

- Claude API calls take 3-5 seconds per request
- Cache generated scenarios in files for reuse
- Consider batch generating scenarios during sprint planning

### Issue: Generated Scenarios Don't Match Your App

- Page description may not match actual implementation
- AI suggestions are starting points, not gospel
- Validate scenarios work before committing them
- Provide feedback loop to improve prompts

---

## 📋 GenAI Test Case Template

Use this template for consistency:

```markdown
## Test Scenario: [Name]
**Generated By:** Claude API | UITestScenarioGenerator  
**Date:** YYYY-MM-DD  
**Purpose:** [What this test validates]

### Page State:
- Currently on: [Page Name]
- Preconditions: [State before test]

### User Actions:
1. [Action 1] - [Expected Element Change]
2. [Action 2] - [Expected Element Change]
3. [Action 3] - [Expected Element Change]

### Expected Results:
- Element: [Selector] → [Expected State/Text]
- Element: [Selector] → [Expected State/Text]
- Navigation: Should navigate to [Page]

### Assertions:
```java
Assert.assertTrue(page.isVisible("[selector]"));
Assert.assertEquals(page.textContent("[selector]"), "expected text");
```

### Why This Case Matters:
[Explanation of why GenAI suggested this scenario and what risk it covers]
```

---

## 🎯 Key Takeaways

1. **GenAI augments manual expertise** — it generates ideas, you validate them
2. **Specific descriptions yield better results** — more detail = better suggestions
3. **Iterative refinement improves coverage** — multiple passes catch different angles
4. **Always validate** against actual application behavior
5. **Document the "why"** — future you will appreciate the context

---

## 📞 Next Steps

- ✅ Set ANTHROPIC_API_KEY environment variable
- ✅ Run UITestScenarioGenerator to see sample output
- ✅ Generate scenarios for your target page
- ✅ Implement 5-10 suggested test cases
- ✅ Compare coverage before/after GenAI

---

**Last Updated:** June 22, 2026  
**Maintained By:** Siddhartha Upadhyay
