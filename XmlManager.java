package datamanagement;

import java.io.IOException;
import java.io.FileWriter;

import org.jdom.JDOMException;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;



/**
 * all about this class...
 */
public class XmlManager
  // or use same style as Java classes e.g. SAXBUilder ???
{
  //===========================================================================
  // Static variables
  //===========================================================================

  private static XmlManager instance_ = null;

  //===========================================================================
  // Instance variables
  //===========================================================================

  private Document document_;

  //===========================================================================
  // Constructors
  //===========================================================================

  /**
   * restrict access to constructor
   */
  private XmlManager()
  {
    initialize();
  }

  //===========================================================================
  // Static methods
  //===========================================================================

  /**
   * return singleton
   */
  public static XmlManager getInstance()
  {
    if (instance_ == null ) {
      instance_ = new XmlManager();

    }
    return instance_;
  }

  //===========================================================================
  // Instance methods: initializer
  //===========================================================================

  /**
   * singleton xmlManager is initialized from xml file
   */
  public void initialize()
  {
    String xmlFile =
        AppProperties.getInstance().getProperties().getProperty("XMLFILE");
    // xmlFileName ???
    // should be refactored - method & variable???

    try {
      SAXBuilder saxBuilder = new SAXBuilder();
      saxBuilder.setExpandEntities(true);
      document_ = saxBuilder.build(xmlFile);
      // create and use a private setDocument() method ???
    }

    catch (JDOMException exception) {
      System.err.printf( "%xmlfile", "DBMD: XMLManager : initialize : " +
                         "caught JDOMException\n" );
      // create string, log string and then use string in exception ???

      throw new RuntimeException("DBMD: XMLManager : initialize : " +
                                 "JDOMException");
    }

    catch (IOException exception) {
      System.err.printf("%xmlfile", "DBMD: XMLManager : initialize : " +
                         "caught IOException\n");

      throw new RuntimeException("DBMD: XMLManager : initialize : " +
                                 "IOException");
      // as above ???
    }
  }

  //===========================================================================
  // Instance methods: primary
  //===========================================================================

  /**
   * saves document to file
   */
  public void saveDocument()
  {
    String xmlFile =
        AppProperties.getInstance().getProperties().getProperty("XMLFILE");

    try (FileWriter fileWriter = new FileWriter(xmlFile)) {
      XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
      xmlOutputter.output( document_, fileWriter );
      fileWriter.close();
    }

    catch (IOException exception) {
      System.err.printf("%s\n", "DBMD : XMLManager : saveDocument : Error " +
                        "saving XML to " + xmlFile);
      throw new RuntimeException("DBMD: XMLManager : saveDocument : error " +
                                 "writing to file");
    }
  }

  //===========================================================================
  // Instance methods: accessors
  //===========================================================================

  /**
   * @return document
   */
  public Document getDocument()
  {
    return document_;
  }



}
