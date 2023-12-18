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
		try (FileWriter writer = new FileWriter("target/repo.json")) {
			gson.toJson(repository, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static CadetRepository load() {

		try (FileReader reader = new FileReader("target/repo.json")) {
			Gson gson = new Gson();
			return gson.fromJson(reader, CadetRepository.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveLog(String duty) {
		try (FileWriter writer = new FileWriter("target/log.txt", true)) {
			writer.write('\n'+duty);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
