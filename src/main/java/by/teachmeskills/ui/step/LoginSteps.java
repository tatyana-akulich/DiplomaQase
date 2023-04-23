package by.teachmeskills.ui.step;

import by.teachmeskills.ui.page.LoginPage;
import by.teachmeskills.ui.page.ProjectsPage;

public class LoginSteps {
    private LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage();
    }

    public ProjectsPage loginWithValidCredentials() {
        loginPage.openPage();
        loginPage.enterValidEmail();
        loginPage.enterValidPassword();
        return loginPage.clickLogin();
    }

    public ProjectsPage login(String email, String password) {
        loginPage.openPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        return loginPage.clickLogin();
    }
}
