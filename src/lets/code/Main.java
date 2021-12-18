package lets.code;
import java.util.Locale;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        boolean hasWinner = false;
        Scanner scan = new Scanner(System.in);

        System.out.print("Play against bot? [y/n]: ");
        String bot = scan.next();

        System.out.print("Chose your symbol! [X/O]: ");
        String player = scan.next().toUpperCase(Locale.ROOT);

        JogoDaVelha game = new JogoDaVelha(bot.charAt(0) == 'y', player.charAt(0));
        game.printBoard();
        while(!hasWinner) {
            if (game.playerTurn == 1 && game.botExists) {
                System.out.println("Bot Turn :)!");
                game.play('1');
            } else{
                System.out.printf("Player %s type a position from 1-9: ", game.getTurn());
                int position = scan.nextInt();
                game.play(position);
            }
            hasWinner = game.hasWinner();
        }
    }
}