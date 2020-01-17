package ru.stqa.selenium;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.selenium.pages.*;


public class CurrentBoardPageTests extends TestBase{
    HomePageHelper homePage;
    LoginPageHelper loginPage;
    BoardsPageHelper boardsPage;
    CurrentBoardPageHelper qa4AutoBoard;

    @BeforeMethod
    public void initTest(){
        homePage = PageFactory.initElements(driver, HomePageHelper.class);
        loginPage = PageFactory.initElements(driver, LoginPageHelper.class);
        boardsPage = PageFactory.initElements(driver, BoardsPageHelper.class);
        qa4AutoBoard = PageFactory.initElements(driver, CurrentBoardPageHelper.class);
        qa4AutoBoard.setName("QA4 Auto");

        homePage.openLoginPage();
        loginPage.waitUntilPageIsLoaded();
        loginPage.loginToTrelloAsAtlassian(LOGIN,PASSWORD);
        boardsPage.waitUntilPageIsLoaded();
    }

    @Test
    public void verifyIfLoadedBoardIsCorrect()  {
        //----Open 'QA 4 Auto' board
        boardsPage.openBoard("QA4 Auto");
        qa4AutoBoard.waitUntilPageIsLoaded();
        Assert.assertTrue(qa4AutoBoard.titleVerification());
    }

    @Test
    public void createNewList()  {
        boardsPage.openBoard("QA4 Auto");
        qa4AutoBoard.waitUntilPageIsLoaded();
        String nameList = "New List";
        if (qa4AutoBoard.getAddButtonName().equals("Add another list")){
            nameList = qa4AutoBoard.genRandomString(7);
            if(qa4AutoBoard.existsList(nameList))
                nameList = qa4AutoBoard.stringWithRandomNumber(1000,
                        nameList);
        }
        int quantityListAtFirst = qa4AutoBoard.getQuantityLists();
        qa4AutoBoard.createNewList(nameList);
        int quantityListAtTheEnd = qa4AutoBoard.getQuantityLists();
        Assert.assertEquals(quantityListAtFirst+1,quantityListAtTheEnd);
        Assert.assertEquals(qa4AutoBoard.getAddButtonName(),
                "Add another list");
    }
    @Test
     public void deleteList(){
        boardsPage.openBoard("QA4 Auto");
        qa4AutoBoard.waitUntilPageIsLoaded();
        if (!qa4AutoBoard.addAnotherListButtonIsDisplayed()){
            qa4AutoBoard.createNewList("New List");
        }
        int quantityListBegining = qa4AutoBoard.getQuantityLists();

        qa4AutoBoard.deleteList();

       Assert.assertEquals(quantityListBegining,qa4AutoBoard.getQuantityLists()+1,
               "quantityListBegining is not quantityListEnd+1");


    }
    @Test
    public void addFirstCardInNewList()  {
        //----Open 'QA 4 Auto' board
        boardsPage.openBoard("QA4 Auto");
        qa4AutoBoard.waitUntilPageIsLoaded();

        //--------Get qantity of 'Add another card' buttons at the beginning----
        int quantityAddAnotherButtonBeg = qa4AutoBoard.getQuantityNotEmptyLists();
        //-----Add a new list------
        qa4AutoBoard.createNewList("New List")
                .addACardToTheLastEmptyList();
        //--------Get qantity of 'Add another card' buttons at the end----
        int quantityAddAnotherButtonEnd = qa4AutoBoard.getQuantityNotEmptyLists();
        Assert.assertEquals(quantityAddAnotherButtonBeg+1, quantityAddAnotherButtonEnd);

    }



}
