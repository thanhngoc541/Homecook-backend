package dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
    int OrderID, HomeCookID, CustomerID;
    Date TimeStamp;
    String ReceiverPhone, ReceiverAddress, ReceiverName, Note, Status;
    double Total;
    ArrayList<OrderItem> OrderItems;
    Map<Integer, String> statusTable= new HashMap<>();
    //Status table
    {
        statusTable.put(1, "Pending");
        statusTable.put(2, "Accept");
        statusTable.put(3, "Delivering");
        statusTable.put(4, "Delivering");
        statusTable.put(5, "Finished");
        statusTable.put(6, "Rejected");
        statusTable.put(7, "Cancelled");
    }

    public Order(int orderID, int homecookID, int customerID, Date timeStamp, String status, String receiverPhone,
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

    public Order(int orderID, Date timeStamp, String status, double total, String note) {
        this.OrderID= orderID;
        this.TimeStamp= timeStamp;
        this.Status= status;
        this.Total= total;
        this.Note= note;
    }
    public Order() {
        OrderID = 0;
        HomeCookID= 0;
        CustomerID= 0;
        TimeStamp= null;
        Status= null;
        ReceiverPhone= null;
        ReceiverAddress= null;
        ReceiverName= null;
        Total= 0;
        Note= null;
        OrderItems= null;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getHomeCookID() {
        return HomeCookID;
    }

    public void setHomeCookID(int homeCookID) {
        HomeCookID = homeCookID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
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
                "OrderID=" + OrderID +
                ", TimeStamp=" + TimeStamp +
                ", ReceiverPhone='" + ReceiverPhone + '\'' +
                ", ReceiverAddress='" + ReceiverAddress + '\'' +
                ", ReceiverName='" + ReceiverName + '\'' +
                ", Note='" + Note + '\'' +
                ", Total=" + Total +
                ", OrderItems=" + OrderItems +
                ", Status=" + Status +
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
}
