package com.rizzywebworks.InspireHub.repository;

import com.rizzywebworks.InspireHub.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

}
