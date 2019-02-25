import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * LoadFile: This class will load/save a file.
 * 
 * @author Artem
 * @author Anthony
 */
public class LoadFile {
	private String fileName = "src/savefiles/default.txt";
	private SaveFile saveFile;

	/**
	 * Class constructor.
	 * 
	 * @param filename the file name
	 */
	public LoadFile(String filename) {
		this.fileName = filename;
	}

	/**
	 * This method will load a file.
	 */
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

	/**
	 * This method will save to a file.
	 */
	public void save() {
		saveFile.resetStates();
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
	public void setFileName(String fileName) {
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