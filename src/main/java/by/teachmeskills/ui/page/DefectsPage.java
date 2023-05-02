package by.teachmeskills.ui.page;

import by.teachmeskills.ui.dto.Filter;
import by.teachmeskills.ui.dto.Severity;
import by.teachmeskills.ui.dto.Status;
import com.codeborne.selenide.CollectionCondition;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class DefectsPage implements BasePage {
    private static final By TITLE = By.xpath("//h1[text()= 'Defects']");
    private static final By CREATE_NEW_DEFECT = By.xpath("//a[text()= 'Create new defect']");
    private static final By ALL_DEFECTS_TITLES = By.xpath("//a[@class='defect-title']");
    private static final By NAV_ELEMENTS = By.xpath("//nav//li");
    private static final String PAGE_NUMBER_LOCATOR = "//nav//span[text()='%s']/ancestor::button";
    private static final By CONFIRMATION_MESSAGE = By.xpath("//span[text()='Defect was created successfully!']");
    private static final String DEFECT_TITLE_LOCATOR = "//a[text()='%s']";
    private static final String DEFECT_ID_LOCATOR = DEFECT_TITLE_LOCATOR + "/ancestor::div[1]/following::div/span[1]";
    private static final String OPTIONS_MENU_LOCATOR =
            "//a[text()='%s']/ancestor::tr/td//a[contains(@class, 'btn btn-dropdown')]";
    private static final String DELETE_DEFECT_LOCATOR = "//a[text()='%s']/ancestor::tr/td//a[text()='Delete']";
    private static final By DELETE_CONFIRMATION = By.xpath("//span[text()='Delete']");
    private static final By APPROVAL_DELETE_TEXT = By.xpath("//div[contains(text(), 'Are you sure')]");
    private static final By DELETE_CONFIRMATION_POP_UP_MESSAGE = By.xpath("//span[contains(text(), 'was successfully deleted')]");
    private static final By SEARCH_INPUT = By.xpath("//input[contains(@class, 'search-input')]");
    private static final By UNSUCCESSFUL_SEARCH = By.xpath("//div[text()='Looks like you don’t have any defects yet.']");
    private static final By LOADING = By.xpath("//span[text()='Loading...']");
    private static final By STATUS_BUTTON = By.xpath("//span[contains(text(), 'Status')]");
    private static final String STATUS_TYPE_LOCATOR = "//span[contains(text(), '%s')]/preceding-sibling::input";
    private static final String STATUS_TYPE_FOR_CLICK = "//span[contains(text(), '%s')]/preceding-sibling::span";
    private static final String DEFECT_STATUS_LOCATOR = "(//a[text()='%s']/ancestor::tr/td/span)[1]";
    private static final By SELECT_ALL_STATUS = By.xpath("//button[text()='Select all']");
    private static final By ADD_FILTER = By.xpath("//button[text()='+ Add filter']");
    private static final String FILTER_TYPE_LOCATOR = "//button[text()='%s']";
    private static final String CLOSE_FILTER_BUTTON = "//span[contains(text(),'%s')]/following-sibling::button";
    private static final String DEFECT_SEVERITY_LOCATOR = "//a[text()='%s']/ancestor::tr/td[5]/span";

    public boolean isPageOpened() {
        log.info("Checking if title DEFECTS is displayed");
        return $(TITLE).shouldBe(visible, Duration.ofSeconds(10)).isDisplayed();
    }

    public NewDefectPage clickCreateNewDefect() {
        log.info("Clicking create new defect button");
        $(CREATE_NEW_DEFECT).shouldBe(enabled).click();
        return new NewDefectPage();
    }

    public List<String> getAllDefectsTitles() {
        log.info("Getting defects titles from all pages");
        List<String> allDefectsNames = new ArrayList<>();
        if (!isUnsuccessfulSearchMessageDisplayed()) {
            log.info("Checking if message about unsuccessful search is displayed");
            int amountOfPages = getAmountOfPages();
            log.info("Getting defects titles from current page");
            allDefectsNames = new ArrayList<>(getDefectsNamesFromPage());
            if (amountOfPages > 1) {
                log.info("Getting amount of pages");
                for (int i = 2; i <= amountOfPages; i++) {
                    log.info("Passing to the next page");
                    $x((String.format(PAGE_NUMBER_LOCATOR, i))).shouldBe(enabled).click();
                    allDefectsNames.addAll(getDefectsNamesFromPage());
                }
            }
        }
        return allDefectsNames;
    }

    public boolean isConfirmCreationMessageDisplayed() {
        log.info("Checking if message about successful adding of defect is shown");
        return isElementDisplayedWithDuration(CONFIRMATION_MESSAGE);
    }

    public DefectDetailsPage openDefectsDetails(String title) {
        log.info("Passing to DefectsDetailsPage for defect {}", title);
        $x(String.format(DEFECT_TITLE_LOCATOR, title)).shouldBe(enabled, Duration.ofSeconds(10)).click();
        return new DefectDetailsPage();
    }

    public DefectsPage openPageNumber(String pageNumber) {
        log.info("Getting amount of pages");
        if (getAmountOfPages() > 1) {
            log.info("Passing to page {}", pageNumber);
            $x((String.format(PAGE_NUMBER_LOCATOR, pageNumber))).shouldBe(enabled).click();
            log.error("Checking that page is not empty");
            $$(ALL_DEFECTS_TITLES).shouldBe(CollectionCondition.sizeNotEqual(0));
        }
        return this;
    }

    public DefectsPage openPageNumber(int pageNumber) {
        return openPageNumber(String.valueOf(pageNumber));
    }

    public int getDefectsPage(String title) {
        log.info("Getting amount of pages");
        int amountOfPages = getAmountOfPages();
        for (int i = 1; i <= amountOfPages; i++) {
            log.info("Opening page {}", i);
            $x((String.format(PAGE_NUMBER_LOCATOR, i))).shouldBe(enabled).click();
            log.info("Checking if defect {} is on the page", title);
            if ($x(String.format(DEFECT_TITLE_LOCATOR, title)).isDisplayed()) {
                return i;
            }
        }
        return 0;
    }

    public DefectsPage clickAdditionalOptions(String title) {
        log.info("Clicking on options menu for defect {}", title);
        $x(String.format(OPTIONS_MENU_LOCATOR, title)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage clickDelete(String title) {
        log.info("Clicking on delete option for defect {}", title);
        $x(String.format(DELETE_DEFECT_LOCATOR, title)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage confirmRemoval() {
        log.info("Clicking on confirm in removal confirmation pop-up");
        $(DELETE_CONFIRMATION).shouldBe(enabled).click();
        return this;
    }

    public boolean isDeleteConfirmationMessageDisplayed() {
        log.info("Checking if delete confirmation message is displayed");
        return isElementDisplayedWithDuration(DELETE_CONFIRMATION_POP_UP_MESSAGE);
    }

    public boolean isDeleteApprovalMessageDisplayed() {
        log.info("Checking if delete approval message is displayed");
        return isElementDisplayedWithDuration(APPROVAL_DELETE_TEXT);
    }

    public String getDefectId(String title) {
        log.info("Getting amount of pages");
        int amountOfPages = getAmountOfPages();
        for (int i = 1; i <= amountOfPages; i++) {
            log.info("Opening page {}", i);
            $x((String.format(PAGE_NUMBER_LOCATOR, i))).shouldBe(enabled).click();
            log.info("Checking if defect {} is on the page", title);
            if ($x(String.format(DEFECT_ID_LOCATOR, title)).isDisplayed()) {
                log.info("Getting defect {} id with date element", title);
                String idWithDate = $x(String.format(DEFECT_ID_LOCATOR, title)).text();
                int firstWhitespace = idWithDate.indexOf(' ');
                return idWithDate.substring(0, firstWhitespace);
            }
        }
        return null;
    }

    public String getRemovalConfirmationMessage() {
        log.info("Getting removal confirmation message");
        return $(APPROVAL_DELETE_TEXT).shouldBe(visible).text();
    }

    public String getDeletePopUpText() {
        log.info("Getting text from element removal pop-up message");
        return $(DELETE_CONFIRMATION_POP_UP_MESSAGE).shouldBe(visible).text();
    }

    public boolean isNavigationElementEnabled(String element) {
        log.info("Checking if navigation panel is displayed");
        return isElementEnabled(By.xpath(String.format(PAGE_NUMBER_LOCATOR, element)));
    }

    public boolean isNavigationElementDisabled(String element) {
        log.info("Checking if navigation element is disabled");
        return !$x(String.format(PAGE_NUMBER_LOCATOR, element)).shouldBe(visible).isEnabled();
    }

    public int getAmountOfPages() {
        log.info("Getting amount of pages");
        int amountOfNavElements = $$(NAV_ELEMENTS).size();
        return amountOfNavElements == 0 ? 1 : (amountOfNavElements - 2);
    }

    public List<String> getDefectsNamesFromPage() {
        log.info("Getting list of titles from current page");
        $(ALL_DEFECTS_TITLES).shouldBe(visible, Duration.ofSeconds(15));
        return new ArrayList<>($$(ALL_DEFECTS_TITLES).texts());
    }

    public DefectsPage enterLineForSearch(String text) {
        log.info("Sending keys {} to search line", text);
        $(SEARCH_INPUT).shouldBe(visible, enabled).clear();
        $(SEARCH_INPUT).shouldBe(visible, enabled).sendKeys(text);
        if (!isUnsuccessfulSearchMessageDisplayed()) {
            log.error("Waiting for loading of search results");
            waitForLoading();
        }
        return this;
    }

    public DefectsPage waitForLoading() {
        log.error("Waiting for loading element to appear");
        $(LOADING).shouldBe(visible, Duration.ofSeconds(10));
        log.error("Waiting for loading element to disappear");
        $(LOADING).shouldNotBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public boolean isUnsuccessfulSearchMessageDisplayed() {
        log.info("Checking if element is displayed");
        return isElementDisplayedWithoutDuration(UNSUCCESSFUL_SEARCH);
    }

    public boolean isSearchFieldEnabled() {
        log.info("Checking if search field is enabled");
        return isElementEnabled(SEARCH_INPUT);
    }

    public String getSearchFieldValue() {
        log.info("Getting text from search field");
        return $(SEARCH_INPUT).getValue();
    }

    public boolean isStatusFilterEnabled() {
        log.info("Checking if status field is enabled");
        return isElementEnabled(STATUS_BUTTON);
    }

    public String getStatusFilterText() {
        log.info("Getting text from status field");
        return $(STATUS_BUTTON).shouldBe(visible).getText();
    }

    public boolean isStatusChosen(String status) {
        log.info("Checking if status {} is chosen", status);
        return $x(String.format(STATUS_TYPE_LOCATOR, status)).isSelected();
    }

    private DefectsPage selectStatusOrFilter(String element) {
        log.info("Clinking on status/filter {}", element);
        $x(String.format(STATUS_TYPE_FOR_CLICK, element)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage clickOnStatusChoice() {
        log.info("Clicking on status button");
        $(STATUS_BUTTON).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage selectStatus(String status) {
        log.info("Clicking on status button");
        clickOnStatusChoice();
        log.info("Clinking on status {}", status);
        selectStatusOrFilter(status);
        return this;
    }

    public DefectsPage selectStatuses(List<String> statuses) {
        for (String status : statuses
        ) {
            log.info("Checking if status {} is chosen", status);
            if (!isStatusChosen(status)) {
                log.info("Clicking on status {}", status);
                selectStatus(status);
            }
        }
        return this;
    }

    public DefectsPage clickSelectAllStatuses() {
        log.info("Clicking on select all statuses");
        clickOnStatusChoice();
        $(SELECT_ALL_STATUS).shouldBe(enabled, Duration.ofSeconds(10)).click();
        return this;
    }

    public DefectsPage clearFilter(String filterName) {
        List<String> filterTypes = null;
        switch (filterName) {
            case "status": {
                filterTypes = List.of(Status.OPEN.getText(), Status.RESOLVED.getText(),
                        Status.IN_PROGRESS.getText(), Status.INVALID.getText());
                break;
            }
            case "severity": {
                filterTypes = List.of(Severity.NOTSET.getText(), Severity.BLOCKER.getText(), Severity.CRITICAL.getText(),
                        Severity.MAJOR.getText(), Severity.NORMAL.getText(), Severity.MINOR.getText(), Severity.TRIVIAL.getText());
                log.info("Clicking on add filter/choose severity filter");
                clickAddFilter().chooseFilterType(Filter.SEVERITY.getText());
                break;
            }
        }
        for (String status : filterTypes
        ) {
            log.info("Selecting status, if it is not chosen");
            if (isStatusChosen(status)) {
                selectStatus(status);
            }
        }

        log.info("Closing of severity filter");
        if (filterName.equals("severity")) {
            closeFilter(Filter.SEVERITY.getText());
        }
        return this;
    }

    public boolean isSelectAllButtonDisplayed() {
        log.info("Checking if select all button is displayed");
        return isElementDisplayedWithoutDuration(SELECT_ALL_STATUS);
    }

    public String getDefectsStatus(String title) {
        log.info("Getting status for defect {}", title);
        return $x(String.format(DEFECT_STATUS_LOCATOR, title)).shouldBe(visible).getAttribute("aria-label");
    }

    private String getDefectsSeverity(String title) {
        log.info("Getting severity for defect {}", title);
        return $x(String.format(DEFECT_SEVERITY_LOCATOR, title)).shouldBe(visible).getText();
    }

    public DefectsPage passToFirstOrOnlyPage() {
        log.info("Getting amount of pages");
        if (getAmountOfPages() > 1) {
            log.info("Passing to first page");
            openPageNumber(1);
        }
        return this;
    }

    public DefectsPage clickAddFilter() {
        log.info("Clicking add filter button");
        $(ADD_FILTER).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage chooseFilterType(String filter) {
        log.info("Clicking choose filter button");
        $x(String.format(FILTER_TYPE_LOCATOR, filter)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage chooseSeverityTypes(List<String> severities) {
        for (String severity : severities
        ) {
            log.info("Choosing of severity {} in list", severity);
            chooseSeverityType(severity);
        }
        return this;
    }

    private DefectsPage chooseSeverityType(String severity) {
        log.info("Choosing of severity {}", severity);
        $x(String.format(STATUS_TYPE_FOR_CLICK, severity)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage closeFilter(String filter) {
        log.info("Clicking close filter button for {}", filter);
        $x(String.format(CLOSE_FILTER_BUTTON, filter)).shouldBe(enabled).click();
        return this;
    }

    public List<String> getDefectsByFilter(List<String> allDefectTitles, List<String> filters, String filterType) {
        List<String> expectedResult = new ArrayList<>();
        int pageNumber = 1;
        int titleNumber = 1;
        for (String title : allDefectTitles) {
            if (titleNumber > 10) {
                pageNumber++;
                log.info("Opening page {}", pageNumber);
                openPageNumber(pageNumber);
                titleNumber = 1;
            }
            for (String filter : filters
            ) {
                switch (filterType) {
                    case "severity": {
                        log.info("Checking if defect's {} severity equals to {}", title, filter);
                        if (getDefectsSeverity(title).equals(filter)) {
                            log.info("Adding {} to expected results list", title);
                            expectedResult.add(title);
                            break;
                        }
                    }
                    case "status": {
                        log.info("Checking if defect's {} severity equals to {}", title, filter);
                        if (getDefectsStatus(title).equals(filter)) {
                            log.info("Adding {} to expected results list", title);
                            expectedResult.add(title);
                        }
                    }
                }

            }
            titleNumber++;
        }
        return expectedResult;
    }

    private boolean isElementDisplayedWithDuration(By locator) {
        log.info("Checking if element {} is displayed with duration", locator);
        return $(locator).shouldBe(visible, Duration.ofSeconds(10)).isDisplayed();
    }

    private boolean isElementDisplayedWithoutDuration(By locator) {
        log.info("Checking if element {} is displayed without duration", locator);
        return $(locator).isDisplayed();
    }

    private boolean isElementEnabled(By locator) {
        log.info("Checking if element {} is enabled", locator);
        return $(locator).shouldBe(visible, enabled).isEnabled();
    }
}

