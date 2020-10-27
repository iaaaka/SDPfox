package Objects;

import java.io.*;
import Math.StaticFunction;
import Util.Numerator;
import java.util.*;

public class GroupingMatrixes {
	private double[][] freqMatrix;

	private double[][] distanceMatrix;

	public static final int VERTICAL_MATRIX = 1;

	public static final int HORIZONTAL_MATRIX = 0;

	private Numerator nameNumerator;

	private int[][] matrixOfGrouping;

	private double[] weights;

	private double[] sortedWeights;

	private int threshold;

	/**
	 * 
	 * @param m �
	 *            ������� - ��������� ������ ����������� - ������ ����� ���
	 *            ��������������� ���������� ������������������
	 */

	public GroupingMatrixes(int[][] m) {
		matrixOfGrouping = m;
		calcFreqAndDistanceMatrix(m);
	}
	
	public void setThreshold(int t){
		threshold = t;
		calcFreqAndDistanceMatrix(matrixOfGrouping,true);
	}

	public int[] getGroupingMatrix(int iter) {
		return matrixOfGrouping[iter];
	}

	public int getIterationCount() {
		return matrixOfGrouping.length;
	}

	public void setNumerator(Numerator r) {
		this.nameNumerator = r;
	}

	public GroupingMatrixes(double[][] dist) {
		distanceMatrix = dist;
	}

	public void setWeights(double[] w) {
		weights = w;
		sortedWeights = w.clone();
		Arrays.sort(sortedWeights);
	}
	

	public GroupingMatrixes(String fname, int matrix_type) {
		FileInputStream f = null;
		try {
			f = new FileInputStream(fname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch (matrix_type) {
		case 0:
			readHorizontalMatrix(f);
			break;
		case 1:
			readVerticalMatrix(f);
			break;

		}
	}

	public double[][] getFrequencesMatrix() {
		return this.freqMatrix;
	}

	public double[][] getDistanceMatrix() {
		return this.distanceMatrix;
	}

	public Numerator getNumerator() {
		return nameNumerator;
	}

	private void readVerticalMatrix(InputStream is) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			nameNumerator = new Numerator();
			Vector<String> data = new Vector<String>();
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				data.add(line);
			}
			StringTokenizer st = new StringTokenizer(data.get(0));
			for (; st.hasMoreTokens();) {
				nameNumerator.addName(st.nextToken());
			}
			int[][] matrix = new int[data.size() - 1][];
			for (int i = 1; i < data.size(); i++) {
				matrix[i - 1] = StaticFunction.parseStringToIntArray(data
						.get(i));
			}
			calcFreqAndDistanceMatrix(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������������� ������� � ����� ������������������� ����� ������ �� ������.
	 * ������� ������ ����� ���: seqName ������ ����� ������ ������ ����� �
	 * ������� ���� ��� ������������������ � ��������� �����������. ����� ������
	 * ������ ������ ���������.
	 * 
	 * @param is
	 *            ����� �� ����� � ��������
	 */
	private void readHorizontalMatrix(InputStream is) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			nameNumerator = new Numerator();
			Vector<String> data = new Vector<String>();
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				data.add(line);
			}
			StringTokenizer t = new StringTokenizer(data.firstElement());
			int noIter = -1;
			for (; t.hasMoreTokens(); noIter++) {
				t.nextToken();
			}
			int[][] matrix = new int[noIter][data.size()];
			for (int i = 0; i < data.size(); i++) {
				t = new StringTokenizer(data.get(i));
				nameNumerator.addName(t.nextToken());
				for (int j = 0; j < noIter; j++) {
					matrix[j][i] = Integer.parseInt(t.nextToken());
				}
			}
			calcFreqAndDistanceMatrix(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printMatrix(PrintWriter pf) throws Exception {
		if (nameNumerator == null) {
			throw new Exception("Internal exception");
		}
		for (int i = 0; i < nameNumerator.getNumOfNames(); i++) {
			pf.print(nameNumerator.getNameForNum(i) + "\t");
		}
		pf.println();
		for (int i = 0; i < matrixOfGrouping.length; i++) {
			for (int j = 0; j < matrixOfGrouping[i].length; j++) {
				pf.print(matrixOfGrouping[i][j] + "\t");
			}
			pf.println();
		}
	}

	private void calcFreqAndDistanceMatrix(int[][] groupingMatrixes) {
		calcFreqAndDistanceMatrix(groupingMatrixes, false);
	}

	public void calcFreqAndDistanceMatrix(int[][] groupingMatrixes,
			boolean withThr) {
		int[][] mfg;
		if (withThr) {
			mfg = new int[threshold + 1][];
			int index = 0;
			for (int i = 0; i < groupingMatrixes.length; i++) {
				if (this.weights[i] <= this.sortedWeights[threshold]) {
					if(index == mfg.length){
						break;
					}
					mfg[index] = groupingMatrixes[i];
					index++;
				}
			}
			matrixOfGrouping = mfg;
		} else {
			matrixOfGrouping = groupingMatrixes;
		}
		freqMatrix = new double[matrixOfGrouping[0].length][matrixOfGrouping[0].length];
		for (int i = 0; i < matrixOfGrouping.length; i++) {
			for (int j1 = 0; j1 < matrixOfGrouping[0].length; j1++) {
				for (int j2 = 0; j2 < matrixOfGrouping[0].length; j2++) {
					if (matrixOfGrouping[i][j1] == matrixOfGrouping[i][j2]) {
						freqMatrix[j1][j2] += (double) 1
								/ matrixOfGrouping.length;
					}
				}
			}
		}
		distanceMatrix = new double[freqMatrix.length][freqMatrix.length];
		for (int i = 0; i < freqMatrix.length; i++) {
			for (int j = 0; j < freqMatrix.length; j++) {
				if (freqMatrix[i][j] == 0) {
					distanceMatrix[i][j] = -Math.log((double) 0.5
							/ matrixOfGrouping.length);
				} else {
					distanceMatrix[i][j] = -Math.log(freqMatrix[i][j]);
				}
			}
		}
		matrixOfGrouping = groupingMatrixes;
	}

}
