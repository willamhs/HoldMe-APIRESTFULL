    package com.holdme.holdmeapi_restfull.repository;

    import com.holdme.holdmeapi_restfull.model.entity.Category;
    import org.springframework.data.jpa.repository.JpaRepository;
    import java.util.Optional;

    public interface CategoryRepository extends JpaRepository<Category, Integer> {
        Optional<Category> findByName(String name);

    }
