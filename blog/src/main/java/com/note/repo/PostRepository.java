package com.note.repo;

import com.note.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long>
{
    @Query("select s from Post s where title like %?1% or text like %?1%")
    List<Post> findByTitle(String title);

}
