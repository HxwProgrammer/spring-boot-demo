package com.example.gamedemo.collectionitem;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionInfoTest {
	public static void main(String[] args) {
		// 1. 컬렉션 목록
		Map<Integer, CollectionInfo> collectionInfoMap = collectionData();

		// 2. 컬렉션 목록에서 모아야 하는 아이템 ID
		List<CollectionItemInfo> collectionItemInfoList = collectionItemData();

		// do it. 아이템 ID 를 키로 하여 컬렉션 목록을 List 로 그루핑
		Map<Integer, List<CollectionInfo>> result = collectionItemInfoList.stream()
			.collect(groupingBy(CollectionItemInfo::getItemId, mapping(info -> collectionInfoMap.get(info.getCollectionId()), toList())));

		result.forEach((k, v) -> System.out.println("k:" + k + " v:" + v));
	}

	private static Map<Integer, CollectionInfo> collectionData() {
		Map<Integer, CollectionInfo> collectionInfoMap = new HashMap<>();
		collectionInfoMap.put(1, new CollectionInfo(1, "앨리스 키친 컬렉션"));
		collectionInfoMap.put(2, new CollectionInfo(2, "로즈우드 재봉 컬렉션"));
		collectionInfoMap.put(3, new CollectionInfo(3, "뒹굴뒹굴 아지트 컬렉션"));
		collectionInfoMap.put(4, new CollectionInfo(4, "스타홈즈 서재 컬렉션"));
		collectionInfoMap.put(5, new CollectionInfo(5, "산뜻한 봄날 컬렉션"));
		collectionInfoMap.put(6, new CollectionInfo(6, "네이비 도트 키친 컬렉션"));
		collectionInfoMap.put(7, new CollectionInfo(7, "해피바스 욕실 컬렉션"));
		collectionInfoMap.put(8, new CollectionInfo(8, "달님맞이 별빛골 컬렉션"));
		collectionInfoMap.put(9, new CollectionInfo(9, "피치블라썸 컬렉션"));
		collectionInfoMap.put(10, new CollectionInfo(10, "모락모락 바스룸 컬렉션"));
		return collectionInfoMap;
	}

	private static List<CollectionItemInfo> collectionItemData() {
		return Arrays.asList(
			new CollectionItemInfo(1, 1001),
			new CollectionItemInfo(1, 1001),

			new CollectionItemInfo(2, 1002),

			new CollectionItemInfo(3, 2001),

			new CollectionItemInfo(4, 3001),

			new CollectionItemInfo(5, 4001),

			new CollectionItemInfo(6, 5001),

			new CollectionItemInfo(7, 6001),
			new CollectionItemInfo(7, 6001),
			new CollectionItemInfo(7, 6001),
			new CollectionItemInfo(7, 6001),

			new CollectionItemInfo(8, 7001),

			new CollectionItemInfo(9, 8001),

			new CollectionItemInfo(10, 9001)
		);
	}
}
