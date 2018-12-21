package com.michalchmielewski;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Arguments not found!");
            return;
        }

        if (args.length != 5) {
            System.out.println("Program needs five arguments: strategyName strategyParameter inputFileName solutionFileName statisticFileName");
            return;
        }

        String strategyName = args[0];
        String strategyParameter = args[1];
        String inputFileName = args[2];
        String solutionFileName = args[3];
        String statisticFileName = args[4];
        String solution;
        String[] statistic;

        if (!checkArguments(strategyName, strategyParameter, inputFileName, solutionFileName, statisticFileName)) {
            return;
        }

        Frame frame = new Frame(inputFileName);
        switch (strategyName) {
            case "bfs":
                BFS bfs = new BFS(frame, strategyParameter);
                bfs.solve();
                solution = bfs.getSolution();
                statistic = bfs.getStatistic();
                saveSolution(solution, solutionFileName);
                saveStatistic(solution, statistic, statisticFileName);
                break;
            case "dfs":
                DFS dfs = new DFS(frame,strategyParameter);
                dfs.solve();
                solution = dfs.getSolution();
                statistic = dfs.getStatistic();
                saveSolution(solution, solutionFileName);
                saveStatistic(solution, statistic, statisticFileName);
                break;
            case "astr":
                AStar aStar = new AStar(frame,strategyParameter);
                aStar.solve();
                solution = aStar.getSolution();
                statistic = aStar.getStatistic();
                saveSolution(solution, solutionFileName);
                saveStatistic(solution, statistic, statisticFileName);
                break;
        }
    }

    private static void saveSolution(String solution, String solutionFileName) {
        File file = new File(solutionFileName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(solution.length() != 0 ? Integer.toString(solution.length()) : "-1");
            writer.write("\n");
            writer.write(solution);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveStatistic(String solution, String[] statistic, String statisticFileName) {
        File file = new File(statisticFileName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(solution.length() != 0 ? Integer.toString(solution.length()) : "-1");//dlugosc rozwiazania
            writer.write("\n");
            writer.write(statistic[0] + "\n");//stany odwiedzone
            writer.write(statistic[1] + "\n");//nodes - przetworzone
            writer.write(statistic[2] + "\n");//depth
            writer.write(statistic[3] + "\n");//time
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkArguments(String strategyName, String strategyParameter, String inputFileName, String solutionFileName, String informationFileName) {
        String[] strategyValues = {"bfs", "dfs", "astr"};
        String[] astrValues = {"hamm", "manh"};
        if (!Arrays.asList(strategyValues).contains(strategyName)) {
            System.out.println(strategyName + ": strategy not found");
            return false;
        }

        if (((strategyName.equals("bfs") || strategyName.equals("dfs")) && !strategyParameter.matches("[LRUD][LRUD][LRUD][LRUD]"))
                || (strategyName == "astr" && (!Arrays.asList(astrValues).contains(strategyParameter)))) {
            System.out.println(strategyParameter + ": strategyParameter for " + strategyName + " not found");
            return false;
        }

        if (!inputFileName.matches("^[a-zA-Z0-9]+_[0-9]+_[0-9]+.txt$")) {
            System.out.println(inputFileName + ": wrong input file name, correct format: size_depth_id.txt");
            return false;
        }

        if (!solutionFileName.equals(inputFileName.substring(0, inputFileName.indexOf('.')) + "_"
                + strategyName + "_" + strategyParameter.toLowerCase() + "_sol.txt")) {
            System.out.println(solutionFileName + ": wrong solution file name, correct format: size_depth_id_strategyName_strategyParameter_sol.txt");
            return false;
        }

        if (!informationFileName.equals(inputFileName.substring(0, inputFileName.indexOf('.')) + "_"
                + strategyName + "_" + strategyParameter.toLowerCase() + "_stats.txt")) {
            System.out.println(informationFileName + ": wrong information file name, correct format: size_depth_id_strategyName_strategyParameter_stats.txt");
            return false;
        }
        return true;
    }
}
