package com.bookshelf2.demo.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Service
public class RcloneCommandExecutor {

    public static String getCommandOutput(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));

        String strArr[];
        strArr=command.split("\\s+");
        for(int i =0; i<strArr.length;i++){
            System.out.println(strArr[i]);
        }

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return output.toString();
        } else {
            throw new IOException("Errore durante l'esecuzione del comando: " + command);
        }
    }
}

