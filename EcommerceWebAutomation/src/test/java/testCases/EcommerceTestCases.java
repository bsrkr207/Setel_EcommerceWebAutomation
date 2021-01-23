package testCases;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import pages.AmazonHomePage;
import pages.FlipkartHomePage;
import utils.Helper;
import utils.TestData;

public class EcommerceTestCases {

	private WebDriver driver;
	private ExtentReports report;
	private ExtentTest test;
	
	Helper helper; 
	TestData testdata = new TestData();
	AmazonHomePage amazonPage;
	FlipkartHomePage flipkartPage;
	
	@BeforeSuite
	public void setup() {
		
		testdata = testdata.readTestData();
		helper = new Helper();
		report = helper.startExtentReport(new Exception().getStackTrace()[0].getClassName());

		//Launch Amazon
		driver = helper.launchBrowser(testdata.getBrowser(), testdata.getEcommerceURL1());
		
		
		
	}
	
	@Test
	public void searchForIteamOnEcommerceWebsites() {
		
		test = report.createTest(new Exception().getStackTrace()[0].getMethodName());
		amazonPage = new AmazonHomePage(driver, test);
		flipkartPage = new FlipkartHomePage(driver, test);
		
		amazonPage.searchForIteamInAmazon(testdata.getProductName());
		
		try {
			String getProductName = helper.findElementAndGetText(amazonPage.productName);
			getProductName.contains(testdata.getProductName());
			helper.writeLogs("pass", testdata.getEcommerceWebsite1Name()+ " Result is shown for the product : "+ testdata.getProductName(), test);
			helper.writeLogsWithScreenshot("pass", testdata.getEcommerceWebsite1Name()+ "_" + testdata.getProductName(), test);
		} catch (Exception e) {
			helper.writeLogs("fail", testdata.getEcommerceWebsite1Name()+ " Result not showing for the product : "+ testdata.getProductName() + " >> "+ e.getMessage(), test);

		}
		
		HashMap<String, Integer>  amazonHmap = helper.getItemDetails(testdata.getEcommerceWebsite1Name(), amazonPage.productSearchResult_Deatils, amazonPage.productName, amazonPage.productsPrice, amazonPage.productURL);
		
		//Navigates to 2nd ecommerce website
		flipkartPage.navigatesToFlipkartURL(testdata.getEcommerceURL2());

		flipkartPage.searchForIteamInFlipkart(testdata.getProductName());
		
		try {
			String getProductName = helper.findElementAndGetText(flipkartPage.productName);
			getProductName.contains(testdata.getProductName());
			helper.writeLogs("pass", testdata.getEcommerceWebsite2Name()+ " Result is shown for the product : "+ testdata.getProductName(), test);
			helper.writeLogsWithScreenshot("pass", testdata.getEcommerceWebsite2Name() + "_" + testdata.getProductName(), test);
		} catch (Exception e) {
			helper.writeLogs("fail", testdata.getEcommerceWebsite2Name() + " Result not showing for the product : "+ testdata.getProductName() + " >> "+ e.getMessage(), test);

		}

		HashMap<String, Integer>  flipkartHmap = helper.getItemDetails(testdata.getEcommerceWebsite2Name(), flipkartPage.productSearchResult_Deatils, flipkartPage.productName, flipkartPage.productsPrice, flipkartPage.productURL);
		
	
		//Merge flipkartHmap values into amazonHmap
		Set<Map.Entry<String, Integer>> entrySet = flipkartHmap.entrySet();
		for (Map.Entry<String, Integer> e : entrySet) {

			amazonHmap.put(e.getKey(), e.getValue());
		}
		
		Map<String, Integer> resultAscendingOrderOfPrice = helper.sortByValue(amazonHmap);
		
		// print the sorted hashmap
        for (Map.Entry<String, Integer> entry : resultAscendingOrderOfPrice.entrySet()) {
            String website = entry.getKey().split("\\|")[0];
            String productName = entry.getKey().split("\\|")[1];
            String linkToProduct = entry.getKey().split("\\|")[2];

            helper.writeLogs("info", "<b>Website: </b>" + website + " " + "<b>Product Name: </b>" + productName + " " + "<b>Price: </b>" + entry.getValue() + " " +"<b>Link: </b>" + linkToProduct +"", test);
            
        }
	}
	
	
	@AfterSuite
	public void tearDown() {
		report.flush();
		helper.tearDown();
		
	}
	
}
