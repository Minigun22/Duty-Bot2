package com.example.demo.serializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.example.demo.cadet.CadetRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CadetRepositorySerializer {
	public static void save(CadetRepository repository) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter("repo.json")) {
			gson.toJson(repository, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static CadetRepository load() {
		try (FileReader reader = new FileReader("repo.json")) {
			Gson gson = new Gson();
			return gson.fromJson(reader, CadetRepository.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveLog(String duty) {
		try (FileWriter writer = new FileWriter("log.txt", true)) {
			writer.write('\n'+duty);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
