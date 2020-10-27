package Objects;

import Objects.Position;
import java.util.*;
import Math.*;

public class Positions {
	public Position[] positions;
	public int bernThreshold;
	/**
	 * ������ Z-scors �� ������ �������. ���� Z-score �� �������� ��� ����
	 * ������� ������ ��� ���� ����������� �������� �� �����������
	 */
	private double[] zscores;
	
	public double[] getZscores(){
		return zscores;
	}

	public Positions(int no) {
		positions = new Position[no];
	}
	
	public int getThresholdPositionNamber(){
		return positions[this.bernThreshold].aligNo;
	}

	/**
	 * ���������� ��� ������� ������������� �� �������� Z-score. �� ��������
	 * ������� - �������� ��������������� ������ (�.�. ���� ��� �������� �����
	 * 1, �� ��� SDP ������� ������������� 0 � 1 ���� �������)
	 * 
	 * @return
	 */
	public int[] getSortedForZscorePositions() {
		int[] result = new int[positions.length + 1];
		for (int i = 0; i < positions.length; i++) {
			result[i] = positions[i].aligNo;
		}
		result[positions.length] = bernThreshold;
		return result;
	}

	public int[] getSDP() {
		int[] result = new int[bernThreshold + 1];
		for (int i = 0; i <= bernThreshold; i++) {
			result[i] = positions[i].aligNo;
		}
		return result;
	}

	public int setAutoTreshold() {
		bernThreshold = StaticFunction.getThreshold(this);
		return bernThreshold;
	}
	
	public double getBernThresholdValue(){
		return positions[bernThreshold].positionProb;
	}

	public void setTreshold(int t) {
		bernThreshold = t;
	}

	public int setThreshold() {
		zscores = new double[this.positions.length];
		double min = 9E100;
		for (int i = 0; i < this.positions.length; i++) {
			if (positions[i].z_score != -999999)
				min = Math.min(min, positions[i].z_score);
		}
		for (int i = 0; i < this.positions.length; i++) {
			if (positions[i].z_score != -999999) {
				zscores[i] = positions[i].z_score;
			}else{
				zscores[i] = min;
			}
		}
		Arrays.sort(positions, new Comparator<Position>() {
			public int compare(Position a, Position b) {
				return Double.compare(b.z_score, a.z_score);
			}
		});
		for (int i = 0; i < positions.length; i++) {
			positions[i].positionProb = StaticFunction.getLogProb(
					positions[i].probability, positions.length, i+1);
		}
		bernThreshold = StaticFunction.getThreshold(this);
		return bernThreshold;
	}

	public void setRegression() {
		double a = 0, b = 0, c = 0, d = 0;
		int size = 0;
		for (int i = 0; i < positions.length; i++) {
			if (positions[i].inf != -999999) {
				a += positions[i].inf * positions[i].inf_sh;
				b += positions[i].inf;
				c += positions[i].inf_sh;
				d += positions[i].inf_sh * positions[i].inf_sh;
				size++;
			}
		}
		double aa = (a * size - b * c) / (d * size - c * c), bb = (b * d - c
				* a)
				/ (d * size - c * c);
		for (int i = 0; i < positions.length; i++) {
			if (positions[i].inf != -999999) {
				positions[i].set_z_score(aa, bb);
			}
		}
	}
}
