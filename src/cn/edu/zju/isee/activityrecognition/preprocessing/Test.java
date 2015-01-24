package cn.edu.zju.isee.activityrecognition.preprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	public static final String PROJECT_DIRECTORY = "/Users/shine/Workspace/ActivityRecognition";
	public static final String DATASET_DIRECTORY = PROJECT_DIRECTORY + "/ActivityRecognition-Datasets";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String rawDataPath = DATASET_DIRECTORY + "/WISDM/WISDM_ar_v1.1_raw.TXT";
//		String rawDataPath = DATASET_DIRECTORY + "/WISDM/test.TXT";
		File rawDataFile = new File(rawDataPath);
		try {
			FileReader fileReader = new FileReader(rawDataFile);
			WisdmRawTextParser wisdmTextParser = new WisdmRawTextParser(fileReader);

			int resampleRatio = 1;
			int resampleIndex = 0;
			int windowSize = 200;
			double windowOverlap = 0;
			Preprocessor preprocessor = new Preprocessor(wisdmTextParser, resampleRatio, resampleIndex, windowSize, windowOverlap);
			
			preprocessor.getWekaInstance();
			preprocessor.getWekaInstance();
			
			
			preprocessor.getWekaInstance();
			
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	WekaInstance createInstance(RawTextParser textParser){
//		
//	}

}
