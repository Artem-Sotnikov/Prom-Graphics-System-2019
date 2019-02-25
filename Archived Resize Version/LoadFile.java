import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoadFile {
	private String fileName = "src/savefiles/savefile.txt";
	private SaveFile saveFile;
	
	public LoadFile(String filename) {
		this.fileName = filename;
	}
	
	public void load() {
		try {
			FileInputStream f = new FileInputStream(new File(fileName));
			ObjectInputStream o = new ObjectInputStream(f);
			this.saveFile = (SaveFile) o.readObject();
			o.close();
			f.close();
		} catch (Exception e) {
			System.out.println("Error loading file.");
			System.exit(0);
		}
	}
	
	public void save() {
		try {
			FileOutputStream f = new FileOutputStream(new File(fileName));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(this.saveFile);
			o.close();
			f.close();
		} catch (Exception e) {
			System.out.println("Error saving file.");
			System.exit(0);
		}
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFilename(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the saveFile
	 */
	public SaveFile getSaveFile() {
		return saveFile;
	}

	/**
	 * @param saveFile the saveFile to set
	 */
	public void setSaveFile(SaveFile saveFile) {
		this.saveFile = saveFile;
	}
}