package com.cloudctrl.sudoku.lambda;

import org.junit.Test;

public class SudokuSolverTest {

    @Test
    public void testSimpleGame() {
        Request request = new Request();
        request.setNumbersRow("379000014060010070080009005435007000090040020000800436900700080040080050850000249");

        Response response = new SudokuSolver().handleRequest(request, null);
        System.out.println(response.getNumbersRow());
    }
}
