package devonboot.poc.pay.common.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class FileInfoArray {
    
    @NotNull
    private String[] checkIndex;
    @NotNull
    private String[] fileName;
    @NotNull
    private String[] uploadFilePath;
    
    public int size() {
        return fileName.length;
    }
    
    public FileInfo getFileInfo(int index) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(fileName[index]);
        fileInfo.setUploadFilePath(uploadFilePath[index]);
        return fileInfo;
    }
    
    public List<FileInfo> getFileInfoList() {
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        for (int inx = 0; inx < this.size(); inx++) {
            fileInfoList.add(getFileInfo(inx));
        }
        
        return fileInfoList;
    }
    
    public List<FileInfo> getCheckedFileInfoList() {
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        for (int inx = 0; inx < this.checkIndex.length; inx++) {
            fileInfoList.add(getFileInfo(Integer.parseInt(checkIndex[inx])));
        }
        
        return fileInfoList;
    }
    
    public String[] getCheckIndex() {
        return checkIndex;
    }

    
    public void setCheckIndex(String[] checkIndex) {
        this.checkIndex = checkIndex;
    }

    
    public String[] getFileName() {
        return fileName;
    }

    
    public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

    
    public String[] getUploadFilePath() {
        return uploadFilePath;
    }

    
    public void setUploadFilePath(String[] uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }
    

}
