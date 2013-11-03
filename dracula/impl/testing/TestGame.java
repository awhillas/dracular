package dracula.impl.testing;

import java.util.Arrays;
import java.util.HashMap;

import dracula.*;
import dracula.impl.*;
import dracula.impl.map.GameMap;
import dracula.impl.map.Map;
import dracula.impl.ai.DracCirclingAI;
import dracula.impl.ai.DracMoveSearch;

public class TestGame {
    private Board board;
    private HashMap<String, Player> hunters;
    private int gameScore;

    public static void main(String[] args) {
        TestGame test = new TestGame();
        test.board = new Board();
        test.hunters = test.board.getHunters();
        test.setupGame();

        //run for ten or so rounds
        int i = 0;
        while (i < 10) {
            test.emulateGame();
            i++;
        }
    }

    public void setupGame() {

        this.gameScore = 366;

        //Madrid, London, Belgrade, Berlin
        //MA, LO, BE, BR
        this.board.parsePastPlay("GMA....");
        this.board.parsePastPlay("SLO....");
        this.board.parsePastPlay("HBE....");
        this.board.parsePastPlay("MBR....");

        String loc1 = this.hunters.get("G").getLocation();
        String loc2 = this.hunters.get("S").getLocation();
        String loc3 = this.hunters.get("H").getLocation();
        String loc4 = this.hunters.get("M").getLocation();

        //Setup worked
        assert loc1.contains("MA");
        assert loc2.contains("LO");
        assert loc3.contains("BE");
        assert loc4.contains("BR");
        for (String key : hunters.keySet()) {
            assert hunters.get(key).getHealth() >= 0;
        }

        System.out.println("Setup OK..");
        System.out.println("Start: GMA... SLO... HBE... MBR...");
    }

    public void emulateGame() {
        while (this.board.getDracula().getHealth() > 0 && this.gameScore > 0) {
            System.out.println("Round: " + this.board.getRound()
                    + " HunterHealth: " + Arrays.toString(board.getHunterHealth())
                    + " DraculaHealth: " + board.getDracHealth()
                    + " Score: " + gameScore);

            DraculaMove move = DracMoveSearch.getBestMove(board);
            String player5 = "D" + move.getPlayAsString();
            board.parsePastPlay(player5);

            String player1 = HunterMover("G", "RANDOM");
            board.parsePastPlay(player1);
            System.out.print("Turn: " + player1);

            String player2 = HunterMover("S", "RANDOM");
            board.parsePastPlay(player2);
            System.out.print(" " + player2);

            String player3 = HunterMover("H", "RANDOM");
            board.parsePastPlay(player3);
            System.out.print(" " + player3);

            String player4 = HunterMover("M", "SEARCH");
            board.parsePastPlay(player4);
            System.out.print(" " + player4);
            System.out.print(" " + player5);
            System.out.println("");
            scoring(player5);
        }
        System.out.println("GAME OVER!");
        System.out.println("Round: " + this.board.getRound()
                + " HunterHealth: " + Arrays.toString(board.getHunterHealth())
                + " DraculaHealth: " + board.getDracHealth()
                + " Score: " + gameScore);
        System.exit(0);
    }

    public void scoring(String play) {
        this.gameScore--;
        int[] hunterHealth = board.getHunterHealth();
        for (int health : hunterHealth) {
            if (health < 1) {
                gameScore -= 6;
            }
        }
        if (play.substring(3).contains("V")) {
            gameScore -= 13;
        }
    }
    /*
     * Create a basic hunter AI and get its first move based on Random or the
     * path finder
     */
    public String HunterMover(String name, String type) {
        Player hunter = hunters.get(name);
        HunterMove newMove = new HunterMove(hunter, type, this.board);
        return newMove.hunterMove();
    }
}
