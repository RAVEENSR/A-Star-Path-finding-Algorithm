/*************************************************************************
 *  Author: Raveen Savinda Rathnayake
 *  IIT ID: 2015358
 *  UoW ID: W1610086
 *  Last update: 02-04-2017
 *************************************************************************/

import java.util.*;

public class AStar {

    //Diagonal cost
    private static int dCost=0;
    //Vertical or Horizontal cost
    private static int verHorCost =0;
    //total final cost
    private static int finalTotalCost=0;
    //Size of the grid ex:NxN
    private static int matrixHSize;
    private static int matrixVSize;
    private static boolean allowDiagonalMove=true;

    //Blocked cells are defined as null values in the grid
    private static Cell [][] grid = new Cell[matrixVSize][matrixHSize];

    private static PriorityQueue<Cell> open;

    private static boolean closed[][];
    //Coordinates of the starting point
    private static int startI;
    private static int startJ;
    //Coordinates of the finishing point
    private static int endI;
    private static int endJ;
    //declaring a private constructor to disable object creation
    private AStar(){}


    private static class Cell{
        //Heuristic cost
        int hCost = 0;
        //G cost
        int gCost = 0;
        //Final cost(G+H)
        int fCost = 0;
        int i;
        int j;
        //stores the parent Cell of a cell
        Cell parent;

        Cell(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }


    //Define a blocked cell
    private static void setBlockedCell(int i, int j){
        grid[i][j] = null;
    }

    //Define the starting cell
    private static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }

    //Define the ending cell
    private static void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }

    /*
     * Checks whether a given cell is blocked cell(null) or a
     * cell which is already checked(closed) and updates the cost
     * if it not a blocked cell
     * @param current = parent cell
     * @param t = Cell which need to check and update
     * @param cost = the cost value
    */
    private static void updateCost(Cell current, Cell t, int tempGCost){
        if(t == null || closed[t.i][t.j])
            return;
        int tFinalCost = t.hCost+tempGCost;

        boolean inOpen = open.contains(t);
        if(!inOpen || tFinalCost<t.fCost){
            t.gCost=tempGCost;
            t.fCost = tFinalCost;
            t.parent = current;
            if(!inOpen)
                open.add(t);
        }
    }

    /*
    * @loop Loops until the end cell is found
    * Checks 8 directions of a cell to determine the final cost.
    *
    */
    private static void aStar(){

        //add the start location to open list.
        open.add(grid[startI][startJ]);

        Cell currentCell;

        while(true){
            currentCell = open.poll();
            if(currentCell==null)
                break;
            closed[currentCell.i][currentCell.j]=true;

            if(currentCell.equals(grid[endI][endJ])){
                return;
            }

            Cell t;

            //Left cell (i-1,j)
            if(currentCell.i-1>=0){
                t = grid[currentCell.i-1][currentCell.j];
                updateCost(currentCell, t, currentCell.gCost+verHorCost);

                //Upper Left cell (i-1,j-1)
                if((currentCell.j-1>=0) && allowDiagonalMove){
                    t = grid[currentCell.i-1][currentCell.j-1];
                    updateCost(currentCell, t, currentCell.gCost+dCost);
                }

                //Down Left cell(i-1,j+1)
                if((currentCell.j+1<grid[0].length) && allowDiagonalMove){
                    t = grid[currentCell.i-1][currentCell.j+1];
                    updateCost(currentCell, t, currentCell.gCost+dCost);
                }
            }
            //Upper cell(i,j-1)
            if(currentCell.j-1>=0){
                t = grid[currentCell.i][currentCell.j-1];
                updateCost(currentCell, t, currentCell.gCost+verHorCost);
            }

            //Down cell(i,j+1)
            if(currentCell.j+1<grid[0].length){
                t = grid[currentCell.i][currentCell.j+1];
                updateCost(currentCell, t, currentCell.gCost+verHorCost);
            }

            //Right cell(i+1,j)
            if(currentCell.i+1<grid.length){
                t = grid[currentCell.i+1][currentCell.j];
                updateCost(currentCell, t, currentCell.gCost+verHorCost);

                //Upper Right cell(i+1,j-1)
                if((currentCell.j-1>=0) && allowDiagonalMove){
                    t = grid[currentCell.i+1][currentCell.j-1];
                    updateCost(currentCell, t, currentCell.gCost+dCost);
                }
                //Down Right cell(i+1,j+1)
                if((currentCell.j+1<grid[0].length) && allowDiagonalMove){
                    t = grid[currentCell.i+1][currentCell.j+1];
                    updateCost(currentCell, t, currentCell.gCost+dCost);
                }
            }
        }
    }

    /*
    * calculates the heuristic value for a cell
    * @param methodNo: The method to calculate the heuristic value
    * @param x, y = Grid's dimensions
    */
    private static void calculateHeuristic(int methodNo,int x, int y){
        finalTotalCost=0;
        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                grid[i][j] = new Cell(i, j);
                switch(methodNo){
                    case 1://Calculates Manhattan value
                        dCost =20;
                        verHorCost =10;
                        allowDiagonalMove=false;
                        grid[i][j].hCost = 10*(Math.abs(i-endI)+Math.abs(j-endJ));
                        break;
                    case 2://Calculates Euclidean value
                        dCost =14;
                        verHorCost =10;
                        allowDiagonalMove=true;
                        int dx=i-endI;
                        int dy=j-endJ;
                        grid[i][j].hCost = (int)(10*(Math.sqrt((dx*dx)+(dy*dy))));
                        break;
                    case 3://Calculates Chebyshev value
                        dCost =10;
                        verHorCost =10;
                        allowDiagonalMove=true;
                        int dx1=Math.abs(i-endI);
                        int dy1=Math.abs(j-endJ);
                        grid[i][j].hCost = 10*(Math.max(dx1,dy1));
                        break;
                    default:System.out.println("Error in Switch case");
                }
            }
        }
    }
    private static void displayMap(int x, int y, int si,int sj,int ei, int ej){
        //Display initial map
        System.out.println("Grid: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                if(i==si&&j==sj)System.out.print("SO  "); //Source
                else if(i==ei && j==ej)System.out.print("DE  ");  //Destination
                else if(grid[i][j]!=null)System.out.printf("%-3d ", 0);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();


        System.out.println("\nScores for cells: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<x;++j){
                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].fCost);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
    * @param methodNo=Method to calculate the cost
    * @param x, y = Grid's dimensions
    * @param si, sj = start location's x and y coordinates
    * @param ei, ej = end location's x and y coordinates
    * @param boolean[][] randomArray = array containing inaccessible cell coordinates
    */
     public static void run(int methodNo, int x, int y, int si, int sj, int ei, int ej,boolean[][] randomArray){

        matrixVSize=x;
        matrixHSize=y;

        grid = new Cell[x][y];
        closed = new boolean[x][y];
        //Initialising the priority queue
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return c1.fCost<c2.fCost?-1:
                    c1.fCost>c2.fCost?1:0;
        });
        //Set start position
        setStartCell(si, sj);

        //Set End position
        setEndCell(ei, ej);

        calculateHeuristic(methodNo,x,y);
        //setting the full cost zero in the starting cell
        grid[si][sj].fCost = 0;

        int N=randomArray.length;
        /*
        * Set blocked cells. Simply set the cell values to null
        * for blocked cells.
        */
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(!randomArray[i][j]){
                    setBlockedCell(i, j);
                }
            }
        }

        aStar();
        displayMap(x,y,si,sj,ei,ej);
        if(closed[endI][endJ]){
            //Trace back the path
            Cell current = grid[endI][endJ];
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledSquare(endJ, x-1-endI, .5);
            pathDrawer(methodNo,current,x);
            System.out.println("\nActual Cost(g cost of the end point) is: "+grid[ei][ej].fCost);
            System.out.println("\nTotal of FCostsa0147 is: "+finalTotalCost);
            System.out.println();
        }else System.out.println("No possible path");
    }

    /*
    * Draws the path according to the coordinates
    * @param methodNo= method which is used to draw the path according to
    * @param current= current cell(this is the final destination of the path)
    * @param x= the size of the grid(Ex:10 for a matrix of 10 x 10)
    */
    private static void pathDrawer(int methodNo,Cell current, int x){
        if(methodNo==2){
            StdDraw.setPenColor(StdDraw.RED);
            int lastI=endI;
            int lastJ=endJ;
            while(current.parent!=null){
                finalTotalCost=finalTotalCost+current.fCost;
                StdDraw.line(current.parent.j,x-1-current.parent.i,lastJ,x-1-lastI);
                lastI=current.parent.i;
                lastJ=current.parent.j;
                current = current.parent;
            }
        }else{
            StdDraw.setPenColor(StdDraw.GREEN);
            while(current.parent!=null){
                finalTotalCost=finalTotalCost+current.fCost;
                StdDraw.filledSquare(current.parent.j, x-1-current.parent.i, .5);
                current = current.parent;
            }
        }
        System.out.println();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledSquare(startJ, x-1-startI, .5);
        StdDraw.setPenColor(StdDraw.BLACK);
    }
}