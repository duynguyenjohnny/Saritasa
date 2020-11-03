package saritasa;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;




public abstract class basePage {
	protected String url;
	protected WebDriver driver;	
//	protected LoginController loginControl;
	protected userController userControl;
//	protected ProductController productControl;
//	protected AppDeviceController appDeviceControl;
//	protected CompanyController companyControl;
//	protected PromotionController promotionControl;
//	protected AudioRouteController audioControl;
//	protected TrainingController trainingControl;
	protected baseController baseAction;
	
	//protected ProductWorkflow productWf;
	//protected UserWorkflow userWf;
	protected void initControllers(WebDriver driver) {
//		loginControl = new LoginController(driver);
		userControl = new userController(driver);
//		productControl = new ProductController(driver);
//		appDeviceControl = new AppDeviceController(driver);
//		companyControl = new CompanyController(driver);
//		promotionControl = new PromotionController(driver);
//		audioControl = new AudioRouteController(driver);
//		trainingControl = new TrainingController(driver);
		baseAction = new baseController(driver);
	};
	
//	protected void initWorkflows(){
//		productWf = new ProductWorkflow(loginControl, productControl, userControl);
//		userWf = new UserWorkflow(userControl, loginControl);
//	};
protected abstract void initData();
	

	@Parameters({"browser", "url"})
	@BeforeMethod
	public void initializeDriver(@Optional("firefox") String browser, @Optional("http://qatrial.saritasa-hosting.com/") String url) {
		try {
			this.url = url;
			if (browser.equalsIgnoreCase("firefox")) {
				System.out.println(" Executing on FireFox");
				//System.setProperty("webdriver.gecko.driver", "webdrivers//geckodriver.exe");
				driver = new FirefoxDriver();
				//String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
				//driver.findElement(By.linkText(url)).sendKeys(selectLinkOpeninNewTab);
			} else if (browser.equalsIgnoreCase("chrome")) {
				System.out.println(" Executing on CHROME");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("chrome.switches","--disable-extensions");
				 options.addArguments("disable-popup-blocking");
				   options.addArguments("disable-impl-side-painting");
				System.setProperty("webdriver.chrome.driver", "webdrivers//chromedriver.exe");
				driver = new ChromeDriver(options);
				//driver = new ChromeDriver();
			
			
			} else if (browser.equalsIgnoreCase("ie")) {
				System.out.println("Executing on IE");
				System.setProperty("webdriver.ie.driver", "webdrivers/IEDriverServer64.exe");
				driver = new InternetExplorerDriver();
				
			} else {
				throw new IllegalArgumentException("The Browser Type is Undefined");
			}
			
			String URL = "http://saritasa:cl1ent@qatrial.saritasa-hosting.com/";
			driver.navigate().to(URL);
			//driver.get(url);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);			
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);			
			driver.manage().window().maximize();			
			initControllers(driver);
//			initWorkflows();
			initData();
			
		} catch (TimeoutException e) {
			System.out.println("Page load time out exception");
			driver.navigate().refresh();
		} catch (UnreachableBrowserException e) {
			System.out.println("Unreacheable browser exception");
			driver.navigate().refresh();
			
		}
		
    } 
	
	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult iTestResult) {
		if(driver == null)
			return;
		Reporter.setCurrentTestResult(iTestResult);
		MouseMover();
		if(!iTestResult.isSuccess()){
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			try {
				File scrnsht = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String destDir = "target/surefire-reports/screenshots";
				DateFormat dateFormat = new SimpleDateFormat(
						"dd_MMM_yyyy__hh_mm_ssaa");
				String destFile = dateFormat.format(new Date()) + ".png";
				org.apache.commons.io.FileUtils.copyFile(scrnsht, new File(destDir
						+ "/" + destFile));
				Reporter.log("View error : <a href=../screenshots/" + destFile + ">Screenshot</a>");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Reporter.log("FAILED", true);
		}else {
			Reporter.log("PASSED", true);
		}
		// close browser after finish test case
		if(driver == null){
			return;
		}
		System.out.println("Close browser");
		driver.close();
		Reporter.log("===========================================================================", true);
	}
	public void MouseMover() {
    	try {
    		Robot robot = new Robot();
			Point point = MouseInfo.getPointerInfo().getLocation();
			robot.mouseMove(point.x+120, point.y +120);
			robot.mouseMove(point.x, point.y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	