package cn.edu.zju.isee.activityrecognition.preprocessing;

import java.io.FileReader;
import java.io.IOException;

public class WisdmRawTextParser extends RawTextParser{
	
	public WisdmRawTextParser(FileReader fileReader) {
		super(fileReader);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get one raw data point from the line string
	 * @param oneLineRawData the string containing one raw data point
	 * @return the raw data point in the string
	 */
	public DataPoint getDataPoint(){
		DataPoint newPoint = new DataPoint();
		
		String oneLineRawData = getLineRawData();
		if(oneLineRawData.equals("")) newPoint = null;
		else{
			String[] partsOfLine = oneLineRawData.replace(';', ' ').split(",");
			
			newPoint.id = Integer.parseInt(partsOfLine[0]);
			newPoint.activity = partsOfLine[1];
			newPoint.timeStamp = Long.parseLong(partsOfLine[2]);
			newPoint.xValue = Double.parseDouble(partsOfLine[3]);
			newPoint.yValue = Double.parseDouble(partsOfLine[4]);
			newPoint.zValue = Double.parseDouble(partsOfLine[5]);
		}
		return newPoint;
	}
	
	//Get one line raw data from the raw data file.
	private String getLineRawData(){
		char cbuf;
		int ibuf;
		String oneLineRawData = "";
		
		try {
			while((ibuf = super.rawTextFileReader.read()) != -1){
				cbuf = (char) ibuf;
				if(cbuf=='\r' || cbuf =='\n') {
					if(cbuf == '\r')
						if((cbuf=(char) super.rawTextFileReader.read()) == '\n') 
							break;
						else 
							System.out.println("\\r is not followed by a \\n");
					else
						break;
				}
				else {
					oneLineRawData += cbuf;
				}
			}
			if(ibuf == -1 && oneLineRawData.equals(""))
				System.out.println("Seems we got the EOF!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oneLineRawData;
	}
}
