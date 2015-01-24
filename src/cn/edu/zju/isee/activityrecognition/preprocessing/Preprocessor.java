package cn.edu.zju.isee.activityrecognition.preprocessing;

public class Preprocessor {
	RawTextParser textParser;
	
	int resampleRatio;
	int resampleIndex;
	int windowSize;
	double windowOverlap;
	
	public Preprocessor(RawTextParser textParser, int resample, int index, int window, double overlap) {
		// TODO Auto-generated constructor stub
		this.textParser  = textParser;
		resampleRatio = resample;
		resampleIndex = index;
		windowSize = window;
		windowOverlap = overlap;
	}
	
	/**
	 * Output instances to WEKA-defined .arff file with the same name and directory of the rawfile
	 */
	void outputToWekaFile(){
		
	}
	
	/**
	 * Get a weka instance from raw data points
	 * @return weka instance if there's enough data points, null esle
	 * @throws IOException 
	 */
	public String getWekaInstance(){
		DataPoint[] points = getWindowSizedPoints(textParser, windowSize, resampleIndex, resampleRatio);
		if(points == null) {
			System.out.println("We got a null points array!");
			return null;
		}
		
		System.out.println("Get one weka instance");

		double[] avrgs = getAverages(points);
		System.out.print("Average: ");
		for (int i = 0; i <3; i++) 
			System.out.print(avrgs[i] + " ");
		System.out.println();
		
		double[] sd = getStandardDeviation(points);
		System.out.print("Standard Deviation: ");
		for (int i = 0; i <3; i++) 
			System.out.print(sd[i] + " ");
		System.out.println();
		
		double resultant = getAverageResultant(points);
		System.out.print("Resultant: ");
		System.out.println(resultant);
		
		System.out.println();
		return "h";
	}
	
	/**
	 * Return averages of x, y and z values of the points
	 * @param points points to caculate averages
	 * @return double[0] for x, double[1] for y and [2] for z
	 */
	public double[] getAverages(DataPoint[] points){
		double[] sums = new double[3];
		double[] avrgs = new double[3];
		
		for(int i=0; i<points.length; i++){
			DataPoint point = points[i];
			sums[0] += point.xValue;
			sums[1] += point.yValue;
			sums[2] += point.zValue;
		}
		for(int i=0; i<3; i++)
			avrgs[i] = sums[i]/points.length;
		
		return avrgs;
	}
	
	/**
	 * Get the standard deviation of x, y and z axes
	 * @param points
	 * @return the standard deviation, [0] for x, [1] for y and [2] for z
	 */
	public double[] getStandardDeviation(DataPoint[] points){
		double[] sd = new double[3];
		double[] avrgs = getAverages(points);
		
		double[] squaredDifSums = new double[3];
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < 3; j++) 
				squaredDifSums[j] += Math.pow(points[i].xValue-avrgs[j], 2);
		}	
		for (int i = 0; i < 3; i++) 
			sd[i] = Math.sqrt(squaredDifSums[i]/points.length);
		return sd;
	}
	
	
	
	/**
	 * Get the resultant of the x, y, z values
	 * @param points
	 * @return
	 */
	public double getAverageResultant(DataPoint[] points){
		double sumResultant = 0;
		
		for (int i = 0; i < points.length; i++) {
			DataPoint point = points[i];
			sumResultant += Math.sqrt(
					Math.pow(point.xValue, 2) +
					Math.pow(point.yValue, 2) +
					Math.pow(point.zValue, 2));
		}
		
		return sumResultant/points.length;
	}
	
	//get window-sized data point
	private DataPoint[] getWindowSizedPoints(RawTextParser textParser, int windowSize, int resampleIndex, int resampleRatio){
		DataPoint[] points = new DataPoint[windowSize];
		
		for(int i=0; i<windowSize; i++){
			DataPoint point = resampleData(textParser, resampleIndex, resampleRatio);
			if(point == null) return null;
			points[i] = point;
		}
		return points;
	}
	
	//Resample the data points
	private DataPoint resampleData(RawTextParser textParser, int index, int resampleRatio){
		if(resampleRatio<1) {
			System.out.println("Resample ratio cannot be less than 1!");
			return null;
		}
			
		if(index<0 || index >= resampleRatio) {
			System.out.println("Resample index is not apropriate! Set to defalut, 0.");
			index = 0;
		}
		
		DataPoint point = null;
		if(resampleRatio == 1) 
			point = textParser.getDataPoint();
		else{
			String activity = null;
			for(int i=0; i<resampleRatio; i++){
				DataPoint tmpPoint = textParser.getDataPoint();
				if(i==0) activity = tmpPoint.activity;
				else if(!activity.equals(tmpPoint.activity)){
					System.out.println("We got a different activity data");
					return null;
				}
					
 				if(i==index) point = tmpPoint;
			}
		}
		if(point == null) System.out.println("We got a null data point! Please check if not beacuse of the EOF!");
		return point;
	}
}
 