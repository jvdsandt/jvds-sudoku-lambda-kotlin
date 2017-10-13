package com.cloudctrl.sudoku.lambda;

public class Response {

    public Response(String row) {
        this.numbersRow = row;
    }

    public String getNumbersRow() {
        return numbersRow;
    }

    public void setNumbersRow(String numbersRow) {
        this.numbersRow = numbersRow;
    }

    private String numbersRow;
}
