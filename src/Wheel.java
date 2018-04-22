import java.util.ArrayList;

public class Wheel extends Thread {
	// capacity, count of currently on-board players, list of currently on-board
	// players and the maximum waiting time of the wheel
	int capacity;
	int onBoardCount;
	ArrayList<Player> onBoardPlayers;
	int maxWaitingTime;

	public Wheel(int capacity, int onBoardCount, ArrayList<Player> onBoardPlayers, int maxWaitingTime) {
		this.capacity = capacity;
		this.onBoardCount = onBoardCount;
		this.onBoardPlayers = onBoardPlayers;
		this.maxWaitingTime = maxWaitingTime;
	}

	public void loadPlayers(Player player) {
		onBoardPlayers.add(player);
		onBoardCount++;
		player.onBoard = true;
		// System.out.printf("limit capacity : %d\n",capacity);
		System.out.printf("Player %d on board, capacity: %d\n", player.id, onBoardCount);
		System.out.println();
	}

	public void runRide() {
		if (onBoardCount > 0) {
			System.out.println("Wheel is full, Let's go for a ride");
			System.out.println();
			System.out.println("Threads in this ride are:");
			System.out.println();

			for (int i = 0; i < onBoardPlayers.size(); i++) {

				onBoardPlayers.get(i).onBoard = true;
				System.out.print(onBoardPlayers.get(i).id + ", ");
			}
			System.out.println();
			System.out.println();
		}
	}

	public void endRide() {
		for (int i = 0; i < onBoardPlayers.size(); i++) {
			onBoardPlayers.get(0).onBoard = false;
			onBoardPlayers.get(0).rideComplete = true;
		}
		onBoardPlayers.clear();
		onBoardCount = 0;

		Operator.wheelNotify();
	}

	@Override
	public void run() {
		try {
			System.out.println("wheel start sleep");
			System.out.println();
			sleep(maxWaitingTime);
			System.out.println("wheel end sleep");
			System.out.println();
			this.runRide();
			this.endRide();
		} catch (InterruptedException e) {
			this.runRide();
			this.endRide();
		}
	}
}
