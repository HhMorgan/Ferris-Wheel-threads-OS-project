import java.util.ArrayList;
import java.util.LinkedList;

public class Wheel extends Thread {
	// capacity, count of currently on-board players, list of currently on-board
	// players and the maximum waiting time of the wheel
	int capacity;
	int onBoardCount;
	ArrayList<Player> onBoardPlayers;
	int maxWaitingTime;
	Operator operator;

	public Wheel(int capacity, int onBoardCount, ArrayList<Player> onBoardPlayers, int maxWaitingTime,
			Operator operator) {
		this.capacity = capacity;
		this.onBoardCount = onBoardCount;
		this.onBoardPlayers = onBoardPlayers;
		this.maxWaitingTime = maxWaitingTime;
		this.operator = operator;
	}

	public void loadPlayers(Player player) {
		onBoardPlayers.add(player);
		onBoardCount++;
		player.onBoard = true;
		//System.out.printf("limit capacity : %d\n",capacity);
		System.out.printf("Player %d on board, capacity: %d\n", player.id, onBoardCount);

	}

	public void runRide() {
		if (onBoardCount > 0) {
			System.out.println("Threads in this ride are: ");
			for (int i = 0; i < onBoardPlayers.size(); i++) {

				onBoardPlayers.get(i).onBoard = true;
				System.out.print(onBoardPlayers.get(i).id + ", ");
			}
			System.out.println();
		}
	}

	public void endRide() {
		// System.out.println("guy?"); onBoardPlayers
		for (int i = 0; i < onBoardPlayers.size(); i++) {
			onBoardPlayers.get(0).onBoard = false;
			onBoardPlayers.get(0).rideComplete = true;
			onBoardPlayers.remove(onBoardPlayers.get(0));
		}
		onBoardPlayers.clear();
		onBoardCount = 0;
		System.out.println();
		
		//System.out.println(operator.totalPlayerCount);
		if (operator.totalPlayerCount > 0) {
			// System.out.println(operator.totalPlayerCount);
			run();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("wheel start sleep");
			sleep(maxWaitingTime);
			System.out.println("wheel end sleep");
			this.runRide();
			// System.out.println("hi there");
			this.endRide();
			// sleepWake();
		} catch (InterruptedException e) {
			System.out.println("Wheel is full, Let's go for a ride");
			this.runRide();
			this.endRide();
			// run();
			// sleepWake();
		}
	}
}
