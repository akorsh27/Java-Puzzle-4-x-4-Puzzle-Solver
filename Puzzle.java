import java.util.Scanner;

public class Puzzle {
    private static final int N = 4;
    private int[][] board = new int[N][N];
    private Coord blank = new Coord(0, 0);

    private static class Coord {
        private int x;
        private int y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public void setX(int x) { this.x = x; }
        public void setY(int y) { this.y = y; }
    }

    public Puzzle(int[][] board) {
        this.board = board;
        findBlank();
    }

    // Find where the blank space [0] is in the puzzle [2D array]
    private void findBlank() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    blank.setY(i);
                    blank.setX(j);
                    return;
                }
            }
        }
    }

    public Puzzle copy() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, N);
        }
        return new Puzzle(copy);
    }

    public void display() {
        for (int i = 0; i < N; i++) {
            System.out.printf("%4d%4d%4d%4d%n", board[i][0], board[i][1], board[i][2], board[i][3]);
        }
    }

    public boolean validMove(char m) {
        switch (m) {
            case 'U':
                return blank.getY() > 0;
            case 'D':
                return blank.getY() < N - 1;
            case 'L':
                return blank.getX() > 0;
            case 'R':
                return blank.getX() < N - 1;
            default:
                return false;
        }
    }

    // Swaps the current blank block with the target block represented by the target coord
    private void swap(Coord target) {
        int temp = board[blank.getY()][blank.getX()];
        board[blank.getY()][blank.getX()] = board[target.getY()][target.getX()];
        board[target.getY()][target.getX()] = temp;
        blank.setX(target.getX());
        blank.setY(target.getY());
    }

    public void doMove(char m) {
        switch (m) {
            case 'U':
                swap(new Coord(blank.getX(), blank.getY() - 1));
                break;
            case 'D':
                swap(new Coord(blank.getX(), blank.getY() + 1));
                break;
            case 'L':
                swap(new Coord(blank.getX() - 1, blank.getY()));
                break;
            case 'R':
                swap(new Coord(blank.getX() + 1, blank.getY()));
                break;
            default:
                break;
        }
    }

    public boolean solved() {
        int val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != val) {
                    return false;
                }
                val++;
            }
        }
        return true;
    }

    public boolean checkSolution(char[] moves) {
        for (char m : moves) {
            if (validMove(m)) {
                doMove(m);
            } else {
                System.out.println("Invalid move");
                System.exit(0);
            }
        }
        if (solved()) {
            display();
            System.out.println("Success");
            return true;
        } else {
            display();
            System.out.println("Failed");
            return false;
        }
    }

    public static void main(String[] args) {
        // Initialize
        int[][] board = new int[N][N];

        // User input and error catch
        Scanner s = new Scanner(System.in);
        System.out.println("Enter puzzle in order [L -> R & U -> D] :");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                while (!s.hasNextInt()) {
            System.out.println("Invalid input. Please enter integers only.");
            s.nextLine();
                }
                board[i][j] = s.nextInt();
            }
        }

        System.out.println("Enter moveset:");
        String movesToken = s.next();
        s.close();

        // Solve
        Puzzle puzzle = new Puzzle(board);
        char[] moves = movesToken.toCharArray();
        puzzle.checkSolution(moves);
    }
}