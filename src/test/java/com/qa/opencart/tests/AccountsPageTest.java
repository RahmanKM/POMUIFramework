package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic - 200: Open cart application Account page design")
@Story("US - 201: Design Account page features")
public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accSetup() {
		accPage= loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Description("Account Page Title Test -- Dev Name: @KM Rahman")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)	
	public void accPageTitleTest () {
		String actAccPageTitle= accPage.getAccPageTitle();
		Assert.assertEquals(actAccPageTitle, AppConstants.ACCOUNT_PAGE_TITLE);
	}
	
	@Description("Account Page Url Test -- Dev Name: @KM Rahman")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void accPageUrlTest() {
		Assert.assertTrue(accPage.getAccPageUrl());
	}

	@Description("Account Page Search feature exist Test -- Dev Name: @KM Rahman")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void searchExistTest() {
		Assert.assertTrue(accPage.isSearchExist());
	}
	
	@Description("Account Page Logout feature exist Test -- Dev Name: @KM Rahman")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=4)
	public void logoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Description("Account Page Header Test -- Dev Name: @KM Rahman")
	@Severity(SeverityLevel.TRIVIAL)
	@Test(priority=5)
	public void accHeadersTest() {
		List<String> actheadersList=accPage.getAccSecHeadersList();
		System.out.println("Actul accPage Headers: "+actheadersList);
		Assert.assertEquals(actheadersList, AppConstants.ACC_PAGE_SECTIONS_HEADERS);
	}
	
	@DataProvider
	public Object[][] getProductKey(){
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Samsung"}
		};
	}
 
	
	@Description("Account Page Key Search check Test -- Dev Name: @KM Rahman")
	@Severity(SeverityLevel.CRITICAL)
	@Test (dataProvider = "getProductKey", priority = 6)
	public void searchCheckTest(String productKey) {
		searchResultsPage= accPage.performSearch(productKey);
		Assert.assertTrue(searchResultsPage.isSearchSuccesssful());
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro"},
			{"Macbook", "MacBook Air"},
			{"iMac", "iMac"},
			{"Samsung", "Samsung SyncMaster 941BW"},
			{"Samsung", "Samsung Galaxy Tab 10.1"},
													};
	}
	
	
	@Description("Account Page Products Search Test -- Dev Name: @KM Rahman")
	@Severity(SeverityLevel.BLOCKER)
	@Test (dataProvider = "getProductData", priority = 7)
	public void searchTest(String searchKey, String mainProductName) {
		searchResultsPage= accPage.performSearch(searchKey);
		if (searchResultsPage.isSearchSuccesssful()) {
			productInfoPage = searchResultsPage.selectProduct(mainProductName);
			String actualProductHeader= productInfoPage.getProductHeader(mainProductName);
			Assert.assertEquals(actualProductHeader, mainProductName);
		}
	}
	
	
	
}
