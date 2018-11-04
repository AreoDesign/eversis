package com.eversis.spaceagencydatahub.assembler;

import com.eversis.spaceagencydatahub.dto.MissionDTO;
import com.eversis.spaceagencydatahub.entity.Mission;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MissionAssembler {

    public Mission convert(MissionDTO missionDTO) {
        Mission mission = null;
        if (Objects.nonNull(missionDTO)) {
            mission = Mission.builder(missionDTO.getName())
                             .imageType(missionDTO.getImageType())
                             .startDate(missionDTO.getStartDate())
                             .endDate(missionDTO.getEndDate())
                             .isActive(missionDTO.isActive())
                             .deactivationDate(missionDTO.getDeactivationDate())
                             .build();
        }

        return mission;
    }

    public MissionDTO convert(Mission mission) {
        MissionDTO missionDTO = null;
        if (Objects.nonNull(mission)) {
            missionDTO = MissionDTO.builder()
                                   .name(mission.getName())
                                   .imageType(mission.getImageType())
                                   .startDate(mission.getStartDate())
                                   .endDate(mission.getEndDate())
                                   .isActive(mission.isActive())
                                   .deactivationDate(mission.getDeactivationDate())
                                   .build();
        }

        return missionDTO;
    }
}
