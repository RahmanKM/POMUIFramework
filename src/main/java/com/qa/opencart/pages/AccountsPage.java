package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage {
private WebDriver driver;
private ElementUtil eleUtil;
	
	
	private By logoutLink= By.linkText("Logout");
	private By search = By.name("search");
	private By searchIcon= By.cssSelector("div#search button");
	private By accSecHeaders= By.cssSelector("div#content h2");
	

	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil= new ElementUtil(driver);
	}
	
	@Step("Waiting for Account page Title and returning the Title")
	public String getAccPageTitle() {
		String title= eleUtil.WaitForTitleIs(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACCOUNT_PAGE_TITLE);
//		 String title= driver.getTitle();
		 System.out.println("Acc page title is: "+title);
		 return title;
	}
	
	@Step("Waiting for Account page Url and returning the Url")
	public boolean getAccPageUrl() {
		String url=eleUtil.waitForUrlContains(AppConstants.DEFAULT_TIME_OUT, AppConstants.ACCOUNT_PAGE_URL_PARAM);
//		String url = driver.getCurrentUrl();
		System.out.println("Acc page url is: "+url);
		if (url.contains(AppConstants.ACCOUNT_PAGE_URL_PARAM)) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	@Step("Logout link exist on Account page")
	public boolean isLogoutLinkExist() {
		return	eleUtil.doEleIsDisplayed(logoutLink);
//		return driver.findElement(logoutLink).isDisplayed();
	}
	
	
	@Step("Search exist on Account page")
	public boolean isSearchExist() {
		return	eleUtil.doEleIsDisplayed(search);
//		return driver.findElement(search).isDisplayed();
	} 
	
	@Step("Perform search.....{0} ")
	public SearchResultsPage performSearch(String productKey) {
		System.out.println("Product name is: "+productKey);
		if (isSearchExist()) {
			eleUtil.doSendKeys(search, productKey);
			eleUtil.doClick(searchIcon);
			return new SearchResultsPage(driver);
		}
		else {
			System.out.println("Search field is not present on the page");
			return null;
		}
	}
	
	@Step("Action Headers List on Account page...")
	public List<String> getAccSecHeadersList() {
		List<WebElement> secList =eleUtil.waitForElementsToBeVisible(accSecHeaders, AppConstants.DEFAULT_LARGE_TIME_OUT);
		
//		List<WebElement> secList= driver.findElements(accSecHeaders);
		System.out.println("Total section headers: "+secList.size());
		List<String> actSecTextList= new ArrayList<String>();
		for (WebElement e : secList) {
			String text= e.getText();
			actSecTextList.add(text);
		}
		return actSecTextList;
	}

}
