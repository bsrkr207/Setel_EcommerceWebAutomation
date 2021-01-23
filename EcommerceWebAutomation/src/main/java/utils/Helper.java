package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Helper {

	public static ExtentReports extent = new ExtentReports();

	public static ExtentSparkReporter spark;

	static WebDriver driver = null ;
	static WebDriverWait wait;
	
	public WebDriver launchBrowser(String browserType, String URL) {
		
		switch (browserType.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			 driver = new FirefoxDriver();
			
			break;

		default:
			System.out.println("Please enter a valid browser type!");
			break;
		}
				
		driver.get(URL);
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, 20);
		return driver;
		
	}
		
	
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
	public ExtentReports startExtentReport(String reportName) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		String time = timestamp.toString();
		
		spark = new ExtentSparkReporter(createReportsPath() +"\\"+ reportName +"_"+ time.replace(":", ".") +".html");
		
		try {
			spark.loadXMLConfig(Paths.get("spark-config.xml").toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", System.getProperty("user.name"));
		extent.setReportUsesManualConfiguration(false);
		extent.attachReporter(spark);
		
		return extent;
	}

	public void writeLogs(String status, String msg, ExtentTest test) {
	
		status = status.toLowerCase();
		
		switch (status) {
		case "pass":
			test.log(Status.PASS, msg);
			break;

		case "fail":
			test.log(Status.FAIL, msg);
			break;

		case "warning":
			test.log(Status.WARNING, msg);
			break;

		case "info":
			test.log(Status.INFO, msg);
			break;
		}
	}
	
	public void writeLogsWithScreenshot(String status, String screenshotName, ExtentTest test) {
		
		status = status.toLowerCase();

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        String image = encodeFileToBase64(scrFile);
        
		switch (status) {
		case "pass":
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;

		case "fail":
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;

		case "warning":
			test.log(Status.WARNING, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;

		case "info":
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;
		}
	}
	
	
	public void endExtentReport() {
		
		extent.flush();
	}
	
	public String returnProjectPath() 
	{
		String root = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
		return root;
	}
	
	
	public static File createReportsPath() {
		
		File dir = null;
		
		try {
			
			dir = new File("C:\\AutomationReports");
		    
			if (!dir.exists()) dir.mkdirs();
		    			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return dir;
	    
	}
	
	private String encodeFileToBase64(File file) {
	    try {
	        
	    	byte[] fileContent = Files.readAllBytes(file.toPath());
	        
	    	return Base64.getEncoder().encodeToString(fileContent);
	    
	    } catch (IOException e) {
	        throw new IllegalStateException("could not read file " + file, e);
	    }
	}
	
	
	//Webdriver extension methods
	
	public void clickOnTheElement(By element) {
    	wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void enterTextIntoTextbox(By element, String text) {
    	wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(text);
    }
	
    public String getTextFromElement(By element) {
    	return wait.until(ExpectedConditions.elementToBeClickable(element)).getText();
    }

    public String findElementAndGetText(By element) {
    	return wait.until(ExpectedConditions.elementToBeClickable(element)).getText();
    }
    
    public void clearTextFromElement(By element) {
    	wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
    }

    public void searchForIteam(By elementSearchBox, By elementSearchButton, String itemName) {
		
		enterTextIntoTextbox(elementSearchBox, itemName);
		clickOnTheElement(elementSearchButton);
	}
    
    public HashMap<String, Integer> getItemDetails(String websiteName, By elementSearchResults, By elementItemName, By elementItemPirce, By elementItemURL) {
		
		HashMap<String, Integer> resultsMap = new HashMap<String, Integer>();
        
		List<WebElement> SearchResults = driver.findElements(elementSearchResults);
        
		for (WebElement result : SearchResults) {
            
        	String productName;
            
        	int price;
            
        	String linkToTheProduct;
            
        	productName = result.findElement(elementItemName).getText();
            
        	try {
                price = Integer.parseInt(result.findElement(elementItemPirce).getText().replaceAll(",", "").replaceAll("₹", ""));
            } catch (Exception e) {
                price = 0;
            }
        	
        	linkToTheProduct = result.findElement(elementItemURL).getAttribute("href");
            
            resultsMap.put(websiteName + "|" + productName + "|" + linkToTheProduct, price);

		}
		return resultsMap;
	}
	
    
    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hmap) {
        
    	// Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hmap.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> l1,
                               Map.Entry<String, Integer> l2) {
                return (l1.getValue()).compareTo(l2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        
        return temp;
    }

	
    
}
