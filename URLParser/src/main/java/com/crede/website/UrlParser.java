package com.crede.website;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class UrlParser {
	
	public static final int NUMBER_OF_PAGES_TO_BE_SEARCHED = 20;
	public static final int ITEM_NUMBER_PER_PAGE = 12;
	
	private static void writeWebElementsToFile(FileWriter fileWriter, WebDriver driver) throws IOException {
		List<WebElement> webElements = driver.findElements(By.className("list-title"));
		int xPathCounter = 0;
		fileWriter.write("-----------------------------------------------------------------------------------------------------------------\n");
		for (WebElement webElement : webElements) {
			String xPathAdInformation = "/html/body/igt-root/main/igt-ad-single/div[2]/ng-component/div/div/div[2]/div[2]/div/div[1]/ul/li[" + xPathCounter + "]/div[2]";
			xPathCounter++;
			if(webElement.getText().equals("İhale Kayıt No")) {
				fileWriter.write("İhale Kayıt No = " + driver.findElement(By.xpath(xPathAdInformation)).getText() + "\n");
			}
			else if(webElement.getText().equals("Niteliği, Türü ve Miktarı")) {
				fileWriter.write("Niteliği, Türü ve Miktarı = " + driver.findElement(By.xpath(xPathAdInformation)).getText() + "\n");
			}
			else if(webElement.getText().equals("İşin Yapılacağı Yer")) {
				fileWriter.write("İşin Yapılacağı Yer = " + driver.findElement(By.xpath(xPathAdInformation)).getText() + "\n");
			}
			else if(webElement.getText().equals("İhale Türü")) {
				fileWriter.write("İhale Türü = " + driver.findElement(By.xpath(xPathAdInformation)).getText() + "\n");
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			FileWriter fileWriter = new FileWriter("adInformations.txt");		
			System.setProperty("webdriver.chrome.driver","C:\\Users\\burak\\Selenium\\chromedriver_win32\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.navigate().to("https://www.ilan.gov.tr/ilan/kategori/9/ihale-duyurulari?txv=9&currentPage=1");
			for(int pageNumber = 1 ; pageNumber <= NUMBER_OF_PAGES_TO_BE_SEARCHED ; pageNumber++) {
				Thread.sleep(3000);
				for(int listItem = 1 ; listItem <= ITEM_NUMBER_PER_PAGE ; listItem++) {
					String xPathListItem = "/html/body/igt-root/main/igt-ad-list/div/div/div[2]/div[3]/div[2]/div[2]/div/igt-ad-single-list[" + listItem + "]/ng-component/a";
					driver.findElement(By.xpath(xPathListItem)).click();
					Thread.sleep(3000);
					writeWebElementsToFile(fileWriter, driver);
					driver.navigate().back();
					Thread.sleep(3000);
				}
				driver.findElement(By.xpath("//*[@id=\"server\"]/pagination-template/div/ul/li[10]/a\r\n")).click();
			}
			fileWriter.close();
			driver.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
