package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList <String> population = new ArrayList<String>();
        ArrayList <String> populationIndex = new ArrayList<String>();

        Random random = new Random();

        String [] array;
        array = new String [9];

        String [] children;
        children = new String[18];

        String [] child1;
        child1 = new String[9];
        String [] child2;
        child2 = new String[9];

        String [][] childMatrix1;
        String [][] childMatrix2;

        childMatrix1 = new String[9][9];
        childMatrix2 = new String[9][9];

        int chose;
        int chosenIndex1;
        int chosenIndex2;
        String [] chosen1;
        chosen1 = new String [9];
        String [] chosen2;
        chosen2 = new String [9];

        int getConflicting;
        int minConflicting = 999;

        String [][] parentMatrix1;
        String [][] parentMatrix2;

        parentMatrix1 = new String[9][9];
        parentMatrix2 = new String[9][9];

        for(int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                parentMatrix1[i][j] = "0";
                parentMatrix2[i][j] = "0";
            }
        }

        for (int i=0; i < 8; i++)
        {
            parentMatrix1[i][random.nextInt(8)] = "1";
            parentMatrix2[i][random.nextInt(8)] = "1";

        }

        getConflicting = Conflict(parentMatrix1);
        parentMatrix1[8][8] = Integer.toString(getConflicting);
        minConflicting = getConflicting;

        getConflicting = Conflict(parentMatrix2);
        parentMatrix2[8][8] = Integer.toString(getConflicting);
        if (getConflicting < minConflicting)
        {
            minConflicting = getConflicting;
        }

        array = AddPopulation(parentMatrix1);
        Collections.addAll(population, array);
        populationIndex.add(parentMatrix1[8][8]);

        array = AddPopulation(parentMatrix2);
        Collections.addAll(population, array);
        populationIndex.add(parentMatrix2[8][8]);

        int don =0;
        while (don != 100000) {
/*
        if(((population.size())/9) == 10000)
        {
            Collections.sort(populationIndex);
            String divide;
            divide = populationIndex.get(5000);

            for (int i= 9; i< population.size(); i++)
            {
                if (i % 9 == 0)
                {
                    if (Integer.parseInt(population.get(i-1)) > Integer.parseInt(divide))
                    {
                        int counter = 0;
                        while(counter != 8)
                        {
                            population.remove(((i-1)-counter));
                            counter++;
                        }
                    }
                }
            }

            for (int i = 5000; i< populationIndex.size(); i++)
            {
                populationIndex.remove(i);
            }
        }
*/
        chose = random.nextInt(population.size());
        chosenIndex1 = chose/9;

        chose = random.nextInt(population.size());
        chosenIndex2 = chose/9;

        while (chosenIndex1 == chosenIndex2)
        {
            chose = random.nextInt(population.size());

            chosenIndex2 = chose/9;
        }

        int index = 0;
        for (int i= (chosenIndex1*9); i<((chosenIndex1*9)+8); i++)
        {
            chosen1[index] = population.get(i);
            index++;
        }

        index = 0;
        for (int i= (chosenIndex2*9); i<((chosenIndex2*9)+8); i++)
        {
            chosen2[index] = population.get(i);
            index++;
        }

        children = Cross(chosen1, chosen2);

        int f = 0;
        for (int i=0; i< 18; i++)
        {
            if (i<9)
            {
                child1[i] = children[i];
            }
            else
            {
                child2[f] = children[i];
                f++;
            }
        }

        if (don % 500 == 0)
        {
            child1[random.nextInt(8)] = Integer.toString(random.nextInt(8));
        }

        childMatrix1 = ConvertToMatrix(child1);
        childMatrix2 = ConvertToMatrix(child2);

        getConflicting = Conflict(childMatrix1);
        childMatrix1[8][8] = Integer.toString(getConflicting);
        if (getConflicting <= minConflicting)
        {
            minConflicting = getConflicting;
            array = AddPopulation(childMatrix1);
            Collections.addAll(population, array);
            populationIndex.add(childMatrix1[8][8]);

            if (getConflicting == 0)
            {
                System.out.println();
                System.out.println("BASARILIIIIII !!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println();
                for(int i=0; i<8; i++)
                {
                    for (int j=0; j<8; j++)
                    {
                        System.out.print(childMatrix1[i][j]);
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.print("PULATION SIZE : ");
                System.out.println(population.size()/9);
                System.out.print("DENEME SAYISI : ");
                System.out.println(don);
                return;
            }
        }

        getConflicting = Conflict(childMatrix2);
        childMatrix2[8][8] = Integer.toString(getConflicting);
        if (getConflicting <= minConflicting)
        {
            minConflicting = getConflicting;
            array = AddPopulation(childMatrix2);
            Collections.addAll(population, array);
            populationIndex.add(childMatrix2[8][8]);

            if (getConflicting == 0)
            {
                System.out.println();
                System.out.println("BASARILIIIIII !!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println();
                for(int i=0; i<8; i++)
                {
                    for (int j=0; j<8; j++)
                    {
                        System.out.print(childMatrix2[i][j]);
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.print("POPULATION SIZE : ");
                System.out.println(population.size()/9);
                System.out.print("DENEME SAYISI : ");
                System.out.println(don);
                return;
            }
        }

        don++;
        }

    }

    public static int Conflict (String[][] matrix)
    {
        int conflicting = 0;

        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (matrix[i][j] == "1")
                {
                    for (int k= (i+1); k<8; k++)
                    {
                        if (matrix[k][j] == "1")
                        {
                            conflicting++;
                        }
                    }

                    int l = j;
                    int m = j;

                    for (int k= (i+1); k<8; k++)
                    {
                        l -= 1;
                        m += 1;

                        if (l >= 0)
                        {
                            if (matrix[k][l] == "1")
                            {
                                conflicting++;
                            }
                        }
                        if (m <= 7)
                        {
                            if (matrix[k][m] == "1")
                            {
                                conflicting++;
                            }
                        }
                    }
                }
            }
        }

        System.out.print("CONFLICTING : ");
        System.out.println(conflicting);
        System.out.println();

        return conflicting;
    }

    public static String [] AddPopulation (String[][] matrix)
    {
        String [] array;
        array = new String[9];

        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if (matrix[i][j] == "1")
                {
                    array[i] = Integer.toString(j);
                }
            }
        }
        array[8] = matrix[8][8];

        return array;

    }

    public static String [] Cross (String [] array1, String [] array2)
    {
        String [] array;
        array = new String[18];

        Random random = new Random();

        int splitIndex;

        splitIndex = random.nextInt(7)+1;

        for (int i=0; i< splitIndex; i++)
        {
            array[i] = array1[i];
        }
        for (int i= splitIndex; i<8; i++)
        {
            array[i] = array2[i];
        }

        int index = 9;
        for (int i=splitIndex; i<8; i++)
        {
            array[index] = array1[i];
            index++;
        }
        for (int i= 0; i< splitIndex; i++)
        {
            array[index] = array2[i];
            index++;
        }

        array[8] = "0";
        array[17] = "0";

        return array;
    }

    public static String [][] ConvertToMatrix (String [] array)
    {
        String [][] matrix;
        matrix = new String[9][9];

        for(int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                matrix[i][j] = "0";
                matrix[i][j] = "0";
            }
        }

        for (int i=0; i < 8; i++)
        {
            matrix[i][Integer.parseInt(array[i])] = "1";
        }

        return matrix;
    }


}
