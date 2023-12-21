package com.example.demo.cadet;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class CadetRepository {
	private List<Cadet> group = new ArrayList<>(
			List.of(new Cadet(1, "Босненко"), new Cadet(2, "Віщун"), new Cadet(3, "Гвілдіс"), new Cadet(4, "Гетун"),
					new Cadet(5, "Даценко"), new Cadet(6, "Кльоц"), new Cadet(7, "Литвяков"), new Cadet(8, "Мініч"),
					new Cadet(9, "Селямієв"), new Cadet(10, "Сіваков"), new Cadet(11, "Хоменко")));
	private static List<Cadet> terkaAcceptList = new ArrayList<>();
	private static List<Cadet> cubarAcceptList = new ArrayList<>();
	private static List<Cadet> audAcceptList = new ArrayList<>();

	public String showAll() {
		String res = String.format("%12s%-4s%-4s%-4s\n","", "Тер","Куб","Ауд");
		return "<code>" + res + group.stream().map((Cadet c) -> String.format("%-3d%-9s%-4d%-4d%-4d",c.getId(),c.getSurname(),c.getTerkaCount(),c.getCubarCount(),c.getAudCount())).collect(Collectors.joining("\n")) + "</code>";
	}

	public void setAllFree() {
		group.stream().forEach(c -> c.setStatus(Status.FREE));
	}

	public void setTerkaFree() {
		terkaAcceptList.stream().forEach(c -> c.setStatus(Status.FREE));
	}

	public void setCubarFree() {
		cubarAcceptList.stream().forEach(c -> c.setStatus(Status.FREE));
	}

	public void setAudFree() {
		audAcceptList.stream().forEach(c -> c.setStatus(Status.FREE));

	}

	public void sortByTerka() {
		group.sort((c1, c2) -> {
			int result = Integer.compare(c1.getTerkaCount(), c2.getTerkaCount());
			return result == 0 ? Integer.compare(c1.getId(), c2.getId()) : result;

		});
	}

	public void sortById() {
		group.sort((c1, c2) -> Integer.compare(c1.getId(), c2.getId()));
	}

	public void sortByCubar() {
		group.sort((c1, c2) -> {
			int result = Integer.compare(c1.getCubarCount(), c2.getCubarCount());
			return result == 0 ? Integer.compare(c1.getId(), c2.getId()) : result;
		});
	}

	public void sortByAud() {
		group.sort((c1, c2) -> {
			int result = Integer.compare(c1.getAudCount(), c2.getAudCount());
			return result == 0 ? Integer.compare(c1.getId(), c2.getId()) : result;

		});
	}

	public String terka() {
		terkaAcceptList.clear();
		sortByTerka();
		String result = "";
		int capacity = 0;
		for (Cadet c : group) {
			if (c.getStatus().equals(Status.FREE) && capacity < 1) {
				terkaAcceptList.add(c);
				result += c.getSurname() + ' ';
				c.setStatus(Status.TAKEN);
				capacity++;
			}
		}
		return result;
	}

	public String terka(List<Integer> willing) {
		terkaAcceptList.clear();
		String result = "";
		for (Cadet c : group) {
			if (willing.contains(c.getId()) && c.getStatus().equals(Status.FREE)) {
				result += c.getSurname() + ' ';
				c.setStatus(Status.TAKEN);
				terkaAcceptList.add(c);
			}
		}
		return result;
	}

	public String cubar() {
		cubarAcceptList.clear();
		sortByCubar();
		String result = "";
		int capacity = 0;
		for (Cadet c : group) {
			if (c.getStatus().equals(Status.FREE) && capacity < 1) {
				cubarAcceptList.add(c);
				result += c.getSurname() + ' ';
				c.setStatus(Status.TAKEN);
				capacity++;
			}
		}
		return result;
	}

	public String cubar(List<Integer> willing) {
		cubarAcceptList.clear();
		String result = "";
		for (Cadet c : group) {
			if (willing.contains(c.getId()) && c.getStatus().equals(Status.FREE)) {
				result += c.getSurname() + ' ';
				c.setStatus(Status.TAKEN);
				cubarAcceptList.add(c);
			}
		}
		return result;
	}

	public String aud() {
		audAcceptList.clear();
		sortByAud();
		String result = "";
		int capacity = 0;
		for (Cadet c : group) {
			if (c.getStatus().equals(Status.FREE) && capacity < 1) {
				audAcceptList.add(c);
				result += c.getSurname() + ' ';
				c.setStatus(Status.TAKEN);
				capacity++;
			}
		}
		return result;
	}

	public String aud(List<Integer> willing) {
		audAcceptList.clear();
		String result = "";
		for (Cadet c : group) {
			if (willing.contains(c.getId()) && c.getStatus().equals(Status.FREE)) {
				result += c.getSurname() + ' ';
				c.setStatus(Status.TAKEN);
				audAcceptList.add(c);
			}
		}
		return result;
	}

	public void accept() {
		terkaAcceptList.stream().forEach(c -> c.itarationTerka());
		cubarAcceptList.stream().forEach(c -> c.itarationCubar());
		audAcceptList.stream().forEach(c -> c.itarationAud());
	}

}
