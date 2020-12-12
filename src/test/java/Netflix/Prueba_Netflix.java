package Netflix;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

public class Prueba_Netflix {
    private WebDriver driver;
    private String pageTitle;
    private String newTitle;

    //LOCATORS
    By SesionTextLocator = By.xpath("//h1");
    By facebookTextLocator = By.xpath("//span[contains(text(), 'Iniciar sesión con Facebook')]");
    By pwdError = By.xpath("//div[@class='ui-message-contents']");


    @BeforeMethod
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.netflix.com/ ");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test(groups = "success")
    public void validarTituloTest(){
        pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Netflix Uruguay: Ve series online, ve películas online", "Current Title and expected were not equals");
    }
    @Test (groups = "success")
    public void iniciarSesionPageTest(){
        driver.findElement(By.xpath("//a[contains(text(), 'Iniciar sesión')]")).click();
        newTitle = driver.getTitle();
        System.out.println(newTitle);
        Assert.assertNotEquals(pageTitle, newTitle);
        WebElement sesionText = driver.findElement(SesionTextLocator);
        Assert.assertEquals(sesionText.getText(), "Inicia sesión");
        Assert.assertTrue(isDisplayed(facebookTextLocator));
    }
    public Boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    @Test (groups = "failure")
    public void loginToNetflixErrorTest(){
        driver.findElement(By.xpath("//a[contains(text(), 'Iniciar sesión')]")).click();
        driver.findElement(By.name("userLoginId")).sendKeys("XXX@yopmail.com");
        driver.findElement(By.name("password")).sendKeys("holamundo");
        driver.findElement(By.xpath("//label[@for='bxid_rememberMe_true']")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'Iniciar sesión')]")).click();
        Assert.assertTrue(isDisplayed(pwdError));
        WebElement check = driver.findElement(By.id("bxid_rememberMe_true"));
        if(check.isSelected()==false){
            check.click();
        } else{
            System.out.println("Recuerdame is already selected");
        }
    }

    @Test(groups = "success")
    public void fakeEmailTest(){
        Faker fakerGenerator = new Faker();
        NetflixPage sendingEmails = new NetflixPage(driver);
        sendingEmails.sendEmails(fakerGenerator.internet().emailAddress());
    }

    @Test(dataProvider = "emails", groups = "success")
    public void dataProviderEmailTest(String email){
        NetflixPage sendingEmails = new NetflixPage(driver);
        sendingEmails.sendEmails(email);
    }
    @DataProvider(name= "emails")
    public Object[][] sendEmail(){
        return new Object[][]{
                {"cuki@test.com"},
                {"test@let.com"},
                {"rock@yopmail.com"},
        };
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}
