package dtos;

public class OrderItem {
    String ItemID, OrderID;
    int Quantity;
    String Note;
    Dish Dish;
    double TotalPrice;

    public OrderItem(String itemID, String orderID, Dish dishItem, int quantity, String note, double totalPrice) {
        ItemID = itemID;
        OrderID = orderID;
        Dish = dishItem; 
        Quantity = quantity;
        Note = note;
        TotalPrice = totalPrice;
    }
    public OrderItem(Dish dishItem, int quantity, String note, double totalPrice) {
        Dish= dishItem;
        Quantity= quantity;
        Note= note;
        TotalPrice= totalPrice;
    }

    public OrderItem() {
        this.ItemID="";
        this.OrderID="";
        this.Dish= null;
        this.Quantity=0;
        this.Note= "";
        this.TotalPrice=0;
    }

    public String getItemID() {
        return ItemID;
    }
    public void setItemID(String itemID) {
        ItemID = itemID;
    }


    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
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
		return this.ItemID.equals(o.ItemID) && this.Note.equals(o.Note) && this.OrderID.equals(o.OrderID)
				&& this.Quantity == o.Quantity && this.TotalPrice == this.TotalPrice
				&& this.Dish.equals(o.Dish);
	}
    
    
}
