package pl.springExercises.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.springExercises.users.dto.GroupDto;
import pl.springExercises.users.service.GroupService;

import java.util.List;

@RestController
@RequestMapping(value = "/group")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public void createGroup(@RequestBody GroupDto groupDto) {
        groupService.createGroup(groupDto);
    }

    @GetMapping
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PutMapping(value = "/{groupId}")
    public void updateGroup(@RequestBody GroupDto groupDto, @PathVariable Long groupId) {
        groupDto.setId(groupId);
        groupService.updateGroup(groupDto);
    }

    @DeleteMapping(value = "/{groupId}")
    public void rmGroup(@PathVariable Long groupId) {
        groupService.rmGroup(groupId);
    }

}
