package com.example.Blog.Controller;



import com.example.Blog.feign.UserDTO;
import com.example.Blog.model.Blog;
import com.example.Blog.services.Blogservice;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Blog")
@CrossOrigin("*")
public class BlogController {
    @GetMapping("/hey")
    public String getAnonymous() {
        String ok="Im Blog";
        System.out.println(ok);
        return ok;
    }
    private final Blogservice blogService;
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public List<String> getAll(){
        return blogService.getFullBlog2();
    }
    @GetMapping("/list-blogs")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public List<Blog> getblogs(){
        return blogService.getAll();
    }


    @GetMapping("/withcomments")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public List<Blog> getAllandcomments(){
        // return blogService.getFullBlog();
    return  null;
    }

    @GetMapping("/byhashtag/{str}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public List<Blog> getAllandcomments(@PathVariable() String str){
        return  blogService.getbyhashtag(str);
    }

    // Create a blog
    @PostMapping(value = "/add" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Blog> addBlog(
            @RequestParam("iduser") Integer iduser,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("country") String country,
            @RequestParam("address") String address,
            @RequestParam("pictures") MultipartFile pictures,
            @RequestParam("hashtags") List<String> hashtags
    ) throws IOException {
        Blog blog = new Blog();
        blog.setIduser(iduser);
        blog.setTitle(title);
        blog.setDate(new Date());  // setting the current date
        blog.setDescription(description);
        blog.setCountry(country);
        blog.setAddress(address);
        blog.setPictures(pictures.getBytes());
        blog.setHashtags(hashtags);

        Blog savedBlog = blogService.createBlog(blog);
        return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
    }

    // Read a blog by ID
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public ResponseEntity<Blog> getBlog(@PathVariable Integer id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }
    @GetMapping("/myblogs/{id_user}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public List<Blog> getMyBlog(@PathVariable Integer id_user) {
        return blogService.getMyBlog(id_user);
    }

    // Update a blog
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public ResponseEntity<Blog> updateBlog(@PathVariable Integer id, @RequestBody Blog blog) {
        return ResponseEntity.ok(blogService.updateBlog(id, blog));
    }

    // Delete a blog
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER-ADMIN','USER','ORGANIZER','GUEST')")
    public ResponseEntity<Void> deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }


}
