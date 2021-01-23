package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Helper;

public class FlipkartHomePage extends Helper{

	private WebDriver driver;
	private ExtentTest test;
	
	
	public By homePage_LoginPopup = By.xpath("//button[@class='_2KpZ6l _2doB4z']");
	public By homePage_SearchBox = By.name("q");
	public By homePage_SearchButton = By.xpath("//*[@id='container']/div/div[1]/div[1]/div[2]/div[2]/form/div/button");
	public By productSearchResult_Deatils = By.xpath("//div[contains(@data-tkid,'SEARCH')]");
	public By productName = By.xpath(".//div[@class='_4rR01T']");
	public By productURL = By.xpath(".//a[@class='_1fQZEK']");
	public By productsPrice = By.xpath(".//div[@class='_30jeq3 _1_WHN1']");

	public FlipkartHomePage (WebDriver driver, ExtentTest test) {            
	     this.driver = driver;  
	     this.test = test;
	}
		
	public void searchForIteamInFlipkart(String itemName) {
		clickOnTheElement(homePage_LoginPopup);
		searchForIteam(homePage_SearchBox, homePage_SearchButton, itemName);
	}

	public void navigatesToFlipkartURL(String url) {
		driver.get(url);
	}
	
	
}
