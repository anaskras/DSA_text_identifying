package Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Nastya on 16.09.2016.
 */
public class Testing {
    public static void main(String[] args) {
        Matrix myMatrix = null;
        try {
            myMatrix = new Matrix(new Scanner(new File("src\\main\\java\\Matrix\\test.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //myMatrix.averageColVector();
        //double[][] matr2 = myMatrix.euclideanDistance();
       // Matrix dist = new Matrix(matr2);
        //System.out.print(dist.toString());
        //System.out.println("Distance between 1 and 3: " + myMatrix.eucDistBetween(1, 3));


        //myMatrix.averageVector();
        //System.out.print(new Matrix(myMatrix.covariationMatrix()));
        System.out.print(new Matrix(myMatrix.MahalanobisDistance()));

        //System.out.println(myMatrix.inverse().toString());
        // (n * (n-1)) / 2 + i * (rows - n) + j - n
    }
}
