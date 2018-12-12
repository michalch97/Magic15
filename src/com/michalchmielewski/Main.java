package com.michalchmielewski;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Arguments not found!");
            return;
        }

        if (args.length != 5) {
            System.out.println("Program needs five arguments: strategyName strategyParameter inputFileName solutionFileName informationFileName");
            return;
        }

        String strategyName = args[0];
        String strategyParameter = args[1];
        String inputFileName = args[2];
        String solutionFileName = args[3];
        String informationFileName = args[4];

        if(!checkArguments(strategyName,strategyParameter,inputFileName,solutionFileName,informationFileName)){
            return;
        }
    }

    private static boolean checkArguments(String strategyName, String strategyParameter, String inputFileName, String solutionFileName, String informationFileName){
        String[] strategyValues = {"bfs","dfs","astr"};
        String[] astrValues = {"hamm","manh"};
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
