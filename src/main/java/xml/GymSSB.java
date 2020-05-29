package xml;

import model.EquipmentDTO;
import model.GymDTO;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static constants.XMLNodes.*;

public class GymSSB implements SBRemote<GymDTO> {

    private static final String FILE_PATH = "D:\\My Documents\\Java\\TeamProjects\\EEP\\src\\main\\java\\xml\\";
    private static final String FILE_NAME = "gym";
    private static final String FILE_EXT_XML = ".xml";
    private static final String FILE_EXT_XSD = ".xsd";

    @Override
    public File exportXML(GymDTO obj) throws JAXBException, FileNotFoundException, SAXException {
        JAXBContext context = JAXBContext.newInstance(GymDTO.class, EquipmentDTO.class);

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(FILE_PATH + FILE_NAME + FILE_EXT_XSD));

        Marshaller marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(obj, new FileOutputStream(FILE_PATH + FILE_NAME + FILE_EXT_XML));

        return new File(FILE_PATH + FILE_NAME + FILE_EXT_XML);
    }

    @Override
    public GymDTO importXML(File file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        GymDTO gymDTO = new GymDTO();
        List<EquipmentDTO> equipmentDTOList = new ArrayList<>();
        List<List<String>> bodyGroupsList = new ArrayList<>();

        if (document.hasChildNodes()) {
            fetchNodes(document.getChildNodes(), gymDTO, equipmentDTOList, bodyGroupsList, null);
        }

        for (int i = 0; i < bodyGroupsList.size(); i++) {
            Set<String> bodyGroups = new HashSet<>(bodyGroupsList.get(i));
            equipmentDTOList.get(i).setBodyGroups(bodyGroups);
        }

        gymDTO.setEquipment(new ArrayList<>(equipmentDTOList));
        
        return gymDTO;
    }

    private void fetchNodes(
            NodeList nodes, GymDTO gymDTO, List<EquipmentDTO> equipmentDTOList, List<List<String>> bodyGroupsList,
            String currentParentNode
    ) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node elemNode = nodes.item(i);
            if (elemNode.getNodeType() == Node.ELEMENT_NODE) {
                switch (elemNode.getNodeName()) {
                    case GYM_NODE:
                    case BODY_GROUP_NODE:
                        currentParentNode = elemNode.getNodeName();
                        break;
                    case EQUIPMENT_NODE:
                        currentParentNode = elemNode.getNodeName();
                        equipmentDTOList.add(new EquipmentDTO());
                        break;
                    case BODY_GROUPS_NODE:
                        currentParentNode = elemNode.getNodeName();
                        bodyGroupsList.add(new ArrayList<>());
                        break;
                    default:
                        break;
                }

                switch (currentParentNode) {
                    case GYM_NODE:
                        if (elemNode.hasAttributes()) {
                            gymDTO.setId(getAttrVal(elemNode.getAttributes()));
                        }
                        switch (elemNode.getNodeName()) {
                            case NAME:
                                gymDTO.setName(elemNode.getTextContent());
                                break;
                            case LOGO_PATH:
                                gymDTO.setLogoPath(elemNode.getTextContent());
                                break;
                            case WEBSITE_URL:
                                gymDTO.setWebsiteURL(elemNode.getTextContent());
                                break;
                            case WEBSITE:
                                gymDTO.setWebsite(elemNode.getTextContent());
                                break;
                            case PHONE:
                                gymDTO.setPhone(elemNode.getTextContent());
                                break;
                            case ADDRESS:
                                gymDTO.setAddress(elemNode.getTextContent());
                                break;
                            default:
                                break;
                        }
                        break;
                    case EQUIPMENT_NODE:
                        EquipmentDTO equipmentDTO = equipmentDTOList.get(equipmentDTOList.size() - 1);
                        if (elemNode.hasAttributes()) {
                            equipmentDTO.setId(getAttrVal(elemNode.getAttributes()));
                        }
                        switch (elemNode.getNodeName()) {
                            case NAME:
                                equipmentDTO.setName(elemNode.getTextContent());
                                break;
                            case DESCRIPTION:
                                equipmentDTO.setDescription(elemNode.getTextContent());
                                break;
                            case IMG_PATH:
                                equipmentDTO.setImgPath(elemNode.getTextContent());
                                break;
                            default:
                                break;
                        }
                        break;
                    case BODY_GROUP_NODE:
                        List<String> bodyGroups = bodyGroupsList.get(bodyGroupsList.size() - 1);
                        if (elemNode.getNodeName().equals(BODY_GROUP_NODE)) {
                            bodyGroups.add(elemNode.getTextContent());
                        }
                        break;
                }

                if (elemNode.hasChildNodes()) {
                    fetchNodes(elemNode.getChildNodes(), gymDTO, equipmentDTOList, bodyGroupsList, currentParentNode);
                }
            }
        }
    }

    private int getAttrVal(NamedNodeMap nodeMap) {
        return Integer.parseInt(nodeMap.item(0).getNodeValue());
    }
}
