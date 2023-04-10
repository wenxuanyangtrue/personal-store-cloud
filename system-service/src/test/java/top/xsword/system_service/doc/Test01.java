package top.xsword.system_service.doc;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.junit.jupiter.api.Test;

/**
 * Author: ywx
 * Create Time: 2023/4/3
 * Description:
 */
public class Test01 {

    @Test
    public void t3() {
        Document document = new Document();
        document.loadFromFile("C:\\Users\\18255\\Desktop\\input.docx");
        document.saveToFile("C:\\Users\\18255\\Desktop\\input.pdf", FileFormat.PDF);
        document.dispose();
    }

    @Test
    public void t2() {
        Document document = new Document();
        document.loadFromFile("C:\\Users\\18255\\Desktop\\input.xml");
        document.saveToFile("C:\\Users\\18255\\Desktop\\input.docx", FileFormat.Docx);
        document.dispose();
    }

    @Test
    public void t1() {
        Document document = new Document();
        document.loadFromFile("C:\\Users\\18255\\Desktop\\阳文轩简历2.docx");
        document.saveToFile("C:\\Users\\18255\\Desktop\\input.xml", FileFormat.Word_Xml);
        document.dispose();
    }
}
