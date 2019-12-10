package Day3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Wire {

    private Set<Position> positions = new HashSet<>();

    public Wire(WirePiece[] wirePieces) {
        for(WirePiece wirePiece: wirePieces) {
            positions.addAll(Arrays.asList(wirePiece.getPositions()));
        }
    }

    public Set<Position> getPositions() {
        return positions;
    }

}
