package com.kbigdata.edu.myexception;

public class NameInputException extends SimpleException{

	private static final long serialVersionUID = 1L;

	public void print() {
		System.out.println("이름을 입력하지 않으셨습니다. 다시입력하세요");
	}
}
