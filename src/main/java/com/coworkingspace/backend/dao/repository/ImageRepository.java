package com.coworkingspace.backend.dao.repository;

import com.coworkingspace.backend.dao.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

	List<Image> getByImageStorageId(String id);
}