package mysqlbase;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import mysqlbase.api.MySQLAccess;
import mysqlbase.basedescr.SDP_CAMPSbase;
import mysqlbase.objects.OHcluster;
import mysqlbase.objects.Group;
import mysqlbase.objects.SDP;
import mysqlbase.objects.Sequence;
import Math.StaticFunction;
import Objects.AlignmentData;
import Objects.Project;
import Run.SDPfoxRun;

public class SDPmaker {
	
	private static long weightTime=0;
	
	private static int getCountOfPairSeqWithIDMoreThan(OHcluster c, double id) throws Exception {
		int result = 0;
		Project project = new Project();
		project.setAlignmentData(c.getInputStreamForAlignment());
		System.out.print(project.getSeqCount());
		double[][] idm = project.getIDMAtrix();
		for(int i=0;i<idm.length;i++){
			for(int j=i+1;j<idm.length;j++){
				if(idm[i][j] >= id)
					result++;
			}
		}
		return result;
	}
	
	private static Project doAll(OHcluster c, int no_iter) throws Exception{
		Project project = new Project();
		AlignmentData allAlign = new AlignmentData(c.getInputStreamForAlignment());
		//filtering by id
		int[] goodseq = StaticFunction.filterByIdentity(allAlign.alignment.getIdentityMatrix(), 0.95);
		int[] removed = new int[allAlign.alignment.getAlignmentCount()-goodseq.length];
		int gi = 0;
		Arrays.sort(goodseq);
		for(int i=0;i<allAlign.alignment.getAlignmentCount();i++){
			if(goodseq[gi] != i)
				removed[i-gi] = i;
			else
				gi++;
		}
		AlignmentData ad = StaticFunction.createNewAlignmentDataFromSeqsNums(goodseq, allAlign);
		project.setAlignmentData(ad);
		//sdptree
		int grcount = SDPfoxRun.getGroupCount(project.getSeqCount());
		project.sdpTree(no_iter, grcount, SDPfoxRun.getSeqInGroupCount(project.getSeqCount(), grcount), null, System.out, null);
		c.setSPtree(project.getRoot().toString());
		int grCountRes  = project.getGroupCount();
		if(grCountRes > 2)
			c.setSDPpvalue(project.getThresholdPvalue());
		c.save();
		//assignment specificity for removed seq
		int[] removedSeqGroup = new int[removed.length];
		for(int i=0;i<removed.length;i++){
			String nnName = allAlign.seqNumerator.getNameForNum(allAlign.alignment.getNearestNeighbour(removed[i],removed));
			removedSeqGroup[i] = project.getGroupForSequence(project.getSequenceNo(nnName));
		}
		//saving into base 
		int[][] grouping = project.getGroupng();
		Hashtable<Integer, Integer> group_group_id = new Hashtable<Integer, Integer>();
		for(int i=0;i<project.getGroupCount();i++){
			Hashtable<String, String> data = new Hashtable<String, String>();
			data.put(Group.ID, "-1");
			data.put(Group.OH_CLUSTER_ID, "" +c.getID());
			if(grouping[i][0] != 0 && grCountRes > 2){
				weightTime -= System.currentTimeMillis();
				data.put(Group.WEIGHT, ""+project.getZscoreOfCumulativeEntropyForGroup(i));
				weightTime += System.currentTimeMillis();
			}
			String nongr = "0";
			if(i == 0)
				nongr = "1";
			data.put(Group.ISNONGROUPED, nongr);
			Group group = new Group(data);
			int group_id = group.save();
			group_group_id.put(i, group_id);
			for(int j=1;j <= grouping[i][0];j++){
				Hashtable<String, String> sdata = new Hashtable<String, String>();
				sdata.put(Sequence.GROUP_ID, ""+group_id);
				sdata.put(Sequence.ID, "-1");
				sdata.put(Sequence.NAME, project.getSeqName(grouping[i][j]));	
				sdata.put(Sequence.REMOVED, "0");
				(new Sequence(sdata)).save();
			}
		}
		//adding of removed sequences
		for(int i=0;i<removed.length;i++){
			Hashtable<String, String> sdata = new Hashtable<String, String>();
			sdata.put(Sequence.GROUP_ID, ""+group_group_id.get(removedSeqGroup[i]));
			sdata.put(Sequence.ID, "-1");
			sdata.put(Sequence.NAME, allAlign.seqNumerator.getNameForNum(removed[i]));	
			sdata.put(Sequence.REMOVED, "1");
			(new Sequence(sdata)).save();
		}
		//adding of sdp
		if(grCountRes > 2){
		double[][] sdp = project.getSDPandZscore();
			for(int i=0;i<sdp.length;i++){
				Hashtable<String, String> sdata = new Hashtable<String, String>();
				sdata.put(SDP.ID, "-1");
				sdata.put(SDP.OH_CLUSTER_ID, ""+c.getID());
				sdata.put(SDP.POSITION, ""+(int)sdp[i][0]);
				sdata.put(SDP.ZSCORE, ""+sdp[i][1]);
				(new SDP(sdata)).save();
			}
		}
		return project;
	}
	
	private static void cleanExceptOH() throws Exception {
		MySQLAccess.clean(SDP_CAMPSbase.GROUP_TABLE);
		MySQLAccess.clean(SDP_CAMPSbase.SDP_TABLE);
		MySQLAccess.clean(SDP_CAMPSbase.SEQUENCE_TABLE);
	}
	
	public static void main(String[] args) throws Exception {
		Vector<Integer> oh_id = MySQLAccess.getAllID(SDP_CAMPSbase.getTable(SDP_CAMPSbase.OH_CLUSTER_TABLE));
		PrintWriter pw = new PrintWriter("result");
		for(int i : oh_id){
			System.out.println(i);
			OHcluster t = MySQLAccess.getOHcluster(i);
			for(Group g : t.getGroups()){
				pw.println(g.getID()+"\t"+t.getID()+"\t"+g.getWeight()+"\t"+g.getSequences().size());
			}
		}
		pw.close();
	}
}
