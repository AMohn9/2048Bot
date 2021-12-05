import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import twentyfourtyeight.Board;
import twentyfourtyeight.BoardLib;
import twentyfourtyeight.BoardScorer;

@Slf4j
public class SmartComputer {

  private static Map<String, Predicate<Board>> validMoves = Map.of(
      "u", BoardLib::slideUp,
      "d", BoardLib::slideDown,
      "l", BoardLib::slideLeft,
      "r", BoardLib::slideRight
  );

  private static BoardScorer boardScorer = new BoardScorer();
  private static int recurseDepth = 0;

  public static void main(String[] args) {
    Board board = Board.initBoard();
    board.printBoard();

    for (int i=0; i<Integer.MAX_VALUE; i++) {
      board = getBestMove(board, recurseDepth);

      if (board == null) {
        log.warn("Game Over");
        System.exit(0);
      }

      log.info("New board:");
      board.printBoard();
      board.addNewTile();
      board.printBoard();
    }
  }

  private static Board tryMove(Board board, Predicate<Board> move) {
    log.debug(move.toString());
    Board boardCopy = board.clone();
    boolean valid = move.test(boardCopy);
    if (valid) {
      if (log.isDebugEnabled()) {
        log.debug("Board after move:");
//        boardCopy.printBoard();
        boardScorer.computeScoreDebug(boardCopy);
      }
    } else {
      log.debug("Move not valid");
    }
    return valid ? boardCopy : null;
  }

  private static Board getBestMove(Board board, int depth) {
    List<Board> options = validMoves.values().stream()
        .map(moveFunction -> tryMove(board, moveFunction))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    if (options.isEmpty()) {
      return null;
    }
//    else if (depth > 0) {
//      Board best = null;
//      int bestScore = Integer.MIN_VALUE;
//
//      for (Board option : options) {
//        Board optionAfterWorstPiece = boardScorer.getWorstAfterNewPiece(option);
//        if (log.isDebugEnabled()) {
//          log.debug("Worst under option");
//          option.printBoard();
//          optionAfterWorstPiece.printBoard();
//        }
//
//        // Option after worst new piece and best move
//        Board bestUnderOption = getBestMove(optionAfterWorstPiece, depth-1);
//
//        // No moves after worst new piece
//        if (bestUnderOption == null) {
//          continue;
//        }
//
//        int bestUnderOptionScore = boardScorer.scoreBaord(bestUnderOption);
//
//        if (bestUnderOptionScore > bestScore) {
//          best = option;
//          bestScore = bestUnderOptionScore;
//        }
//      }
//      return best;
//    }
    else {
      return Collections.max(options, Comparator.comparing(boardScorer::scoreByBestWorst));
    }
  }
}
