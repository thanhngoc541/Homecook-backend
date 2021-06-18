package dtos;

public class Dish {

    String DishName, DishId, HomeCookID;

    double Price;
    boolean IsAvailable;
    String Description, ImageURL;

    public Dish(String dishId, String HomeCookID, String dishName, double price, boolean isAvailable, String description, String imageURL) {
        DishId = dishId;
        this.HomeCookID = HomeCookID;
        DishName = dishName;
        Price = price;
        IsAvailable = isAvailable;
        Description = description;
        ImageURL = imageURL;
    }


    public Dish(String homeCookID, String dishName, double price, boolean isAvailable, String description, String imageURL) {
        DishId = "";
        HomeCookID = homeCookID;
        DishName = dishName;
        Price = price;
        IsAvailable = isAvailable;
        Description = description;
        ImageURL = imageURL;
    }


    //Huy dung cho OrderDAO
    public Dish(String dishId, String homeCookID, String dishName, double price, String imgURL) {
        this.DishId= dishId;
        this.HomeCookID= homeCookID;
        this.DishName= dishName;
        this.Price= price;
        this.ImageURL= imgURL;
    }

    public String getHomeCookID() {
        return HomeCookID;
    }

    public void setHomeCookID(String homeCookID) {
        HomeCookID = homeCookID;
    }

    public String getDishId() {
        return DishId;
    }

    public void setDishId(String dishId) {
        DishId = dishId;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public boolean isAvailable() {
        return IsAvailable;
    }

    public void setAvailable(boolean available) {
        IsAvailable = available;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "DishId=" + DishId +
                ", DishName='" + DishName + '\'' +
                ", Price=" + Price +
                ", IsAvailable=" + IsAvailable +
                ", Description='" + Description + '\'' +
                ", ImageURL='" + ImageURL + '\'' +
                '}';
    }

	@Override
	public boolean equals(Object obj) {
		Dish d = (Dish) obj;
		return this.IsAvailable == d.IsAvailable && this.DishId.equals(d.DishId) 
				&& this.Description.equals(d.Description) && this.DishName.equals(d.DishName)
				&& this.ImageURL.equals(d.ImageURL) && this.HomeCookID.equals(d.HomeCookID) 
				&& this.Price == d.Price;
	}
    
    
}
