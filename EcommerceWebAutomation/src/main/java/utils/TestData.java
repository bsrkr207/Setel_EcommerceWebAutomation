package utils;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class TestData {

	private String browser;
	private String ecommerceWebsite1Name;
	private String ecommerceURL1;
	private String ecommerceWebsite2Name;
	private String ecommerceURL2;
	private String reportsPath;
	private String productName;
	
	
	
	public TestData readTestData() {
		
		TestData testdata = null;
		
		try {
			// create Gson instance
		    Gson gson = new Gson();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get("TestData.Json"));

		    // convert JSON string to User object
		    testdata = gson.fromJson(reader, TestData.class);

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return testdata;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getEcommerceURL1() {
		return ecommerceURL1;
	}

	public void setEcommerceURL1(String ecommerceURL1) {
		this.ecommerceURL1 = ecommerceURL1;
	}

	public String getEcommerceURL2() {
		return ecommerceURL2;
	}

	public void setEcommerceURL2(String ecommerceURL2) {
		this.ecommerceURL2 = ecommerceURL2;
	}

	public String getReportsPath() {
		return reportsPath;
	}

	public void setReportsPath(String reportsPath) {
		this.reportsPath = reportsPath;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getEcommerceWebsite1Name() {
		return ecommerceWebsite1Name;
	}

	public void setEcommerceWebsite1Name(String ecommerceWebsite1Name) {
		this.ecommerceWebsite1Name = ecommerceWebsite1Name;
	}

	public String getEcommerceWebsite2Name() {
		return ecommerceWebsite2Name;
	}

	public void setEcommerceWebsite2Name(String ecommerceWebsite2Name) {
		this.ecommerceWebsite2Name = ecommerceWebsite2Name;
	}
}
