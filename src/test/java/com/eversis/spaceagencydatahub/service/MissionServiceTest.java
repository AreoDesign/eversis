package com.eversis.spaceagencydatahub.service;

import com.eversis.spaceagencydatahub.assembler.MissionAssembler;
import com.eversis.spaceagencydatahub.dto.MissionDTO;
import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.repository.MissionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MissionServiceTest {
    @Mock
    private MissionRepository missionRepository;
    @Mock
    private MissionAssembler missionAssembler;

    @InjectMocks
    private MissionService missionService;

    private MissionDTO missionDTO;
    private Mission missionEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        missionDTO = new MissionDTO();
        missionEntity = new Mission();
    }

    @Test
    public void conversionWithSubsequentSaveTest() {
//        given
        BDDMockito.given(missionAssembler.convert(ArgumentMatchers.eq(missionDTO)))
                  .willReturn(missionEntity);
        BDDMockito.given(missionRepository.save(ArgumentMatchers.eq(missionEntity)))
                  .willReturn(missionEntity);
        BDDMockito.given(missionAssembler.convert(ArgumentMatchers.eq(missionEntity)))
                  .willReturn(missionDTO);
//        when
        missionService.add(missionDTO);
//        then
        Mockito.verify(missionAssembler, Mockito.times(1)).convert(ArgumentMatchers.eq(missionDTO));
        Mockito.verify(missionRepository, Mockito.times(1)).save(ArgumentMatchers.eq(missionEntity));
        Mockito.verify(missionAssembler, Mockito.times(1)).convert(ArgumentMatchers.eq(missionEntity));
    }

}