package imdb_tests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class imdb_top250_tests {
	public WebDriver driver;

	
	  @Test(description="Check if at least 1 movie is in the list of movies on the IMDb page top 250 ")
	  public void testOfTop250() {
		  List<WebElement> listOfMovies = driver.findElements(By.className("titleColumn"));
		
		  System.out.println("Number of items in movie list is:" + listOfMovies.size());
		  assertTrue(listOfMovies.size() >1);
	  }
	  
	  @Test(description="Try to sort by any available option in the Sort by dropdown option on page Top rated movies and check if at least 1 movie is in the list")
	  public void testOfSorting() {
		Select dropdown = new Select(driver.findElement(By.name("sort")));
		List<WebElement> sortOption = dropdown.getOptions();
		
		for(WebElement e : sortOption){
			
			dropdown.selectByVisibleText(e.getText());
			List<WebElement> listOfMovies = driver.findElements(By.className("titleColumn"));
			System.out.println("test of sortby: " + e.getText());
			assertTrue(listOfMovies.size() >1);
		}
		
	  }
	  
	  @Test(description="Try to click on genre western and display and check if at least 1 movie is in the list")

	  public void testOfGenre()throws Exception  {
		  try{
		  driver.findElement(By.linkText("Western")).click();
		  } catch(Exception e){
			  
			  getscreenshot();
		  }
		  
		  List<WebElement> listOfMovies = driver.findElements(By.className("lister-item-header"));
			
		  System.out.println("Number of items in movie list is:" + listOfMovies.size());
		  assertTrue(listOfMovies.size() >1);
	  }
	  
	  public void getscreenshot() throws Exception 
	  {
	          File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	       //The below method will save the screen shot in d drive with name "screenshot.png"
	          FileUtils.copyFile(scrFile, new File("D:\\screenshot.png"));
	  }
	  
	  
	  @BeforeSuite
	  public void beforeMethod() {
			
			//File file = new File("C:/selenium/java/geckodriver.exe");
			//System.setProperty("webdriver.gecko.driver",file.getAbsolutePath());
			//driver = new FirefoxDriver();
			
			System.setProperty("webdriver.chrome.driver", "C:/selenium/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.get("http://www.imdb.com/chart/top");
			//assertEquals( driver.getTitle(), "IMDb Top 250 - IMDb", "IMDb page was not loaded properly");
	  }
	  
	  
	  

	  @AfterSuite
	  public void afterMethod() {	  
		  driver.quit();
	  }
}
