package com.sciodev.blogoner.controllers;

import com.sciodev.blogoner.models.dao.ICommentDAO;
import com.sciodev.blogoner.models.entity.Comment;
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
public class CommentController {
    @Autowired
    @Qualifier("CommentDAOJPA")
    private ICommentDAO commentDAO;

    @RequestMapping(value = "formComment/{id}")
    public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model) {
        if(id > 0) {
            Comment comment = commentDAO.findOne(id);
            model.put("comment", comment);
            model.put("title", "Edit Comment");
            model.put("bText", "Edit Comment");
            return "formComment";
        } else {
            return "redirect:list-comments";
        }
    }

    @RequestMapping(value = "deleteComment/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        if(id > 0) {
            commentDAO.deleteComment(id);
        }
        return "redirect:/list-comments";
    }

    @RequestMapping(value = "/formComment")
    public String create(Map<String, Object> model) {
        Comment comment = new Comment();
        model.put("comment", comment);
        model.put("title", "Comment Form");
        model.put("bText", "Create Comment");
        return "formComment";
    }

    @RequestMapping(value = "/list-comments", method = RequestMethod.GET)
    public String listComments(Model model) {
        model.addAttribute("title", "Comment List");
        model.addAttribute("comments", commentDAO.findAll());
        return "list-comments";
    }

    @RequestMapping(value = "formComment", method = RequestMethod.POST)
    public String save(@Valid Comment comment, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("title", "Comment Form");
            return "formComment";
        }
        commentDAO.save(comment);
        return "redirect:list-comments";
    }
}
