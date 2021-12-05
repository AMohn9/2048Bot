package twentyfourtyeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Board {
  @Getter @Setter
  private List<Integer> board = new ArrayList<>();

  private static final Random random = new Random();

  public static Board initBoard() {
    Board board = emptyBoard();
    board.addNewTile();
    board.addNewTile();
    return board;
  }

  private static Board emptyBoard() {
    ArrayList<Integer> board = new ArrayList<>();
    for (int i=0; i<4; i++) {
      board.addAll(List.of(0, 0, 0, 0));
    }
    return of(board);
  }

  static Board of(List<Integer> board) {
    Board ret = new Board();
    ret.board.addAll(board);
    return ret;
  }

  public Board clone() {
    return Board.of(board);
  }

  boolean isGameOver() {
    if (BoardLib.slideLeft(clone())) {
      return false;
    }
    if (BoardLib.slideRight(clone())) {
      return false;
    }
    if (BoardLib.slideUp(clone())) {
      return false;
    }
    if (BoardLib.slideDown(clone())) {
      return false;
    }
    return true;
  }

  Integer getCell(int x, int y) {
    return board.get(y*4 + x);
  }

  void  setCell(int x, int y, Integer value) {
    board.set(y*4+x, value);
  }

  void  setCell(Integer cellNum, Integer value) {
    board.set(cellNum, value);
  }

  public void printBoard() {
    for (int y=0; y<4; y++) {
      StringBuilder rowString = new StringBuilder();
      for (int x=0; x<4; x++) {
        rowString.append(String.format("%-6s", getCell(x, y)));
      }
      log.info(rowString.toString());
    }
    log.info("");
  }

  List<Integer> findEmpties() {
    ArrayList<Integer> zeros = new ArrayList<>();
    for (int y=0; y<4; y++) {
      for (int x = 0; x < 4; x++) {
        if (getCell(x, y) == 0) {
          zeros.add(y*4 + x);
        }
      }
    }
    return zeros;
  }

  public void addNewTile() {
    List<Integer> zeros = findEmpties();
    Integer cell = zeros.get(random.nextInt(zeros.size()));
    Integer tile = random.nextInt(10) < 9 ? 2 : 4;

    setCell(cell, tile);
  }

  @Override
  public boolean equals(Object other) {
    return ((other instanceof Board) && ((Board) other).getBoard().equals(board));
  }

  @Override
  public int hashCode() {
    return board.hashCode();
  }
}
