package com.example.demanding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DemandingDto {
	private DemandingBoardInfo demandingBoardInfo;
	private PlayerDemandingInfo playerDemandingInfo;
}
