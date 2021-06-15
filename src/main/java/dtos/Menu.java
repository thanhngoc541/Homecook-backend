package dtos;

import java.util.ArrayList;

public class Menu {
    String MenuName, MenuID;
    boolean IsServing;
    String HomeCookName;
    ArrayList<Dish> Dishes;

    public Menu() {
    }

    public Menu(String menuID, String menuName, boolean isServing, String homeCookName, ArrayList<Dish> dishes) {
        MenuID = menuID;
        MenuName = menuName;
        IsServing = isServing;
        HomeCookName = homeCookName;
        Dishes = dishes;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public boolean isServing() {
        return IsServing;
    }

    public void setServing(boolean serving) {
        IsServing = serving;
    }

    public String getHomeCookName() {
        return HomeCookName;
    }

    public void setHomeCookName(String homeCookName) {
        HomeCookName = homeCookName;
    }

    public ArrayList<Dish> getDishes() {
        return Dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        Dishes = dishes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "MenuID=" + MenuID +
                ", MenuName='" + MenuName + '\'' +
                ", IsServing=" + IsServing +
                ", Dishes=" + Dishes +
                '}';
    }

	@Override
	public boolean equals(Object obj) {
		Menu m = (Menu) obj;
		return this.IsServing == m.IsServing && this.MenuID.equals(m.MenuID) && this.MenuName.equals(m.MenuName)
				&& this.HomeCookName.equals(m.HomeCookName) && this.Dishes.containsAll(m.Dishes);
	}
    
    
}
