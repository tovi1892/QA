package tests;

import Pages.aboutPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll; // ודאי שזה Jupiter
import org.junit.jupiter.api.Test;      // שונה מ-org.junit.Test
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ex4 {
    public static WebDriver driver;
    public static aboutPage page1;

    @BeforeAll
    public static void BeforeAll(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automation.co.il/tutorials/selenium/demo1/indexID.html");

        page1 = new aboutPage(driver);

        System.out.println("BeforeAll finished and page1 initialized");
    }
    @AfterAll
    public static void AfterAll() {
        driver.quit();

    }


        @Test
    public void e2e(){
        page1.getFirstName().sendKeys("Tovi");
        page1.getLastName().sendKeys("Cohen");
        page1.getEmail().sendKeys("Tovi@gmail.com");
        page1.getBtnNext().click();

        page1   .getLevelIntermediate().click();
        page1.getBtnNext().click();

        page1.getStreetName().sendKeys("רחוב שדה חמד");
        page1.getStreetNumber().sendKeys("38");
        page1.getCity().sendKeys("מודיעין עילית");
        page1.selectCountry("Argentina");

        page1.getBtnFinish().click();

        String finalMessage = page1.getSuccessMessage().getText();
        Assertions.assertTrue(finalMessage.contains("כל הכבוד אם הצלחת להריץ אותו בעזרת אוטומציה"));
    }
    @Test
    public  void  email_error() {
        page1.getFirstName().sendKeys("Tovi");
        page1.getLastName().sendKeys("Cohen");

        page1.getEmail().sendKeys("tovi1892gmail.com");
        page1.getBtnNext().click();

        String errorText = page1.getEmailError().getText();
        System.out.println("Error found: " + errorText);
        page1.getEmail().clear();
        page1.getEmail().sendKeys("tovi@gmail.com");
        page1.getBtnNext().click();

        Assertions.assertFalse(errorText.isEmpty(), "הודעת השגיאה לא הופיעה!");

    }

    @Test
    public void nameValidationNegativeTest() {
        page1.getFirstName().sendKeys("h");
        page1.getLastName().sendKeys("bh");
        page1.getEmail().sendKeys("tovi@gmail.com"); // אימייל תקין כדי לבודד את שגיאת השמות

        page1.getBtnNext().click();

        String fNameError = page1.getFirstNameError().getText();
        System.out.println("First Name Error: " + fNameError);

        String lNameError = page1.getLastNameError().getText();
        System.out.println("Last Name Error: " + lNameError);

        Assertions.assertEquals("Please enter at least 3 characters.", fNameError);
        Assertions.assertEquals("Please enter at least 3 characters.", lNameError);
    }


}