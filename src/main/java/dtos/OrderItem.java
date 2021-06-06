package dtos;

public class OrderItem {
    int ItemID, OrderID, Quantity;
    String Note;
    Dish Dish;
    double TotalPrice;

    public OrderItem(int itemID, int orderID, Dish dishItem, int quantity, String note, double totalPrice) {
        ItemID = itemID;
        OrderID = orderID;
        Dish = dishItem; 
        Quantity = quantity;
        Note = note;
        TotalPrice = totalPrice;
    }

    public OrderItem() {
        this.ItemID=0;
        this.OrderID=0;
        this.Dish= null;
        this.Quantity=0;
        this.Note= "";
        this.TotalPrice=0;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public Dish getDish() {
		return Dish;
	}

	public void setDish(Dish dish) {
		Dish = dish;
	}

	public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "\nOrderItem{" +
                "ItemID=" + ItemID +
                ", OrderID=" + OrderID +
                ", Dish=" + Dish.getDishId() +
                ", Quantity=" + Quantity +
                ", Note='" + Note + '\'' +
                ", TotalPrice=" + TotalPrice +
                '}';
    }

	@Override
	public boolean equals(Object obj) {
		OrderItem o = (OrderItem) obj;
		return this.ItemID == o.ItemID && this.Note.equals(o.Note) && this.OrderID == o.OrderID
				&& this.Quantity == o.Quantity && this.TotalPrice == this.TotalPrice
				&& this.Dish.equals(o.Dish);
	}
    
    
}
