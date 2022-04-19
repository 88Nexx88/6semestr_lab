import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tools {
    public byte[] readFromFile(String path) throws IOException {
        byte[] array = Files.readAllBytes(Paths.get(path));
        for(byte b : array){
            System.out.print(b+" ");
        }
        System.out.println();
        return array;
    }

    public void writeToFile(String path, byte[] txt) throws IOException {
        Files.write(Paths.get(path), txt);

    }

    public List<Integer> readFromFileSymbol(String path) throws IOException {
        List<Integer> key = new ArrayList<>();
        FileReader fileIn = null;

        try {
            fileIn = new FileReader(path);

            int a;
            while ((a = fileIn.read()) != -1) {
                key.add(a);
            }
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
        return key;
    }

    public void writeToFileSymbol(String path, List<Integer> txt) throws IOException {
        FileWriter fileOut = null;

        try {
            fileOut = new FileWriter(path);
            for(int i = 0; i < txt.size(); i++){
                fileOut.write(txt.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    public void writeVectorKey(List<byte []> keys) throws IOException {
        FileWriter fileOut = null;

        try {
            fileOut = new FileWriter("round/keys");
            for(int i = 0; i < keys.size(); i++){
                for(byte b : keys.get(i)){
                    fileOut.write(b);
                }
                fileOut.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    public List<byte []> readVectorKey() throws IOException {
        List<byte []> all_keys = new ArrayList<>();
        FileReader fileIn = null;

        try {
            fileIn = new FileReader("round/keys.csv");

            int a;
            int c = 0;
            List<Byte> b = new ArrayList<>();
            while ((a = fileIn.read()) != -1) {
                b.add((byte) a);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
        return  all_keys;
    }



}
