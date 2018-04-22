import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Operator {
	static Wheel wheel;
	static int totalPlayerCount;
	static int wheelCapacity;
	static int maxWaitTime;
	static Queue<Player> queuedPlayers;

	public static void startWheel() {
		wheel = new Wheel(wheelCapacity, 0, new ArrayList<Player>(), maxWaitTime);
		wheel.start();
	}

	public static void playerNotify(Player player) {
		if (wheel.onBoardCount < wheel.capacity) {
			System.out.printf("passing player: %d to the operator\n", player.id);
			System.out.println();
			wheel.loadPlayers(player);
			totalPlayerCount--;

			if (wheel.capacity == wheel.onBoardCount) {
				wheel.interrupt();
			}
		} else {
			queuedPlayers.add(player);
		}
	}

	public static void wheelNotify() {
		if (totalPlayerCount == 0)
			return;

		while (!queuedPlayers.isEmpty() && wheel.onBoardCount < wheel.capacity) {
			Player player = queuedPlayers.poll();
			System.out.printf("passing player: %d to the operator\n", player.id);
			System.out.println();
			wheel.loadPlayers(player);
			totalPlayerCount--;

		}

		wheel.run();

		if (wheel.capacity == wheel.onBoardCount)
			wheel.interrupt();
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the path for the input file:");
		String path = sc.nextLine();
		sc.close();
		System.out.println();
		FileReader in = new FileReader(path);
		BufferedReader br = new BufferedReader(in);
		wheelCapacity = 5;
		maxWaitTime = Integer.parseInt(br.readLine());
		totalPlayerCount = Integer.parseInt(br.readLine());
		LinkedList<Player> playerList = new LinkedList<Player>();
		queuedPlayers = new LinkedList<Player>();
		String playerInfoString;
		while ((playerInfoString = br.readLine()) != null) {

			if (playerInfoString.length() > 0) {
				String[] playerInfoSeprator = playerInfoString.split(",");
				int playerID = Integer.parseInt(playerInfoSeprator[0]);
				int playerWaitingTime = Integer.parseInt(playerInfoSeprator[1]);
				Player player = new Player(playerID, playerWaitingTime, false, true);
				playerList.add(player);
			}
		}

		in.close();

		startWheel();
		for (Player player : playerList) {
			player.start();
		}

	}
}
