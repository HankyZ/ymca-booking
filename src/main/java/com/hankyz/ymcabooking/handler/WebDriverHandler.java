package com.hankyz.ymcabooking.handler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class WebDriverHandler {

    private final String chromeDriverPath = "drivers/chromedriver.exe";
    private final String ymcaUrl = "https://inscription.ymcaquebec.org/Facilities/FacilitiesSearchWizard.asp";

    private final String username = "077053";
    private final String password = "420150";
    private final String gfBadmintonFunctionText = "GF Badminton";
    private final String badmintonCourtThreeText = "Badminton Court #2";
    private final String startTimeText = "4";
    private final String startAmPmText = "PM";
    private final String endTimeText = "6";
    private final String endAmPmText = "PM";

    private final String signInButtonId = "toolbar-login";
    private final String usernameInputId = "ClientBarcode";
    private final String passwordInputId = "AccountPIN";
    private final String signInConfirmButtonId = "Enter";
    private final String loggedClientFieldId = "toolbar-loggedclient";

    private final String searchFacilityBookingRadioId = "search-facbook-radio";
    private final String startDaySelectId = "DayFrom";
    private final String startMonthSelectId = "MonthFrom";
    private final String startYearSelectId = "YearFrom";
    private final String endDaySelectId = "DayTo";
    private final String endMonthSelectId = "MonthTo";
    private final String endYearSelectId = "YearTo";
    private final String startTimeSelectName = "TimeFrom";
    private final String startAmPmSelectName = "AMPMFrom";
    private final String endTimeSelectName = "TimeTo";
    private final String endAmPmSelectName = "AMPMTo";
    private final String facilityFunctionSelectId = "FacilityFunctions";
    private final String facilityTypeSelectId = "FacilityTypes";
    private final String searchButtonXpathSelector = "(.//*[normalize-space(text()) and normalize-space(.)='West-Island'])[1]/following::input[3]";

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
        driver.manage().window().maximize();
        driver.get(ymcaUrl);

//        driver.quit();
    }

    private void signIn() {
        // click on the sign in button
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.id(signInButtonId)))
                .click();

        // enter username
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.id(usernameInputId)))
                .sendKeys(username);

        // enter password
        driver.findElement(By.id(passwordInputId)).sendKeys(password);

        // click on the sign in confirm button
        driver.findElement(By.id(signInConfirmButtonId)).click();

        // wait for sign in to complete
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(loggedClientFieldId)));
    }

    public void book() {

        LocalDateTime bookDay = getBookingDay();
        String day = String.valueOf(bookDay.getDayOfMonth());
        String month = String.valueOf(bookDay.getMonthValue());
        String year = String.valueOf(bookDay.getYear());
        signIn();

        // select facility booking
        driver.findElement(By.id(searchFacilityBookingRadioId)).click();

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

        // select start time
        Select startTimeSelect = new Select(driver.findElement(By.name(startTimeSelectName)));
        Select startAmPmSelect = new Select(driver.findElement(By.name(startAmPmSelectName)));
        startTimeSelect.selectByVisibleText(startTimeText);
        startAmPmSelect.selectByVisibleText(startAmPmText);

        // select start time
        Select endTimeSelect = new Select(driver.findElement(By.name(endTimeSelectName)));
        Select endAmPmSelect = new Select(driver.findElement(By.name(endAmPmSelectName)));
        endTimeSelect.selectByVisibleText(endTimeText);
        endAmPmSelect.selectByVisibleText(endAmPmText);

        // select GF badminton function
        Select functionSelect = new Select(driver.findElement(By.id(facilityFunctionSelectId)));
        functionSelect.selectByVisibleText(gfBadmintonFunctionText);

        // select Badminton court 3 type
        Select typeSelect = new Select(driver.findElement(By.id(facilityTypeSelectId)));
        typeSelect.selectByVisibleText(badmintonCourtThreeText);

        // click on search
        driver.findElement(By.xpath(searchButtonXpathSelector)).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("chkBook1")));

        try {
            driver.findElement(By.id("chkBook2")).click();
            driver.findElement(By.id("chkBook3")).click();
//            driver.findElement(By.id("chkBook3")).click();
//            driver.findElement(By.id("chkBook4")).click();
        } catch (Exception ignored) {

        }
        driver.findElement(By.id("AddBookBottom")).click();

    }

    /**
     * Get the booking day by incrementing 2 days to today's date, 3 days if between 11:59:00 pm and 0:00:00 pm.
     *
     * @return The booking day as a LocalDateTime object.
     */
    private LocalDateTime getBookingDay() {
        // we want to launch the app at 23:59:00 and keep searching until 0:00:00:000 (midnight)

        // get times
        LocalDate today = LocalDate.now(ZoneId.of("America/Montreal"));
        LocalTime bookingTime = LocalTime.of(23, 59, 00, 00000);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Montreal"));

        // if the current time is between 23:59:00 and 0:00:00, we want to book three days later
        if (now.isAfter(LocalDateTime.of(today, bookingTime))) {
            return now.plusDays(3);
        }
        // else we want to book courts that are two days later
        return now.plusDays(2);
    }

}
