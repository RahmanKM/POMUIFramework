package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic - 100: Open cart application Login page design")
@Story("US - 101: Design login page features")
public class LoginPageTest extends BaseTest {
	
	
	@Description("Login Page Title test....")
	@Severity(SeverityLevel.MINOR)
	@Test (priority = 1)
	public void loginPageTitleTest() {
//		LoginPage loginPage= new LoginPage(driver); //inherit from BaseTest class
		String actualTitle= loginPage.getLoginPageTitle(); 
		AssertJUnit.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Login Page URL test....")
	@Severity(SeverityLevel.NORMAL)
	@Test (priority = 2)
	public void loginPageUrl() {
//		LoginPage loginPage= new LoginPage(driver);
		Assert.assertTrue(loginPage.getLoginPageUrl());
	}
	
	@Description("Login Page Forgot Password test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test (priority = 3)
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	

	@Description("Login Page Login test with correct username and password....")
	@Severity(SeverityLevel.BLOCKER)
	@Test (priority=4)
	public void loginTest()
	{
		accPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
}
