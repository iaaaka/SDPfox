package Util;

import java.io.*;
import java.util.StringTokenizer;
import java.net.*;

public class MandDInfMatrix {
	private double[][] mInf = new double[199][500];
	private double[][] dInf = new double[199][500];

	public MandDInfMatrix() {
		this("/matrix/mInf", "/matrix/dInf");
	}

	public MandDInfMatrix(String mFname, String dFname) {
		mInf = readResource(mFname, 199, 500);
		dInf = readResource(dFname, 199, 500);
	}

	public double getMInf(int GroupCount, int AcidCount) {
		if (AcidCount > 500)
			return (double) GroupCount / 2;
		return mInf[GroupCount - 2][AcidCount - 1];
	}

	public double getDInf(int GroupCount, int AcidCount) {
		if (AcidCount > 500)
			return (double) GroupCount / 2;
		return dInf[GroupCount - 2][AcidCount - 1];
	}

	private double[][] readBufferedReader(BufferedReader r, int rowCount,
			int columnCount) {
		double[][] result = new double[rowCount][columnCount];
		try {
			String line = r.readLine();
			line = r.readLine();
			for (int i = 0; i < rowCount; line = r.readLine(), i++) {
				if(line == null)
					throw new Exception("Bad MD_matrix. RowCount = "+rowCount+" i = "+i);
				StringTokenizer t = new StringTokenizer(line, " \t");
				for (int j = 0; j < columnCount; j++) {
					if(!t.hasMoreTokens())
						throw new Exception("Bad MD_matrix. RowCount = "+rowCount+" i = "+i+" columnCount = "+columnCount+" j = "+j);
					result[i][j] = Double.parseDouble(t.nextToken());
				}
			}
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private double[][] readResource(String fname, int rowCount, int columnCount) {
		InputStream is = MandDInfMatrix.class.getResourceAsStream(fname);
		return readBufferedReader(
				new BufferedReader(new InputStreamReader(is)), rowCount,
				columnCount);
	}

	private double[][] readFile(String fname, int rowCount, int columnCount) {
		try {
			return readBufferedReader(
					new BufferedReader(new FileReader(fname)), rowCount,
					columnCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
