package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	
	private static final Logger LOG= Logger.getLogger(DriverFactory.class);
	
	public static ThreadLocal<WebDriver> tlDriver= new ThreadLocal<WebDriver>();
	
	public static String highlight;
	public OptionsManager optionsManager;
	
	/**
	 * this method used to initialize the driver on the basis of given browser name
	 * @param browserName
	 * @return this will return browser instance
	 */
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName= prop.getProperty("browser".toLowerCase());
		System.out.println("Browser name is: "+browserName);
		LOG.info("Browser name is: "+browserName);
		
		highlight= prop.getProperty("highlight").trim();
		optionsManager= new OptionsManager(prop);
		
		if (browserName.equals("chrome")) {
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote run
				init_remoteDriver("chrome");
			}
			else {
				//local run
//				driver= new ChromeDriver();
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			
			
			
			
			

		}
		
		else if (browserName.equals("firefox")) {
			
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote run
				init_remoteDriver("firefox");
			}
			else {
				//local run

				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
//				driver= new FirefoxDriver();
			}
				
		}
		
		else if (browserName.equals("edge")) {
			
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				//remote run
				init_remoteDriver("edge");
			}
			else {
				//local run

//				driver= new EdgeDriver();
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
				
		}
			
		
		else if (browserName.equals("safari")) {
			// only local execution [Docker does not support safari]
//			driver= new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		
		else {
			System.out.println("Please pass the right browser: "+browserName);
			LOG.error("Please pass the right browser name: "+ browserName);
			throw new FrameworkException(AppError.BROWSER_NOT_FOUND);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
		
	}
	
	/**
	 * remote execution
	 * @param browser
	 */
	private void init_remoteDriver(String browser) {
		
		System.out.println("Running test cases on remote Grid machine with browser: "+browser);
		
		if (browser.equals("chrome")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		else if (browser.equals("firefox")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (browser.equals("edge")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			System.out.println("Please pass the right browser for remote execution..");
		}
		
		
	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get(); 
	}
	
	

	/**
	 * this method is used to initialize the config properties
	 * @return
	 */
	public Properties initProp() {
		 prop= new Properties();
		FileInputStream ip= null; 
		 
		 //mvn clean install -Denv="qa"
		//String envName= System.getenv("env");
		String envName= System.getProperty("env");
		System.out.println("Running test cases on environment:------------> "+envName); 
		
		LOG.info("Running test cases on environment:------------> "+envName);
		
		if (envName== null) {
			System.out.println("No env is given, hence running it on QA env");
			try {
				ip= new FileInputStream("./src/test/resource/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
		try {	
			switch (envName) {
			case "qa":
				ip= new FileInputStream("./src/test/resource/config/qa.config.properties");
				break;
				
			case "dev":
				ip= new FileInputStream("./src/test/resource/config/dev.config.properties");
				break;
			case "stage":
				ip= new FileInputStream("./src/test/resource/config/stage.config.properties");
				break;
			case "uat":
				ip= new FileInputStream("./src/test/resource/config/uat.config.properties");
				break;
			case "prod":
				ip= new FileInputStream("./src/test/resource/config/config.properties");
				break;
			
			default:
				System.out.println("Pass the right env name: "+envName);
				throw new FrameworkException(AppError.ENV_NOT_FOUND);
//				break;
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		 
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 try {
//			FileInputStream ip= new FileInputStream("./src/test/resource/config/config.properties");
//			prop.load(ip);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 return prop;
	}
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		
		File srcFile= ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path= System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination= new File(path);
		
		
		try {
			FileUtils.copyFile (srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
