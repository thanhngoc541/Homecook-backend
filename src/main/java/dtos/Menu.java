package dtos;

import java.util.ArrayList;

public class Menu {
    int MenuID;
    String MenuName;
    boolean IsServing;
    String HomeCookName;
    ArrayList<Dish> Dishes;
    int HomeCookID;
    public Menu() {
    }

    public Menu(int HomeCookID,int MenuID, String MenuName, boolean IsServing, String HomeCookName, ArrayList<Dish> Dishes) {
        this.MenuID = MenuID;
        this.HomeCookID=HomeCookID;
        this.MenuName = MenuName;
        this.IsServing = IsServing;
        this.HomeCookName = HomeCookName;
        this.Dishes = Dishes;
    }

    public int getMenuID() {
        return MenuID;
    }

    public void setMenuID(int MenuID) {
        this.MenuID = MenuID;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String MenuName) {
        this.MenuName = MenuName;
    }

    public boolean isServing() {
        return IsServing;
    }

    public void setServing(boolean IsServing) {
        this.IsServing = IsServing;
    }

    public String getHomeCookName() {
        return HomeCookName;
    }

    public void setHomeCookName(String HomeCookName) {
        this.HomeCookName = HomeCookName;
    }

    public ArrayList<Dish> getDishes() {
        return Dishes;
    }

    public void setDishes(ArrayList<Dish> Dishes) {
        this.Dishes = Dishes;
    }

    public int getHomeCookID() {
        return HomeCookID;
    }

    public void setHomeCookID(int HomeCookID) {
        this.HomeCookID = HomeCookID;
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
		return this.IsServing == m.IsServing && this.MenuID == m.MenuID && this.MenuName.equals(m.MenuName)
				&& this.HomeCookName.equals(m.HomeCookName) && this.Dishes.containsAll(m.Dishes);
	}
    
    
}
