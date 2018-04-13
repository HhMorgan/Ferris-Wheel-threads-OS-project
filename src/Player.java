
public class Player extends Thread {
	// ID, waiting time, on-board flag, and a ride-complete flag.
	int id;
	int waitingTime;
	boolean onBoard;
	boolean rideComplete;
	Operator operator;

	public Player(int id, int waitingTime, boolean onBoard, boolean rideComplete, Operator operator) {
		this.id = id;
		this.waitingTime = waitingTime;
		this.onBoard = onBoard;
		this.rideComplete = rideComplete;
		this.operator = operator;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//System.out.println(id);
			sleep(waitingTime);
			System.out.println("player wakes up: "+id);
			operator.addPlayer(this);
		} catch (InterruptedException e) {
			System.out.println("player wakes up: "+id);
			operator.addPlayer(this);
		}
		
	}
}
