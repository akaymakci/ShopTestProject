package Pages;

import Utilities.GWD;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utilities.GWD;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DialogContent extends Parent {

    public DialogContent() throws AWTException {
        PageFactory.initElements(GWD.getDriver(), this);
//     the mouse disappear by moving it outside of screen
        Robot robot = new Robot();
        Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
        robot.mouseMove(-SCREENSIZE.width, -SCREENSIZE.height);

    }
    @FindBy(id = "genderManButton")
    private WebElement genderMan;

    @FindBy(xpath = "//div[@class='genderPopup__bottom']//button")
    private List<WebElement> gender;

    public WebElement getSearchInput() {
        return searchInput;
    }

    @FindBy(xpath = "//input[@class='default-input o-header__search--input']")
    private WebElement searchInput;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement cookies;

    @FindBy(xpath = "//div[contains(@class,'col-sm-4')]")
    private List<WebElement> product;
    @FindBy(xpath = "//span[@class='o-productDetail__description']")
    private WebElement productName;

    @FindBy(xpath = "//ins[@class='m-price__new']")
    private WebElement productPrice;

    @FindBy(xpath = "//div[@class='m-variation']//span")
    private List<WebElement> bodySize;

    public WebElement getAddCart() {
        return addCart;
    }

    @FindBy(id = "addBasket")
    private WebElement addCart;

    @FindBy(xpath = "//span[@class='m-productPrice__salePrice']")
    private WebElement cartPrice;

    public WebElement getCartButton() {
        return cartButton;
    }

    @FindBy(xpath = "//a[@title='Sepetim']")
    private WebElement cartButton;

    @FindBy(xpath = "//option[@value='2']")
    private WebElement productPiece;

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    @FindBy(xpath = "//button[@class='m-basket__remove']")
    private WebElement deleteButton;

    @FindBy(xpath = "//strong[text()='Sepetinizde Ürün Bulunmamaktadır']")
    private WebElement emptyBasket;

    WebElement myElement;

    public void findAndSend(String strElement, String value) {  // 2.aşama
        // burda string isimden weblemente ulaşıcam
        switch (strElement) {
            case "searchInput": myElement = searchInput; break;
        }

        sendKeysFunction(myElement, value);
    }

    public void findAndClick(String strElement) {
        switch (strElement) {
            case "cookies": myElement = cookies; break;
            case "bodySize":
                List<WebElement> bodySizes = new ArrayList<>();
                for (int i = 0; i < bodySize.size(); i++) {
                    if(!bodySize.get(i).getAttribute("class").contains("disable")) {
                        waitUntilVisible(bodySize.get(i));
                        bodySizes.add(bodySize.get(i));
                    }
                }
                waitUntilVisible(bodySizes.get(0));
                myElement = bodySizes.get(0); break;
            case "addCart":
                waitUntilVisible(addCart);
                myElement = addCart; break;
            case "cartButton":
                waitUntilVisible(cartButton);
                myElement = cartButton; break;
            case "deleteButton": myElement = deleteButton; break;
            case "gender":
                List<WebElement> genders = new ArrayList<>(gender);
                int numb = getRandomNumberUsingNextInt(0,genders.size());
                System.out.println(numb+" -> "+ genders.get(numb).getText());
                waitUntilVisible(genders.get(numb));
                myElement = genders.get(numb); break;
            case "product":
                GWD.Bekle(getRandomNumberUsingNextInt(1,5));
                List<WebElement> products = new ArrayList<>(product);
                waitUntilVisible(products.get(product.size()-1));
                int number=getRandomNumberUsingNextInt(2,products.size()-1);
                waitUntilVisible(products.get(number));
                myElement = products.get(number); break;
        }

        clickFunction(myElement);
    }

    public void findAndContainsText(String strElement, String text) {
        switch (strElement) {
            case "productName" : myElement =productName; break;
            case "productPrice" : myElement =productPrice; break;
            case "emptyBasket" : myElement =emptyBasket; break;
            case "productPiece" : myElement =productPiece; break;
        }

        verifyContainsText(myElement, text);
    }

    public String  findAndGetText(String strElement) {
        switch (strElement) {
            case "productName" : myElement =productName; break;
            case "productPrice" : myElement =productPrice; break;
            case "cartPrice" : myElement =cartPrice; break;
        }

        return verifyContainsText(myElement);
    }

    public void findAndClear(String strElement) {
        switch (strElement) {
            case "searchInput": myElement = searchInput; break;
        }

        clear(myElement);
    }
    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}
