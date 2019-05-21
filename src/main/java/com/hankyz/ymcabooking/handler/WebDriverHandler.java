package com.hankyz.ymcabooking.handler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;

public class WebDriverHandler {

    private final String chromeDriverPath = "drivers/chromedriver.exe";
    private final String ymcaUrl = "https://inscription.ymcaquebec.org/Facilities/FacilitiesSearchWizard.asp";

    private final String searchFacilityBookingRadioId = "search-facbook-radio";
    private final String startDaySelectId = "DayFrom";
    private final String startMonthSelectId = "MonthFrom";
    private final String startYearSelectId = "YearFrom";
    private final String endDaySelectId = "DayTo";
    private final String endMonthSelectId = "MonthTo";
    private final String endYearSelectId = "YearTo";
    private final String facilityFunctionSelectId = "FacilityFunctions";
    private final String gfBadmintonFunctionValue = "38";
    private final String searchButtonCssSelector = ".ui-state-default.ui-corner-all";

    private static WebDriverHandler instance;

    private WebDriver driver;

    public static WebDriverHandler getInstance() {
        if (instance == null)
            instance = new WebDriverHandler();
        return instance;
    }

    private WebDriverHandler() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        // start chrome browser
        driver = new ChromeDriver();
        driver.get(ymcaUrl);
        // maximize browser
//        driver.manage().window().maximize();

//        driver.quit();
    }

    public void book(String day, String month, String year) {
        // select facility booking
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.id(searchFacilityBookingRadioId)))
                .click();

        // wait for page to load
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(startDaySelectId)));

        // select start date
        Select startDaySelect = new Select(driver.findElement(By.id(startDaySelectId)));
        Select startMonthSelect = new Select(driver.findElement(By.id(startMonthSelectId)));
        Select startYearSelect = new Select(driver.findElement(By.id(startYearSelectId)));
        startDaySelect.selectByValue(day);
        startMonthSelect.selectByValue(month);
        startYearSelect.selectByValue(year);

        // select end date
        Select endDaySelect = new Select(driver.findElement(By.id(endDaySelectId)));
        Select endMonthSelect = new Select(driver.findElement(By.id(endMonthSelectId)));
        Select endYearSelect = new Select(driver.findElement(By.id(endYearSelectId)));
        endDaySelect.selectByValue(day);
        endMonthSelect.selectByValue(month);
        endYearSelect.selectByValue(year);

        // select GF badminton function
        Select functionSelect = new Select(driver.findElement(By.id(facilityFunctionSelectId)));
        functionSelect.selectByValue(gfBadmintonFunctionValue);

        // click on search
        driver.findElement(By.cssSelector(searchButtonCssSelector)).click();
    }

}
