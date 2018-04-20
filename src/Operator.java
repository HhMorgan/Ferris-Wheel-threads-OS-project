import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Operator {
	Wheel wheel;
	int totalPlayerCount;
	int wheelCapacity;
	int maxWaitTime;

	public Operator(int wheelCapacity, int maxWaitTime, int totalPlayerCount) {
		this.totalPlayerCount = totalPlayerCount;
		this.wheelCapacity = wheelCapacity;
		this.maxWaitTime = maxWaitTime;
	}

	public void startWheel() {
		wheel = new Wheel(wheelCapacity, 0, new ArrayList<Player>(), maxWaitTime, this);
		wheel.start();

	}

	int waiting;

	public synchronized void addPlayer(Player player, boolean flag) {
		System.out.printf("passing player: %d to the operator\n", player.id);
		wheel.loadPlayers(player);
		totalPlayerCount--;
		if (wheel.capacity == wheel.onBoardCount) {
			wheel.interrupt();

		}


	}

	public static void main(String[] args) throws NumberFormatException, IOException {

		FileReader in = new FileReader("input-2.txt");
		BufferedReader br = new BufferedReader(in);
		int wheelCapacity = 5;
		int maxWaitTime = Integer.parseInt(br.readLine());
		// System.out.println(maxWaitTime);
		int totalPlayerCount = Integer.parseInt(br.readLine());
		// System.out.println(totalPlayerCount);
		LinkedList<Player> playerList = new LinkedList<Player>();
		// System.out.println("hi");
		Operator operator = new Operator(wheelCapacity, maxWaitTime, totalPlayerCount);
		// System.out.println("guy?");
		String playerInfoString;
		while ((playerInfoString = br.readLine()) != null && totalPlayerCount > 0) {

			// System.out.println(playerInfoString);
			if (playerInfoString.length() > 0) {
				String[] playerInfoSeprator = playerInfoString.split(",");
				int playerID = Integer.parseInt(playerInfoSeprator[0]);
				int playerWaitingTime = Integer.parseInt(playerInfoSeprator[1]);
				Player player = new Player(playerID, playerWaitingTime, false, true, operator);
				playerList.add(player);
				totalPlayerCount--;
			}
		}
		// operator.wheel.start();
		operator.startWheel();
		for (Player player : playerList) {
			player.start();
		}
		in.close();
	}
}
