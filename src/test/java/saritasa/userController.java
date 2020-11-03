package saritasa;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;


public class userController extends baseController {
	public userController(WebDriver driver) {
		super(driver);
	}


	
	public boolean isCheckBoxEnable(WebElement checkBox) {
		try {
			return !checkBox.getAttribute("innerHTML").contains("disabled");
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : checkBoxEnable");
		}
		return false;
	}


	
	public String getAtribute(WebElement element, String atr) {
		try {
			//waitForAjax();
			String text = element.getAttribute(atr);
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : " + element);
			return "";
		}
	}
	
	

	
	// TODO: not yet checked
		public HashMap<String, String> getItemUserMenu(String xpath) {
			HashMap<String, String> data = new HashMap<String, String>();
			try {
				WebElement footer = driver.findElement(By.xpath(xpath));
				List<WebElement> columns = footer.findElements(By.tagName("a"));
				for (int i = 1; i <= columns.size(); i++) {
					data.put("option " + i, columns.get(i - 1).getAttribute("text"));
				}
			} catch (NoSuchElementException e) {
				addLog("NoSuchElementException on getItemUserMenu " + xpath);
			}
			return data;
		}
	
	// TODO 
	public ArrayList<String> getAllLinkA(String xpath) {
		ArrayList<String> links = new ArrayList<String>();
		try {
			WebElement action = driver.findElement(By.xpath(xpath));
			List<WebElement> actions = action.findElements(By.tagName("a"));
			for (WebElement webElement : actions) {
				String act = webElement.getText();
				links.add(act);
			}
		} catch (NoSuchElementException e) {
			System.err.println("No such element exception : getAllLinkA");
		}
		return links;
	}

	// TODO
	public String[] getConfirm() {
		String confirm[] = new String[2];
		int i = 0;
		try {
			List<WebElement> links = driver.findElements(By.tagName("a"));
			for (WebElement webElement : links) {
				String href = webElement.getAttribute("href");
				if (href != null && href.contains("javascript:;")) {
					confirm[i] = webElement.getText();
					System.out.println("Confirm : " + confirm[i]);
					i++;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("No Such element exception : getConfirm");
		}
		return confirm;
	}
	
	// TODO modify, make generic and move to BaseController (maybe duplicated)
	public boolean selectOptionInDropdown(String xpath, String name) {
		try {
			if (name == null | name == "") {
				return false;
			}
			// Get dropdown box
			WebElement groupBox = driver.findElement(By.xpath(xpath));
			// Click on dropdown
			groupBox.findElement(By.tagName("button")).click();
			//waitForAjax();
			// Select checkbox
			List<WebElement> options = groupBox.findElement(By.tagName("ul")).findElements(By.tagName("li"));
			for (WebElement option : options) {
				if (option.getText().contains(name)) {
					addLog("Select option: " + name);
					option.findElement(By.tagName("a")).click();
				//	waitForAjax();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
			}
			addLog("Option: " + name + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return false;
		}
	}
	

	
	@Override
	public String getTextByXpath(String xpath) {
		String text = "";
		try {
			text = driver.findElement(By.xpath(xpath)).getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath :  "+ xpath);
		}
		return text;
	}
	
	public void clicklgin() {
		WebElement lgin = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('big-blue-button float-right mr68')[0]");  
		lgin.click();
		
	}
	
	public void clickDOM(String exScript) {
		WebElement clickDOM = (WebElement) ((JavascriptExecutor) driver).executeScript(exScript);  
		clickDOM.click();
		
	}
	
	public void clicklgout() {
		WebElement lgout = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('button blue float-left ml20 mt6')[0]");  
		lgout.click();
		
	}
	
	public void clickElement(String exScript ) {
		try {
		WebElement click = (WebElement) ((JavascriptExecutor) driver).executeScript(exScript);  
		click.click();
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  "+ exScript);
		}
	}
	@Override
	public void type(String xpath, String data) {
		try {
			//waitForAjax();
			driver.findElement(By.xpath(xpath)).clear();
			addLog("change data : "+data);
			driver.findElement(By.xpath(xpath)).sendKeys(data);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  "+ xpath);
		}
		
	}
	
	@Override
	public void opennewurl(String url) {
		try {
			//waitForAjax();
			addLog("Open new url : " + url);
//			Thread.sleep(2000);
			driver.navigate().to(url);
			//href example: "a[href*='long']"
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("No element exception : " + url);
			Assert.assertTrue(false);
		}
	}
	
	@Override
	public void assertbackgroundcolor(String name,  String verify) {
		try {
			//waitForAjax();
			addLog("get background color : " + name);
//			Thread.sleep(2000);
			//ing color = driver.findElement(By.name("btnK")).getCssValue("background-color");
			String color = driver.findElement(By.name(name)).getCssValue("background-color");
			System.out.println("color is "+color);
			Assert.assertEquals(verify, color);
			//driver.navigate().to(url);
			//href example: "a[href*='long']"
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + name);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	@Override
	public void inputtexttofield(String text,  String name) {
		try {
			//waitForAjax();
			addLog("input text to : " + name);
//			Thread.sleep(2000);
			//ing color = driver.findElement(By.name("btnK")).getCssValue("background-color");
			driver.findElement(By.name(name)).sendKeys(text);;
			//driver.navigate().to(url);
			//href example: "a[href*='long']"
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + name);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	@Override
	public String jsID(String Id) {
		String text = "";
		try {
			WebElement btn = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById(Id)");
			text = btn.getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByjsID :  "+ Id);
		}
		return text;
	}
	
	@Override
	public void clickclassname(String classname) {
		//	waitForAjax();
			try {
				addLog("Click : " + classname);
				driver.findElement(By.className(classname)).click();
				//waitForAjax();
//			
			} catch (NoSuchElementException e) {
				Assert.assertTrue(false);
				addLog("No element exception : " + classname);

			}
		}
	
	@Override
	public void click(String xpath) {
		//waitForAjax();
		try {
			addLog("Click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
		//	waitForAjax();

		} catch (NoSuchElementException e) {
			addLog("No element exception : " + xpath);
			Assert.assertTrue(false);
		}
	}
	
}
