import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Encrypt {
	private final static String dir = System.getProperty("user.dir");
	private final static int CHARS_IN_ONE_LINE = 30;

	public static void main(String[] args) {
		String fileData = "";
		String input = dir + "\\input.txt";
		String output = dir + "\\bomb.kgb";
		String error = dir + "\\encryption_error.txt";
		RSA key = new RSA();
		int offset = 0;
		BigInteger encryptedMessage;
		String encryptedString, oneLine, errorString;
		FileOutputStream stream, errorStream;
		FileReader reader;

		try {
			reader = new FileReader(input);
			int i;
			while ((i = reader.read()) != -1)
				fileData += (char) i;

			stream = new FileOutputStream(output);
			while (fileData.length() - offset > CHARS_IN_ONE_LINE) {
				oneLine = fileData.substring(offset, offset + CHARS_IN_ONE_LINE);

				encryptedMessage = key.encrypt(RSA.stringToBigInt(oneLine));

				encryptedString = encryptedMessage.toString();

				stream.write(encryptedString.getBytes());
				stream.write(System.getProperty("line.separator").getBytes());
				offset += CHARS_IN_ONE_LINE;
			}

			if (fileData.length() - offset < CHARS_IN_ONE_LINE && fileData.length() - offset > 0) {
				oneLine = fileData.substring(offset);

				encryptedMessage = key.encrypt(RSA.stringToBigInt(oneLine));

				encryptedString = encryptedMessage.toString();

				stream.write(encryptedString.getBytes());
			}

			stream.close();
		} catch (FileNotFoundException e0) {
			try {
				errorStream = new FileOutputStream(error);

				errorString = "ERROR: input not found.";
				errorString += "Please ensure that there is a file named \"input.txt\" to encrypt.";
				errorString += "You may delete this file.";

				errorStream.write(errorString.getBytes());
				errorStream.close();
			} catch (Exception e) {
				System.out.println("You done goofed");
			}
			System.exit(0);
		} catch (IOException e1) {
			try {
				errorStream = new FileOutputStream(error);

				errorString = "ERROR: I/O Exception detected.";
				errorString += "You may delete this file.";

				errorStream.write(errorString.getBytes());
				errorStream.close();
			} catch (Exception e) {
				System.out.println("You done goofed");
			}
			System.exit(0);
		}
	}
}
