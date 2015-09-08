package com.myproject.parking.lib.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	public static List<String> readFile(File file) throws WebException{
		
		if(file == null){
			throw new WebException(WebException.NE_DATA_NOT_FOUND, new String [] {"File"});
		}
		List<String> listContent = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			try {
				String content = "";
				while((content = reader.readLine()) != null){		
					if (!("").equals(content.trim()))						
						listContent.add(content);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//throw new SmisWebException(SmisWebException.NE_READ_LINE_FILE);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//throw new SmisWebException(SmisWebException.NE_FILE_NOT_FOUND);
		}
		return listContent;
	}

}
