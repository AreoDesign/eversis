package com.eversis.spaceagencydatahub.service;

import com.eversis.spaceagencydatahub.assembler.MissionAssembler;
import com.eversis.spaceagencydatahub.dto.MissionDTO;
import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.repository.MissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service adds, edits and removes Missions - implementation for Process Content Administrator MISSIONS management
 */
@Service
@Slf4j
public class MissionService {

    private MissionRepository missionRepository;
    private MissionAssembler missionAssembler;

    public MissionService(MissionRepository missionRepository, MissionAssembler missionAssembler) {
        this.missionRepository = missionRepository;
        this.missionAssembler = missionAssembler;
    }

    public MissionDTO add(MissionDTO missionDTO) {
        Validate.notNull(missionDTO, "New mission cannot be null!");
        throwExceptionWhenMissionExists(missionDTO);
        Mission missionEntity = missionAssembler.convert(missionDTO);
        Mission mission = missionRepository.save(missionEntity);

        return missionAssembler.convert(mission);
    }

    public MissionDTO remove(String missionName) {
        Validate.notNull(missionName, "Existing mission name cannot be null!");
        Mission missionEntity = getMissionEntityByName(missionName);
        //record deactivation instead of remove
        missionEntity.setActive(false);
        missionRepository.save(missionEntity);
        return missionAssembler.convert(missionEntity);
    }

    public MissionDTO edit(MissionDTO missionDTO) {
        Validate.notNull(missionDTO, "Existing mission for edition cannot be null!");
        getMissionEntityByName(missionDTO.getName());
        Mission missionEntity = missionAssembler.convert(missionDTO);
        missionEntity = missionRepository.save(missionEntity);
        return missionAssembler.convert(missionEntity);
    }

    public List<MissionDTO> getAllMissions() {
        List<Mission> missions = missionRepository.findAll();
        return missions.stream().map(mission -> missionAssembler.convert(mission)).collect(Collectors.toList());
    }

    public MissionDTO getMissionByName(String name) {
        Mission missionEntity = getMissionEntityByName(name);
        return missionAssembler.convert(missionEntity);
    }

    public Mission getMissionEntityByName(String missionName) {
        return missionRepository.findById(missionName)
                                .orElseThrow(() -> {
                                    String errMsg = String.format("Mission name: %s not found.", missionName);
                                    log.error(errMsg);
                                    return new EntityNotFoundException(errMsg);
                                });
    }

    //***************************************************************
    //******************* PRIVATE METHODS SECTION *******************
    //***************************************************************
    private void throwExceptionWhenMissionExists(MissionDTO missionDTO) {
        boolean missionExistOnDb = missionRepository.findById(missionDTO.getName()).isPresent();

        if (missionExistOnDb) {
            String errMsg = String.format("Mission name '%s', exists already.", missionDTO.getName());
            log.error(errMsg);
            throw new EntityExistsException(errMsg);
        }
    }
}
