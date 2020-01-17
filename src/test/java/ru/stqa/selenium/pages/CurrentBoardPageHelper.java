package ru.stqa.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CurrentBoardPageHelper extends PageBase{
    String name;
    @FindBy(css = ".placeholder")
    WebElement addButton;

    @FindBy(xpath = "//span[@dir='auto']")
    WebElement fieldNemeList;

    @FindBy(css = ".placeholder")
    WebElement addListButton;

    @FindBy(css = ".list-name-input")
    WebElement listInputNameField;

    @FindBy(xpath = "//input[@type='submit']")
    WebElement submitListButton;

    @FindBy(css = "a.js-cancel-edit")
    WebElement xListButton;

    @FindBy(xpath = "//h2")
    List<WebElement> listHeaders;

    @FindBy(xpath = "//button[@data-test-id = 'header-member-menu-button']")
    WebElement initialsUpperRightIcon;

    @FindBy(xpath = "//span[contains(text(),'Profile and Visibility')]")
    WebElement profileAndVisibilityMenu;

    @FindBy(css = ".js-open-list-menu")
    WebElement openListActionsButton;

    @FindBy(css = ".js-close-list")
    WebElement xCloseListButton;

    @FindBy(xpath = "//span[@class= 'js-add-another-card']")
    List<WebElement> addAnotherCardButtonList;

    @FindBy(xpath = "//span[@class = 'js-add-a-card']")
    List<WebElement> addCardButtonList;

    @FindBy(xpath = "//input[@class='primary confirm mod-compact js-add-card']")
    WebElement addCardButton;

    @FindBy(xpath = "//textarea[@placeholder='Enter a title for this card…']")
    WebElement enterCardTitleField;

    @FindBy(css = "a.js-cancel")
    WebElement addCardCancelButton;


    public CurrentBoardPageHelper(WebDriver driver) {
        super(driver);

    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public void waitUntilPageIsLoaded() {
        waitUntilElementIsClickable(addButton,30);
    }

    public boolean titleVerification() {
        return fieldNemeList.getText().equals(name);
    }

    public CurrentBoardPageHelper createNewList(String name) {
        addListButton.click();
        waitUntilElementIsVisible(listInputNameField,10);
        listInputNameField.sendKeys(name);
        submitListButton.click();
        waitUntilElementIsClickable(xListButton,10);
        xListButton.click();
        return this;
    }

    public String getAddButtonName() {
        return addListButton.getText();
    }

    public boolean addAnotherListButtonIsDisplayed(){
        return addListButton.getText().equals("Add another list");

    }

    public boolean existsList(String nameList) {
        boolean exitName = false;
        for(WebElement element: listHeaders){
            //System.out.println("Name - " + element.getText());
            if(element.getText().equals(nameList)) exitName = true;
        }
        return exitName;

    }

    public int getQuantityLists() {
        return listHeaders.size();
    }

    public void openUserProfile() {
        initialsUpperRightIcon.click();
        waitUntilElementIsClickable(profileAndVisibilityMenu,10);
        profileAndVisibilityMenu.click();
    }

    public void deleteList() {
        waitUntilElementIsClickable(openListActionsButton,10);
        openListActionsButton.click();
        waitUntilElementIsClickable(xCloseListButton,10);
        xCloseListButton.click();
    }

    public int getQuantityNotEmptyLists() {
        waitUntilAllElementsAreVisible(addAnotherCardButtonList,10);
        return addAnotherCardButtonList.size();

    }

    public void addACardToTheLastEmptyList() {
        //----- Get the last 'Add card' button----
        waitUntilAllElementsAreVisible(addCardButtonList,15);
        int sizeLstAddCardButtons = addCardButtonList.size();
        WebElement lastAddCardButton = addCardButtonList.get(sizeLstAddCardButtons-1);
        //----Add a first card for any new list
        lastAddCardButton.click();

        waitUntilElementIsClickable(addCardButton,10);
        enterCardTitleField.sendKeys("text");
        addCardButton.click();

        waitUntilElementIsClickable(addCardCancelButton,10);
        addCardCancelButton.click();

        //--------Get qantity of 'Add another card' buttons at the end----
        waitUntilAllElementsAreVisible(addAnotherCardButtonList,10);
    }
}
