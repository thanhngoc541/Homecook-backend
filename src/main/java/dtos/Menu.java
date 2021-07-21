package dtos;

import java.util.ArrayList;
import java.util.List;
public class Menu {
    String MenuName, MenuID, HomeCookID;

    String HomeCookName;
    boolean IsServing;
    List<Dish> Dishes;
    String MenuURL,MenuDescription;
    float Rating;
    String Servings;
    float Price;
    public Menu() {
    }

    public Menu(String menuName, String menuID, String homeCookID, String homeCookName, boolean isServing, List<Dish> dishes, String menuURL, String menuDescription, float rating, String servings, float price) {
        MenuName = menuName;
        MenuID = menuID;
        HomeCookID = homeCookID;
        HomeCookName = homeCookName;
        IsServing = isServing;
        Dishes = dishes;
        MenuURL = menuURL;
        MenuDescription = menuDescription;
        Rating = rating;
        Servings = servings;
        Price = price;
    }

    public String getServings() {
        return Servings;
    }

    public void setServings(String servings) {
        Servings = servings;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getMenuURL() {
        return MenuURL;
    }

    public void setMenuURL(String menuURL) {
        MenuURL = menuURL;
    }

    public String getMenuDescription() {
        return MenuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        MenuDescription = menuDescription;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getHomeCookID() {
        return HomeCookID;
    }

    public void setHomeCookID(String homeCookID) {
        HomeCookID = homeCookID;
    }

    public String getHomeCookName() {
        return HomeCookName;
    }

    public void setHomeCookName(String homeCookName) {
        HomeCookName = homeCookName;
    }

    public boolean isServing() {
        return IsServing;
    }

    public void setServing(boolean serving) {
        IsServing = serving;
    }

    public List<Dish> getDishes() {
        return Dishes;
    }

    public void setDishes(List<Dish> dishes) {
        Dishes = dishes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "MenuName='" + MenuName + '\'' +
                ", MenuID='" + MenuID + '\'' +
                ", HomeCookID='" + HomeCookID + '\'' +
                ", HomeCookName='" + HomeCookName + '\'' +
                ", IsServing=" + IsServing +
                ", Dishes=" + Dishes +
                ", MenuURL='" + MenuURL + '\'' +
                ", MenuDescription='" + MenuDescription + '\'' +
                ", Rating=" + Rating +
                ", Servings='" + Servings + '\'' +
                ", Price=" + Price +
                '}';
    }
}
