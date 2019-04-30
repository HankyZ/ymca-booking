import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Steps {

    // Variables
    private WebDriver driver;
    private final String chromeDriverPath = "drivers/chromedriver.exe";

    /**
     * method to confirm system is in appropriate initial state
     */
    private void setUp() {
        // start chrome browser
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        // maximize browser
        driver.manage().window().maximize();
    }

    /**
     * method to ensure system is returned to initial state
     */
    private void tearDown() {
        driver.quit();
    }

}