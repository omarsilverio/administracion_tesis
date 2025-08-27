
import com.mycompany.maestria.Vista.Componentes.Alertas;
import java.io.File;
import javax.swing.JDialog;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author omarsilverio
 */
public class Documento {
    
    public static void main(String[] args) throws Exception {
        
        String SO = System.getProperty("os.name");
        File directorioCarpeta = null;
        if(SO.equalsIgnoreCase("Mac OS X")){            
           directorioCarpeta = new File("/Applications/Control_Maestria");
           if(!directorioCarpeta.exists()){
               if(!directorioCarpeta.mkdirs()){
                   System.out.println("No se pudo crear la carpeta");
               }                 
           }else return;
        }else if(SO.equalsIgnoreCase("Windows")){
            
        }
       
    }
        /*
        //PDDocument pdfDoc = PDDocument.load(new File(filePath) carga un documento existente
        //PDPage pdfPage = pdfDoc.getPage(iPage); //obtiene una pagina
        List<Integer> numeros = List.of(11, 8, 9, 15, 39, 1, 4, 83);
        
       // long resultado = numeros.stream().filter(num -> num > 10).count():
       
        try (PDDocument document = new PDDocument()) {            
            PDPage page = new PDPage(PDRectangle.LETTER); //pagina tamaño carta           
            document.addPage(page); //agregar pagina al documento
            PDPageContentStream contentStream = new PDPageContentStream(document, page); //sirve para escribir en la pagina
            
            // Text
            contentStream.setLeading(10); //salto de linea
            contentStream.beginText(); //inicio del texto
            contentStream.setFont(PDType1Font.COURIER, 12); //Fuente y tamaño de fuente
            contentStream.newLineAtOffset( page.getMediaBox().getWidth() /2, page.getMediaBox().getHeight() - 100); //corrdenadas donde inicia la escritura
            contentStream.showText("Lista de Adeudos");
            contentStream.newLine();  
            
           // contentStream.newLineAtOffset( 100, page.getMediaBox().getHeight() - 100); //corrdenadas donde inicia la escritura
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
            
            contentStream.showText("M15632563 Omar Silverio Ibañez 2");
            contentStream.newLine();  
            contentStream.showText("M15632563 Omar Silverio Ibañez 2");
            contentStream.endText();
            
            
            
           //PDImageXObject image = PDImageXObject.createFromByteArray(document, Documento.class.getResourceAsStream("/java.png").readAllBytes(), "Java Logo");
           // contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);
           
           contentStream.close();

            document.save("recursos/documentojava2.pdf");
       }**/
        
    
    
}
