package Objects;

public class Profiles {
	private final int[] SDP;
	private final Profile[] profiles;
	
	public Profiles(int[] sdp,int[][] grouping,Alignment a){
		SDP = sdp;
		profiles = new Profile[grouping.length];
		for(int i=1;i<grouping.length;i++){
			profiles[i] =new Profile(SDP,grouping[i],a);
		}
		profiles[0] = new Profile(SDP,a.noForAllseq,a);	
	}
	/**
	 * 
	 * @param seq sequence in int
	 * @return weights for seq for all group. First weight - weight for overall alignment
	 */
	
	public double[] getWeightsForAllGroup(int[] seq){
		double[] result = new double[profiles.length];
		for(int i=0;i<profiles.length;i++){
			result[i]= profiles[i].getWeightForSeq(seq);
		}
		return result;
	}
	
	public String getProfileToString(int gr_i){
		return profiles[gr_i].toString();
	}
	
	public double[] getInformations(int groupNo){
		return this.profiles[groupNo].getInformations();
	}
	
	public double[][] getFrequencies(int groupNo){
		return this.profiles[groupNo].getFrequencies();
	}
	
	public String toString(){
		String result = "";
			for(int i=0;i<profiles.length;i++){
				result +="Profile"+i+"\n"+profiles[i].toString();
			}
		return result;
	}
}
