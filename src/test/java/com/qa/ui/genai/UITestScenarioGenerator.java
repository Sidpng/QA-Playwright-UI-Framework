package com.qa.ui.genai;

import com.anthropic.client.Anthropic;
import com.anthropic.models.Message;
import com.anthropic.models.MessageCreateParams;

/**
 * GenAI-Powered UI Test Scenario Generator
 * Uses Claude API to intelligently generate UI test scenarios,
 * user interaction flows, and edge cases
 *
 * HOW TO USE:
 * 1. Set ANTHROPIC_API_KEY environment variable with your Claude API key
 * 2. Call generateTestScenarios() with page description
 * 3. Review generated test flows and implement them
 */
public class UITestScenarioGenerator {

    private final Anthropic client;

    public UITestScenarioGenerator() {
        // Initialize Anthropic client (reads ANTHROPIC_API_KEY from env)
        this.client = Anthropic.builder().build();
    }

    /**
     * Generate comprehensive test scenarios for a UI page/feature
     * @param pageName Name of the page (e.g., LoginPage, ProductListPage)
     * @param pageDescription Description of the page elements and functionality
     * @return Generated test scenarios
     */
    public String generateUITestScenarios(String pageName, String pageDescription) {
        String prompt = String.format("""
            You are an expert QA automation engineer specializing in UI testing with Playwright.
            Generate comprehensive test scenarios for the following page/feature:

            Page Name: %s
            Description: %s

            Generate test scenarios covering:
            1. Happy path user interactions
            2. Form validation scenarios
            3. Error handling and edge cases
            4. Accessibility considerations
            5. Cross-browser compatibility scenarios
            6. Responsive design test cases
            7. User flow variations

            For each scenario, provide:
            - Test Case Name
            - User Action Sequence
            - Expected Result
            - Key Assertions
            - Priority Level (Critical/High/Medium)

            Format as a numbered list.
            """, pageName, pageDescription);

        Message message = client.messages().create(MessageCreateParams.builder()
                .model("claude-opus-4-1")
                .maxTokens(2048)
                .messages(java.util.List.of(
                        new MessageCreateParams.MessageParam.UserMessageParam(prompt)
                ))
                .build());

        return message.content().getFirst().text();
    }

    /**
     * Generate user interaction flows and sequences
     * @param userJourney Description of the user journey (e.g., "Customer adds item to cart and checks out")
     * @return Step-by-step interaction sequence
     */
    public String generateUserInteractionFlow(String userJourney) {
        String prompt = String.format("""
            Generate a detailed step-by-step user interaction flow for the following scenario:

            User Journey: %s

            Provide:
            1. Detailed action sequence
            2. Element interactions (click, fill, select, etc.)
            3. Expected page states after each action
            4. Error recovery paths
            5. Validation checkpoints

            Format as:
            Step N: [Action] | Expected Result | Element Selector (if applicable)
            """, userJourney);

        Message message = client.messages().create(MessageCreateParams.builder()
                .model("claude-opus-4-1")
                .maxTokens(1536)
                .messages(java.util.List.of(
                        new MessageCreateParams.MessageParam.UserMessageParam(prompt)
                ))
                .build());

        return message.content().getFirst().text();
    }

    /**
     * Generate form validation test cases
     * @param formFields List of form fields with their types (e.g., "username:text, email:email, password:password")
     * @return Test cases for form validation scenarios
     */
    public String generateFormValidationTests(String formFields) {
        String prompt = String.format("""
            Generate comprehensive form validation test cases for a form with the following fields:

            Fields: %s

            For each field, provide:
            1. Valid input test cases
            2. Invalid input test cases
            3. Boundary value test cases
            4. Special character handling
            5. Cross-field validation (if applicable)
            6. Error message verification

            Format:
            Field: [fieldname]
            Valid Cases: [list]
            Invalid Cases: [list]
            Edge Cases: [list]
            """, formFields);

        Message message = client.messages().create(MessageCreateParams.builder()
                .model("claude-opus-4-1")
                .maxTokens(1728)
                .messages(java.util.List.of(
                        new MessageCreateParams.MessageParam.UserMessageParam(prompt)
                ))
                .build());

        return message.content().getFirst().text();
    }

    /**
     * Generate assertions and verification points for a given UI element
     * @param elementDescription Description of the UI element (e.g., "Error message box on login page")
     * @return Suggested assertions and verification points
     */
    public String generateUIAssertions(String elementDescription) {
        String prompt = String.format("""
            Generate key assertions and verification points for the following UI element:

            Element: %s

            Provide assertions for:
            1. Element visibility and presence
            2. Element properties (text, attributes, styling)
            3. Element behavior (click-ability, input-ability)
            4. Related elements (children, siblings)
            5. Dynamic content (loading states, animations)

            Format as Playwright-compatible assertions:
            assertion_name | purpose | code_snippet
            """, elementDescription);

        Message message = client.messages().create(MessageCreateParams.builder()
                .model("claude-opus-4-1")
                .maxTokens(1024)
                .messages(java.util.List.of(
                        new MessageCreateParams.MessageParam.UserMessageParam(prompt)
                ))
                .build());

        return message.content().getFirst().text();
    }

    /**
     * Generate accessibility test cases
     * @param pageType Type of page (e.g., LoginPage, CheckoutPage, DashboardPage)
     * @return Accessibility test scenarios (keyboard navigation, screen reader, WCAG compliance)
     */
    public String generateAccessibilityTests(String pageType) {
        String prompt = String.format("""
            Generate comprehensive accessibility test cases for a %s with focus on:

            1. Keyboard Navigation
               - Tab order verification
               - Focus indicators
               - Keyboard shortcuts

            2. Screen Reader Compatibility
               - ARIA labels and roles
               - Alternative text for images
               - Semantic HTML usage

            3. WCAG 2.1 Compliance
               - Color contrast ratios
               - Font sizing and readability
               - Error messages clarity

            4. Mobile Accessibility
               - Touch target sizes
               - Gesture alternatives

            Format each test case with:
            Test Name | WCAG Criterion | Validation Method | Expected Result
            """, pageType);

        Message message = client.messages().create(MessageCreateParams.builder()
                .model("claude-opus-4-1")
                .maxTokens(1536)
                .messages(java.util.List.of(
                        new MessageCreateParams.MessageParam.UserMessageParam(prompt)
                ))
                .build());

        return message.content().getFirst().text();
    }

    public static void main(String[] args) {
        UITestScenarioGenerator generator = new UITestScenarioGenerator();

        // Example 1: Generate test scenarios for Login Page
        System.out.println("=== Login Page Test Scenarios ===");
        String loginScenarios = generator.generateUITestScenarios(
                "LoginPage",
                "A login page with username and password fields, login button, and error message display"
        );
        System.out.println(loginScenarios);

        // Example 2: Generate user interaction flow
        System.out.println("\n=== User Journey: Complete Login Flow ===");
        String userFlow = generator.generateUserInteractionFlow(
                "User logs in with valid credentials and sees the inventory page"
        );
        System.out.println(userFlow);

        // Example 3: Generate form validation tests
        System.out.println("\n=== Form Validation Tests ===");
        String formTests = generator.generateFormValidationTests(
                "username:text, password:password"
        );
        System.out.println(formTests);

        // Example 4: Generate accessibility tests
        System.out.println("\n=== Accessibility Tests ===");
        String a11yTests = generator.generateAccessibilityTests("LoginPage");
        System.out.println(a11yTests);
    }
}
