package Matrix;

import java.lang.reflect.Array;
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

    private double[][] averageVector;
    private boolean needToCalcAverVect;

    private double[][] covariationMartix;
    private boolean needToCalcCovMatr;

    private double[][] euclideanDistances;
    private double maxEuclDist;
    private boolean needToCalcEuclDist;

    private double determinant;
    private boolean needToCalcDet;

    public Matrix(double[][] matr) {
        this.matr = matr;
        rows = matr.length;
        cols = matr[0].length;
        needToCalcDet = true;
        needToCalcEuclDist = true;
        euclideanDistances = null;
        maxEuclDist = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covariationMartix = null;
    }

    public Matrix(Scanner inputStream){
        rows = inputStream.nextInt();
        cols = inputStream.nextInt();
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
        maxEuclDist = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covariationMartix = null;
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
        maxEuclDist = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covariationMartix = null;
    }

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
        maxEuclDist = 0;
        needToCalcAverVect = true;
        averageVector = null;
        needToCalcCovMatr = true;
        covariationMartix = null;
    }

    public Matrix transp() {
        double[][] matr2 = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matr2[j][i] = matr[i][j];
            }
        }
        return new Matrix(matr2);
    }

    public double[][] inverse() {
        double[][] E = new double[cols][cols];
        for (int i = 0; i < cols; i++)
            E[i][i] = 1;

        double temp;
        for (int k = 0; k < cols; k++) {
            int p = k;
            temp = matr[k][k];
            while (temp == 0) {
                ++p;
                swapLines(matr, k, p);
                temp = matr[k][k];
            }
            for (int j = 0; j < cols; j++) {
                matr[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < cols; i++) {
                temp = matr[i][k];

                for (int j = 0; j < cols; j++) {
                    matr[i][j] -= matr[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int k = cols - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = matr[i][k];

                for (int j = 0; j < cols; j++) {
                    matr[i][j] -= matr[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        return E;
    }

    public double[][] inverseCovariationMatrix() {
        if (!needToCalcCovMatr) {
            double[][] covariation = new double[cols][cols];
            for (int i = 0; i < cols; i++) {
                covariation[i] = covariationMartix[i].clone();
            }

            double[][] E = new double[cols][cols];
            for (int i = 0; i < cols; i++)
                E[i][i] = 1;

            double temp;
            for (int k = 0; k < cols; k++) {
                int p = k;
                temp = covariation[k][k];
                while (temp == 0) {
                    ++p;
                    swapLines(covariation, k, p);
                    temp = covariation[k][k];
                }
                for (int j = 0; j < cols; j++) {
                    covariation[k][j] /= temp;
                    E[k][j] /= temp;
                }

                for (int i = k + 1; i < cols; i++) {
                    temp = covariation[i][k];

                    for (int j = 0; j < cols; j++) {
                        covariation[i][j] -= covariation[k][j] * temp;
                        E[i][j] -= E[k][j] * temp;
                    }
                }
            }
            for (int k = cols - 1; k > 0; k--) {
                for (int i = k - 1; i >= 0; i--) {
                    temp = covariation[i][k];

                    for (int j = 0; j < cols; j++) {
                        covariation[i][j] -= covariation[k][j] * temp;
                        E[i][j] -= E[k][j] * temp;
                    }
                }
            }
            return E;
        } else
            return null;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
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

    public Matrix sub(Matrix matr2) {
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

    //vector multiplication
    public Matrix mult(Matrix matr2) {
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
                    return mult(matr2);
                } else {
                    return null;
                }
            } else if (matr2.getRows() == 1) {
                if (cols == matr2.getCols()) {
                    return mult(matr2.transp());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (cols == 1) {
            if (matr2.getCols() == 1) {
                if (rows == matr2.getRows()) {
                    return transp().mult(matr2);
                } else {
                    return null;
                }
            } else if (matr2.getRows() == 1) {
                if (rows == matr2.getCols()) {
                    return matr2.mult(this);
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

    private void swapLines(double[][] matrix, int i, int j) {
        double[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
    }

    //Gauss
    public double det() {
        if (needToCalcDet) {
            double det = 1;
            int sign = 1;
            double[][] matr2 = matr.clone();
            for (int i = 0; i < rows; i++) {
                double max = abs(matr2[i][i]);
                int max_id = i;
                for (int j = i + 1; j < rows; j++)
                    if (abs(matr2[j][i]) > max) {
                        max = abs(matr2[j][i]);
                        max_id = j;
                    }
                if (max_id != i) {
                    swapLines(matr2, max_id, i);
                    sign *= -1;
                }
                for (int k = i + 1; k < rows; k++) {
                    for (int j = i + 1; j < rows; j++)
                        matr2[k][j] -= matr2[k][i] / matr2[i][i] * matr2[i][j];
                    matr2[k][i] = 0;
                }
                det *= matr2[i][i];
            }
            determinant = det * sign;
            needToCalcDet = false;
            return det * sign;
        } else {
            return determinant;
        }
    }

    public double firstNorm(boolean vector) {
        double norm = 0;
        if (!vector) {
            for (int i = 0; i < rows; ++i) {
                double sum = 0;
                for (int j = 0; j < cols; j++) {
                    sum += abs(matr[i][j]);
                }
                if (sum > norm) {
                    norm = sum;
                }
            }
            return norm;
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++)
                    norm += matr[i][j] * matr[i][j];
            }
            return Math.sqrt(norm);
        }
    }

    public double[][] euclideanSquareDistance() {
        if (needToCalcEuclDist) {
            euclideanDistances = new double[rows][rows];
            maxEuclDist = 0;
            if (rows > 1) {
                for (int i = 0; i < rows; i++)
                    for (int j = i + 1; j < rows; j++) {
                        for (int k = 0; k < cols; k++) {
                            euclideanDistances[i][j] += (matr[i][k] - matr[j][k]) * (matr[i][k] - matr[j][k]);
                        }
                        if (euclideanDistances[i][j] > maxEuclDist)
                            maxEuclDist = euclideanDistances[i][j];
                        euclideanDistances[j][i] = euclideanDistances[i][j];
                    }
            } else {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++)
                        euclideanDistances[0][0] += matr[i][j] * matr[i][j];
                    maxEuclDist = euclideanDistances[0][0];
                }
            }
            needToCalcEuclDist = false;
        }
        return euclideanDistances;
    }

    public double[][] averageVector() {
        if (needToCalcAverVect) {
            averageVector = new double[1][cols];
            for (int j = 0; j < cols; j++) {
                for (int i = 0; i < rows; i++)
                    averageVector[0][j] += matr[i][j];
                averageVector[0][j] /= rows;
            }
            needToCalcAverVect = false;
        }
        return averageVector;
    }

    public double[][] covariationMatrix() {
        if (!needToCalcAverVect && needToCalcCovMatr) {
            //Matrix result = new Matrix(cols);
            covariationMartix = new double[cols][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = i; j < rows; j++) {
                    for (int k = 0; k < cols; k++) {
                        covariationMartix[i][j] += (matr[i][k] - averageVector[0][k]) * (matr[j][k] - averageVector[0][k]);
                    }
                    covariationMartix[j][i] = covariationMartix[i][j];
                }
            }
            for (int i = 0; i < cols; i++)
                for (int j = 0; j < cols; j++)
                    covariationMartix[i][j] /= (rows - 1);
            needToCalcCovMatr = false;
            return covariationMartix;
        } else if (needToCalcAverVect)
            return null;
        return covariationMartix;
    }

    /*public double[][] MahalanobisDistance() {
        if (needToCalcCovMatr)
            this.covariationMatrix();
        double[][] invCov = this.inverseCovariationMatrix();
        double[][] result = new double[cols][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                double[] temp = new double[cols];
                for (int p = 0; p < invCov.length; p++) {
                    for (int k = 0; k < invCov[0].length; k++) {     //invCov[0].length == cols
                        temp[p] += (matr[i][k] - matr[j][k]) * invCov[k][p];
                    }
                }
                for (int k = 0; k < invCov[0].length; k++) {
                    result[i][j] += temp[k] * (matr[i][k] - matr[j][k]);
                }
            }
        }
        return result;
    }*/

    public double[][] MahalanobisDistance() {
        if (needToCalcCovMatr)
            this.covariationMatrix();
        double[][] invCov = this.inverseCovariationMatrix();
        double[][] result = new double[cols][cols];
        for (int i = 0; i< rows;i++) {
            for (int j = i + 1; j < rows; j++) {

                double[] diff = new double[cols];
                for (int k = 0; k < cols; k++) {
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
