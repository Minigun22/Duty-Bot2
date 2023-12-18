package com.example.demo.serializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;

import com.example.demo.cadet.CadetRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CadetRepositorySerializer {
	public static void save(CadetRepository repository) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		InputStream is = CadetRepositorySerializer.class.getResourceAsStream("/repo.json");
		try (FileWriter writer = new FileWriter(new InputStreamResource(is).getFile())) {
			gson.toJson(repository, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static CadetRepository load() {
		InputStream is = CadetRepositorySerializer.class.getResourceAsStream("/repo.json");
		try (FileReader reader = new FileReader(new InputStreamResource(is).getFile())) {
			Gson gson = new Gson();
			return gson.fromJson(reader, CadetRepository.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveLog(String duty) {
		InputStream is = CadetRepositorySerializer.class.getResourceAsStream("/log.txt");
		try (FileWriter writer = new FileWriter(new InputStreamResource(is).getFile(), true)) {
			writer.write('\n'+duty);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
