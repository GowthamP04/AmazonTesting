package FlipkartDataScrapping;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlipkartProduct {

	public WebDriver driver;

	static int iteration =1;

	@BeforeClass
	public void launch() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Test(priority = 1)
	public void landingURL() {
		driver.get("https://www.flipkart.com/");
	}

	@Test(priority = 2)
	public void searchProduct() {
		driver.findElement(By.className("Pke_EE")).sendKeys("realme");
		Actions a = new Actions(driver);	
		a.keyDown(Keys.ENTER).keyUp(Keys.ENTER).build().perform();
	}

	@Test(priority = 3)
	public void Product() {
		String pagenumber = driver.findElement(By.xpath("//div[@class='_2MImiq']//span[1]")).getText();
		System.out.println(pagenumber);
		String [] part = pagenumber.split("Page 1 of ");	
		String totalpage = part[1].replace(",","").trim();

		int totalpagenumber = Integer.parseInt(totalpage);
		System.out.println("Total Page Number :" + totalpagenumber);

		ArrayList<String> strurl = new ArrayList<String>();
		for(int p = 1;p <= totalpagenumber; p++) {
			
			if(totalpagenumber>1) {
				
				try {
					
					String products = "//div[@class='_1YokD2 _2GoDe3']//div[2]//div[@class='_1AtVbE col-12-12']//div[@class='_13oc-S']//a";
					List<WebElement> links = driver.findElements(By.xpath(products));

					for (WebElement link : links) {
						String urls = link.getAttribute("href");
						strurl.add(urls);
					}
				}catch (Exception e) {

				}
			}
		}

		for (String url : strurl) {
			driver.navigate().to(url);
			System.out.println(url);

			String productname = driver.findElement(By.xpath("//span[@class='B_NuCI']")).getText();
			System.out.println("Product Name :"+productname);

			String productprice = driver.findElement(By.xpath("//div[@class='_30jeq3 _16Jk6d']")).getText();
			System.out.println("Product Price :"+productprice);

			try {
				String availablitystatus = driver.findElement(By.xpath("//div[@class='rd9nIL']")).getText();
				System.out.println("Product Status :"+availablitystatus);
			}catch (Exception e) {
				String availablitystatus = "";

			}
			System.out.println("*******************+ iteration++ +*******************");
		}
	}
}