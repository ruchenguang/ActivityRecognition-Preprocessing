package cn.edu.zju.isee.activityrecognition.preprocessing;

import java.io.FileReader;

public abstract class RawTextParser {
	protected FileReader rawTextFileReader;
	public RawTextParser(FileReader fileReader) {
		this.rawTextFileReader = fileReader;
	}
	
	abstract DataPoint getDataPoint();
}
