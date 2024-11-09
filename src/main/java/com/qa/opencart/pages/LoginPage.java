package com.qa.opencart.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. By locator:
	private By emailId= By.id("input-email");
	private By password= By.id("input-password");
	private By loginBtn= By.xpath("//input[@value='Login']");
	private By logoImage= By.cssSelector("img[title='naveenopencart']");
	private By forgotPwdLink =By.linkText("Forgotten Password");
	private By registerLink =By.linkText("Register");
	
	private static final Logger LOG= Logger.getLogger(LoginPage.class);
	
	//2. page const...
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil= new ElementUtil(driver); 
	}
	
	
	//3. page actions:
	@Step("Waiting for Login page Title and returning the Title")
	public String getLoginPageTitle() {
		
		String title= eleUtil.WaitForTitleIs(AppConstants.DEFAULT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE);
//		String title= driver.getTitle();
		System.out.println("Login Page Title: "+title);
		LOG.info("Login Page Title: "+title);
		return title;
	}
	
	@Step("Waiting for Login page Url and returning the Url")
	public Boolean getLoginPageUrl() {
		
		String url=eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_PARAM);
//		String url= driver.getCurrentUrl();
		System.out.println("Login Page url is: "+url);
		if (url.contains(AppConstants.LOGIN_PAGE_URL_PARAM)) {
			return true;
		}
		return false;
	}

	@Step("Checking Forgot password displayed on Login page")
	public Boolean isForgotPwdLinkExist() {
		return eleUtil.doEleIsDisplayed(forgotPwdLink);
//		return driver.findElement(forgotPwdLink).isDisplayed();
	}
	
	@Step("Login with username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("user creds are: "+username+ " :"+pwd);
		eleUtil.doSendKeysWithWait(emailId, AppConstants.DEFAULT_LARGE_TIME_OUT, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
		return new AccountsPage(driver);
	}

	@Step("Navigating to register page")
	public RegisterPage navigateToRegisterPage() {
		System.out.println("Navigating to register page....");
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
	
}

