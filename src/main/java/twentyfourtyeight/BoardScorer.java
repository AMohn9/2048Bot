package twentyfourtyeight;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardScorer {

  private static LoadingCache<Board, Integer> memo = CacheBuilder.newBuilder()
      .maximumSize(100)
      .build(CacheLoader.from(BoardScorer::computeScore));

  public Integer scoreBaord(Board board) {
    return memo.getUnchecked(board);
  }

  public Integer scoreByBestWorst(Board board) {
    return scoreBaord(getWorstAfterNewPiece(board));
  }

  public static int computeScoreDebug(Board board) {
    if (board.isGameOver()) {
      return Integer.MIN_VALUE;
    }

    if (log.isDebugEnabled()) {
      board.printBoard();
    }
    int maxVal = highestValuedCell(board);
    log.debug("Max Val: " + maxVal);

    int maxInCorner = 2 * maxVal * (isMaxInCorner(board) ? 1 : 0);
    log.debug("Max In Corner: " + maxInCorner);

    int smoothScore = smoothness(board);
    log.debug("Smoothness: " + smoothScore);

    double averageCellValue = board.getBoard().stream().filter(cell -> cell != 0).mapToDouble(a->a).average().getAsDouble();
    int zeroScore = (int) averageCellValue * board.findEmpties().size();
    log.debug("Zero score: " + zeroScore);

    int edgeScore = getEdgeScore(board);
    log.debug("Edge score: " + edgeScore);

    log.debug("");
    return maxVal + maxInCorner + smoothScore + zeroScore + edgeScore;
  }

  private static int computeScore(Board board) {
    if (board.isGameOver()) {
      return Integer.MIN_VALUE;
    }

//    if (log.isDebugEnabled()) {
//      board.printBoard();
//    }
    int maxVal = highestValuedCell(board);
//    log.debug("Max Val: " + maxVal);

    int maxInCorner = 2 * maxVal * (isMaxInCorner(board) ? 1 : 0);
//    log.debug("Max In Corner: " + maxInCorner);

    int smoothScore = smoothness(board);
//    log.debug("Smoothness: " + smoothScore);

    double averageCellValue = board.getBoard().stream().filter(cell -> cell != 0).mapToDouble(a->a).average().getAsDouble();
    int zeroScore = (int) averageCellValue * board.findEmpties().size();
//    log.debug("Zero score: " + zeroScore);

    int edgeScore = getEdgeScore(board);
//    log.debug("Edge score: " + edgeScore);

//    log.debug("");
    return maxVal + maxInCorner + smoothScore + zeroScore + edgeScore;
  }

  private static int highestValuedCell(Board board) {
    return Collections.max(board.getBoard());
  }

  private static boolean isMaxInCorner(Board board) {
    int maxVal = highestValuedCell(board);

    for (int y=0; y<4; y++) {
      for (int x = 0; x < 4; x++) {
        if (board.getCell(x, y) == maxVal && (x%3 == 0 && y%3 == 0)) {
          return true;
        }
      }
    }
    return false;
  }

  private static int smoothness(Board board) {
    int total = 0;
    for (int y=0; y<4; y++) {
      for (int x = 0; x < 4; x++) {
        if (y>0) {
          total += getCellSmoothness(board, x, y, x, y-1);
        }
        if (y<3) {
          total += getCellSmoothness(board, x, y, x, y+1);
        }
        if (x>0) {
          total += getCellSmoothness(board, x, y, x-1, y);
        }
        if (x<3) {
          total += getCellSmoothness(board, x, y, x+1, y);
        }
      }
    }
    return total;
  }

  private static int getCellSmoothness(Board board, int x1, int y1, int x2, int y2) {
    int inCell1 = board.getCell(x1, y1);
    int inCell2 = board.getCell(x2, y2);

    double maxy = Double.max(inCell1, inCell2);
    double miny = Double.min(inCell1, inCell2);

    return (int) (maxy * (miny / maxy));
  }

  private static int getEdgeScore(Board board) {
    int total = 0;
    for (int y=0; y<4; y++) {
      for (int x = 0; x < 4; x++) {
        if (x%3 != 0 && y%3 != 0) {
          continue;
        }
        total += board.getCell(x, y);
      }
    }
    return total;
  }

  public Board getWorstAfterNewPiece(Board board) {

    if (log.isDebugEnabled()) {
      log.debug("Finding worst new tile for board:");
      board.printBoard();
    }

    Board worstBoard = null;
    Integer worstScore = Integer.MAX_VALUE;

    for (Integer zero : board.findEmpties()) {
      for (Integer possibleNewValue : List.of(2, 4)) {
        Board boardCopy = board.clone();
        boardCopy.setCell(zero, possibleNewValue);
        Integer score = scoreBaord(boardCopy);

        if (score < worstScore) {
          worstBoard = boardCopy;
          worstScore = score;
        }
      }
    }

    if (log.isDebugEnabled()) {
      log.debug("Worst after new piece");
      worstBoard.printBoard();
    }

    return worstBoard;
  }
}
