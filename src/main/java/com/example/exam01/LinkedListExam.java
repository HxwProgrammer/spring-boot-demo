package com.example.exam01;

public class LinkedListExam {
	public static void main(String[] args) {
		/*
		 * [Head]->[[10][L]] -> [[20][L]] -> [[30][L]] -> [[40][L]] <- [Tail]
		 *           |   |
		 *           |   ---> Link Field
		 *           ---> Data field
		 */

		LinkedList numbers;

		numbers = new LinkedList();
		numbers.addLast(10);
		numbers.addLast(20);
		numbers.addLast(30);
		System.out.println(numbers);

		numbers = new LinkedList();
		numbers.addLast(5);
		numbers.addLast(10);
		numbers.addLast(20);
		numbers.addLast(30);
		System.out.println("before:" + numbers);
		System.out.println("removed:" + numbers.removeFirst());
		System.out.println("after:" + numbers);

		numbers = new LinkedList();
		numbers.addLast(10);
		numbers.addLast(15);
		numbers.addLast(20);
		numbers.addLast(30);
		System.out.println("before:" + numbers);
		System.out.println("removed:" + numbers.remove(1));
		System.out.println("after:" + numbers);
	}
}

class LinkedList {

	// 첫번째 노드를 가리키는 필드
	private Node head;

	// 마지막 노드를 가리키는 필드
	private Node tail;

	private int size;

	private class Node {
		private Object data;
		private Node next;

		public Node(Object data) {
			this.data = data;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder("Node{");
			builder.append("data=")
				.append(data)
				.append("next=")
				.append(next)
				.append("}");

			return builder.toString();
		}
	}

	public int size() {
		return size;
	}

	public Object get(int k) {
		Node temp = node(k);
		return temp.data;
	}

	public int indexOf(Object data) {
		// 탐색 대상이 되는 노드를 temp 로 지정
		Node temp = head;

		// 탐색 대상이 몇번째 엘리먼트에 있는지
		int index = 0;

		// 탐색 값과 탐색 대상의 값을 비교
		while (temp.data != data) {
			temp = temp.next;
			index++;

			// temp 의 값이 null 이라는 것은 더 이상 탐색 대상이 없음
			if (temp == null) {
				return -1;
			}
		}

		// 탐색 대상을 찾았다면 대상의 인덱스 값을 리턴
		return index;
	}

	// 시작에 노드 추가
	public void addFirst(Object data) {
		// 새 노드를 생성
		Node newNode = new Node(data);

		// 새 노드의 다음 노드로 head 를 지정
		newNode.next = head;
		size++;

		head = newNode;

		if (head.next == null) {
			tail = head;
		}
	}

	// 끝에 추가
	public void addLast(Object data) {
		// 새 노드를 생성
		Node newNode = new Node(data);

		// 리스트의 노드가 없으면 첫번째 노드를 추가하는 메소드를 사용.
		if (size == 0) {
			addFirst(data);
			return;
		}

		// 마지막 노드의 다음 노드로 생성한 노드를 지정
		tail.next = newNode;

		// 마지막 노드 갱신
		tail = newNode;

		size++;
	}

	public Node node(int index) {
		Node x = head;
		for (int i = 0; i < index; i++) {
			x = x.next;
		}
		return x;
	}

	// 중간에 추가
	public void add(int k, Object data) {
		if (k == 0) {
			addFirst(data);
			return;
		}

		Node temp1 = node(k - 1);

		// k 번째 노드를 temp2로 지정
		Node temp2 = temp1.next;

		// 새로운 노드 생성
		Node newNode = new Node(data);

		// temp1의 다음 노드로 새로운 노드를 지정
		temp1.next = newNode;

		// 새로운 노드의 다음 노드로 temp2를 지정
		newNode.next = temp2;

		size++;

		// 새로운 노드의 다음 노드가 없다면 새로운 노드가 마지막 노드이기 때문에 tail 로 지정
		if (newNode.next == null) {
			tail = newNode;
		}
	}

	// 처음 노드 삭제
	public Object removeFirst() {

		// 첫번째 노드를 temp 로 지정하고 head 의 값을 두번째 노드로 변경
		Node temp = head;
		head = temp.next;

		size--;

		// 데이터 삭제전 삭제 데이터를 임시 변수에 담기
		return temp.data;
	}

	// 중간의 데이터를 삭제
	public Object remove(int k) {
		if (k == 0) {
			return removeFirst();
		}

		Node temp = node(k - 1);
		Node deleteNode = temp.next;
		temp.next = temp.next.next;

		Object deleteData = deleteNode.data;

		if (deleteData == tail) {
			tail = temp;
		}

		size--;

		return deleteData;
	}

	@Override
	public String toString() {
		if (head == null) {
			return "[]";
		}

		Node temp = head;

		StringBuilder builder = new StringBuilder("[");

		while (temp.next != null) {
			builder.append(temp.data).append(",");
			temp = temp.next;
		}

		builder.append(temp.data).append("]");

		return builder.toString();
	}
}

