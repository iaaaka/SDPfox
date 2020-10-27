package test;

import Objects.*;
import Math.StaticFunction;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.*;
import painter.*;
import Util.Numerator;
import Tree.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

public class Test {

	
	public static void main(String[] args) throws Exception  {
		AlignmentData ad = new AlignmentData("in/alignment/LacI.gde");
		Positions p = StaticFunction.calculateZ_scoreForAll(ad, 0.3);
		System.out.println("Alignment\tZ-score\tZscore after regression");
		for(int i=0;i<p.positions.length;i++){
			System.out.println(p.positions[i].aligNo+"\t"+p.positions[i].zScoreWithoutRegression+"\t"+p.positions[i].z_score);
		}
	}
}
