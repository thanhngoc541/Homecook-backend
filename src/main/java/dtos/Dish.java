package dtos;

public class Dish {
    int DishId=0;
    int HomeCookID;
    String DishName;
    double Price;
    boolean IsAvailable;
    String Description, ImageURL;

    public Dish(int dishId, int HomeCookID, String dishName, double price, boolean isAvailable, String description, String imageURL) {
        DishId = dishId;
        this.HomeCookID = HomeCookID;
        DishName = dishName;
        Price = price;
        IsAvailable = isAvailable;
        Description = description;
        ImageURL = imageURL;
    }

    //
//    public Dish(int homeCookID, String dishName, double price, boolean isAvailable, String description, String imageURL) {
////        DishId = 0;
//        HomeCookID = homeCookID;
//        DishName = dishName;
//        Price = price;
//        IsAvailable = isAvailable;
//        Description = description;
//        ImageURL = imageURL;
//    }

    //Huy dung cho OrderDAO
    public Dish(int dishId, int homeCookID, String dishName, double price, String imgURL) {
        this.DishId= dishId;
        this.HomeCookID= homeCookID;
        this.DishId= dishId;
        this.Price= price;
        this.ImageURL= imgURL;
    }

    public int getHomeCookID() {
        return HomeCookID;
    }

    public void setHomeCookID(int homeCookID) {
        HomeCookID = homeCookID;
    }

    public int getDishId() {
        return DishId;
    }

    public void setDishId(int dishId) {
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
		return this.IsAvailable == d.IsAvailable && this.DishId == d.DishId 
				&& this.Description.equals(d.Description) && this.DishName.equals(d.DishName)
				&& this.ImageURL.equals(d.ImageURL) && this.HomeCookID == d.HomeCookID 
				&& this.Price == d.Price;
	}
    
    
}
