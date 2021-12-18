package lets.code;
import static java.lang.Integer.parseInt;

public class JogoDaVelha {
    public String[][] board = {
            {"_","_","_"},
            {"_","_","_"},
            {"_","_","_"},
    };
    private final int[] winnerArray = {292,448,146,56,73,7,84,273};
    private String[] players;
    public Bot bot;
    public boolean botExists = false;
    public int playerTurn = 0;

    public JogoDaVelha(boolean bot, char player, int turn) {
        this.players = new String[]{"X", "O"};
        if (player == 'O') this.players = new String[]{"O", "X"};
        this.botExists=bot;
        if (bot) this.bot = new Bot(this.board, this.players);
        this.playerTurn = turn;
    }

    public boolean hasWinner() {
        boolean draw = true;
        for(String player: this.players){
            String playerGameMask = "";
            for (String[] row: this.board) {
                for (String column: row) {
                    if (column == player) playerGameMask += "1";
                    else {
                        playerGameMask += "0";
                        if (column == "_") draw = false;
                    }
                }
            }
            for (int j : this.winnerArray) {
                if ((parseInt(playerGameMask, 2) & j ^ j) == 0) {
                    System.out.printf("The * %s * player won the game!", player);
                    return true;
                }
            }
        }
        if (draw) {
            System.out.println("It's a Draw!");
            return true;
        }
        return false;
    }

    public void printBoard() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        for (String[] row: this.board) {
            System.out.printf("%s%n", String.join("|", row));
        }
        System.out.println();
    }

    public boolean play(int position) {
        if (this.playerTurn==1 && this.botExists) position = bot.botMove(this.board);
        int pos = 0;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++) {
                pos++;
                if (pos == position && this.board[i][j] == "_") {
                    this.board[i][j] = this.players[this.playerTurn];
                    printBoard();
                    this.playerTurn = this.playerTurn == 0 ? 1 : 0;
                    return true;
                }
            }
        }
        printBoard();
        System.out.println("Invalid Play!");
        return false;
    }

    public String getTurn(){
        return this.players[this.playerTurn];
    }
}

