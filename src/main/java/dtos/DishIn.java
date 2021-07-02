package dtos;

public class DishIn {
    String MenuID, DishId;

    public DishIn() {
    }

    public DishIn(String menuID, String dishId) {
        MenuID = menuID;
        DishId = dishId;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getDishId() {
        return DishId;
    }

    public void setDishId(String dishId) {
        DishId = dishId;
    }
}
