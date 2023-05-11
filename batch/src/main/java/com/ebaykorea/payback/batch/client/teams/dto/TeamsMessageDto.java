package com.ebaykorea.payback.batch.client.teams.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamsMessageDto {

  private String title;
  private String text;

}
