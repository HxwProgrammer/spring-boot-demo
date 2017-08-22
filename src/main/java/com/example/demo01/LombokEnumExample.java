package com.example.demo01;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LombokEnumExample {
	public static void main(String[] args) {
		System.out.println("ItemTypeEnum.NONE:" + ItemTypeEnum.NONE.value);
		System.out.println("ItemTypeEnum.GOLD:" + ItemTypeEnum.GOLD.value);
		System.out.println("ItemTypeEnum.CASH:" + ItemTypeEnum.CASH.value);
		System.out.println("ItemTypeEnum.SOCIAL:" + ItemTypeEnum.SOCIAL.value);
		System.out.println("ItemTypeEnum.INVENTORY:" + ItemTypeEnum.INVENTORY.value);
	}

	@Getter
	@AllArgsConstructor
	public enum ItemTypeEnum {
		NONE(0), GOLD(1), CASH(2), SOCIAL(3), INVENTORY(4);
		int value;
	}
}
