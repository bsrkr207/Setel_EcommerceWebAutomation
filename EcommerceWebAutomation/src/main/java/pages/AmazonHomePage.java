package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Helper;

public class AmazonHomePage extends Helper{

	private WebDriver driver;
	private ExtentTest test;
	
	public By homePage_SearchBox = By.id("twotabsearchtextbox");
	public By homePage_SearchButton = By.id("nav-search-submit-button");
	public By productSearchResult_Deatils = By.xpath("//*[@data-component-type='s-search-result']");
	public By productName = By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal']");
	public By productURL = By.xpath(".//a[@class='a-link-normal a-text-normal']");
	public By productsPrice = By.xpath(".//span[@class='a-price-whole']");

	public AmazonHomePage (WebDriver driver, ExtentTest test) {            
	     this.driver = driver;  
	     this.test = test;
	}
		
	public void searchForIteamInAmazon(String itemName) {
		
		searchForIteam(homePage_SearchBox, homePage_SearchButton, itemName);
	}

	
	
	
}
