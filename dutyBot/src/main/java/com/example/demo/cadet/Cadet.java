package com.example.demo.cadet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cadet {
	private int id = 0;
	private String surname;
	private int terkaCount;
	private int audCount;
	private int cubarCount;
	private Status status;

	public Cadet(int id, String surname) {
		super();
		this.id = id;
		this.surname = surname;
		status = Status.FREE;
	}

	public void itarationTerka() {
		this.terkaCount++;
	}

	public void itarationAud() {
		this.audCount++;
	}

	public void itarationCubar() {
		this.cubarCount++;
	}

}
