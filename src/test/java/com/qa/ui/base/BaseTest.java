package com.qa.ui.base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base Test class for Playwright UI automation
 * Handles browser initialization, page setup, and common utilities
 * Supports Chrome, Firefox, and Safari browsers
 */
public class BaseTest {

    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected String baseURL = "https://www.saucedemo.com";
    protected String browserType = "chromium"; // Options: chromium, firefox, webkit

    @BeforeClass
    public void setup() {
        // Initialize Playwright and launch browser
        Playwright playwright = Playwright.create();

        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }

        // Create context and page
        context = browser.newContext();
        page = context.newPage();

        // Set viewport size
        page.setViewportSize(1280, 720);
    }

    @AfterClass
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
    }

    /**
     * Navigate to application URL
     * @param url URL to navigate to
     */
    protected void navigateTo(String url) {
        page.navigate(url);
    }

    /**
     * Navigate to base URL
     */
    protected void goToBaseURL() {
        page.navigate(baseURL);
    }

    /**
     * Click on element by selector
     * @param selector CSS selector
     */
    protected void clickElement(String selector) {
        page.click(selector);
    }

    /**
     * Fill text input field
     * @param selector CSS selector
     * @param text Text to enter
     */
    protected void fillInput(String selector, String text) {
        page.fill(selector, text);
    }

    /**
     * Get text from element
     * @param selector CSS selector
     * @return Element text content
     */
    protected String getText(String selector) {
        return page.textContent(selector);
    }

    /**
     * Wait for element to be visible
     * @param selector CSS selector
     * @param timeout Timeout in milliseconds
     */
    protected void waitForElement(String selector, int timeout) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeout));
    }

    /**
     * Check if element is visible
     * @param selector CSS selector
     * @return True if element is visible
     */
    protected boolean isElementVisible(String selector) {
        try {
            return page.isVisible(selector);
        } catch (PlaywrightException e) {
            return false;
        }
    }

    /**
     * Get current page URL
     * @return Current URL
     */
    protected String getCurrentURL() {
        return page.url();
    }

    /**
     * Get page title
     * @return Page title
     */
    protected String getPageTitle() {
        return page.title();
    }

    /**
     * Execute JavaScript in page context
     * @param script JavaScript code
     * @return Script result
     */
    protected Object executeScript(String script) {
        return page.evaluate(script);
    }

    /**
     * Take screenshot of current page
     * @param filename Filename to save screenshot
     */
    protected void takeScreenshot(String filename) {
        page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get("screenshots/" + filename)));
    }
}
