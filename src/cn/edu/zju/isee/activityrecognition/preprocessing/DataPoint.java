package cn.edu.zju.isee.activityrecognition.preprocessing;

public class DataPoint{
	double xValue;
	double yValue;
	double zValue;
	String activity;
	long timeStamp;
	int id;
	
	public DataPoint() {
		xValue = 0;
		yValue = 0;
		zValue = 0;
		activity = "Sitting";
		timeStamp = 0;
	}
	
	public DataPoint(double x, double y, double z, String activity) {
		xValue = x;
		yValue = y;
		zValue = z;
		this.activity = activity;
	}
	
	public DataPoint(double x, double y, double z, String activity, long timeStamp, int id) {
		this(x, y, z, activity);
		this.timeStamp = timeStamp;
		this.id = id;
	}
}
