package com.fforbeck.spreadsheet.application.init;

import com.fforbeck.spreadsheet.application.util.SpreadsheetCache;

import java.io.*;

public class Solver {

    public static void main(String... args) throws IOException {
        int maxColumns, maxRows, maxLinesAllowed = 26;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            maxColumns = Integer.valueOf(in.readLine());
            maxRows = Integer.valueOf(in.readLine());
            SpreadsheetCache.INSTANCE.create(maxColumns, maxRows,
                    in.lines().limit(Math.min(maxColumns * maxRows, maxLinesAllowed)));
            //System.out.println(SpreadsheetHolder.INSTANCE.print());
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            writer.append(String.valueOf(maxColumns));
            writer.newLine();
            writer.append(String.valueOf(maxRows));
            writer.newLine();
            SpreadsheetCache.INSTANCE.evaluate()
                    .stream()
                    .forEach(value -> tryWrite(writer, value));
        }
    }

    private static void tryWrite(BufferedWriter out, String value) {
        try {
            out.append(value);
            out.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}