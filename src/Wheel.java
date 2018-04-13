import java.util.LinkedList;

public class Wheel extends Thread {
	// capacity, count of currently on-board players, list of currently on-board
	// players and the maximum waiting time of the wheel
	int capacity;
	int onBoardCount;
	LinkedList<Player> onBoardPlayers;
	int maxWaitingTime;

	public Wheel(int capacity, int onBoardCount, LinkedList<Player> onBoardPlayers, int maxWaitingTime) {
		this.capacity = capacity;
		this.onBoardCount = onBoardCount;
		this.onBoardPlayers = onBoardPlayers;
		this.maxWaitingTime = maxWaitingTime;
		try {
			Thread.sleep(maxWaitingTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadPlayers(Player player) {
		onBoardPlayers.add(player);
		onBoardCount++;
		player.onBoard = true;
		System.out.printf("Player %d on board, capacity: %d\n", player.id, onBoardCount);
	}

	public void runRide() {
		if (onBoardCount > 0) {
			System.out.println("Threads in this ride are: ");
			for (Player player : onBoardPlayers) {
				player.onBoard = true;
				System.out.print(player.id + ", ");
			}
			System.out.println("\n");
		}
	}

	public void endRide() {
		for (Player player : onBoardPlayers) {
			player.onBoard = false;
			player.rideComplete = true;
		}
		onBoardPlayers.clear();
		onBoardCount = 0;

	}

	public void sleepWake() {
		try {
			System.out.println("wheel start sleep");
			this.sleep(maxWaitingTime);
			System.out.println("wheel end sleep");
			this.runRide();
			this.endRide();
			System.out.println();
			
		} catch (InterruptedException e) {
			System.out.println("Wheel is full, Let's go for a ride");
			this.runRide();
			this.endRide();
			sleepWake();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		sleepWake();
	}
}
