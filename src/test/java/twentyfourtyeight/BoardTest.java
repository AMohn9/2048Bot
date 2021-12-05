package twentyfourtyeight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BoardTest {

  @Test
  public void testClone() {
    // Verify that copying a board and making a move on the copy doesn't modify the original
    Board board = getMoreComplexExample();
    Board boardCopy = board.clone();
    BoardLib.slideLeft(boardCopy);
    assertNotEquals(board, boardCopy);
  }

  @Test
  public void testSlideLeft() {
    Board simpleBoard = getSimpleExampleBoard();
    BoardLib.slideLeft(simpleBoard);
    ArrayList<Integer> simpleCorrectBoardList = new ArrayList<>();
    simpleCorrectBoardList.addAll(List.of(4, 0, 0, 0));
    simpleCorrectBoardList.addAll(List.of(4, 0, 0, 0));
    simpleCorrectBoardList.addAll(List.of(0, 0, 0, 0));
    simpleCorrectBoardList.addAll(List.of(4, 0, 0, 0));
    Board simpleCorrectBoard = Board.of(simpleCorrectBoardList);
    assertEquals(simpleBoard, simpleCorrectBoard);


    Board complexBoard = getMoreComplexExample();
    BoardLib.slideLeft(complexBoard);
    ArrayList<Integer> correctBoardList = new ArrayList<>();
    correctBoardList.addAll(List.of(4, 4, 0, 0));
    correctBoardList.addAll(List.of(4, 4, 0, 0));
    correctBoardList.addAll(List.of(8, 4, 0, 0));
    correctBoardList.addAll(List.of(4, 2, 4, 0));
    Board correctBoard = Board.of(correctBoardList);
    assertEquals(complexBoard, correctBoard);
  }

  @Test
  public void testSlideRight() {
    Board board = getMoreComplexExample();
    BoardLib.slideRight(board);

    ArrayList<Integer> correctBoardList = new ArrayList<>();
    correctBoardList.addAll(List.of(0, 0, 4, 4));
    correctBoardList.addAll(List.of(0, 0, 4, 4));
    correctBoardList.addAll(List.of(0, 0, 4, 8));
    correctBoardList.addAll(List.of(0, 2, 4, 4));
    Board correctBoard = Board.of(correctBoardList);

    assertEquals(board, correctBoard);
  }

  @Test
  public void testSlideUp() {
    Board board = getMoreComplexExample();
    BoardLib.slideUp(board);

    ArrayList<Integer> correctBoardList = new ArrayList<>();
    correctBoardList.addAll(List.of(4, 4, 2, 8));
    correctBoardList.addAll(List.of(0, 4, 8, 4));
    correctBoardList.addAll(List.of(0, 2, 2, 0));
    correctBoardList.addAll(List.of(0, 0, 0, 0));
    Board correctBoard = Board.of(correctBoardList);

    assertEquals(board, correctBoard);
  }

  @Test
  public void testSlideDown() {
    Board board = getMoreComplexExample();
    BoardLib.slideDown(board);

    ArrayList<Integer> correctBoardList = new ArrayList<>();
    correctBoardList.addAll(List.of(0, 0, 0, 0));
    correctBoardList.addAll(List.of(0, 4, 2, 0));
    correctBoardList.addAll(List.of(0, 4, 8, 4));
    correctBoardList.addAll(List.of(4, 2, 2, 8));
    Board correctBoard = Board.of(correctBoardList);

    assertEquals(board, correctBoard);
  }

  @Test
  public void testCantMove() {
    Board board = getCantMoveBoard();
    Board boardCopy = board.clone();

    assertFalse(BoardLib.slideLeft(board));
    assertEquals(board, boardCopy);

    assertFalse(BoardLib.slideRight(board));
    assertEquals(board, boardCopy);

    assertFalse(BoardLib.slideUp(board));
    assertEquals(board, boardCopy);

    assertFalse(BoardLib.slideDown(board));
    assertEquals(board, boardCopy);
  }

  @Test
  public void testBiggerBoard() {
    Board board = getHigherScoreExampleBoard();
    BoardLib.slideUp(board);
    assertTrue(BoardLib.slideUp(getHigherScoreExampleBoard()));
  }

  private Board getSimpleExampleBoard() {
    ArrayList<Integer> boardList = new ArrayList<>();
    boardList.addAll(List.of(0, 0, 2, 2));
    boardList.addAll(List.of(2, 2, 0, 0));
    boardList.addAll(List.of(0, 0, 0, 0));
    boardList.addAll(List.of(2, 0, 0, 2));
    return Board.of(boardList);
  }

  private Board getMoreComplexExample() {
    ArrayList<Integer> boardList = new ArrayList<>();
    boardList.addAll(List.of(0, 2, 2, 4));
    boardList.addAll(List.of(2, 2, 4, 0));
    boardList.addAll(List.of(0, 4, 4, 4));
    boardList.addAll(List.of(2, 2, 2, 4));
    return Board.of(boardList);
  }

  private Board getHigherScoreExampleBoard() {
    ArrayList<Integer> boardList = new ArrayList<>();
    boardList.addAll(List.of(128, 16, 2, 32));
    boardList.addAll(List.of(128, 8, 4, 8));
    boardList.addAll(List.of(32, 4, 16, 4));
    boardList.addAll(List.of(8, 2, 4, 2));
    return Board.of(boardList);
  }

  private Board getCantMoveBoard() {
    ArrayList<Integer> boardList = new ArrayList<>();
    boardList.addAll(List.of(64, 8, 4, 2));
    boardList.addAll(List.of(8, 64, 32, 8));
    boardList.addAll(List.of(4, 16, 8, 4));
    boardList.addAll(List.of(2, 8, 4, 2));
    return Board.of(boardList);
  }
}
