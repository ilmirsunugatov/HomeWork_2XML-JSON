import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json);
    }

    public static List<Employee> parseXML(String fileName) {
        List<Employee> people = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    NodeList node1 = element.getElementsByTagName("id");
                    String id = node1.item(0).getTextContent();
                    NodeList node2 = element.getElementsByTagName("firstName");
                    String firstName = node2.item(0).getTextContent();
                    NodeList node3 = element.getElementsByTagName("lastName");
                    String lastName = node3.item(0).getTextContent();
                    NodeList node4 = element.getElementsByTagName("country");
                    String country = node4.item(0).getTextContent();
                    NodeList node5 = element.getElementsByTagName("age");
                    String age = node5.item(0).getTextContent();
                    people.add(new Employee(Long.parseLong(id), firstName, lastName, country, Integer.parseInt(age)));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return people;
    }

    private static void writeString(String json) {
        try (FileWriter file = new FileWriter("data.json")) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }
}
