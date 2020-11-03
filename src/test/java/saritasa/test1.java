package saritasa;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;



public class test1 extends basePage{

	JavascriptExecutor js = (JavascriptExecutor) driver;
	protected baseController baseAction;
	String whitebackground = "rgba(255, 255, 255, 1)";
	String redbackground = "rgba(252, 199, 200, 1)"; 
	//String classsendmail = "center.plr7";
	
	@Override
	protected void initData() {
	}

	/*
	 * Check that Contact Us form is working. http://qatrial.saritasa-hosting.com/contactus
	 */

	@Test
	public void TEST_01(){
		
		userControl.addLog("ID : TEST_01 : Check that Contact Us form is working." );
		/*
			1. Form should not highlight any fields when first opened.
			2. After you submit incorrect data - it should highlight missed/incorrect fields with red background 
			and "Required" message.
		 */

		//Open contact us page
		userControl.opennewurl("http://qatrial.saritasa-hosting.com/contactus");
		//VP: 1. Form should not highlight any fields when first opened.
		userControl.assertbackgroundcolor("fullName", whitebackground);
		userControl.assertbackgroundcolor("storeName",whitebackground);
		userControl.assertbackgroundcolor("phone", whitebackground);
		userControl.assertbackgroundcolor("email", whitebackground);
		userControl.assertbackgroundcolor("confirmEmail", whitebackground);
		userControl.assertbackgroundcolor("comments", whitebackground);
		userControl.addLog("Confirmed! Form does not highlight any fields when first opened");
		
		//VP:2. After you submit incorrect data - it should highlight missed/incorrect fields with red background
		//and "Required" message.
		userControl.inputtexttofield("test", "storeName");
		userControl.inputtexttofield("test", "comments");
		//click send email button
		 WebElement btn = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('center plr7')[2]");  
		 btn.click(); //document.forms[index of the form].elements[index of the element] a#sendMail57ff574a51a20.center.plr7
		//assert background and required texts storename and comments
		userControl.assertbackgroundcolor("storeName",whitebackground);
		String valstorename = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('input-row')[1].querySelectorAll('span')[0].innerHTML");
		//Assert.assertEquals("none",userControl.isElementPresent(".//*[@id='valStore57ff574a4f44b']"));
		Assert.assertEquals("",valstorename);
		userControl.assertbackgroundcolor("comments", whitebackground);
		String valcomments = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('input-row')[5].querySelectorAll('span')[0].innerHTML");
		Assert.assertEquals("",valcomments);
		//Assert.assertEquals(false,userControl.isElementPresent(".//*[@id='valcomment57ff574a51717']"));
		userControl.addLog("Store name and comments are checked validation correctly");
		
		//assert background and required texts email and valemail.
		userControl.assertbackgroundcolor("email", redbackground);
		String valEmail = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('input-row')[3].querySelectorAll('span')[0].innerHTML");  
		Assert.assertEquals("Required",valEmail );
		userControl.assertbackgroundcolor("confirmEmail", redbackground);
		String valEmailagain = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('input-row')[4].querySelectorAll('span')[0].innerHTML");
		Assert.assertEquals("Required",valEmailagain );
	
		userControl.addLog("Email and confirmEmail are checked validation correctly");

	}

}