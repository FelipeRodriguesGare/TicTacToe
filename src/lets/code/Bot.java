package lets.code;
import java.util.*;

public class Bot {
    String[][] board;
    String[][] auxBoard = {
            {"_","_","_"},
            {"_","_","_"},
            {"_","_","_"},
    };
    private String[] players;
    private final String[] winnerArray = {
            "111000000",
            "000111000",
            "000000111",
            "100100100",
            "010010010",
            "001001001",
            "100010001",
            "001010100"
    };


    public Bot(String[][] board,String[] players) {
        this.board = board;
        this.players = players;
    }

    public int botMove(String[][] board){
        this.board = board;
        int move = fakeMove(board);
        return move;
    }

    public int fakeMove(String[][] board) {
        ArrayList<Integer> playIndex = new ArrayList<Integer>();
        ArrayList<Integer> minIndex = new ArrayList<Integer>();
        String[][] auxBoard;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                auxBoard = cleanBoard(board);
                if(auxBoard[i][j] == "_") {
                    auxBoard[i][j] = this.players[1];
                    int custo = botBoardAvaliation(auxBoard);
                    playIndex.add(custo);
//                    for (int k=0; k<3; k++){
//                        if(auxBoard[i][j] == "_") {
//                            auxBoard[i][k] = this.players[0];
//                            custo = botBoardAvaliation(auxBoard);
//                            minIndex.add(custo);
//                        }
//                    }
                } else playIndex.add(-1000);

            }
        }
//        System.out.println(playIndex);
//        System.out.println(minIndex);
//        for (int i=0; i<9; i++){
//            playIndex.set(i,(playIndex.get(i) - minIndex.get(i)));
//        }
        Integer maxVal = Collections.max(playIndex);
        Integer maxIdx = playIndex.indexOf(maxVal);
        System.out.println(playIndex);
        return maxIdx+1;
    }

    private String[][] cleanBoard(String[][] board){
        for(int i=0; i<board.length; i++)
            for(int j=0; j<board[i].length; j++)
                this.auxBoard[i][j]=board[i][j];
        return this.auxBoard;
    }

    public int botBoardAvaliation(String[][] auxBoard) {
        String gameMask = gameMask(auxBoard);
        ArrayList<Integer> chancesToWin =new ArrayList<Integer>();
        ArrayList<Integer> chancesToLose =new ArrayList<Integer>();
        for (String row: this.winnerArray){
            int index = 0;
            int badIndex = 0;
            boolean flag = true;

            for (int i=0; i<row.length(); i++){
//                "111000000"
                if(row.charAt(i) == '1' && gameMask.charAt(i) == '1') index++;
                else if(gameMask.charAt(i) == 'x' && row.charAt(i) == '1') {
                    badIndex--;
                }
            }
            if (badIndex < 0 && index > 0) chancesToLose.add(0);
            else chancesToLose.add(badIndex);

            if (badIndex < 0) chancesToWin.add(0);
            else chancesToWin.add(index);
        }
        System.out.println(chancesToLose);
        System.out.println(chancesToWin);
        return custo(chancesToLose.toArray(new Integer[8]),chancesToWin.toArray(new Integer[8]));
    }

    private String gameMask(String[][] board){
        String mask = "";
        for (String[] row: board) {
            for (String column : row) {
                if (column == this.players[1]) mask += "1";
                else if (column == "_") mask += "0";
                else mask += "x";
            }
        }
        return mask;
    }

    private int custo(Integer[] p1, Integer[] bot) {
        Map<Integer,Integer> p1Count = new HashMap<Integer,Integer>();
        Arrays.stream(p1).forEach(x -> p1Count.put(x , p1Count.computeIfAbsent(x, s -> 0) + 1));
        Map<Integer,Integer> botCount = new HashMap<Integer,Integer>();
        Arrays.stream(bot).forEach(x -> botCount.put(x , botCount.computeIfAbsent(x, s -> 0) + 1));
        for (int i=1; i <= 3; i++){
            botCount.putIfAbsent(i, 0);
            p1Count.putIfAbsent(-i,0);
        }
        return (100*botCount.get(3)+3*botCount.get(2)+botCount.get(1))-(30*p1Count.get(-3)+10*p1Count.get(-2)+p1Count.get(-1));
    }
}
