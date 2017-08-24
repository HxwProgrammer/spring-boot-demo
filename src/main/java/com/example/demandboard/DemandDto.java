package com.example.demandboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DemandDto {
	private DemandBoardInfo demandBoardInfo;
	private PlayerDemandInfo playerDemandInfo;
}
