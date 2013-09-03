package TicTacToe;

import org.junit.Before;
import org.junit.Test;

import static TicTacToe.BoardMarker.*;
import static org.junit.Assert.assertEquals;

public class BoardMarkerArrayTest {
    BoardMarkerArray arr;

    @Before
    public void initialize(){
        arr = new BoardMarkerArray(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}});
    }

    @Test
    public void shouldBeEqualIfSameState(){
        BoardMarkerArray arr2 = new BoardMarkerArray(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}});
        assertEquals(arr, arr2);

        arr = new BoardMarkerArray(new BoardMarker[][]{{X, O, _}, {O, _, X}, {_, X, O}});
        arr2 = new BoardMarkerArray(new BoardMarker[][]{{X, O, _}, {O, _, X}, {_, X, O}});
        assertEquals(arr, arr2);
    }

    @Test
    public void shouldHaveSameHashcodeIfSameState(){
        BoardMarkerArray arr2 = new BoardMarkerArray(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}});
        assertEquals(arr.hashCode(), arr2.hashCode());

        arr = new BoardMarkerArray(new BoardMarker[][]{{X, O, _}, {O, _, X}, {_, X, O}});
        arr2 = new BoardMarkerArray(new BoardMarker[][]{{X, O, _}, {O, _, X}, {_, X, O}});
        assertEquals(arr.hashCode(), arr2.hashCode());
    }
}
