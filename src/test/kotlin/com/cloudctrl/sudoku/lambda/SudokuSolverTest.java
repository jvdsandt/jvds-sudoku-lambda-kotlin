package com.cloudctrl.sudoku.lambda;

import org.junit.Test;
import static org.junit.Assert.*;

public class SudokuSolverTest {

    @Test
    public void testSimpleGame() {
        Request request = new Request();
        request.setNumbersRow("379000014060010070080009005435007000090040020000800436900700080040080050850000249");

        Response response = new SudokuSolver().handleRequest(request, null);
        assertEquals("Solved", response.getMessage());
    }

    @Test
    public void testInvalidInput() {
        Request request = new Request();
        request.setNumbersRow("--invalid--");

        Response response = new SudokuSolver().handleRequest(request, null);
        assertNotEquals("Solved", response.getMessage());
    }
}
