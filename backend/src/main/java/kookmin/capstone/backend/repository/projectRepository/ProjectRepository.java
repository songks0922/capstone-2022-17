package kookmin.capstone.backend.repository.projectRepository;


import kookmin.capstone.backend.domain.project.Project;
import kookmin.capstone.backend.repository.customProjectRepository.ProjectRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    Project findByTitle(String title);

    Optional<Project> findById(Long id);

}
