Feature: Beymen Test project

  Scenario:
    Given Navigate to beymen.com
    And The word "şort" is entered into the search box.
    And The value entered in the search box is deleted.
    And The word "gömlek" is entered into the search box.
    Then Press the enter key on the keyboard
    And One of the products listed according to the result is randomly selected
    And Product information and amount information of the selected product are written in a txt file.
    Then The selected product is added to the cart
    And Compares the price on the product page with the price of the product in the cart.
    Then It is verified that the number of products is two by increasing the number.
    And The product is deleted from the cart and it is verified that the cart is empty.

