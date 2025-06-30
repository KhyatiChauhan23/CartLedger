package utilities;
import POJOs.ZeptoOrder;
import POJOs.BlinkitOrder;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class csvParser {
    public static List<ZeptoOrder> parseZeptoCSV(String filePath) {
        List<ZeptoOrder> orders = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                orders.add(new ZeptoOrder(
                        fields[0],
                        LocalDate.parse(fields[1]),
                        Double.parseDouble(fields[2]),
                        Double.parseDouble(fields[3]),
                        Double.parseDouble(fields[4]),
                        Double.parseDouble(fields[5]),
                        Double.parseDouble(fields[6]),
                        Double.parseDouble(fields[7])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static List<BlinkitOrder> parseBlinkitCSV(String filePath) {
        List<BlinkitOrder> orders = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                orders.add(new BlinkitOrder(
                        fields[0],
                        LocalDate.parse(fields[1]),
                        Double.parseDouble(fields[2]),
                        Double.parseDouble(fields[3]),
                        Double.parseDouble(fields[4]),
                        Double.parseDouble(fields[5])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
