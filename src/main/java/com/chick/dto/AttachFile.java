package com.chick.dto;

public class AttachFile {
	
	private String fileName;
	private String saveFileName;
	private String filePath;
	private String fileSize;
	private String type;
	private Thumbnail thumbnail;
	private String attSeqNo;
	
	
	
	public AttachFile() {
		super();
	}

	public AttachFile(Thumbnail thumbnail) {
		super();
		this.thumbnail = thumbnail;
	}
	
	
	public String getAttSeqNo() {
		return attSeqNo;
	}

	public void setAttSeqNo(String attSeqNo) {
		this.attSeqNo = attSeqNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Thumbnail getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	} 
	
	
	
}
