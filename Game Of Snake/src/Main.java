import enums.Direction;
import service.SnakeGameImpl;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SnakeGameImpl snakeGame = new SnakeGameImpl(5,5);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Snake Game!");
        System.out.println("Use W (up), S (down), A (left), D (right) to control the snake.");
        System.out.println("Initial board state:");
        snakeGame.printBoardState();

        while (!snakeGame.isGameOver()) {

            System.out.print("Enter direction (W/S/A/D): ");
            String input = scanner.nextLine().toUpperCase();
            Direction direction;

            switch (input) {
                case "W":
                    direction = Direction.UP;
                    break;
                case "S":
                    direction = Direction.DOWN;
                    break;
                case "A":
                    direction = Direction.LEFT;
                    break;
                case "D":
                    direction = Direction.RIGHT;
                    break;
                default:
                    System.out.println("Invalid input. Use W/S/A/D.");
                    continue;
            }

            snakeGame.moveSnake(direction);
            System.out.println("Current board state:");
            snakeGame.printBoardState();

            if (snakeGame.isGameOver()) {
                System.out.println("Game Over!");
            }
        }

        scanner.close();
    }
}