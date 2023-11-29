package com.egg.news.repositories;

import com.egg.news.entities.NewPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewPieceRepository extends JpaRepository <NewPiece, String> {
}
