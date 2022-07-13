package helpers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileHandler {
	public static void write(String file, String input) {
		try {
			Files.write(Paths.get(file), input.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String read(String file) {
		String content = "";
		try {
			Path p = Paths.get(file);
			if(Files.exists(p)) {
				content = new String(Files.readAllBytes(p));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static <T> T readJsonAsObject(String file, Class<T> cls){		
		return new Gson().fromJson(FileHandler.read(file), (Type) cls);		
	}
	
	public static <T> void writeObjectAsJson(String file, T input){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileHandler.write(file, new String(gson.toJson(input).getBytes()));
	}


}
