package pl.springExercises.users.service;


import org.springframework.stereotype.Service;
import pl.springExercises.users.dto.GroupDto;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.entity.GroupEntity;
import pl.springExercises.users.entity.UserEntity;
import pl.springExercises.users.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupDto createGroup(GroupDto groupDto) {
        GroupEntity groupEntity = convertDTOEntity(groupDto);
        GroupEntity savedGroupe = groupRepository.save(groupEntity); //wrzucenie usera do bazy, wraz ze zwrotką encji z bazy.
        GroupDto savedGroupDto = convertEntityToDTO(savedGroupe);
        return savedGroupDto;
    }

    public List<GroupDto> getAllGroups() {
        List<GroupEntity> all = groupRepository.findAll();
        List<GroupDto> result = new ArrayList<>();

        for (GroupEntity group : all) {
            result.add(convertEntityToDTO(group));
        }

//        List<UserDto> result2 = all.stream().map(this::convertUserToDTO).collect(Collectors.toList());
        return result;
    }

    private GroupEntity convertDTOEntity(GroupDto groupDto) { //z wyswietlenia do bazy
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(groupDto.getId());
        groupEntity.setName(groupDto.getName());
        return groupEntity;
    }

    private GroupDto convertEntityToDTO(GroupEntity groupEntity) { //z bazy do wyswietlenia
        GroupDto groupDto = new GroupDto();
        groupDto.setId(groupEntity.getId());
        groupDto.setName(groupEntity.getName());
        return groupDto;
    }


    public GroupDto updateGroup(GroupDto groupDto) {
        return createGroup(groupDto);
    }

    public void rmGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }


}
