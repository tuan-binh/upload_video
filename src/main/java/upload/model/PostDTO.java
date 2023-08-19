package upload.model;

import org.springframework.web.multipart.MultipartFile;

public class PostDTO {
	private int id;
	private String name;
	private MultipartFile url;
	
	public PostDTO() {
	}
	
	public PostDTO(int id, String name, MultipartFile url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public MultipartFile getUrl() {
		return url;
	}
	
	public void setUrl(MultipartFile url) {
		this.url = url;
	}
}
