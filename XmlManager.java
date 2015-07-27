/**
 * all about this class...
 */

package datamanagement;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import java.io.FileWriter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import java.io.IOException;
import org.jdom.JDOMException;



public class XmlManager
{
  //===========================================================================
  // Static data members
  //===========================================================================

  private static XmlManager instance_ = null;

  //===========================================================================
  // Instance data members
  //===========================================================================

  private Document document_;

  //===========================================================================
  // Static initializer
  //===========================================================================

  // null

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
  // Instance initializer
  //===========================================================================

  /**
   * singleton xmlManager is initialized from xml file
   */
  public void initialize()
  {
    String s =
      AppProperties.getInstance().getProperties().getProperty("XMLFILE");

    try {
      SAXBuilder b = new SAXBuilder();
      b.setExpandEntities(true);
      document_ = b.build(s);}

    catch (JDOMException e) {
      System.err.printf( "%s",
                         "DBMD: XMLManager : initialize : caught JDOMException\n" );

      throw new RuntimeException("DBMD: XMLManager : initialize : JDOMException");
    }

    catch (IOException e) {
      System.err.printf( "%s",
                         "DBMD: XMLManager : initialize : caught IOException\n" );

      throw new RuntimeException("DBMD: XMLManager : initialize : IOException");
    }
  }

  //===========================================================================
  // Instance constructors
  //===========================================================================

  /**
   * restrict access to constructor
   */
  private XmlManager()
  {
    initialize();
  }

  //===========================================================================
  // Instance methods
  //===========================================================================

  /**
   * saves document to file
   */
  public void saveDocument()
  {
    String xmlfile =
        AppProperties.getInstance().getProperties().getProperty("XMLFILE");

    try (FileWriter fout = new FileWriter(xmlfile)) {
      XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
      outputter.output( document_, fout);
      fout.close();
    }

    catch (IOException ioe) {
      System.err.printf("%s\n", "DBMD : XMLManager : saveDocument : Error " +
                        "saving XML to " + xmlfile);
      throw new RuntimeException("DBMD: XMLManager : saveDocument : error " +
                                 "writing to file");
    }
  }



  /**
   * @return document
   */
  public Document getDocument()
  {
    return document_;
  }



}
