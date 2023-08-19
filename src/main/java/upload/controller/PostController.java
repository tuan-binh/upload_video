package upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import upload.model.Post;
import upload.model.PostDTO;
import upload.service.PostService;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
@PropertySource({"classpath:upload.properties"})
public class PostController {
	
	@Value("${path_video}")
	private String pathUpload;
	
	@Autowired
	private PostService postService;
	
	@GetMapping
	public String home() {
		return "home";
	}
	
	@GetMapping("/show")
	public String show(Model model) {
		model.addAttribute("videos",postService.findAll());
		return "show";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("post",new PostDTO());
		return "add";
	}
	
	@PostMapping("/handleAdd")
	public String handleAdd(@ModelAttribute PostDTO postDTO) {
		File file = new File(pathUpload);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String filename = postDTO.getUrl().getOriginalFilename();
		try {
			FileCopyUtils.copy(postDTO.getUrl().getBytes(),new File(pathUpload + filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Post post = new Post(0,postDTO.getName(),filename);
		postService.save(post);
		return "redirect:/show";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		postService.delete(Integer.parseInt(String.valueOf(id)));
		return "redirect:/show";
	}
	
}
