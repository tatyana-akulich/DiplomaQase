package by.teachmeskills.ui.page;

import by.teachmeskills.ui.dto.Filter;
import by.teachmeskills.ui.dto.Severity;
import by.teachmeskills.ui.dto.Status;
import com.codeborne.selenide.CollectionCondition;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

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
        return $(TITLE).shouldBe(visible, Duration.ofSeconds(10)).isDisplayed();
    }

    public NewDefectPage clickCreateNewDefect() {
        $(CREATE_NEW_DEFECT).shouldBe(enabled).click();
        return new NewDefectPage();
    }

    public List<String> getAllDefectsTitles() {
        List<String> allDefectsNames = new ArrayList<>();
        if (!isUnsuccessfulSearchMessageDisplayed()) {
            int amountOfPages = getAmountOfPages();
            allDefectsNames = new ArrayList<>(getDefectsNamesFromPage());
            if (amountOfPages > 1) {
                for (int i = 2; i <= amountOfPages; i++) {
                    $x((String.format(PAGE_NUMBER_LOCATOR, i))).shouldBe(enabled).click();
                    allDefectsNames.addAll(getDefectsNamesFromPage());
                }
            }
        }
        return allDefectsNames;
    }

    public boolean isConfirmCreationMessageDisplayed() {
        return isElementDisplayedWithDuration(CONFIRMATION_MESSAGE);
    }

    public DefectDetailsPage openDefectsDetails(String title) {
        $x(String.format(DEFECT_TITLE_LOCATOR, title)).shouldBe(enabled, Duration.ofSeconds(10)).click();
        return new DefectDetailsPage();
    }

    public DefectsPage openPageNumber(String pageNumber) {
        if (getAmountOfPages() > 1) {
            $x((String.format(PAGE_NUMBER_LOCATOR, pageNumber))).shouldBe(enabled).click();
//            $x((String.format(PAGE_NUMBER_LOCATOR, pageNumber))).shouldBe(focused);
            $$(ALL_DEFECTS_TITLES).shouldBe(CollectionCondition.sizeNotEqual(0));
        }
        return this;
    }

    public int getDefectsPage(String title) {
        int amountOfPages = getAmountOfPages();
        for (int i = 1; i <= amountOfPages; i++) {
            $x((String.format(PAGE_NUMBER_LOCATOR, i))).shouldBe(enabled).click();
            if ($x(String.format(DEFECT_TITLE_LOCATOR, title)).isDisplayed()) {
                return i;
            }
        }
        return 0;
    }

    public DefectsPage clickAdditionalOptions(String title) {
        $x(String.format(OPTIONS_MENU_LOCATOR, title)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage clickDelete(String title) {
        $x(String.format(DELETE_DEFECT_LOCATOR, title)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage confirmRemoval() {
        $(DELETE_CONFIRMATION).shouldBe(enabled).click();
        return this;
    }

    public boolean isDeleteConfirmationMessageDisplayed() {
        return isElementDisplayedWithDuration(DELETE_CONFIRMATION_POP_UP_MESSAGE);
    }

    public boolean isDeleteApprovalMessageDisplayed() {
        return isElementDisplayedWithDuration(APPROVAL_DELETE_TEXT);
    }

    public String getDefectId(String title) {
        int amountOfPages = getAmountOfPages();
        for (int i = 1; i <= amountOfPages; i++) {
            $x((String.format(PAGE_NUMBER_LOCATOR, i))).shouldBe(enabled).click();
            if ($x(String.format(DEFECT_ID_LOCATOR, title)).isDisplayed()) {
                String idWithDate = $x(String.format(DEFECT_ID_LOCATOR, title)).text();
                int firstWhitespace = idWithDate.indexOf(' ');
                return idWithDate.substring(0, firstWhitespace);
            }
        }
        return null;
    }

    public String getRemovalConfirmationMessage() {
        return $(APPROVAL_DELETE_TEXT).shouldBe(visible).text();
    }

    public String getDeletePopUpText() {
        return $(DELETE_CONFIRMATION_POP_UP_MESSAGE).shouldBe(visible).text();
    }

    public boolean isNavigationElementEnabled(String element) {
        return isElementEnabled(By.xpath(String.format(PAGE_NUMBER_LOCATOR, element)));
    }

    public boolean isNavigationElementDisabled(String element) {
        return !$x(String.format(PAGE_NUMBER_LOCATOR, element)).shouldBe(visible).isEnabled();
    }

    public int getAmountOfPages() {
        int amountOfNavElements = $$(NAV_ELEMENTS).size();
        return amountOfNavElements == 0 ? 1 : (amountOfNavElements - 2);
    }

    public List<String> getDefectsNamesFromPage() {
        $(ALL_DEFECTS_TITLES).shouldBe(visible, Duration.ofSeconds(15));
        return new ArrayList<>($$(ALL_DEFECTS_TITLES).texts());
    }

    public DefectsPage enterLineForSearch(String text) {
        $(SEARCH_INPUT).shouldBe(visible, enabled).clear();
        $(SEARCH_INPUT).shouldBe(visible, enabled).sendKeys(text);
        waitForLoading();
        return this;
    }

    public DefectsPage waitForLoading() {
        $(LOADING).shouldBe(visible, Duration.ofSeconds(10));
        $(LOADING).shouldNotBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public boolean isUnsuccessfulSearchMessageDisplayed() {
        return isElementDisplayedWithoutDuration(UNSUCCESSFUL_SEARCH);
    }

    public boolean isSearchFieldEnabled() {
        return isElementEnabled(SEARCH_INPUT);
    }

    public String getSearchFieldValue() {
        return $(SEARCH_INPUT).getValue();
    }

    public boolean isStatusFilterEnabled() {
        return isElementEnabled(STATUS_BUTTON);
    }

    public String getStatusFilterText() {
        return $(STATUS_BUTTON).shouldBe(visible).getText();
    }

    public boolean isStatusChosen(String status) {
        return $x(String.format(STATUS_TYPE_LOCATOR, status)).isSelected();
    }

    public DefectsPage clickOnStatusChoice() {
        $(STATUS_BUTTON).shouldBe(enabled).click();
        return this;
    }

    private DefectsPage selectStatusOrFilter(String element) {
        $x(String.format(STATUS_TYPE_FOR_CLICK, element)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage selectStatus(String status) {
        clickOnStatusChoice();
        selectStatusOrFilter(status);
        return this;
    }

    public DefectsPage selectStatuses(List<String> statuses) {
        for (String status : statuses
        ) {
            if (!isStatusChosen(status)) {
                selectStatus(status);
                //waitForLoading();
            }
        }
        return this;
    }

    public DefectsPage clickSelectAllStatuses() {
        clickOnStatusChoice();
        $(SELECT_ALL_STATUS).shouldBe(enabled, Duration.ofSeconds(10)).click();
        return this;
    }

    public DefectsPage clearStatusFilter(String filterName) {
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
                clickAddFilter().chooseFilterType(Filter.SEVERITY.getText());
                break;
            }
        }
        for (String status : filterTypes
        ) {
            if (isStatusChosen(status)) {
                selectStatus(status);
            }
        }
        if (filterName.equals("severity")) {
            closeFilter(Filter.SEVERITY.getText());
        }
        return this;
    }

    public boolean isSelectAllButtonDisplayed() {
        return isElementDisplayedWithoutDuration(SELECT_ALL_STATUS);
    }

    public String getDefectsStatus(String title) {
        return $x(String.format(DEFECT_STATUS_LOCATOR, title)).shouldBe(visible).getAttribute("aria-label");
    }

    public String getDefectsSeverity(String title) {
        return $x(String.format(DEFECT_SEVERITY_LOCATOR, title)).shouldBe(visible).getText();
    }

    public DefectsPage passToFirstOrOnlyPage() {
        if (getAmountOfPages() > 1) {
            openPageNumber("1");
        }
        return this;
    }

    public DefectsPage clickAddFilter() {
        $(ADD_FILTER).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage chooseFilterType(String filter) {
        $x(String.format(FILTER_TYPE_LOCATOR, filter)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage chooseSeverityTypes(List<String> severities) {
        for (String severity : severities
        ) {
            chooseSeverityType(severity);
        }
        return this;
    }

    private DefectsPage chooseSeverityType(String severity) {
        $x(String.format(STATUS_TYPE_FOR_CLICK, severity)).shouldBe(enabled).click();
        return this;
    }

    public DefectsPage closeFilter(String filter) {
        $x(String.format(CLOSE_FILTER_BUTTON, filter)).shouldBe(enabled).click();
        return this;
    }

    private boolean isElementDisplayedWithDuration(By locator) {
        return $(locator).shouldBe(visible, Duration.ofSeconds(10)).isDisplayed();
    }

    private boolean isElementDisplayedWithoutDuration(By locator) {
        return $(locator).isDisplayed();
    }

    private boolean isElementEnabled(By locator) {
        return $(locator).shouldBe(visible, enabled).isEnabled();
    }
}

