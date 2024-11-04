package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	private WebDriver driver;
	private Select sel;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver){
		this.driver=driver;
		act= new Actions(driver);
		jsUtil= new JavaScriptUtil(driver);
	}
	
	
	public void doSendKeys(By locator, String value) {
		WebElement ele =getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public WebElement getElement(By locator) {
		WebElement ele = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);
		}
		return ele;
	}
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
		
	}	
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	public boolean doEleIsDisplayed (By locator) {
		return getElement(locator).isDisplayed();
	}
	
	public boolean isStringElementPresent (By locator) {
		List <WebElement> list = driver.findElements(locator);
		System.out.println(list.size());
		
		if (list.size()==1) {
			System.out.println("single search ele is present on the page");
			return true;
		}
		else {
			System.out.println("no search or multiple search present on the page");
			return false;
		}
	}
	
	public ArrayList<String> getElementsTextList (By locator) {
		List <WebElement> eleList =  getElements(locator);
		ArrayList<String> eleTextList= new ArrayList<String>();
		
		for (WebElement e : eleList) {
			String text= e.getText();
			if (text.length() != 0) {
			eleTextList.add(text);
			}
		}
		return eleTextList;
	}
	
	//****************** Drop Down List ********************//
	public void doSelectDropDownByIndex(By locator, int index) {
		sel= new Select (getElement(locator));
		sel.selectByIndex(index);
	}
	
	public void doSelectDropDownByVisibleText(By locator, String text) {
		sel= new Select (getElement(locator));
		sel.selectByVisibleText(text);
	}
	
	public void doSelectDropDownByValue(By locator, String value) {
		sel= new Select (getElement(locator));
		sel.selectByValue(value);
	}
	
	public List<WebElement> getDropDownOptions(By locator) {	
		Select sel= new Select(getElement(locator));
		 return sel.getOptions();						
	}
	
	public void getDropDownOptionsList(By locator) {
		List <WebElement> CountryOptions = getDropDownOptions(locator);
		for (WebElement e: CountryOptions) {
			String text= e.getText();
			System.out.println(text);
		}
	}
	
	public void doSelectValueFromDropDown(By locator, String value) {
		List<WebElement> optionsList= getElements(locator);
		
		for (WebElement e: optionsList) {
			String text= e.getText();
			System.out.println(text);
			
			if (text.equals(value)){
				e.click();
				break;
			}
		}
	}

	public void doSearch (String tagName, String text) {
		By suggLocator= By.xpath("//"+tagName+"[text()='"+text+"']");
		getElement(suggLocator).click();
	}
	
	public boolean checkElementIsMandatory(String jsScript) {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		String man_text= js.executeScript(jsScript).toString();
		System.out.println(man_text);
		
		if (man_text.contains("*")) {
			System.out.println("Ele is a mandatory field");
			return true;
		}
		else {
			System.out.println("FN not mandatory");
			return false;
		}
	}
	
	
	//************************** Action Utils **********************************//

	public void handleLeveOneMenuItems(By parentMenu, By childMenu) throws InterruptedException {
		Actions act= new Actions(driver);
		
		act.moveToElement(getElement(parentMenu)).build().perform();
		Thread.sleep(3000);
		//getElement(childMenu).click();
		doClick(childMenu);
	}
	
	public void doActionsClick(By locator) {	
		act.click(getElement(locator)).build().perform();
	}
	
	public void doActionsSendkeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).build().perform();
	}
	
	//*************************** Wait Utils **********************************//
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does notnecessarily mean that the element is visible.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public void doSendKeysWithWait(By locator, int timeout, String value) {
		waitForElementPresence(locator, timeout).sendKeys(value);
	}
	
	public void doClickWithWait(By locator, int timeout) {
		waitForElementPresence(locator, 5).click();
	}
	
	public String getElementTextWithWait (By locator, int timeout) {
		return waitForElementPresence(locator, timeout).getText();
	}
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				
	}
	public WebElement waitForElementVisible(By locator, int timeout, int poolingTime) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(poolingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page. and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0
	 * @param locator
	 * @param timeout
	 * @return
	 */
	 
	public List <WebElement> waitForElementsToBeVisible(By locator, int timeout) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
				
	}
	
	public void doSendKeysWithVisibleElement(By locator, int timeout, String value) {
		waitForElementVisible(locator, timeout).sendKeys(value);
	}
	
	public void doClickWithVisibleElement(By locator, int timeout) {
		waitForElementVisible(locator, 5).click();
	}
	
	public String getElementTextWithVisibleElement (By locator, int timeout) {
		return waitForElementVisible(locator, timeout).getText();
	}
	
	
	
		
	public  Alert WaitForAlert(int timeout) {	
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	public String getAlertText(int timeout) {
	return WaitForAlert(timeout).getText();
	}
	
	public void acceptAlert(int timeout) {
		WaitForAlert(timeout).accept();
	}
	
	public void dismissAlert(int timeout) {
		WaitForAlert(timeout).dismiss();
	}
	
	public void alertSendKeys(int timeout, String value) {
		WaitForAlert(timeout).sendKeys(value);
	}
	
	
	public String WaitForTitleContains(int timeout, String titleFraction) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeout));
		
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		}
		
		else {
				return null;
		}
}

	public String WaitForTitleIs(int timeout, String titleValue) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeout));
		
		if (wait.until(ExpectedConditions.titleIs(titleValue))) {
			return driver.getTitle();
		}
		
		else {
				return null;
		}

}

	public  String waitForUrlContains(int timeout, String urlFraction) {
	WebDriverWait wait=  new WebDriverWait(driver, Duration.ofSeconds(timeout));
	
	if	(wait.until(ExpectedConditions.urlContains(urlFraction))) {
		return driver.getCurrentUrl();
	}
	else {
		return null;
	}
		
}
	
	public  String waitForUrlis(int timeout, String urlValue) {
	WebDriverWait wait=  new WebDriverWait(driver, Duration.ofSeconds(timeout));
	
	if	(wait.until(ExpectedConditions.urlToBe(urlValue))) {
		return driver.getCurrentUrl();
	}
	else {
		return null;
	}
	
	}
	
	
	public  void waitForFrame(int timeout, int frameIndex) {
		WebDriverWait wait=  new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	
	public  void waitForFrame(int timeout, String nameOrId) {
		WebDriverWait wait=  new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
	}
	
	public  void waitForFrame(int timeout, WebElement FrameElement) {
		WebDriverWait wait=  new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameElement));
	}
	
	public  void waitForFrame(int timeout, By FrameLocator) {
		WebDriverWait wait=  new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameLocator));
	}


}