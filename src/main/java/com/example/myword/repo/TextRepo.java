package com.example.myword.repo;

import com.example.myword.model.entity.Text;
import com.example.myword.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextRepo extends JpaRepository<Text, Long> {

    Text findByURL(String url);

    List<Text> findAllByCreator(User creator);
}
