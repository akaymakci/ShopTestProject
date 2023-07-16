package StepDefinitions;

import Pages.DialogContent;
import Pages.Parent;
import Utilities.GWD;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import junit.framework.Assert;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class _01_BeymenTest {

    DialogContent dc = new DialogContent();
    Parent p = new Parent();

    public _01_BeymenTest() throws AWTException {
    }

    @Given("Navigate to beymen.com")
    public void navigateToBeymenCom() {

        GWD.getDriver().get("https://www.beymen.com/");
        GWD.getDriver().manage().window().maximize();


    }

    @And("The word \"şort\" is entered into the search box.")
    public void theWordIsEnteredIntoTheSearchBox() throws IOException {

        dc.findAndClick("cookies");
        dc.findAndClick("gender");
        dc.findAndSend("searchInput", testDataFunction(0));


    }

    @And("The value entered in the search box is deleted.")
    public void theValueEnteredInTheSearchBoxIsDeleted() {

        dc.findAndClear("searchInput");
        dc.findAndClick("searchInput");

    }

    @Then("The word \"gömlek\" is entered into the search box.")
    public void theWordGomlekIsEnteredIntoTheSearchBox() throws IOException {
        dc.findAndSend("searchInput", testDataFunction(1));

    }

    @Then("Press the enter key on the keyboard")
    public void pressTheEnterKeyOnTheKeyboard() throws AWTException {

        dc.getSearchInput().sendKeys(Keys.ENTER);

    }

    @And("One of the products listed according to the result is randomly selected")
    public void oneOfTheProductsListedAccordingToTheResultIsRandomlySelected() {

        dc.findAndClick("product");

    }

    String productPrice;

    @And("Product information and amount information of the selected product are written in a txt file.")
    public void productInformationAndAmountInformationOfTheSelectedProductAreWrittenInATxtFile() {

        String productName = dc.findAndGetText("productName");
        productPrice =dc.findAndGetText("productPrice");

        System.out.println("productName = " + productName);
        System.out.println("productPrice = " + productPrice);

        try {
            FileWriter myWriter = new FileWriter("src/test/java/TextFile/ProductInfo.txt");
            myWriter.write(productName);
            myWriter.write("\n"+ productPrice);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    @Then("The selected product is added to the cart")
    public void theSelectedProductIsAddedToTheCart() {

        dc.findAndClick("bodySize");
        WebElement addCartElement = dc.getAddCart();
        p.waitUntilVisible(addCartElement);
        dc.findAndClick("addCart");

    }

    @And("Compares the price on the product page with the price of the product in the cart.")
    public void comparesThePriceOnTheProductPageWithThePriceOfTheProductInTheCart() {

        WebElement cartButtonElement = dc.getCartButton();
        p.waitUntilClickable(cartButtonElement);
        dc.findAndClick("cartButton");
        String cartPrice =dc.findAndGetText("cartPrice");

        if (productPrice.equals(cartPrice)) {
            System.out.println("Cart price and product price are equal");
        } else {
            System.out.println("Cart price and product price are not equal");
        }

    }

    @Then("It is verified that the number of products is two by increasing the number.")
    public void itIsVerifiedThatTheNumberOfProductsIsByIncreasingTheNumber() {

        GWD.Bekle(2);

        GWD.getDriver().navigate().back();

        dc.findAndClick("bodySize");
        dc.findAndClick("addCart");
        dc.findAndClick("cartButton");

        dc.findAndContainsText("productPiece","2 adet");

    }

    @And("The product is deleted from the cart and it is verified that the cart is empty.")
    public void theProductIsDeletedFromTheCartAndItIsVerifiedThatTheCartIsEmpty() {

        WebElement deleteButtonElement = dc.getDeleteButton();
        p.waitUntilVisible(deleteButtonElement);
        dc.findAndClick("deleteButton");
        dc.findAndContainsText("emptyBasket","Sepetinizde");

    }
    public String testDataFunction(int rowNum) throws IOException {

        String file = "src/test/java/resource/BeymenTestDatas.xlsx";
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(0);

        return cell.toString();

    }
}
