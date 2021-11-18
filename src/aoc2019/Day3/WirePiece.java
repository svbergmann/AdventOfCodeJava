package aoc2019.Day3;

public class WirePiece {

    private Position[] positions;

    public WirePiece(Position position, char direction, int length) {
        positions = new Position[length];
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case 'U':
                for (int i = 0; i < length; i++) {
                    positions[i] = new Position(x, y + i + 1);
                }
                break;
            case 'R':
                for (int i = 0; i < length; i++) {
                    positions[i] = new Position(x + i + 1, y);
                }
                break;
            case 'D':
                for (int i = 0; i < length; i++) {
                    positions[i] = new Position(x, y - i - 1);
                }
                break;
            case 'L':
                for (int i = 0; i < length; i++) {
                    positions[i] = new Position(x - i - 1, y);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public Position[] getPositions() {
        return positions;
    }

}
