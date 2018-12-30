package com.eversis.spaceagencydatahub.controller;

import com.eversis.spaceagencydatahub.dto.MissionDTO;
import com.eversis.spaceagencydatahub.service.MissionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class MissionController {

    private MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping("/missions")
    public Set<MissionDTO> getMissions(){
        return missionService.getAllMissions();
    }

    @GetMapping("/missions/{missionName}")
    public MissionDTO getMission(@PathVariable String missionName){
        return missionService.getMissionByName(missionName);
    }

    @PostMapping("/missions")
    public MissionDTO addMission(@RequestBody MissionDTO missionDTO) {
        return missionService.add(missionDTO);
    }

    @PutMapping("/missions")
    public MissionDTO editMission(@RequestBody MissionDTO missionDTO) {
        return missionService.edit(missionDTO);
    }

    @DeleteMapping("/missions/{missionName}")
    public MissionDTO removeMission(@PathVariable String missionName) {
        return missionService.remove(missionName);
    }

}
