package imdb_tests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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
	public List<WebElement> listOfMovies;
	
	  @Test(description="Check if at least 1 movie is in the list of movies on the IMDb page top 250 ")
	  public void testOfTop250() throws Exception {
		  
		  try{
			  listOfMovies = driver.findElements(By.className("titleColumn"));
			  } catch(Exception e){
				  System.out.println(e);
				  getscreenshot("testOfTop250");
			  }
		  		
		  System.out.println("Number of items in movie list is:" + listOfMovies.size());
		  assertTrue(listOfMovies.size() >=1);
	  }
	  
	  @Test(description="Try to sort by any available option in the Sort by dropdown on page Top rated movies and check if at least 1 movie is in the list")
	  public void testOfSorting() throws Exception {
		  Select dropdown = null; 
		  List<WebElement> sortOption = null;
		  
		try{
			dropdown = new Select(driver.findElement(By.name("sort")));
			sortOption = dropdown.getOptions();
			
			  } catch(Exception e){
				  System.out.println(e);
				  getscreenshot("testOfSorting");
			  }
			  
		for(WebElement e : sortOption){			
			dropdown.selectByVisibleText(e.getText());
			listOfMovies = driver.findElements(By.className("titleColumn"));
			System.out.println("test of sortby: " + e.getText());
			assertTrue(listOfMovies.size() >1);
		}		
	  }
	  
	  @Test(description="Try to click on genre western and display and check if at least 1 movie is in the list")
	  public void testOfGenre()throws Exception  {
		  
		  try{
		  driver.findElement(By.linkText("Western")).click();
		  } catch(Exception e){
			  System.out.println(e);
			  getscreenshot("testOfGenre");
		  }
		  
		  listOfMovies = driver.findElements(By.className("lister-item-header"));			
		  System.out.println("Number of items in movie list is:" + listOfMovies.size());
		  assertTrue(listOfMovies.size() >1);
	  }
	  
	  public void getscreenshot(String name) throws Exception 
	  {		
	         File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	  
	         String destDir = System.getProperty("user.dir") + "/" + "test-output/screenshots";
	         new File(destDir ).mkdirs();
	         String destFile = destDir + "/" + name +  new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
	         FileUtils.copyFile(scrFile, new File(destFile));
	  }
	  
	  
	  @BeforeSuite
	  public void beforeMethod() {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.get("http://www.imdb.com/chart/top");
			assertEquals( driver.getTitle(), "IMDb Top 250 - IMDb");
	  }
	  
	  @AfterSuite
	  public void afterMethod() {	  
		  driver.quit();
	  }
}
