package com.eversis.spaceagencydatahub.controller;

import com.eversis.spaceagencydatahub.dto.MissionDTO;
import com.eversis.spaceagencydatahub.service.MissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MissionControllerTest {

    @Mock
    private MissionService missionService;

    @InjectMocks
    private MissionController missionController;

    private MockMvc mockMvc;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(missionController).build();
    }

    @Test
    public void serviceInvokeTest() throws Exception {
//        given
        MissionDTO missionDTO = MissionDTO.builder()
                                          .name("Andromeda")
                                          .isActive(true)
                                          .build();
        BDDMockito.given(missionService.add(ArgumentMatchers.eq(missionDTO)))
                  .willReturn(missionDTO);
//        when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/missions")
                                                                      .contentType(MediaType.APPLICATION_JSON)
                                                                      .content(objectMapper.writeValueAsString(missionDTO));
        ResultActions resultActions = mockMvc.perform(request).andDo(MockMvcResultHandlers.log());
//        then
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        BDDMockito.verify(missionService, Mockito.times(1)).add(ArgumentMatchers.eq(missionDTO));
        Assertions.assertThat(missionDTO).isEqualTo(getMissionDTO(resultActions));
    }

    private MissionDTO getMissionDTO(ResultActions resultActions) throws java.io.IOException {
        String responseAsString = resultActions.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(responseAsString, MissionDTO.class);
    }
}