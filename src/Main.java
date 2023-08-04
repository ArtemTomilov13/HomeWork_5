import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        String sourceDirectory = "path/to/source/directory";
        String backupDirectory = "path/to/backup/directory";

        try {
            createBackup(sourceDirectory, backupDirectory);
            System.out.println("Backup created successfully!");

            int[][] gameBoard = {
                    {1, 2, 0},
                    {0, 1, 2},
                    {2, 0, 1}
            };

            String gameBoardFile = "path/to/game/board/file.txt";
            saveGameBoardToFile(gameBoard, gameBoardFile);
            System.out.println("Game board saved to file!");

            int[][] loadedGameBoard = loadGameBoardFromFile(gameBoardFile);
            printGameBoard(loadedGameBoard);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void createBackup(String sourceDirectory, String backupDirectory) throws IOException {
        File sourceDir = new File(sourceDirectory);
        File backupDir = new File(backupDirectory);

        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("Source directory does not exist or is not a directory");
        }

        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Path sourcePath = file.toPath();
                    Path backupPath = backupDir.toPath().resolve(file.getName());
                    Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public static void saveGameBoardToFile(int[][] gameBoard, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (int[] row : gameBoard) {
                for (int cell : row) {
                    writer.print(cell);
                }
                writer.println();
            }
        }
    }

    public static int[][] loadGameBoardFromFile(String filePath) throws IOException {
        int[][] gameBoard = new int[3][3];

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < 3) {
                for (int col = 0; col < 3 && col < line.length(); col++) {
                    char cellChar = line.charAt(col);
                    gameBoard[row][col] = Character.getNumericValue(cellChar);
                }
                row++;
            }
        }

        return gameBoard;
    }

    public static void printGameBoard(int[][] gameBoard) {
        for (int[] row : gameBoard) {
            for (int cell : row) {
                switch (cell) {
                    case 0:
                        System.out.print("• ");
                        break;
                    case 1:
                        System.out.print("X ");
                        break;
                    case 2:
                        System.out.print("O ");
                        break;
                    default:
                        System.out.print("? ");
                        break;
                }
            }
            System.out.println();
        }
    }
}