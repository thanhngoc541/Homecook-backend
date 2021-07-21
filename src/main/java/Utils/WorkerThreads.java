package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import daos.OrderDAO;
import dtos.Order;

public class WorkerThreads extends Clock{

	private final Instant t0Instant;
	private final LocalDateTime t0LocalDateTime;

	private final ZoneOffset zoneOffset;
	private final Instant whenObjectCreatedInstant;

	public static final ClockTimeTravel timeTravel = new ClockTimeTravel(
			LocalDateTime.parse("2021-06-12T00:00:00"), ZoneOffset.of("-17:00")
			);


	public WorkerThreads(LocalDateTime t0, ZoneOffset zoneOffset){
		this.zoneOffset = zoneOffset;
		this.t0LocalDateTime = t0;
		this.t0Instant = t0.toInstant(zoneOffset);
		this.whenObjectCreatedInstant = Instant.now(timeTravel);
	}

	@Override public ZoneId getZone() {
		return zoneOffset;
	}

	/** The caller needs to actually pass a ZoneOffset object here. */
	@Override public Clock withZone(ZoneId zone) {
		return new ClockTimeTravel(t0LocalDateTime, (ZoneOffset)zone);
	}

	@Override
	public Instant instant() {
		// TODO Auto-generated method stub
		return null;
	}


	/** Simple demo of the behaviour of this class. */
	public static void main(String[] args) throws InterruptedException {

		Runnable r = new Runnable() {
			public void run() {
		         while(Thread.currentThread().getState() != Thread.State.TIMED_WAITING) {
		        	 OrderDAO dao = new OrderDAO();

			         Instant now = Instant.now(timeTravel);
			         long differ = 0;
			         for (Order order : dao.getAllOrder(1, "all")) {
			        	 differ = now.toEpochMilli() - order.getTimeStamp().toEpochMilli();
			        	 System.out.println(order.getOrderID());
			        	 System.out.println("currentTime: " + now);
			        	 System.out.println("Order time: " + order.getTimeStamp());
			        	 System.out.println("Difference: " + differ);
			        	 System.out.println("\n\n");
						 if(differ >= 86400000 && differ <= 92000000) {
							Connection conn = DBContext.makeConnection();
							String sql = "EXEC systemChangeOrderStatus "
									+ "@StartTime = ?, "
									+ "@EndTime = ?";
					        java.sql.Timestamp startTime = new java.sql.Timestamp(now.toEpochMilli());

					        java.sql.Timestamp endTime = new java.sql.Timestamp(now.toEpochMilli() + 92000000);

							try {
								PreparedStatement stmt = conn.prepareStatement(sql);
								stmt.setTimestamp(1, startTime);
								stmt.setTimestamp(2, endTime);
								System.out.println(startTime);
								System.out.println(endTime);
								System.out.println(stmt.executeUpdate());
								System.out.println("Executed");
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

			        try {
						Thread.sleep(10000);
			        }
			        catch (Exception e)
			        {
			        	e.printStackTrace();
			        }
		         }

		    }
		};

		new Thread(r).start();
	}
}
