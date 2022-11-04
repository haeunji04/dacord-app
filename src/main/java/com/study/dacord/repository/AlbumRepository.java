package com.study.dacord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.dacord.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long>{

}
