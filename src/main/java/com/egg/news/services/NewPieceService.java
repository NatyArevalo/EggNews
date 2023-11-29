package com.egg.news.services;

import com.egg.news.entities.NewPiece;
import com.egg.news.exceptions.MiException;
import com.egg.news.repositories.NewPieceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewPieceService {
    @Autowired
    NewPieceRepository newPieceRepository;

    @Transactional
    public void createNewPiece(String title, String body) throws MiException {
        validate(title, body);
        NewPiece newPiece = new NewPiece();
        newPiece.setTitle(title);
        newPiece.setBody(body);
        newPiece.setAlta(new Date());
        newPieceRepository.save(newPiece);
    }
    @Transactional
    public void modifyPiece(String id, String title, String body) throws MiException {
        if (id == null || id.isEmpty()){
            throw new MiException("ID cannot be empty or null");
        }
        validate(title, body);
        Optional<NewPiece> response = newPieceRepository.findById(id);
        if (response.isPresent()){
            NewPiece newPiece = response.get();
            newPiece.setTitle(title);
            newPiece.setBody(body);
            newPieceRepository.save(newPiece);
        }
    }
    @Transactional
    public void deletePiece(String id) throws MiException {
        if (id == null || id.isEmpty()){
            throw new MiException("ID cannot be empty or null");
        }
        NewPiece newPiece = newPieceRepository.getById(id);
        newPieceRepository.delete(newPiece);

    }

    public NewPiece getOne(String id){
        return newPieceRepository.getOne(id);
    }
    public List<NewPiece> listNews(){
        List<NewPiece> news = new ArrayList<>();
        news = newPieceRepository.findAll();
        return news;
    }
    public void validate(String title, String body) throws MiException {
        if(title == null || title.trim().isEmpty()){
            throw new MiException("Title cannot be empty or null");
        }
        if(body == null || body.trim().isEmpty()){
            throw new MiException("News body cannot be empty");
        }
    }
}
