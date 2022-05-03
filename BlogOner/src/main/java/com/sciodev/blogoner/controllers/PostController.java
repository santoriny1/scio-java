package com.sciodev.blogoner.controllers;

import com.sciodev.blogoner.models.dao.IPostDAO;
import com.sciodev.blogoner.models.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class PostController {
    @Autowired
    @Qualifier("PostDAOJPA")
    private IPostDAO postDAO;

    @RequestMapping(value = "formPost/{id}")
    public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model) {
        if(id > 0) {
            Post post = postDAO.findOne(id);
            model.put("post", post);
            model.put("title", "Edit Post");
            model.put("bText", "Edit Post");
            return "formPost";
        } else {
            return "redirect:list-posts";
        }
    }

    @RequestMapping(value = "deletePost/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        if(id > 0) {
            postDAO.deletePost(id);
        }
        return "redirect:/list-posts";
    }

    @RequestMapping(value = "/formPost")
    public String create(Map<String, Object> model) {
        Post post = new Post();
        model.put("post", post);
        model.put("title", "Post Form");
        model.put("bText", "Create Post");
        return "formPost";
    }

    @RequestMapping(value = "/list-posts", method = RequestMethod.GET)
    public String listPosts(Model model) {
        model.addAttribute("title", "Post List");
        model.addAttribute("posts", postDAO.findAll());
        return "list-posts";
    }

    @RequestMapping(value = "formPost", method = RequestMethod.POST)
    public String save(@Valid Post post, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("title", "Post Form");
            return "formPost";
        }
        postDAO.save(post);
        return "redirect:list-posts";
    }
}
