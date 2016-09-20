package Matrix;

import Jama.CholeskyDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by tokar on 04.09.2016.
 */
public class Matrix {
    private double[][] matr;
    private int rows;
    private int cols;

    private double[] averageVector;
    private boolean needToCalcAverVect;

    private double[][] covarianceMartix;
    private boolean needToCalcCovMatr;

    private double[] euclideanDistances;
    private double euclDiametr;
    private boolean needToCalcEuclDist;

    private double[] euclideanDistancesToAverVect;
    private double euclDiametrToAverVect;
    private boolean needToCalcEuclDistToAverVect;

    private double determinant;
    private boolean needToCalcDet;


    public Matrix(double[][] matrix) {
        this.matr = matrix;
        rows = matr.length;
        cols = matr[0].length;
        needToCalcDet = true;
        needToCalcEuclDist = true;
        euclideanDistances = null;
        euclDiametr = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covarianceMartix = null;
        euclideanDistancesToAverVect = null;
        euclDiametrToAverVect = 0;
        needToCalcEuclDistToAverVect = true;
    }

    public Matrix(Scanner inputStream) {
        rows = inputStream.nextInt();
        cols = inputStream.nextInt();
        matr = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matr[i][j] = inputStream.nextDouble();
            }
        }
        needToCalcDet = true;
        needToCalcEuclDist = true;
        euclideanDistances = null;
        euclDiametr = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covarianceMartix = null;
        euclideanDistancesToAverVect = null;
        euclDiametrToAverVect = 0;
        needToCalcEuclDistToAverVect = true;
    }

    public Matrix(int rows, int cols, Scanner inputStream) {
        matr = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matr[i][j] = inputStream.nextDouble();
            }
        }
        this.rows = rows;
        this.cols = cols;
        needToCalcDet = true;
        needToCalcEuclDist = true;
        euclideanDistances = null;
        euclDiametr = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covarianceMartix = null;
        euclideanDistancesToAverVect = null;
        euclDiametrToAverVect = 0;
        needToCalcEuclDistToAverVect = true;
    }

    /**
     * @param N Ones matrix
     */
    public Matrix(int N) {
        matr = new double[N][N];
        for (int i = 0, j = 0; i < N; i++, j++) {
            matr[i][j] = 1;
        }
        cols = N;
        rows = N;
        determinant = 1;
        needToCalcDet = false;
        needToCalcEuclDist = true;
        euclideanDistances = null;
        euclDiametr = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covarianceMartix = null;
        euclideanDistancesToAverVect = null;
        euclDiametrToAverVect = 0;
        needToCalcEuclDistToAverVect = true;
    }

    private double[][] inverse(double[][] m) {
        double[][] E = new double[cols][cols];
        for (int i = 0; i < cols; i++)
            E[i][i] = 1;
        double[][] matr2 = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++){
            matr2[i] = m[i].clone();
        }
        double temp;
        for (int k = 0; k < cols; k++) {
            temp = matr2[k][k];
            double maxInCol = abs(temp);
            int maxRowID = k;
            for (int i = k + 1; i < rows; i++) {
                if (abs(matr2[i][k]) > maxInCol) {
                    maxInCol = abs(matr2[i][k]);
                    maxRowID = i;
                }
            }
            if (maxRowID != k) {
                swapLines(matr2, k, maxRowID);
                swapLines(E, k, maxRowID);
                temp = matr2[k][k];
            }
            for (int j = 0; j < cols; j++) {
                matr2[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < cols; i++) {
                temp = matr2[i][k];
                for (int j = 0; j < cols; j++) {
                    matr2[i][j] -= matr2[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int k = cols - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = matr2[i][k];
                for (int j = 0; j < cols; j++) {
                    matr2[i][j] -= matr2[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        return E;
    }

    //Matrix multiplication
    private double[][] multiply(double[][] m1, double[][] m2) {
        if (m1[0].length != m2.length) {
            return null;
        } else {
            double[][] ans = new double[m1.length][m2[0].length];
            for (int i = 0; i < m1.length; i++) {
                for (int j = 0; j < m2[0].length; j++) {
                    for (int k = 0; k < m1[0].length; k++) {
                        ans[i][j] += m1[i][k] * m2[k][j];
                    }
                }
            }
            return ans;
        }
    }

    private double[] multiply(double[] v, double[][] m) {
        if (v.length != m.length) {
            return null;
        } else {
            double[] ans = new double[m[0].length];
            for (int j = 0; j < ans.length; j++) {
                for (int k = 0; k < v.length; k++) {
                    ans[j] += v[k] * m[k][j];
                }
            }
            return ans;
        }
    }

    private double[] multiply(double[][] m, double[] v) {
        if (m[0].length != v.length) {
            return null;
        } else {
            double[] ans = new double[m.length];
            for (int i = 0; i < m.length; i++) {
                for (int k = 0; k < cols; k++) {
                    ans[i] += m[i][k] * v[k];
                }
            }
            return ans;
        }
    }

    private double[][] multiply(double[] v1, double[] v2) {
        double[][] ans = new double[v1.length][v2.length];
        for (int i = 0; i < v1.length; i++) {
            for (int j = 0; j < v2.length; j++) {
                ans[i][j] += v1[i] * v2[j];
            }
        }
        return ans;
    }

    private double scalarProduct(double[] v1, double[] v2) {
        double ans = 0;
        if (v1.length != v2.length) {
            System.out.print("scalarProduct error");
            System.exit(-1);
        }
        for (int i = 0; i < v1.length; i++) {
            ans += v1[i] * v2[i];
        }
        return ans;
    }

    private void swapLines(double[][] matrix, int i, int j) {
        double[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
    }

    //Gauss
    private double det(double[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return 0;
        } else {
            double det = 1;
            int sign = 1;
            int N = matrix.length;
            double[][] m = matrix.clone();
            for (int i = 0; i < N; i++) {
                double max = abs(m[i][i]);
                int max_id = i;
                for (int j = i + 1; j < N; j++) {
                    if (abs(m[j][i]) > max) {
                        max = abs(m[j][i]);
                        max_id = j;
                    }
                }
                if (max_id != i) {
                    swapLines(m, max_id, i);
                    sign *= -1;
                }
                for (int k = i + 1; k < N; k++) {
                    for (int j = i + 1; j < N; j++) {
                        m[k][j] -= m[k][i] / m[i][i] * m[i][j];
                    }
                    m[k][i] = 0;
                }
                det *= m[i][i];
            }
            if (abs(det) > 9.336808689942024E-20)
                return det * sign;
            else return 0;
        }
    }

    private double[][] transpose(double[][] matrix) {
        double[][] m = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                m[j][i] = matrix[i][j];
            }
        }
        return m;
    }

    private double[][] add(double[][] m1, double[][] m2) {
        if ((m1.length != m2.length) || (m1[0].length != m2[0].length)) {
            return null;
        } else {
            int r = m1.length;
            int c = m1[0].length;
            double[][] ans = new double[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    ans[i][j] = m1[i][j] + m2[i][j];
                }
            }
            return ans;
        }
    }

    private double[][] subtract(double[][] m1, double[][] m2) {
        if ((m1.length != m2.length) || (m1[0].length != m2[0].length)) {
            return null;
        } else {
            int r = m1.length;
            int c = m1[0].length;
            double[][] ans = new double[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    ans[i][j] = m1[i][j] - m2[i][j];
                }
            }
            return ans;
        }
    }

    public Matrix transpose() {
        return new Matrix(transpose(matr));
    }

    public double[][] inverse() {
        return inverse(matr);
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public double[] getRow(int i){
        return matr[i];
    }

    public double[] getCol(int j) {
        double[] result = new double[matr[0].length];
        for (int i = 0; i < result.length; i++){
            result[i] = matr[i][j];
        }
        return result;
    }

    public double get(int row, int col) {
        return matr[row][col];
    }

    public Matrix add(Matrix matr2) {
        if ((cols != matr2.getCols()) || (rows != matr2.getRows())) {
            return null;
        } else {
            double[][] ans = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    ans[i][j] = matr[i][j] + matr2.get(i, j);
                }
            }
            return new Matrix(ans);
        }
    }

    public Matrix subtract(Matrix matr2) {
        if ((cols != matr2.getCols()) || (rows != matr2.getRows())) {
            return null;
        } else {
            double[][] ans = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    ans[i][j] = matr[i][j] - matr2.get(i, j);
                }
            }
            return new Matrix(ans);
        }
    }

    //Matrix multiplication
    public Matrix multiply(Matrix matr2) {
        if (cols != matr2.getRows()) {
            return null;
        } else {
            needToCalcDet = true;
            double[][] ans = new double[rows][matr2.getCols()];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < matr2.getCols(); j++) {
                    for (int k = 0; k < cols; k++) {
                        ans[i][j] += matr[i][k] * matr2.get(k, j);
                    }
                }
            }
            return new Matrix(ans);
        }
    }

    public Matrix scalarProduct(Matrix matr2) {
        if (rows == 1) {
            if (matr2.getCols() == 1) {
                if (cols == matr2.getRows()) {
                    return multiply(matr2);
                } else {
                    return null;
                }
            } else if (matr2.getRows() == 1) {
                if (cols == matr2.getCols()) {
                    return multiply(matr2.transpose());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (cols == 1) {
            if (matr2.getCols() == 1) {
                if (rows == matr2.getRows()) {
                    return transpose().multiply(matr2);
                } else {
                    return null;
                }
            } else if (matr2.getRows() == 1) {
                if (rows == matr2.getCols()) {
                    return matr2.multiply(this);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        String out = rows + " " + cols + System.lineSeparator();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                out += matr[i][j] + " ";
            }
            out += System.lineSeparator();
        }
        return out;
    }


    public double det() {
        if (needToCalcDet) {
            determinant = det(matr);
            needToCalcDet = false;
        }
        return determinant;
    }

    /**
     * @return if (i != j) double[i][j] - distance between i and j rows
     */
    public double[] euclideanSquareDistance() {
        if (needToCalcEuclDist) {
            euclideanDistances = new double[rows * (rows - 1) / 2];
            euclDiametr = 0;
            int p = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = i + 1; j < rows; j++) {
                    for (int k = 0; k < cols; k++) {
                        euclideanDistances[p] += (matr[i][k] - matr[j][k]) * (matr[i][k] - matr[j][k]);
                    }
                    if (euclideanDistances[p] > euclDiametr) {
                        euclDiametr = euclideanDistances[p];
                    }
                    p++;
                }

            }
            needToCalcEuclDist = false;
        }
        return euclideanDistances;
    }

    public double[] euclideanDistance() {
        if (needToCalcEuclDist) {
            euclideanSquareDistance();
        }
        double[] result = euclideanDistances.clone();
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.sqrt(result[i]);
        }

        return result;
    }

    public double eucDistBetween(int i, int j){
        int n = i + 2;
        return Math.sqrt(euclideanDistances[n * (n - 1) / 2 + i * (euclideanDistances.length - n) + j - n]);
    }

    public static double euclideanSquareDistance(double[] myVector, double[] averageVector) {
        double euclideanDistance = 0;
        for (int i = 0; i < myVector.length; i++) {
            euclideanDistance += (myVector[i] - averageVector[i]) * (myVector[i] - averageVector[i]);
        }
        return euclideanDistance;
    }

    public static double euclidianDistance(double[] myVector, double[] averageVector){
        return Math.sqrt(euclideanSquareDistance(myVector, averageVector));
    }

    public double[] euclideanSquareDistToAverVect() {
        if (needToCalcEuclDistToAverVect) {
            euclideanDistancesToAverVect = new double[rows];
            euclDiametrToAverVect = 0;
            for (int i = 0; i < rows; i++) {
                for (int k = 0; k < cols; k++) {
                    euclideanDistancesToAverVect[i] += (matr[i][k] - averageVector[k]) * (matr[i][k] - averageVector[k]);
                }
                if (euclideanDistancesToAverVect[i] > euclDiametrToAverVect) {
                    euclDiametrToAverVect = euclideanDistancesToAverVect[i];
                }
            }
        }
        needToCalcEuclDistToAverVect = false;
        return euclideanDistancesToAverVect;
    }

    public double[] euclidianDistToAverVect(){
        if (needToCalcEuclDistToAverVect){
            euclideanSquareDistToAverVect();
        }
        double [] eucl  = euclideanDistancesToAverVect.clone();
        for (int i = 0; i < eucl.length; i++){
            eucl[i] = Math.sqrt(eucl[i]);
        }
        return eucl;
    }
    public double getSquareEuclDiametrToAverVect() {
        if (needToCalcEuclDistToAverVect) {
            euclideanSquareDistToAverVect();
        }
        return euclDiametrToAverVect;
    }
    public double getEuclDiametrToAverVect() {
        if (needToCalcEuclDistToAverVect) {
            euclideanSquareDistToAverVect();
        }
        return Math.sqrt(euclDiametrToAverVect);
    }

    public double[] averageVector() {
        if (needToCalcAverVect) {
            averageVector = new double[cols];
            for (int j = 0; j < cols; j++) {
                for (int i = 0; i < rows; i++)
                    averageVector[j] += matr[i][j];
                averageVector[j] /= rows;
            }
            needToCalcAverVect = false;
        }
        return averageVector;
    }

    private double covariance(int first, int second){
        double cov = 0;
        for (int i = 0; i < matr.length; i++){
            cov += matr[i][first] * matr[i][second];
        }
        cov /= matr.length;
        return cov;
    }
    private double covariance(double [][] matrix, int first, int second){
        double cov = 0;
        for (int i = 0; i < matrix.length; i++){
            cov += matrix[i][first] * matrix[i][second];
        }
        if (matrix.length > 1) {
            cov /= (matrix.length - 1);
        } else {
            cov /= matrix.length;
        }
        return cov;
    }

    public double[][] covarianceMatrix() {
        /*if (needToCalcAverVect) {
            this.averageVector();
        }*/
        /*System.out.println("built in cov matrix:");
        RealMatrix cov = new Covariance(matr).getCovarianceMatrix();
        System.out.print(new Matrix(cov.getData()));*/
        //System.out.println("built in invcov matrix:");
       // System.out.print(new Matrix( new LUDecomposition(cov).getSolver().getInverse().getData()));
        if (needToCalcCovMatr) {
            /*covarianceMartix = new double[cols][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = i; j < rows; j++) {
                    for (int k = 0; k < cols; k++) {
                        covarianceMartix[i][j] += (matr[i][k] - averageVector[k]) * (matr[j][k] - averageVector[k]);
                    }
                    covarianceMartix[j][i] = covarianceMartix[i][j];
                }
            }
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < cols; j++) {
                    covarianceMartix[i][j] /= (rows - 1);
                }
            }*/

            //creating the copy of 'matr'
            double[][] copyMatr = new double[rows][];
            for (int i = 0; i < rows; i++){
                copyMatr[i] = matr[i].clone();
            }

            double[] mean = new double[matr[0].length];
            //Arrays.fill(mean, 0);
            //calculating the mean (average vector)
            for (int i = 0; i < copyMatr.length; i++){
                for (int j = 0; j < copyMatr[i].length; j++){
                    mean[j] += copyMatr[i][j];
                }
            }

            for (int i = 0; i < mean.length; i++){
                mean[i] /= copyMatr.length;
            }

            //extracting the mean
            for (int i = 0; i < copyMatr.length; i++){
                for (int j = 0; j < copyMatr[i].length; j++){
                    copyMatr[i][j] -= mean[j];
                }
            }

            //double[][] covMatrix = new double[copyMatr[0].length][copyMatr[0].length];
            covarianceMartix = new double[copyMatr[0].length][copyMatr[0].length];
            for (int i = 0; i < covarianceMartix.length; i++){
                for(int j = 0; j < covarianceMartix.length; j++){
                    covarianceMartix[i][j] = covariance(copyMatr,i, j);
                }
            }

            needToCalcCovMatr = false;
        }
        return covarianceMartix;
    }

    public double[][] MahalanobisDistance() {
        if (needToCalcCovMatr) {
            this.covarianceMatrix();
        }

        double[][] result = new double[cols][cols];
        double[][] ones = new double[cols][cols];
        for (int i = 0; i < ones.length; i++){
            ones[i][i] = 1;
        }
        //CholeskyDecomposition chol = new CholeskyDecomposition(new Jama.Matrix(covarianceMartix));
        //Jama.Matrix oness = new Jama.Matrix(ones);
        //Jama.Matrix invCo = chol.solve(oness);
        //double[][] invCov = chol.solve(oness).getArray();
        double [][] invCov = inverse(covarianceMartix);
        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < rows; j++) {

                double[] diff = new double[cols];
                for (int k = 0; k < cols; k++) {      //difference between matr[i] and matr[j]
                    diff[k] = matr[i][k] - matr[j][k];
                }

                double[] ans = new double[cols];


                for (int p = 0; p < cols; p++) {
                    for (int k = 0; k < cols; k++) {
                        ans[p] += diff[k] * invCov[k][p];
                    }
                }

                for (int k = 0; k < cols; k++) {
                    result[i][j] += diff[k] * ans[k];
                }
                result[i][j] = Math.sqrt(result[i][j]);
                result[j][i] = result[i][j];
            }
        }
        return result;
    }

}
