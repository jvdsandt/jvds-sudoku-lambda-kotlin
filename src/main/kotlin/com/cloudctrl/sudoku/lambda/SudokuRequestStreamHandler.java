package com.cloudctrl.sudoku.lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.cloudctrl.sudoku.model.SudokuBoard;
import com.cloudctrl.sudoku.model.SudokuGame;
import com.cloudctrl.sudoku.model.SudokuGameBase;
import com.cloudctrl.sudoku.model.SudokuGameBuilder;
import com.google.gson.Gson;

public class SudokuRequestStreamHandler implements RequestStreamHandler {

    final static LocalDateTime INIT_TIME = LocalDateTime.now();

    final static SudokuBoard BOARD = SudokuBoard.Companion.default9x9();

    final Gson gson = new Gson();

    final LocalDateTime INIT_TIME2 = LocalDateTime.now();

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Map eventData = gson.fromJson(reader, HashMap.class);
        String numbersRow = null;
        String httpMethod = (String) eventData.get("httpMethod");

        if ("GET".equals(httpMethod)) {
            Map queryParams = (Map) eventData.get("queryStringParameters");
            numbersRow = queryParams == null ? null : (String) queryParams.get("numbersRow");
        } else if ("POST".equals(httpMethod)) {
            String body = (String) eventData.get("body");
            Request request = gson.fromJson(body, Request.class);
            numbersRow = request.getNumbersRow();
        } else {
            respondError("Unsupported method", outputStream);
            return;
        }
        if (numbersRow != null) {
            SudokuGameBuilder builder = new SudokuGameBuilder(BOARD);
            try {
                builder.initFromNumberLine(numbersRow);
            } catch (IllegalArgumentException ex) {
                respondError("Invalid input: " + ex.getMessage(), outputStream);
                return;
            }
            SudokuGame game = builder.newGame();
            respondGame(game, outputStream, context);
        } else {
            respondError("numbersRow parameter missing", outputStream);
        }
    }

    void respondGame(SudokuGameBase game, OutputStream outputStream, Context context) throws IOException {
        Map<String, Object> response = new HashMap<>();
        context.getLogger().log("Game:\n" + game.toString());
        SudokuGameBase solvedGame = game.asSolvedGame();
        context.getLogger().log("Solved game:\n" + solvedGame.toString());

        response.put("rows", solvedGame.asArray());
        response.put("message", "Solved");
        response.put("init-time", INIT_TIME.toString());
        response.put("init-time2", INIT_TIME2.toString());
        respond(200, gson.toJson(response), outputStream);
    }

    void respondError(String msg, OutputStream outputStream) throws IOException {
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("init-time", INIT_TIME.toString());
        responseJson.put("message", msg);

        respond(400, gson.toJson(responseJson), outputStream);
    }

    void respondException(Exception ex, OutputStream outputStream) throws IOException {
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("init-time", INIT_TIME.toString());
        responseJson.put("message", ex.getMessage());

        respond(400, gson.toJson(responseJson), outputStream);
    }

    void respond(int statusCode, String body, OutputStream outputStream) throws IOException {
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("isBase64Encoded", false);
        responseJson.put("statusCode", statusCode);
        responseJson.put("body", body);

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        gson.toJson(responseJson, writer);
        writer.close();
    }
}
