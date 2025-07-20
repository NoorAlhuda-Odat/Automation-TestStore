import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases {
	
	WebDriver driver = new ChromeDriver();
	String URL ="https://www.automationteststore.com";
	String LoginOrRegisterPage ="https://automationteststore.com/index.php?rt=account/login";
	String logoutPage = "https://automationteststore.com/index.php?rt=account/logout";
	
	String[] firstnames = {"Noor","Ragad","Shahed","Malak","Zain"};
	String[] lastnames = {"Mohamed","Ahmad","Mahmood","Ali","Kalid"};
	
	Random rand = new Random();
	
	String PublicUserName = "";
	
	@BeforeTest
	public void MySetup() throws InterruptedException{
		driver.get(URL);
		driver.manage().window().maximize();
		 Thread.sleep(5000); 
	}
	
	@Test(priority = 1)
	public void MyRegisterTest() throws InterruptedException {
		driver.findElement(By.linkText("Login or register")).click();
		//driver.get("LoginOrRegisterPage");
		//driver.findElement(By.partialLinkText("Login or ")).click();
		
		driver.findElement(By.xpath("//button[@title='Continue']")).click();
		//driver.findElement(By.cssSelector(".btn.btn-orange.pull-right")).click();
		
		int RandomIndex = rand.nextInt(5);
		
		String RandomFirstName =firstnames[RandomIndex];
		WebElement FirstName = driver.findElement(By.id("AccountFrm_firstname"));
		FirstName.sendKeys(RandomFirstName);
		
		String RandomLastName = lastnames [RandomIndex];
		WebElement LastNames = driver.findElement(By.id("AccountFrm_lastname"));
		LastNames.sendKeys(RandomLastName);
	
		WebElement Email = driver.findElement(By.id("AccountFrm_email"));
		int RandomIndexForTheEmail = rand.nextInt(11111);
		String Username = RandomFirstName+RandomLastName+RandomIndexForTheEmail ;
		Email.sendKeys(Username+"@gmail.com");
		
		PublicUserName = Username ;
		
		driver.findElement(By.id("AccountFrm_telephone")).sendKeys("0798214525");
		driver.findElement(By.id("AccountFrm_fax")).sendKeys("ABC");
		driver.findElement(By.id("AccountFrm_company")).sendKeys("DEF");
		driver.findElement(By.id("AccountFrm_address_1")).sendKeys("000");
		driver.findElement(By.id("AccountFrm_address_2")).sendKeys("000");
		driver.findElement(By.id("AccountFrm_city")).sendKeys("Amman");
		
		WebElement country = driver.findElement(By.id("AccountFrm_country_id"));
		Select MySelect = new Select(country);
		MySelect.selectByVisibleText("Jordan");
		//MySelect.selectByValue("108");
		//MySelect.selectByIndex(1);
		
		Thread.sleep(1000);
		
		WebElement Region = driver.findElement(By.id("AccountFrm_zone_id"));
		Select MySelect2 = new Select(Region);
		MySelect2.selectByValue("1704");
		//MySelect2.selectByVisibleText("Amman");
		//MySelect.selectByIndex(1);
		
		driver.findElement(By.id("AccountFrm_postcode")).sendKeys("123");
		driver.findElement(By.id("AccountFrm_loginname")).sendKeys(Username);
		driver.findElement(By.id("AccountFrm_password")).sendKeys("P@ssw0rd");
		driver.findElement(By.id("AccountFrm_confirm")).sendKeys("P@ssw0rd");
		
		driver.findElement(By.id("AccountFrm_newsletter0")).click();
		driver.findElement(By.id("AccountFrm_agree")).click();
		driver.findElement(By.xpath("//button[@title='Continue']")).click();
		
		driver.get(logoutPage);	
	}
	
	@Test(priority = 2)
	public void MyLoginTest() throws InterruptedException {
		driver.get(LoginOrRegisterPage);
		
		WebElement Username = driver.findElement(By.id("loginFrm_loginname"));
		WebElement Password = driver.findElement(By.id("loginFrm_password"));
		Username.sendKeys(PublicUserName);
		Password.sendKeys("P@ssw0rd");
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		
	}
	
	@Test(priority = 3)
	public void RandomItemTest() throws InterruptedException {
		driver.get("https://automationteststore.com/index.php?rt=product/category&path=58");
		List<WebElement> Items = driver.findElements(By.cssSelector(".col-md-2.col-sm-2.col-xs-6"));
		int RandomItem= rand.nextInt(Items.size());
		Items.get(RandomItem).click();
		
		List<WebElement> Items2 = driver.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12"));
		int RandomItem2= rand.nextInt(Items2.size());
		Items2.get(RandomItem2).click();
		
		driver.findElement(By.className("cart")).click();
		
		int randomQuantity = rand.nextInt(2, 10);
		// int randomQuantity = rand.nextInt(9) + 2; 
		WebElement quantityInput = driver.findElement(By.cssSelector(".form-control.short"));
		quantityInput.clear(); 
		quantityInput.sendKeys(String.valueOf(randomQuantity)); 
		    
		driver.findElement(By.id("cart_checkout1")).click();
		// driver.findElement(By.xpath("//a[@id='cart_checkout1']")).click();
	
		driver.findElement(By.id("checkout_btn")).click();
		 
		System.out.println("Your Order Has Been Processed!");
		
		Thread.sleep(2000);
		driver.findElement(By.linkText("Continue")).click();

	}
	
	@Test(priority = 4)
	public void SearchProductTest() throws InterruptedException {
	    WebElement searchBox = driver.findElement(By.id("filter_keyword"));
	    searchBox.sendKeys("Skincare");

	    WebElement searchButton = driver.findElement(By.cssSelector(".button-in-search"));
	    searchButton.click();

	    Thread.sleep(5000); 
	    
	    List<WebElement> results = driver.findElements(By.cssSelector(".productname"));
	    Assert.assertTrue(results.size() > 0, "Search results should be displayed");
	}
	
	@Test(priority = 5)
	public void LogoutTest() throws InterruptedException {
		Actions action = new Actions(driver) ;
		
		WebElement accountMenu = driver.findElement(By.cssSelector("a.menu_account"));
		action.moveToElement(accountMenu).perform();
		accountMenu.click();
	    Thread.sleep(1000);
	    
	    WebElement logoffLink = driver.findElement(By.linkText("Logoff"));
	    logoffLink.click();
	    
	    WebElement continueBtn = driver.findElement(By.linkText("Continue"));
	    continueBtn.click();

	   
	}


	
}
