import java.util.LinkedList;
import java.util.Scanner;

public class Operator {
	Wheel wheel;
	int totalPlayerCount;
	public Operator(int wheelCapacity, int maxWaitTime, int totalPlayerCount) {
		this.totalPlayerCount = totalPlayerCount;
		wheel = new Wheel(wheelCapacity, 0, new LinkedList<Player>(), maxWaitTime);
		wheel.start();
	}

	public void addPlayer(Player player) {
		System.out.printf("passing player: %d to the operator\n", player.id);
		wheel.loadPlayers(player);
		totalPlayerCount--;
		if (wheel.capacity == wheel.onBoardCount) {
			wheel.interrupt();
			if (totalPlayerCount > 0)
				wheel.sleepWake();
		}

	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int wheelCapacity = 5;
		int maxWaitTime = scn.nextInt();
		int totalPlayerCount = scn.nextInt();
		// LinkedList<Player> playerList=new LinkedList<Player>();
		Operator operator = new Operator(wheelCapacity, maxWaitTime, totalPlayerCount);
		while (totalPlayerCount > 0) {
			String playerInfoString = scn.nextLine();
			if (playerInfoString.length() > 0) {
				String[] playerInfoSeprator = playerInfoString.split(",");
				int playerID = Integer.parseInt(playerInfoSeprator[0]);
				int playerWaitingTime = Integer.parseInt(playerInfoSeprator[1]);
				Player player = new Player(playerID, playerWaitingTime, false, true, operator);
				player.run();
				totalPlayerCount--;
			}
		}
		scn.close();
	}
}
