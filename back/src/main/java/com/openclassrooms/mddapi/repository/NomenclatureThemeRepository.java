package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.NomenclatureTheme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomenclatureThemeRepository extends CrudRepository<NomenclatureTheme, Long>{
}

