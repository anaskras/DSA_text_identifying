package library;

import Matrix.Matrix;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Nastya on 20.09.2016.
 */
public class AnalysisOfSamples {
    public Vector<Integer> getAuthorsBySamples (ArrayList<Matrix> authorMatrix, Matrix sampleTests){
        //create an array where we will keep authors for samples
        Vector<Integer> result = new Vector<Integer>(sampleTests.getCols());
        for (int i = 0; i < sampleTests.getRows(); i++){
            double minDist = 0;
            for (int j = 0; j < authorMatrix.size(); j++){
                double dist = Matrix.euclidianDistance(sampleTests.getRow(i), authorMatrix.get(j).averageVector());
                if ((dist > minDist) && (authorMatrix.get(j).euclideanSquareDistToAverVect()[i] >= dist)){
                    result.add(i);
                    break;
                } else if (j == authorMatrix.size() - 1) {
                    result.add(0);
                }
            }
        }
        return result;
    }
}
