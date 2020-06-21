package com.Match_My_PC.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @Autowired
  private PCController controller;

  @Test
  public void contexLoads() {
    Assertions.assertThat(controller).isNotNull();
  }

  @Test
  public void getPC() throws Exception {

    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/pc"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers
            .content()
            .json(
                Stream
                    .of("MAC", "ASUS", "DELL")
                    .collect(Collectors.toList()).toString()));
  }

}
