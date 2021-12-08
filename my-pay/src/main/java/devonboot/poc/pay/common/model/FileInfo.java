package devonboot.poc.pay.common.model;



public class FileInfo {
    
    String fileName ="";
    
    String uploadFilePath = "";
    
    String uploadFileName ="";
    
    long fileSize = 0;
    
    String uploadDate;

    
    public String getFileName() {
        return fileName;
    }

    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    
    
    public String getUploadFilePath() {
        return uploadFilePath;
    }


    
    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }


    public String getUploadFileName() {
        return uploadFileName;
    }

    
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    
    public long getFileSize() {
        return fileSize;
    }

    
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    
    public String getUploadDate() {
        return uploadDate;
    }

    
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

}
