package ru.shestakov.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class SwaggerMainPage {

    public static String url() {
        return "https://petstore.swagger.io/#/";
    }
    public SelenideElement mainDiv() {
        return $x("//div[@id='swagger-ui']").as("главный контейнер");
    }
    public SelenideElement titleH2() {
        return $x("//h2").as("заголовок h2");
    }
    public SelenideElement listStore() {
        return $x("//h3[@id='operations-tag-store']").as("лист store");
    }
    public SelenideElement listPet() {
        return $x("//h3[@id='operations-tag-pet']").as("лист pet");
    }
    public SelenideElement listUser() {
        return $x("//h3[@id='operations-tag-user']").as("лист user");
    }
    public SelenideElement blockGetInStore() {
        return $x("//div[@id='operations-store-getInventory']").as("лист store блок get");
    }
    public SelenideElement blockGetInPet() {
        return $x("//div[@id='operations-pet-uploadFile']").as("лист pet блок get");
    }
    public SelenideElement blockGetInUser() {
        return $x("//div[@id='operations-user-createUsersWithListInput']").as("лист user блок get");
    }
    public SelenideElement listPetMethodDeprecated() {
        return $x("//span[@data-path='/pet/findByTags']").as("лист pet метод get Deprecated");
    }
    public SelenideElement deprecatedMethodGet() {
        return $x("//div[@id='operations-pet-findPetsByTags']//*[contains(text(),' Warning: Deprecated')]")
                .as("лист Pet блок get Deprecated");
    }
    public ElementsCollection blockListOfPet() {
        return $$x("//*[text()='DELETE']").as("кнопки delete");
    }
    public SelenideElement findByStatus() {
        return $x("//div[contains(text(),'Finds Pets by status')]").as("лист pet ручка /findByStatus");
    }
    public SelenideElement buttonTryItOut() {
        return $x("//div[@id='operations-pet-findPetsByStatus']//button[contains(text(), 'Try it out')]")
                .as("кнопка Try it out");
    }
    public SelenideElement selectStatus() {
        return $x("//div[@id='operations-pet-findPetsByStatus']//option[@value='available']")
                .as("выбор статуса");
    }
    public SelenideElement buttonExecute() {
        return $x("//div[@id='operations-pet-findPetsByStatus']//button[contains(text(), 'Execute')]")
                .as("кнопка Execute");
    }




}
