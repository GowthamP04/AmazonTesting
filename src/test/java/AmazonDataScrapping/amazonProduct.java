package AmazonDataScrapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class amazonProduct {
	static int iteration = 1;
	static Cell cell;
	public WebDriver driver;
	String avilablitystatus;
	String productPrice;
	String productName;
	// String url;

	@BeforeClass
	public void launchbrowser() {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Test(priority = 1)
	public void landingURL() {
		driver.get("https://www.amazon.in/");
	}

	@Test(priority = 2)
	public void searchProduct() throws IOException {

		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("beatxp smart watch");
		Actions a = new Actions(driver);
		a.keyDown(Keys.ENTER).keyUp(Keys.ENTER).build().perform();

		String Totalnumber = driver
				.findElement(By.xpath(
						"//span[@class='s-pagination-strip']//span[@class='s-pagination-item s-pagination-disabled']"))
				.getText();
		int tn = Integer.parseInt(Totalnumber);
		System.out.println("Total pages :" + tn);

		ExcelWriter excelWriter = new ExcelWriter(System.getProperty("user.dir") + "\\src\\test\\java\\ExcelSheet.xlsx",
				"Sheet1");
		excelWriter.setColumnWidth(0, 15 * 1500);
		excelWriter.setColumnWidth(1, 15 * 1000);
		excelWriter.setColumnWidth(2, 15 * 400);
		excelWriter.setColumnWidth(3, 15 * 400);
		excelWriter.writeRow(new String[] { "url", "PRODUCT NAME", "PRODUCT PRICE", "DESCRIPTION" });
		for (int i = 0; i <= 3; i++) {
			excelWriter.setCellColor(0, i, IndexedColors.YELLOW.getIndex());
		}

		ArrayList<String> StrUrls = new ArrayList<String>();
		for (int p = 1; p <= 1; p++) {
			if (tn > 1) {
				try {

					String products = "//div[@class='aok-relative']//span[@class='rush-component']//a";
					List<WebElement> links = driver.findElements(By.xpath(products));
					// int size = pn.size();
					// System.out.println("Total Number of pages :" +pn.size());

					for (WebElement link : links) {
						String urls = link.getAttribute("href");
						// System.out.println(urls);
						StrUrls.add(urls);
					}

				} catch (Exception e) {

				}
			}

		}
		for (String url : StrUrls) {
			driver.navigate().to(url);
			System.out.println("URL :" + url);

			productName = driver.findElement(By.xpath("//span[@id='productTitle']")).getText();
			System.out.println("ProductName :" + productName);

			try {
				productPrice = driver.findElement(By.xpath(
						"//span[@class='a-price aok-align-center reinventPricePriceToPayMargin priceToPay']//span[@class='a-price-whole']"))
						.getText();
				System.out.println("ProductPrice :" + productPrice);

			} catch (Exception en) {
				System.out.println("ProductPrice : " + "Price is not Available");
			}
			try {
				avilablitystatus = driver.findElement(By.xpath("//span[@class='a-size-medium a-color-success']"))
						.getText();
				System.out.println("Product Status :" + avilablitystatus);

			} catch (Exception e) {
				avilablitystatus = "Out of Stock";
				System.out.println("Product Status :" + avilablitystatus);

			}
			excelWriter.writeRow(new String[] { url, productName, productPrice, avilablitystatus });

			System.out.println("***************************" + iteration++ + "***************************");

		}

		excelWriter.save(System.getProperty("user.dir") + "\\src\\test\\java\\AmazonDataScrapping\\ExcelSheet.xlsx");
	}

	@AfterClass
	public void Endbrowser() {
		driver.quit();	
		}

}
