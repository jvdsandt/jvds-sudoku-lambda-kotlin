package com.cloudctrl.sudoku.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cloudctrl.sudoku.model.SudokuGame;
import com.cloudctrl.sudoku.model.SudokuGameBase;
import com.cloudctrl.sudoku.model.SudokuGameBuilder;

public class SudokuSolver implements RequestHandler<Request, Response> {

    public Response handleRequest(Request request, Context context) {

        SudokuGameBuilder builder = new SudokuGameBuilder();
        builder.initFromNumberLine(request.getNumbersRow());

        SudokuGame game = builder.newGame();

        SudokuGameBase solvedGame = game.asSolvedGame();

        return new Response(solvedGame.toString());
    }
}
