import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import twentyfourtyeight.Board;

@Slf4j
public class RandomComputer {

  private static List<String> validMoves = List.of("u", "d", "l", "r");

  public static void main(String[] args) {
//    ArrayList<String> moveOptions = new ArrayList<>(validMoves);
//
//    Board board = Board.initBoard();
//    board.printBoard();
//
//    Random random = new Random();
//
//    for (int i=0; i<Integer.MAX_VALUE; i++) {
//      String move = moveOptions.get(random.nextInt(moveOptions.size()));
//      log.info("Chosen move: " + move);
//      Board newBoard = movakeMove(board, move);
//
//      if (newBoard != null) {
//        board = newBoard;
//        board.printBoard();
//        board.addNewTile();
//        board.printBoard();
//        moveOptions = new ArrayList<>(validMoves);
//      } else {
//        log.warn("Invalid move choice: " + move);
//        moveOptions.remove(move);
//
//        if (moveOptions.isEmpty()) {
//          log.warn("Game Over");
//          System.exit(0);
//        }
//      }
//    }
  }
}
