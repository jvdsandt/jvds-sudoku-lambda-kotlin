package com.cloudctrl.sudoku.lambda;

public class Response {

    private Integer[][] rows;
    private String message;

    public Response(Integer[][] rows) {
        this(rows, "Solved");
    }

    public Response(Integer[][] rows, String message) {
        this.rows = rows;
        this.message = message;
    }

    public Integer[][] getRows() {
        return rows;
    }

    public void setRows(Integer[][] rows) {
        this.rows = rows;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
