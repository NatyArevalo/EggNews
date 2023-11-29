package com.egg.news.controllers;

import com.egg.news.entities.NewPiece;
import com.egg.news.exceptions.MiException;
import com.egg.news.services.NewPieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class NewPieceController {
    @Autowired
    private NewPieceService newPieceService;


    @GetMapping("/")
    public String indexlist(ModelMap model) {
        List<NewPiece> news = newPieceService.listNews();
        model.addAttribute("news", news);
        return "index.html";
    }
    @GetMapping("/edit")
    public String indexlistedit(ModelMap model) {
        List<NewPiece> news = newPieceService.listNews();
        model.addAttribute("news", news);
        return "paneladmin.html";
    }

    @GetMapping("/edit/create")
    public String create(){
        return "newpiece.html";
    }

    @PostMapping("/edit/created")
    public String created(@RequestParam String title, @RequestParam String body, ModelMap model){
        try{
            newPieceService.createNewPiece(title, body);
            model.put("Sucess", "The new piece of news was uploaded correclty");

        } catch (MiException e) {
            model.put("error", e.getMessage());
            return "newpiece.html";
        }
        return "redirect:/edit";
    }
    @GetMapping("/edit/modify/{id}")
    public String modify(@PathVariable String id, ModelMap model){
        model.put("piece",newPieceService.getOne(id));
        return "modifypiece.html";
    }
    @PostMapping("/edit/modify/{id}")
    public String modify(@PathVariable String id, String title, String body, ModelMap model){
        try{
            newPieceService.modifyPiece(id, title, body);
            return "redirect:/edit";

        }catch (MiException e){
            model.put("error", e.getMessage());
            return "modifypiece.html";
        }
    }
    @GetMapping("/view/{id}")
    public String view(@PathVariable String id, ModelMap model){
        NewPiece newPiece = newPieceService.getOne(id);
        model.addAttribute("piece", newPiece);
        return "viewpiece.html";
    }
    @RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
    public String delete (@PathVariable String id, ModelMap model) throws MiException {
        newPieceService.deletePiece(id);
        List<NewPiece> news = newPieceService.listNews();
        model.addAttribute("news", news);
        return "paneladmin.html";
    }



}
