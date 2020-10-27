package statistic;

import Objects.AlignmentData;
import Objects.Grouping;
import Objects.Positions;
import Tree.Node;
import Tree.TreeMaker;
import Util.GroupingMatrixGeneratorListener;
import Util.Numerator;
import Math.*;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.*;

public class SDPstatistic {
	private static double[] positionSDPrate;
	private static double[][] pairwiseRateMatrix;
	private static float[][][] tripleRateMatrix;
	public static AlignmentData ta;
	final static int numOfIter = 0;

	public static void getSDPstat(int[] iterRes) {
		for (int i = 0; i < iterRes.length; i++) {
			positionSDPrate[iterRes[i]] += (double) 1 / numOfIter;
			for (int j = i + 1; j < iterRes.length; j++) {
				pairwiseRateMatrix[iterRes[i] + 1][iterRes[j] + 1] += (double) 1
						/ numOfIter;
				pairwiseRateMatrix[iterRes[j] + 1][iterRes[i] + 1] += (double) 1
						/ numOfIter;
				try {
					for (int k = j + 1; k < iterRes.length; k++) {
						tripleRateMatrix[iterRes[i] + 1][iterRes[j] + 1][iterRes[k] + 1] += (float) 1
								/ numOfIter;
						tripleRateMatrix[iterRes[j] + 1][iterRes[k] + 1][iterRes[i] + 1] += (float) 1
								/ numOfIter;
						tripleRateMatrix[iterRes[k] + 1][iterRes[i] + 1][iterRes[j] + 1] += (float) 1
								/ numOfIter;
						tripleRateMatrix[iterRes[j] + 1][iterRes[i] + 1][iterRes[k] + 1] += (float) 1
								/ numOfIter;
						tripleRateMatrix[iterRes[k] + 1][iterRes[j] + 1][iterRes[i] + 1] += (float) 1
								/ numOfIter;
						tripleRateMatrix[iterRes[i] + 1][iterRes[k] + 1][iterRes[j] + 1] += (float) 1
								/ numOfIter;
					}
				} catch (NullPointerException e) {
				}
			}
		}
	}

	public static void generateSDPset(AlignmentData ad, int numOfIter,
			int numOfGroup, int numOfSeqInGr,
			GroupingMatrixGeneratorListener listener, double max_gap_part) {
		Grouping backupGrouping = ad.grouping;
		Numerator bacupGroupNumerator = ad.groupNumerator;
		for (int i = 0; i < numOfIter; i++) {
			StaticFunction.randomizeGrouping(numOfGroup, numOfSeqInGr, ad);
			Positions p = StaticFunction.calculateZ_scoreForAll(ad,
					max_gap_part);
			int[] result = null;
			if (p != null)
				result = p.getSDP();
			if (result == null)
				i--;
			else {
				listener.matrixComplete(result);
			}
			if(i%10 == 0) System.out.println(i);
		}
		ad.grouping = backupGrouping;
		ad.groupNumerator = bacupGroupNumerator;
	}

	public static void main(String[] args) throws Exception {
		ta = new AlignmentData("in/alignment/LacI.gde");
		int len = ta.alignment.getAlignmetnLength();
		positionSDPrate = new double[len];
		pairwiseRateMatrix = new double[len + 1][len + 1];
		if (len < 230)
			tripleRateMatrix = new float[len + 1][len + 1][len + 1];
		else
			tripleRateMatrix = null;
		for (int i = 0; i < len + 1; i++) {
			for (int j = 0; j < len + 1; j++) {
				try {
					for (int k = 0; k < len + 1; k++) {
						if (k == 0 && j == 0)
							tripleRateMatrix[i][j][k] = i;
						else if (k == 0 && j != 0)
							tripleRateMatrix[i][j][k] = j;
						else if (j == 0 && k != 0)
							tripleRateMatrix[i][j][k] = k;
						else
							tripleRateMatrix[i][j][k] = 0;
					}
				} catch (NullPointerException e) {
				}
				if (i == 0)
					pairwiseRateMatrix[i][j] = j;
				else if (j == 0)
					pairwiseRateMatrix[i][j] = i;
				else if (i == j)
					pairwiseRateMatrix[i][j] = 1;
				else
					pairwiseRateMatrix[i][j] = 0;

			}
		}

		Arrays.fill(positionSDPrate, 0);

		generateSDPset(ta, numOfIter, ta.groupNumerator.getNumOfNames(),
				ta.seqNumerator.getNumOfNames()
						/ ta.groupNumerator.getNumOfNames(),
				new GroupingMatrixGeneratorListener() {
					public void matrixComplete(int[] matrix) {
						getSDPstat(matrix);

					}
				}, 0.3);

		// Sorting sortirator = new Sorting(positionSDPrate, pairwiseRateMatrix,
		// tripleRateMatrix, "3gr_10seq_4sdp");
		Sorting sortirator = new Sorting(pairwiseRateMatrix, "LacI");
		sortirator.saveAllmatrixes();
		//sortirator.savePairwisingMatrixImage();
		sortirator.saveMatrixForNJ();
		// sortirator.saveLayersMatrixes();
		//double[][] m = sortirator.getPairwiseSDPmatrix();
		double[][] m = new double[][]{
				{0,5,4,2,1},
				{5,0,5,1,1},
				{4,5,0,2,2},
				{2,1,2,0,7},
				{1,1,2,7,0}
		};
		DecimalFormat f = new DecimalFormat("0.00");
		for(double[] mr : m){
			for(double mm : mr)
				System.out.print(mm+"\t");
			System.out.println();
		}
		int[] r = StaticFunction.searchConnectedComponents(m, 4);
		System.out.println("result");
		for(int i :r)
			System.out.print(i+"\t");
		/*SDPclustering clusterator = new SDPclustering(sortirator
				.getPairwiseSDPmatrix(), 350);
		double[][] smtha = clusterator.SDPfreqMatr;
		FileWriter loada = new FileWriter("LacI_distmatr.txt");
		for (double[] x : smtha) {
			for(double y : x){
				loada.write(y + "\t");
		}
			loada.write("\n");
	}
		loada.close();/*
		Node root = TreeMaker.makeUPGMATreeFromDistantMatrix(
				clusterator.SDPfreqMatr, clusterator.posNum);
		
		System.out.println(root);*/
		
		
		/*int[] smth = clusterator.clusterPerPosition;

		FileWriter load = new FileWriter("LacI_cluster.txt");
		
		for (int i = 0; i < smth.length; i++) {
			load.write(clusterator.posNum.getNameForNum(i) + "\t" + smth[i] + "\n");
		}
		load.close();*/
		/*clusterator.getClusters();*/
	}
}