package com.qa.ui.pages;

import com.microsoft.playwright.Page;

/**
 * LoginPage - Page Object Model for SauceDemo login page
 * Encapsulates all locators and actions for login functionality
 */
public class LoginPage {

    private final Page page;

    // Locators
    private final String USERNAME_INPUT = "#user-name";
    private final String PASSWORD_INPUT = "#password";
    private final String LOGIN_BUTTON = "#login-button";
    private final String ERROR_MESSAGE = "[data-test='error']";
    private final String INVENTORY_CONTAINER = ".inventory_container";

    public LoginPage(Page page) {
        this.page = page;
    }

    /**
     * Enter username in the username field
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        page.fill(USERNAME_INPUT, username);
    }

    /**
     * Enter password in the password field
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        page.fill(PASSWORD_INPUT, password);
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        page.click(LOGIN_BUTTON);
    }

    /**
     * Perform login with username and password
     * @param username Username
     * @param password Password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Get error message text
     * @return Error message
     */
    public String getErrorMessage() {
        return page.textContent(ERROR_MESSAGE);
    }

    /**
     * Check if error message is displayed
     * @return True if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return page.isVisible(ERROR_MESSAGE);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if login was successful (inventory page loaded)
     * @return True if inventory container is visible
     */
    public boolean isLoginSuccessful() {
        page.waitForSelector(INVENTORY_CONTAINER, new Page.WaitForSelectorOptions().setTimeout(5000));
        return page.isVisible(INVENTORY_CONTAINER);
    }

    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        return page.title();
    }
}
