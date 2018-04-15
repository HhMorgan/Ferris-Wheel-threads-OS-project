import java.util.LinkedList;
import java.util.Scanner;

public class Operator {
	Wheel wheel;
	int totalPlayerCount;
	int wheelCapacity;
	int maxWaitTime;
	public Operator(int wheelCapacity, int maxWaitTime, int totalPlayerCount) {
		this.totalPlayerCount = totalPlayerCount;
		this.wheelCapacity=wheelCapacity;
		this.maxWaitTime=maxWaitTime;
	}
	public void startWheel(){
		wheel = new Wheel(wheelCapacity, 0, new LinkedList<Player>(), maxWaitTime, this);
		wheel.start();
		
	}
	public synchronized void  addPlayer(Player player) {
		System.out.printf("passing player: %d to the operator\n", player.id);
		wheel.loadPlayers(player);
		totalPlayerCount--;
		if (wheel.capacity == wheel.onBoardCount) {
			wheel.interrupt();

		}

	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int wheelCapacity = 5;
		int maxWaitTime = scn.nextInt();
		int totalPlayerCount = scn.nextInt();
		LinkedList<Player> playerList=new LinkedList<Player>();
		//System.out.println("hi");
		Operator operator = new Operator(wheelCapacity, maxWaitTime, totalPlayerCount);
		//System.out.println("guy?");
		while (totalPlayerCount > 0) {
			String playerInfoString = scn.nextLine();
			//System.out.println(playerInfoString);
			if (playerInfoString.length() > 0) {
				String[] playerInfoSeprator = playerInfoString.split(",");
				int playerID = Integer.parseInt(playerInfoSeprator[0]);
				int playerWaitingTime = Integer.parseInt(playerInfoSeprator[1]);
				Player player = new Player(playerID, playerWaitingTime, false, true, operator);
				playerList.add(player);
				totalPlayerCount--;
			}
		}
		//operator.wheel.start();
		operator.startWheel();
		for(Player player:playerList){
			player.start();
		}
		scn.close();
	}
}
