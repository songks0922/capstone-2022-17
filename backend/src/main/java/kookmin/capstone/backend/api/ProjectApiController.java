package kookmin.capstone.backend.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kookmin.capstone.backend.domain.ProjectTech;
import kookmin.capstone.backend.domain.TechStack;
import kookmin.capstone.backend.domain.user.User;
import kookmin.capstone.backend.dto.ProjectDTO;
import kookmin.capstone.backend.dto.ProjectSearchCond;
import kookmin.capstone.backend.service.ProjectService;
import kookmin.capstone.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@Api(tags = {"프로젝트 API"})
public class ProjectApiController {

    private final ProjectService projectService;
    private final UserService userService;

    // 보낸 객체를 그대로
    @PostMapping("/v1/project/regist")
    @ApiOperation(value = "프로젝트 등록")
    public ProjectDTO registProject(@RequestBody ProjectDTO projectDTO) {
        projectService.registProject(projectDTO);
        return projectDTO;
    }

    @PostMapping("/v1/project/modify")
    @ApiOperation(value = "프로젝트 수정")
    public ProjectDTO modifyProject(@RequestBody ProjectDTO projectDTO) {
        projectService.modifyProject(projectDTO);
        return projectDTO;
    }

    @PostMapping("/v1/project/remove")
    @ApiOperation(value = "프로젝트 삭제")
    public ProjectDTO removeProject(@RequestBody ProjectDTO projectDTO) {
        projectService.removeProject(projectDTO.getId());
        return projectDTO;
    }

    @PostMapping("/v1/user")
    @ApiOperation(value = "수정해야 됨 테스트용")
    public CreateUserResponse registUser(@RequestBody User user) {
        Long id = userService.join(user);
        return new CreateUserResponse(id);
    }

    @GetMapping("/v1/project/list")
    @ApiOperation(value = "프로젝트 조회")
    public Long list(@RequestParam("page") Long page, @RequestBody ProjectSearchCond condition) {
        for (int i = 0; i < condition.getTeckStacks().size(); i++) {
            log.info(condition.getTeckStacks().get(i));
        }
        return page;
    }


    @Data @AllArgsConstructor
    static class CreatePojectResponse {
        private Long id;
    }

    @Data @AllArgsConstructor
    static class CreateUserResponse {
        private Long id;
    }

    @Getter
    static class TechStackDto {

        private String stack;

        public TechStackDto(TechStack techStack) {
            stack = techStack.getStack();
        }
    }
}
