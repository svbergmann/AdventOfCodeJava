package Day3;

public class WirePiece {

    private Position[] positions;

    public WirePiece(Position position, char direction, int length) {
        positions = new Position[length+1];
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case 'U':
                for (int i = 0; i < length+1; i++) {
                    positions[i] = new Position(x, y + i);
                }
                break;
            case 'R':
                for (int i = 0; i < length+1; i++) {
                    positions[i] = new Position(x + i, y);
                }
                break;
            case 'D':
                for (int i = 0; i < length+1; i++) {
                    positions[i] = new Position(x, y - i);
                }
                break;
            case 'L':
                for (int i = 0; i < length+1; i++) {
                    positions[i] = new Position(x - i, y);
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
