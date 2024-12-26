import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //Proyecto CAPSTONE - Game of Life
        //PARAMETERS
        int width = 0;
        int height = 0;
        int generations = -1;
        int speed = 0;
        String population = "";
        int neighborhood = 3;
        int x = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == 'w') {
                width = parseParameters(args, i);
            } else if (args[i].charAt(0) == 'h') {
                height = parseParameters(args, i);
            } else if (args[i].charAt(0) == 'g') {
                generations = parseParameters(args, i);
            } else if (args[i].charAt(0) == 's') {
                speed = parseParameters(args, i);
            } else if (args[i].charAt(0) == 'p') {
                population = parsePopulation(args, i);
            } else if (args[i].charAt(0) == 'n') {
                neighborhood = parseParameters(args, i);
            } else if (args[i].charAt(0) == 'x') {
                x = parseParameters(args, i);
            }
        }

        validateAllParameters(width, height, generations, speed, population, neighborhood);
        //END PARAMETERS

        //SETUP
        int[][] grid = prepareGrid(width, height, population);
        //END SETUP

        //GAME START
        playGame(grid, generations, neighborhood, speed, x);
        //END GAME

    }

    //SETUP

    static void playGame(int[][] grid, int generations, int neighborhood, int speed, int x){
        if (generations == 0) {

            int generationsAux = 1;

            for (int i = -1; i < generations; i--) {
                drawGrid(grid);

                if (generationsAux == x) {
                    continue;
                }else {
                    grid = applyRules(grid, neighborhood);
                    generationsAux++;
                }

                try {
                    Thread.sleep(speed * 10);
                }catch (Exception e){
                    Thread.currentThread().interrupt();
                }

            }
        } else {


            for (int i = 0; i < generations; i++) {
                drawGrid(grid);

                if (generations == x) {
                    continue;
                }else {
                    grid = applyRules(grid, neighborhood);
                }

                try {
                    Thread.sleep(speed * 10);
                }catch (Exception e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


    static int[][] prepareGrid(int width, int height, String population) {

        int[][] grid = new int[height][width];

        if (population.equals("rnd")) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    Random rand = new Random();
                    grid[i][j] = rand.nextInt(2);
                }
            }
        } else {
            int heightIndex = 0;
            int widthIndex = 0;

            for (int i = 0; i < population.length(); i++) {
                char c = population.charAt(i);
                if (c == '1') {
                    grid[heightIndex][widthIndex] = 1;
                    widthIndex++;
                } else if (c == '0') {
                    widthIndex++;
                } else if (c == '#') {
                    heightIndex++;
                    widthIndex = 0;
                }
            }
        }

        return grid;
    }

    static void drawGrid(int[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------");
    }

    //END SETUP

    //PARSE

    static String parsePopulation(String[] args, int index){
        String auxiliarParameter = "";

        try {
            auxiliarParameter = args[index].substring(2);
        }catch (Error error){
            System.out.println("error = fallo en el parse de la población");
        }

        return auxiliarParameter;
    }


    static int parseParameters(String[] args, int index){
        int verifiedParameter = 0;

        try{
            verifiedParameter = Integer.parseInt(args[index].substring(2));
        }catch(Error error){
            System.out.println("error = fallo en el parseo");
        }

        return verifiedParameter;
    }

    //END PARSE

    //VALIDATIONS

    static void validateWidth(int parameter) {
        if (parameter == 10 || parameter == 20 || parameter == 40 || parameter == 80) {
            System.out.println("width = [" + parameter + "]");
        } else if (parameter == 0) {
            System.out.println("width = [No presente]");
        } else {
            System.out.println("width = [Invalido]");
        }
    }


    static void validateHeight(int parameter) {
        if (parameter == 10 || parameter == 20 || parameter == 40) {
            System.out.println("height = [" + parameter + "]");
        }else if(parameter == 0){
            System.out.println("height = [No presente]");
        }else {
            System.out.println("height = [Invalido]");
        }
    }


    static void validateGenerations(int parameter){
        if (parameter >= 0) {
            System.out.println("generations = [" + parameter + "]");
        }else if(parameter == -1){
            System.out.println("generations = [No presente]");
        }else{
            System.out.println("generations = [Invalido]");
        }
    }


    static void validateSpeed(int parameter){
        if (parameter >= 250 && parameter <= 1000) {
            System.out.println("speed = [" + parameter + "]");
        }else if(parameter == 0){
            System.out.println("speed = [No presente]");
        }else{
            System.out.println("speed = [Invalido]");
        }
    }


    static void validatePopulation(String parameter, int width, int height) {
        boolean validationFlag = true;

        for (int i = 0; i < parameter.length(); i++) {
            char validCharacter = parameter.charAt(i);
            if (validCharacter != '1' && validCharacter != '0' && validCharacter != '#' && validCharacter != 'r' && validCharacter != 'n' && validCharacter != 'd') {
                validationFlag = false;
            }
        }

        int heightCounter = 0;
        int widthCounter = 0;

        for (int i = 0; i < parameter.length(); i++) {
            char rowsAndColumnsCounter = parameter.charAt(i);

            if (rowsAndColumnsCounter == '1') {
                widthCounter++;
            } else if (rowsAndColumnsCounter == '0') {
                widthCounter++;
            } else if (rowsAndColumnsCounter == '#') {
                heightCounter++;
                widthCounter = 0;
            }
        }

        if (heightCounter >= height || widthCounter >= width || !validationFlag) {
            System.out.println("population = [Invalido]");
        } else {
            if (parameter.equals("")) {
                System.out.println("population = [No presente]");
            } else if (parameter.equals("rnd")) {
                System.out.println("population = [" + parameter + "]");
            } else {
                System.out.println("population = [" + parameter + "]");
            }
        }
    }

    static void validateNeighborhood(int parameter){
        if (parameter > 0 && parameter <= 5) {
            System.out.println("neighborhood = [" + parameter + "]");
        }else {
            System.out.println("neighborhood = [Invalido]");
        }
    }


    static void validateAllParameters(int width, int height, int generations, int speed, String population, int neighborhood){

        validateWidth(width);
        validateHeight(height);
        validateGenerations(generations);
        validateSpeed(speed);
        validatePopulation(population, width, height);
        validateNeighborhood(neighborhood);

        if (width == 0 || height == 0 || generations == -1 || speed == 0 || population.equals("")) {
            System.out.println("Alguno de los parametros no es valido, revise su ingreso y vuelva a intentarlo");
            System.exit(0);
        }
    }

    //END VALIDATIONS

    //METHODS

    static int[][] applyRules(int[][] grid, int neighborhood){

        int[][] auxiliarGrid = new int[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                auxiliarGrid[i][j] = grid[i][j];
            }
        }

        for (int columns = 0; columns < grid.length; columns++) {
            for (int rows = 0; rows < grid[columns].length; rows++) {
                int neighbourNumber = countNeighbors(grid, rows, columns, neighborhood);

                if (grid[columns][rows] == 1) {
                    if (neighbourNumber < 2 || neighbourNumber > 3) {
                        auxiliarGrid[columns][rows] = 0;
                    }else{
                        auxiliarGrid[columns][rows] = 1;
                    }
                } else if (neighbourNumber == 3) {
                    auxiliarGrid[columns][rows] = 1;
                }
            }
        }

        return auxiliarGrid;
    }



    static int countNeighbors(int[][] grid, int row, int column, int neighborhood){
        int neighbourCounter = 0;

        switch(neighborhood){
            case 1:
                if(column - 1 >= 0) {
                    if (grid[column - 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if(row - 1 >= 0){
                    if (grid[column][row - 1] == 1) {
                        neighbourCounter++;
                    }
                }

                if (column + 1 < grid.length) {
                    if (grid[column + 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if (row + 1 < grid[column].length) {
                    if (grid[column][row + 1] == 1) {
                        neighbourCounter++;
                    }
                }
                break;

            case 2:

                if(column - 1 >= 0) {
                    if (grid[column - 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if(row - 1 >= 0){
                    if (grid[column][row-1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column - 1 >= 0 && row - 1 >= 0) {
                    if (grid[column-1][row-1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length) {
                    if (grid[column + 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if (row + 1 < grid[column].length) {
                    if (grid[column][row + 1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length && row + 1 < grid[column].length) {
                    if (grid[column+1][row+1] == 1) {
                        neighbourCounter++;
                    }
                }

                break;

            case 3:

                if(column - 1 >= 0) {
                    if (grid[column - 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if(row - 1 >= 0){
                    if (grid[column][row-1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column - 1 >= 0 && row - 1 >= 0) {
                    if (grid[column-1][row-1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column - 1 >= 0 && row + 1 < grid[column].length) {
                    if (grid[column-1][row+1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length) {
                    if (grid[column + 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if (row + 1 < grid[column].length) {
                    if (grid[column][row + 1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length && row + 1 < grid[column].length) {
                    if (grid[column+1][row+1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length && row - 1 >= 0) {
                    if (grid[column+1][row-1] == 1) {
                        neighbourCounter++;
                    }
                }

                break;

            case 4:

                if (column - 1 >= 0 && row - 1 >= 0) {
                    if (grid[column-1][row-1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column - 1 >= 0 && row + 1 < grid[column].length) {
                    if (grid[column-1][row+1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length && row + 1 < grid[column].length) {
                    if (grid[column+1][row+1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length && row - 1 >= 0) {
                    if (grid[column+1][row-1] == 1) {
                        neighbourCounter++;
                    }
                }

                break;

            case 5:

                if(column - 1 >= 0) {
                    if (grid[column - 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column - 1 >= 0 && row - 1 >= 0) {
                    if (grid[column-1][row-1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column - 1 >= 0 && row + 1 < grid[column].length) {
                    if (grid[column-1][row+1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length) {
                    if (grid[column + 1][row] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length && row + 1 < grid[column].length) {
                    if (grid[column+1][row+1] == 1) {
                        neighbourCounter++;
                    }
                }
                if (column + 1 < grid.length && row - 1 >= 0) {
                    if (grid[column+1][row-1] == 1) {
                        neighbourCounter++;
                    }
                }

                break;


        }

        return neighbourCounter;

    }
}
