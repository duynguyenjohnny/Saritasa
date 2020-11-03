package saritasa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;



public class test3 extends basePage{

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
		
		userControl.addLog("ID : TEST_03 : Test 3 ." );
		/*
		VP: Check nested categories creation: http://qatrial.saritasa-hosting.com/admin/categories create root 
		category and assign several subcategories to that then delete root category.
		 */

		//Open admin page
		userControl.opennewurl("http://qatrial.saritasa-hosting.com/admin");
		//VP: 1.Check login to backend under admin@spggoods.com/admin credentials and make sure 
		//username appears on top right corner ("Hello Nik Froehlich, Welcome to SPGgoods.com").
		userControl.inputtexttofield(adminname, "email");
		userControl.inputtexttofield(adminpass, "password");

		// add new nest categories
		userControl.opennewurl("http://qatrial.saritasa-hosting.com/admin/categories/add");
		userControl.inputtexttofield("NewNest", "title");
		userControl.inputtexttofield("1", "sequence");
		
		// click save
		userControl.clickDOM("return document.getElementsByClassName('button black float-left ml20')[2]");
		Thread.sleep(2000);
		//add subcategories 1 
		userControl.opennewurl("http://qatrial.saritasa-hosting.com/admin/categories/add");
		
		userControl.inputtexttofield("SubNest1", "title");
		userControl.inputtexttofield("1", "sequence");
		
		WebElement parentoption = driver.findElement(By.id("select_parentCategoryID58049962a7d8f"));
		parentoption.click();

		WebElement nestcategories = parentoption.findElement(By.xpath("//*[.=\"NewNest\"]"));
		nestcategories.click();
		userControl.clickDOM("return document.getElementsByClassName('button black float-left ml20')[2]");
		
		//add subcategories 2 
		userControl.opennewurl("http://qatrial.saritasa-hosting.com/admin/categories/add");
		
		userControl.inputtexttofield("SubNest1", "title");
		userControl.inputtexttofield("1", "sequence");
		
		WebElement parentoption2 = driver.findElement(By.id("select_parentCategoryID58049962a7d8f"));
		parentoption2.click();

		WebElement nestcategories2 = parentoption2.findElement(By.xpath("//*[.=\"NewNest\"]"));
		nestcategories2.click();
		userControl.clickDOM("return document.getElementsByClassName('button black float-left ml20')[2]");
		
		//delete root categories
		userControl.click(".//*[@id='cat_0']/td[4]/div/a[3]/div");
		
		userControl.addLog("Test 3 was completed");
	}

}