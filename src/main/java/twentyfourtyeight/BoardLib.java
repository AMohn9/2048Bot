package twentyfourtyeight;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoardLib {

  private static ArrayList<Integer> slideRowLeft(List<Integer> row) {
    ArrayList<Integer> newRow = new ArrayList<>(4);
    List<Integer> noZeros = row.stream().filter(n -> n != 0).collect(Collectors.toList());
    for (int i=0; i<noZeros.size(); i++) {
      // Haven't combined last element with previous
      // Has nothing to the right so just add it
      if (i == noZeros.size() - 1) {
        newRow.add(noZeros.get(i));
      } else if (noZeros.get(i).equals(noZeros.get(i + 1))) {
        // If these are equal add their sum
        newRow.add(noZeros.get(i) * 2);
        // And skip over the next one
        i++;
      } else {
        newRow.add(noZeros.get(i));
      }
    }

    // Add zeros to the end to make the appropriate size
    int upTo = 4-newRow.size();
    for (int i=0; i<upTo; i++) {
      newRow.add(0);
    }
    return newRow;
  }

  public static boolean slideLeft(Board board) {
    ArrayList<Integer> newBoard = new ArrayList<>();

    Iterable<List<Integer>> rows = Iterables.partition(board.getBoard(), 4);
    for (List<Integer> row : rows) {
      newBoard.addAll(slideRowLeft(row));
    }
    boolean changed = ! newBoard.equals(board.getBoard());
    board.setBoard(newBoard);
    return changed;
  }

  public static boolean slideRight(Board board) {
    flipHorizontal(board);
    boolean changed = slideLeft(board);
    flipHorizontal(board);
    return changed;
  }

  public static boolean slideUp(Board board) {
    transpose(board);
    boolean changed = slideLeft(board);
    transpose(board);
    return changed;
  }

  public static boolean slideDown(Board board) {
    transpose(board);
    boolean changed = slideRight(board);
    transpose(board);
    return changed;
  }

  private static void swapCells(Board board, int x1, int y1, int x2, int y2) {
    int cell1 = board.getCell(x1, y1);
    int cell2 = board.getCell(x2, y2);
    board.setCell(x1, y1, cell2);
    board.setCell(x2, y2, cell1);
  }

  private static Board flipHorizontal(Board board) {
    for (int y=0; y<4; y++) {
      swapCells(board, 0, y, 3, y);
      swapCells(board, 1, y, 2, y);
    }
    return board;
  }

  private static Board transpose(Board board) {
    for (int y=0; y<4; y++) {
      for (int x=y+1; x < 4; x++) {
        swapCells(board, x, y, y, x);
      }
    }
    return board;
  }
}
