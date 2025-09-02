import java.io.*;
import java.util.*;
//poop
abstract class ChessPiece {
    protected String name, color;
    protected char posX; // 'a' to 'h'
    protected int posY;  // 1 to 8

    public ChessPiece(String name, String color, char posX, int posY) {
        this.name = name;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
    }

    public abstract boolean canMoveTo(char x, int y);

    public boolean isInsideBoard(char x, int y) {
        return (x >= 'a' && x <= 'h' && y >= 1 && y <= 8);
    }

    @Override
    public String toString() {
        return name + " at " + Character.toUpperCase(posX) + ", " + posY;
    }
}

class King extends ChessPiece {
    public King(String color, char x, int y) { super("King", color, x, y); }
    public boolean canMoveTo(char x, int y) {
        return isInsideBoard(x, y) &&
               Math.abs(x - posX) <= 1 &&
               Math.abs(y - posY) <= 1;
    }
}

class Queen extends ChessPiece {
    public Queen(String color, char x, int y) { super("Queen", color, x, y); }
    public boolean canMoveTo(char x, int y) {
        return isInsideBoard(x, y) &&
              (x == posX || y == posY || Math.abs(x - posX) == Math.abs(y - posY));
    }
}

class Rook extends ChessPiece {
    public Rook(String color, char x, int y) { super("Rook", color, x, y); }
    public boolean canMoveTo(char x, int y) {
        return isInsideBoard(x, y) && (x == posX || y == posY);
    }
}

class Bishop extends ChessPiece {
    public Bishop(String color, char x, int y) { super("Bishop", color, x, y); }
    public boolean canMoveTo(char x, int y) {
        return isInsideBoard(x, y) && (Math.abs(x - posX) == Math.abs(y - posY));
    }
}

class Knight extends ChessPiece {
    public Knight(String color, char x, int y) { super("Knight", color, x, y); }
    public boolean canMoveTo(char x, int y) {
        return isInsideBoard(x, y) &&
               ((Math.abs(x - posX) == 2 && Math.abs(y - posY) == 1) ||
                (Math.abs(x - posX) == 1 && Math.abs(y - posY) == 2));
    }
}

class Pawn extends ChessPiece {
    public Pawn(String color, char x, int y) { super("Pawn", color, x, y); }
    public boolean canMoveTo(char x, int y) {
        if (!isInsideBoard(x, y) || x != posX) return false;
        int dir = color.equalsIgnoreCase("white") ? 1 : -1;
        return (y - posY) == dir;
    }
}

public class ChessValidator {
    public static void main(String[] args) throws Exception {
        // read just one piece from file
        Scanner fileScanner = new Scanner(new File("piece.txt"));
        String[] parts = fileScanner.nextLine().split(",\\s*");
        String type = parts[0];
        String color = parts[1];
        char x = parts[2].toLowerCase().charAt(0);
        int y = Integer.parseInt(parts[3]);

        ChessPiece piece = switch (type.toLowerCase()) {
            case "king" -> new King(color, x, y);
            case "queen" -> new Queen(color, x, y);
            case "rook" -> new Rook(color, x, y);
            case "bishop" -> new Bishop(color, x, y);
            case "knight" -> new Knight(color, x, y);
            case "pawn" -> new Pawn(color, x, y);
            default -> null;
        };

        if (piece == null) {
            System.out.println("Unknown piece type!");
            return;
        }

        // ask the user for a new position
        Scanner input = new Scanner(System.in);
        System.out.print("Enter target position (e.g., e 4): ");
        char targetX = input.next().toLowerCase().charAt(0);
        int targetY = input.nextInt();

        // check if move is valid
        if (piece.canMoveTo(targetX, targetY)) {
            System.out.println(piece + " can move to " + Character.toUpperCase(targetX) + ", " + targetY);
        } else {
            System.out.println(piece + " can NOT move to " + Character.toUpperCase(targetX) + ", " + targetY);
        }
    }
}
