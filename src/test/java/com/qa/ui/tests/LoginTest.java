package com.qa.ui.tests;

import com.qa.ui.base.BaseTest;
import com.qa.ui.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test suite for SauceDemo Login functionality
 * Tests valid login, invalid credentials, empty fields scenarios
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void loginPageSetup() {
        goToBaseURL();
        loginPage = new LoginPage(page);
    }

    @Test(priority = 1, description = "Verify succesful login with valid credentials")
    public void testLoginWithValidCredentials() {
        // Arrange
        String username = "standard_user";
        String password = "secret_sauce";

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful with valid credentials");
        Assert.assertTrue(getCurrentURL().contains("inventory"), "Should redirect to inventory page");
    }

    @Test(priority = 2, description = "Verify login fails with invalid password")
    public void testLoginWithInvalidPassword() {
        // Arrange
        String username = "standard_user";
        String password = "wrong_password";

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("do not match"),
                "Error should mention credentials mismatch"
        );
    }

    @Test(priority = 3, description = "Verify login fails with non-existent user")
    public void testLoginWithNonExistentUser() {
        // Arrange
        String username = "invalid_user_12345";
        String password = "secret_sauce";

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("not recognized"),
                "Error should mention user not recognized"
        );
    }

    @Test(priority = 4, description = "Verify login fails when username is empty")
    public void testLoginWithEmptyUsername() {
        // Arrange
        String username = "";
        String password = "secret_sauce";

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username is required"),
                "Error should mention username is required"
        );
    }

    @Test(priority = 5, description = "Verify login fails when password is empty")
    public void testLoginWithEmptyPassword() {
        // Arrange
        String username = "standard_user";
        String password = "";

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Password is required"),
                "Error should mention password is required"
        );
    }

    @Test(priority = 6, description = "Verify login with locked out user account")
    public void testLoginWithLockedOutUser() {
        // Arrange
        String username = "locked_out_user";
        String password = "secret_sauce";

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for locked user");
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("locked out"),
                "Error should mention account is locked"
        );
    }

    /**
     * Helper method to get current URL
     * @return Current page URL
     */
    private String getCurrentURL() {
        return page.url();
    }
}
