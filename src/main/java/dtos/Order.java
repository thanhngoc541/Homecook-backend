package dtos;

import java.util.*;

public class Order {
    String OrderID, HomeCookID, CustomerID;
    Date TimeStamp;
    String ReceiverPhone, ReceiverAddress, ReceiverName, Note, Status;
    double Total;
    ArrayList<OrderItem> OrderItems;
    //transient để json ko in ra cái status table
    transient Map<Integer, String>  statusTable= new HashMap<>();
    //Status table
    {
        statusTable.put(1, "Pending");
        statusTable.put(2, "Accept");
        statusTable.put(3, "Delivering");
        statusTable.put(4, "Delivered");
        statusTable.put(5, "Finished");
        statusTable.put(6, "Rejected");
        statusTable.put(7, "Cancelled");
    }

    public Order(String orderID, String homecookID, String customerID, Date timeStamp, String status, String receiverPhone,
                 String receiverAddress,
                 String receiverName, double total, String note, ArrayList<OrderItem> orderItems) {
        OrderID = orderID;
        HomeCookID=  homecookID;
        CustomerID= customerID;
        TimeStamp= timeStamp;
        Status= status;
        ReceiverPhone= receiverPhone;
        ReceiverAddress= receiverAddress;
        ReceiverName= receiverName;
        Total= total;
        Note= note;
        OrderItems= orderItems;
    }
    public Order(String homecookID, String customerID, Date timeStamp, String status, String receiverPhone,
                 String receiverAddress,
                 String receiverName, double total, String note, ArrayList<OrderItem> orderItems) {
        HomeCookID=  homecookID;
        CustomerID= customerID;
        TimeStamp= timeStamp;
        Status= status;
        ReceiverPhone= receiverPhone;
        ReceiverAddress= receiverAddress;
        ReceiverName= receiverName;
        Total= total;
        Note= note;
        OrderItems= orderItems;
    }

    public Order(String orderID, Date timeStamp, String status, double total, String note) {
        this.OrderID= orderID;
        this.TimeStamp= timeStamp;
        this.Status= status;
        this.Total= total;
        this.Note= note;
    }
    public Order() {
        OrderID = "";
        HomeCookID= "";
        CustomerID= "";
        TimeStamp= null;
        Status= null;
        ReceiverPhone= null;
        ReceiverAddress= null;
        ReceiverName= null;
        Total= 0;
        Note= null;
        OrderItems= null;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getHomeCookID() {
        return HomeCookID;
    }

    public void setHomeCookID(String homeCookID) {
        HomeCookID = homeCookID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatus() { return Status; }

    public String getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        ReceiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        OrderItems = orderItems;
    }

    @Override
    public String toString() {
        return "\nOrder{" +
                " OrderID=" + OrderID +
                ", HomeCookID=" + HomeCookID +
                ", CustomerID=" + CustomerID +
                ", TimeStamp=" + TimeStamp +
                ", ReceiverPhone='" + ReceiverPhone + '\'' +
                ", ReceiverAddress='" + ReceiverAddress + '\'' +
                ", ReceiverName='" + ReceiverName + '\'' +
                ", Note='" + Note + '\'' +
                ", Status='" + Status + '\'' +
                ", Total=" + Total +
                ", OrderItems=" + OrderItems +
                '}';
    }

    public String getStatusName(int statusID) {return statusTable.get(statusID);}

    public int getStatusID(String statusName) {
        for (Integer key : statusTable.keySet()) {
            if (statusName.equals(statusTable.get(key)))
                return key.intValue();
        }
        return 0;
    }

	@Override
	public boolean equals(Object obj) {
		Order o = (Order) obj;
		return this.CustomerID.equals(o.CustomerID) && this.HomeCookID.equals(o.HomeCookID) && this.OrderID.equals(o.OrderID)
				&& this.ReceiverAddress.equals(o.ReceiverAddress) && this.ReceiverName.equals(o.ReceiverName)
				&& this.ReceiverPhone.equals(o.ReceiverPhone) && this.Note.equals(o.Note)
				&& this.OrderItems.containsAll(o.OrderItems) && this.Status.equals(o.Status);
	}
    private class Orders {
        List<Order> orders= new ArrayList<>();

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }
        public void addOrders (Order ord) {
            this.orders.add(ord);
        }

        @Override
        public String toString() {
            return "Orders{" +
                    "orders=" + orders +
                    '}';
        }
    }
    
}
