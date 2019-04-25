package fastfood.utils;

public class RenameFile {
	public static String renameFile(String fileName){
		String[] arrImg =  fileName.split("\\.");
		String duoiFileImg = arrImg[arrImg.length - 1];	
		
		String nameFile = "";		
		for (int i  = 0;i< (arrImg.length - 1) ; i++) {
			if(i == 0){
				nameFile = arrImg[i];
			}else{
				nameFile += "-"+arrImg[i];
			}
		}
		nameFile = nameFile + "_"+System.nanoTime() +"."+duoiFileImg;

		return nameFile;
	}
}