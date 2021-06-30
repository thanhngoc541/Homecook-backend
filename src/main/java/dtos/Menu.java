package dtos;

import java.util.ArrayList;
import java.util.List;
public class Menu {
    String MenuName, MenuID, HomeCookID;

    String HomeCookName;
    boolean IsServing;
    List<Dish> Dishes;
    Float rating;
    String MenuURL,MenuDescription;
    public Menu() {
    }

    public Menu(String menuName, String menuID, String homeCookID, String homeCookName, boolean isServing, List<Dish> dishes, Float rating, String menuURL, String menuDescription) {
        MenuName = menuName;
        MenuID = menuID;
        HomeCookID = homeCookID;
        HomeCookName = homeCookName;
        IsServing = isServing;
        Dishes = dishes;
        this.rating = rating;
        MenuURL = menuURL;
        MenuDescription = menuDescription;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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
}
