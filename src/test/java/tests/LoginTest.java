package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import base.BaseTest;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.ExtentReportManager;
import utils.Log;

public class LoginTest extends BaseTest {
	
	@DataProvider(name="LoginData")
	public Object[][] getLoginData()throws IOException{
		String filePath = System.getProperty("user.dir")+"/testdata/TestData.xlsx";
		ExcelUtils.loadExcel(filePath, "Sheet1");
		int rowCount = ExcelUtils.getRowCount();
		Object[][] data = new Object[rowCount-1][2];
		
		for(int i=1; i<rowCount; i++) {
			data[i-1][0] = ExcelUtils.getCellData(i,0); //User name
			data[i-1][1] = ExcelUtils.getCellData(i,1); //Password
		}
		ExcelUtils.closeExcel();
		return data;
	}
	
	@DataProvider(name="LoginData2")
	public Object[][] getData(){
		return new Object[][] {
			{"user1","pass1"},
			{"user2","pass2"},
			{"user3","pass3"},
			{"admin@yourstore.com","admin"},
		};
	}

	
	//@Test
	//@Parameters({"username","password"})
	@Test(dataProvider = "LoginData")
	public void testValidLogin(String username, String password)
	{
		Log.info("Starting login test...");
		test = ExtentReportManager.createTest("Login Test - "+username);
		
		test.info("Navigating to URL");
		LoginPage loginPage = new LoginPage(driver);
		
		Log.info("Adding credentials");
		test.info("Adding Credentials");
		//loginPage.enterUsername("admin@yourstore.com");
		//loginPage.enterPassword("admin");
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		test.info("Click on Login button");
		loginPage.clickLogin();
		
		System.out.println("Title of the page is : "+driver.getTitle());
		Log.info("Verifying page title");
		test.info("Verifying page title");
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
		
		test.pass("Login Success");
		
	}
	
	/*
	@Test
	public void testLoginWithInvalidCredentials()
	{
		Log.info("Starting login test...");
		test = ExtentReportManager.createTest("Login Test with Invalid Credentials");
		
		test.info("Navigating to URL");
		LoginPage loginPage = new LoginPage(driver);
		
		Log.info("Adding credentials");
		test.info("Adding Credentials");
		loginPage.enterUsername("admin1234@yourstore.com");
		loginPage.enterPassword("admin");
		test.info("Click on Login button");
		loginPage.clickLogin();
		
		System.out.println("Title of the page is : "+driver.getTitle());
		Log.info("Verifying page title");
		test.info("Verifying page title");
		Assert.assertEquals(driver.getTitle(), "nopCommerce demo store. Login");
		
		test.pass("Login Success");
		
	}*/
}
