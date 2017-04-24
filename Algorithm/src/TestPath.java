import java.util.Scanner;

/*************************************************************************
 *  Author: Raveen Savinda Rathnayake
 *  IIT ID: 2015358
 *  UoW ID: W1610086
 *  Last update: 02-04-2017
 *************************************************************************/
public class TestPath {

    public static void main(String[] args){
        //the size of the grid
        int size=10;
        // The following will generate a size X size squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = PathFindingOnSquaredGrid.random(size, 0.6);

        StdArrayIO.print(randomlyGenMatrix);
        PathFindingOnSquaredGrid.show(randomlyGenMatrix, true);

        System.out.println();
        System.out.println("The system percolates: " + PathFindingOnSquaredGrid.percolates(randomlyGenMatrix));

        System.out.println();
        System.out.println("The system percolates directly: " + PathFindingOnSquaredGrid.percolatesDirect(randomlyGenMatrix));
        System.out.println();

        // Reading the coordinates for points A and B on the input squared grid.
        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int bj = in.nextInt();
        System.out.println();

        boolean flag=true;
        int number;
        while(flag) {
            System.out.println("-------------------------------------------");
            System.out.println("Select the method to find the cost");
            System.out.println("1: Manhattan");
            System.out.println("2: Euclidean");
            System.out.println("3: Chebyshev");
            System.out.println("4: Exit");
            System.out.print("Enter number: ");
            try{
                number = in.nextInt();
                if(number>0 && number<4) {
                    StdDraw.clear();

                    PathFindingOnSquaredGrid.show(randomlyGenMatrix, true, ai, aj, bi, bj);
                    Stopwatch timerFlow = new Stopwatch();
                    AStar.run(number,size, size,ai,aj,bi,bj,randomlyGenMatrix);
                    StdOut.println("Elapsed time = " + timerFlow.elapsedTime());

                }else if(number==4){
                    System.exit(0);
                }else{
                    System.out.println("Invalid number!");
                }
            }catch(Exception e){
                flag=false;
                System.out.println("Invalid number!");
            }
            System.out.println();
            System.out.println("-------------------------------------------");
            System.out.println();

        }
    }
}
