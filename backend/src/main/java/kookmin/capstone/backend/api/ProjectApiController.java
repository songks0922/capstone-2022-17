package kookmin.capstone.backend.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kookmin.capstone.backend.domain.TechStack;
import kookmin.capstone.backend.domain.member.Member;
import kookmin.capstone.backend.dto.memberDTO.RequestMemberDTO;
import kookmin.capstone.backend.dto.projectDTO.ProjectDTO;
import kookmin.capstone.backend.dto.projectDTO.ProjectPositionDTO;
import kookmin.capstone.backend.dto.projectDTO.ProjectSearchCond;
import kookmin.capstone.backend.exception.memberException.MemberAddException;
import kookmin.capstone.backend.exception.memberException.MemberException;
import kookmin.capstone.backend.exception.projectException.DuplicateProjectException;
import kookmin.capstone.backend.exception.projectException.ProjectException;
import kookmin.capstone.backend.response.DefalutResponse;
import kookmin.capstone.backend.response.MemberResDTO;
import kookmin.capstone.backend.response.ResponseMessage;
import kookmin.capstone.backend.response.StatusCode;
import kookmin.capstone.backend.service.ProjectService;
import kookmin.capstone.backend.service.UserService;
import kookmin.capstone.backend.service.jwt.JwtTokenService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@Api(tags = {"프로젝트 API"})
public class ProjectApiController {

    private final ProjectService projectService;
    private final JwtTokenService jwtTokenService;

    // 보낸 객체를 그대로
    @PostMapping("/v1/project")
    @ApiOperation(value = "프로젝트 등록")
    public ResponseEntity registProject(@RequestBody ProjectDTO projectDTO, HttpServletRequest request) throws ProjectException {
        // JWT 토큰에서 유저 ID 가져오기
        projectDTO.setUserId(jwtTokenService.get(request, "id", Long.class));
        try {
            projectService.registProject(projectDTO);
        } catch (DuplicateProjectException e) {
            return ResponseEntity.badRequest().body(DefalutResponse.res(StatusCode.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok(DefalutResponse.res(StatusCode.OK, ResponseMessage.PROJECT_ADD_SUCCESS, projectDTO));
    }

    @PatchMapping("/v1/project")
    @ApiOperation(value = "프로젝트 수정")
    public ProjectDTO modifyProject(@RequestBody ProjectDTO projectDTO, HttpServletRequest request) {
        projectDTO.setUserId(jwtTokenService.get(request, "id", Long.class));
        projectService.modifyProject(projectDTO);
        return projectDTO;
    }

    @DeleteMapping("/v1/project")
    @ApiOperation(value = "프로젝트 삭제")
    public void removeProject(@RequestParam(name = "id") Long id, HttpServletRequest request) {
        projectService.removeProject(id);
    }

    @GetMapping("/v1/project/list")
    @ApiOperation(value = "프로젝트 조회")
    public Long list(@RequestParam("page") Long page, @RequestBody ProjectSearchCond condition) {

        return page;
    }

    @GetMapping("/v1/project/position")
    @ApiOperation(value = "프로젝트 현황 조회")
    public ResponseEntity getProjectPositionList(@RequestParam Long id) {
        return ResponseEntity.ok(DefalutResponse.res(StatusCode.OK, ResponseMessage.POSITION_GET_SUCCESS ,projectService.findProjectPositions(id)));
    }

    @GetMapping("/v1/project")
    @ApiOperation(value = "프로젝트 단건 조회")
    public ResponseEntity getProjectOne(@RequestParam Long id) {
        return ResponseEntity.ok(DefalutResponse.res(StatusCode.OK, ResponseMessage.PROJECT_GET_SUCCESS, projectService.findProjectById(id)));
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
