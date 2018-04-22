
public class Player extends Thread {
	// ID, waiting time, on-board flag, and a ride-complete flag.
	int id;
	int waitingTime;
	boolean onBoard;
	boolean rideComplete;

	public Player(int id, int waitingTime, boolean onBoard, boolean rideComplete) {
		this.id = id;
		this.waitingTime = waitingTime;
		this.onBoard = onBoard;
		this.rideComplete = rideComplete;
	}

	private synchronized void notifyOperator() {
		System.out.println("player wakes up: " + id);
		System.out.println();
		Operator.playerNotify(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// System.out.println(id);
			sleep(waitingTime);
			notifyOperator();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
