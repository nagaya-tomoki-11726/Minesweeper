package com.example;

import java.util.Random;

public class MinesweeperGame {
  int rows;
  int cols;
  int mines;

  int[][] board;
  boolean[][] revealed;
  boolean[][] flagged;
  boolean gameOver = false;
  int revealedCount = 0;

  public MinesweeperGame(int rows,
      int cols,
      int mines) {
    this.rows = rows;
    this.cols = cols;
    this.mines = mines;
    board = new int[rows][cols];
    revealed = new boolean[rows][cols];
    flagged = new boolean[rows][cols];
  }

  public void placeMines(int firstR, int firstC) {
    Random rand = new Random();
    int place = 0;
    while (place < mines) {
      int r = rand.nextInt(rows);
      int c = rand.nextInt(cols);
      if (board[r][c] == 0 && (Math.abs(r - firstR) > 1 || Math.abs(c - firstC) > 1)) {
        board[r][c] = -1;
        place++;
      }
    }
  }

  public void calcNumbers() {
    int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
    int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (board[r][c] == -1)
          continue;
        int count = 0;
        for (int d = 0; d < 8; d++) {
          int nr = r + dr[d];
          int nc = c + dc[d];
          if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && board[nr][nc] == -1) {
            count++;
          }
        }
        board[r][c] = count;
      }
    }
  }

  public void reveal(int r, int c) {
    if (r < 0 || r >= rows || c < 0 || c >= cols)
      return;
    if (revealed[r][c])
      return;
    if (flagged[r][c])
      return;

    revealed[r][c] = true;
    revealedCount++;

    if (board[r][c] == -1) {
      gameOver = true;
      return;
    }

    if (board[r][c] == 0) {
      int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
      int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };
      for (int d = 0; d < 8; d++) {
        reveal(r + dr[d], c + dc[d]);
      }
    }
  }

  public boolean isWon() {
    return revealedCount == rows * cols - mines;
  }

  public void chord(int r, int c) {
    if (!revealed[r][c] || board[r][c] <= 0)
      return;

    int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
    int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

    int flagCount = 0;
    int unrevealedCount = 0;
    for (int d = 0; d < 8; d++) {
      int nr = r + dr[d];
      int nc = c + dc[d];
      if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
        if (flagged[nr][nc])
          flagCount++;
        if (!revealed[nr][nc] && !flagged[nr][nc])
          unrevealedCount++;
      }
    }

    if (flagCount == board[r][c]) {
      for (int d = 0; d < 8; d++) {
        reveal(r + dr[d], c + dc[d]);
      }
    } else if (flagCount + unrevealedCount == board[r][c]) {
      for (int d = 0; d < 8; d++) {
        int nr = r + dr[d];
        int nc = c + dc[d];
        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !revealed[nr][nc]) {
          flagged[nr][nc] = true;
        }
      }
    }
  }
}
