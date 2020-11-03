package saritasa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;



public class test2 extends basePage{

	JavascriptExecutor js = (JavascriptExecutor) driver;
	protected baseController baseAction;
	String adminname = "admin@spggoods.com";
	String adminpass = "admin"; 
	//String classsendmail = "center.plr7";
	
	@Override
	protected void initData() {
	}

	/*
	 * Check that Contact Us form is working. http://qatrial.saritasa-hosting.com/contactus
	 */

	@Test
	public void TEST_02() throws InterruptedException{
		
		userControl.addLog("ID : TEST_02 : Test 2 ." );
		/*
		VP1 Check login to backend under admin@spggoods.com/admin credentials and make sure 
		username appears on top right corner ("Hello Nik Froehlich, Welcome to SPGgoods.com").
		
		VP2 Make sure test is also working for vendors (firstvendor@spggoods.com/vendor and 
		secondvendor@spggoods.com/vendor).
		
		VP3 Test new product creation (including product image upload): 
		http://qatrial.saritasa-hosting.com/admin/products/add and make sure created product also displays on frontend.
		 */

		//Open admin page
		userControl.opennewurl("http://qatrial.saritasa-hosting.com/admin");
		//VP: 1.Check login to backend under admin@spggoods.com/admin credentials and make sure 
		//username appears on top right corner ("Hello Nik Froehlich, Welcome to SPGgoods.com").
		userControl.inputtexttofield(adminname, "email");
		userControl.inputtexttofield(adminpass, "password");
		userControl.clicklgin();
		
		Thread.sleep(2000);
		String Helloadmin = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('mt10 float-left')[1].innerHTML");
		Assert.assertTrue(Helloadmin.contains("Nik Froehlich"));
		userControl.addLog("Confirmed! Username appears on top right corner");
		
		//VP:2. Make sure test is also working for vendors (firstvendor@spggoods.com/vendor and 
		//secondvendor@spggoods.com/vendor).
		//log out
		userControl.clicklgout();
		
		Thread.sleep(2000);
		userControl.inputtexttofield("firstvendor@spggoods.com", "email");
		userControl.inputtexttofield("vendor", "password");
		userControl.clicklgin();
		
		Thread.sleep(2000);
		String Hellovendor1 = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('mt10 float-left')[1].innerHTML");
		Assert.assertTrue(Hellovendor1.contains("First Vendor"));
		
		userControl.clicklgout();
		
		Thread.sleep(2000);
		userControl.inputtexttofield("secondvendor@spggoods.com", "email");
		userControl.inputtexttofield("vendor", "password");
		userControl.clicklgin();
		
		Thread.sleep(2000);
		String Hellovendor2 = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('mt10 float-left')[1].innerHTML");
		Assert.assertTrue(Hellovendor2.contains("Second Vendor"));
		
		userControl.addLog("Name was right for first and second Vendor!");
		
		
		//VP:3. Test new product creation (including product image upload): 
		//http://qatrial.saritasa-hosting.com/admin/products/add and make sure created product also displays on frontend.
		userControl.clicklgout();
		//login with admin
		Thread.sleep(2000);
		userControl.inputtexttofield(adminname, "email");
		userControl.inputtexttofield(adminpass, "password");
		userControl.clicklgin();
		
		Thread.sleep(2000);
		
		userControl.opennewurl("http://qatrial.saritasa-hosting.com/admin/products/add");
		Thread.sleep(5000);
		
		userControl.clickElement("return document.getElementsByClassName('icon-button select')[2]");
		Thread.sleep(2000);
		userControl.clickElement("return document.getElementsByClassName('select-ul')[2].children[1]");
		Thread.sleep(3000);
		
		userControl.inputtexttofield("testProduct", "name");
		
		//userControl.clickElement("return document.getElementsByClassName('button middle-black')[0]");
		userControl.uploadFileElement("imsaritasa.jpg");
		WebElement code = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('products-attributes-size')[1]");
		code.sendKeys("testSKU");
		//driver.findElement(By.id("add-attribute-button")).click();;
		//btnadd.click();
		//Thread.sleep(5000);
		//driver.findElement(By.cssSelector("input[onchange*='ajax.submit']")).click();
		
		userControl.inputtexttofield("15", "price");
		userControl.inputtexttofield("10", "weight");
		userControl.inputtexttofield("11", "productLength");
		userControl.inputtexttofield("12", "width");
		userControl.inputtexttofield("13", "height");
		
		
		userControl.clickElement("return document.getElementsByClassName('button black float-left ml20')[2]");
		Thread.sleep(4000);
		
		//userControl.clickElement("return document.getElementById('container_btnUploadImage5800a4f62cd46')[0]");
		//document.getElementsByClassName("select-ul")[2].children[1]
		
		userControl.addLog("New product was added correctly");
		//check new Product was displayed on FE 
		userControl.opennewurl("http://qatrial.saritasa-hosting.com");
		Thread.sleep(2000);
		userControl.inputtexttofield("testProduct", "searchText");
		WebElement search = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('icon-search')[0]");
		search.click();
		Thread.sleep(2000);
		WebElement exist = driver.findElement(By.className("image"));
		Assert.assertTrue(exist.isDisplayed());
		userControl.addLog("New product was displayed correctly");
	}

}