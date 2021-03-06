/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gausstt;
import java.awt.Color;
import java.awt.Image;
import org.opencv.core.*;
import org.opencv.videoio.*;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FPS;


/**
 *
 * @author Eduardo
 */
public class gaussGUI {
 
  static EstadisticasPixel[][] Estadisticas1;
  static Mat frame;
  static VideoCapture camera;
  static JFrame jframe,jframe2;
  static JLabel vidpanel,vidpanel2;
  static int ref = 0;
  static int nRegiones = 0;
  static int deltaY = 80;

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> scId = new ArrayList<>();
        ArrayList<Integer> FreqId = new ArrayList<>();
                
        ArrayList<Integer> identifCandidato = new ArrayList<>();
        ArrayList<Integer> FreqIdCandidato = new ArrayList<>();
                
       
         long refTime = 0;
                     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    frame = new Mat();
    camera = new VideoCapture("ou.mp4");
    camera.set(CV_CAP_PROP_FPS,5);
    jframe = new JFrame("Proyecto perrón");
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    vidpanel = new JLabel();
    jframe.setContentPane(vidpanel);
       jframe.setSize(800, 500);
       jframe.setVisible(true);
       
     jframe2 = new JFrame("Proyecto perrón procesado");
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    vidpanel2 = new JLabel();
    jframe2.setContentPane(vidpanel2);
       jframe2.setSize(800, 500);
    jframe2.setVisible(true);
    // 307200 345600
    Estadisticas1 = new EstadisticasPixel[800][800];
//    for(int x = 0; x < 307200; x++){
//         Estadisticas1[x] = new EstadisticasPixel();
//    }
       Integer regObjetoPer = 0;
              
          //  ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
             int cont =0;
           Principal:   while (true) {
           // Thread.sleep(20);
        if (camera.read(frame)) {
          ref++;
         
            if(ref < 1){
              
                continue Principal;
               
            }
            
           
            int [] observacionRGB = new int[2];
          
            ImageIcon image = new ImageIcon(Mat2bufferedImage(frame));
            vidpanel.setIcon(image);
          
            Image img = image.getImage();
            BufferedImage buf = (BufferedImage) img;
         
          
//            exec.scheduleAtFixedRate(new Runnable() {
//              @Override
//              public void run() {
                  BufferedImage buf2 = new BufferedImage(frame.cols(), frame.rows(),BufferedImage.TYPE_INT_RGB);
                //  System.out.println("fr "+frame.cols()*frame.rows());
            
                 // boolean sw = false;
                  
                     //int conta =0;
                    
                  for (int j = 0; j < frame.rows(); j++) {
                    for(int h = 0; h < frame.cols(); h++){
                        int regActual =0;
                        int colorActual =0;
                        
                         Color col = new Color(buf.getRGB(h, j));
          //              System.out.println(""+col.getGreen());
                        observacionRGB[0] = col.getGreen();
                        observacionRGB[1] = col.getRed();
                        //inicializar medias
                        if(ref == 1){
                            Estadisticas1[h][j] = new EstadisticasPixel();
                           for(int k = 0; k < 3; k++){
                              
                             Estadisticas1[h][j].getMezclaPix().gaussMuestra[k].setMedia(observacionRGB);
                           
                           }
                        }
                        
                       //Verificar si es pixel de primer plano, y actualizar datos de las distribuciones
                        Estadisticas1[h][j].actualizarObservacion(observacionRGB);
                     
                         
                       //Operaciones sobre el pixel actual, basadas en si es primer plano o no
                        if(Estadisticas1[h][j].getpPlano()){
                           if(!Estadisticas1[h][j].isRegion()){
          
                                
                                    
                               try {
                                   
                           //   int regevidence = 0;
                 
//           
                                            for(int y = 0; y < deltaY; y++){
                                                
                                                 if(Estadisticas1[h][j+y].isRegion()){
                                                        regActual = Estadisticas1[h][j+y].getRegion();
                                                        colorActual = Estadisticas1[h][j+y].getRegioncolor();
                                               //         regevidence++;
                                                        break;
                                                    }
                                                 
                                                    if(Estadisticas1[h][j-y].isRegion()){
                                                        regActual = Estadisticas1[h][j-y].getRegion();
                                                        colorActual = Estadisticas1[h][j-y].getRegioncolor();
                                               //         regevidence++;
                                                        break;
                                                    }
                                                
                                                for(int x = 0; x < deltaY; x++){
                                                 
                                                    if(Estadisticas1[h+x][j].isRegion()){
                                                        regActual = Estadisticas1[h+x][j].getRegion();
                                                        colorActual = Estadisticas1[h+x][j].getRegioncolor();
                                             //           regevidence++;
                                                        break;
                                                    }
                                                  
                                                   
                                                     if(Estadisticas1[h-x][j].isRegion()){
                                                        regActual = Estadisticas1[h-x][j].getRegion();
                                                        colorActual = Estadisticas1[h-x][j].getRegioncolor();
                                              //          regevidence++;
                                                        break;
                                                    }
                                                   
                                                  
                                               }
                                            }
                                            
                                            
                                            if(regActual == 0){
                                                 regActual = nuevaRegion();
                                                 colorActual = crearColorRegion();
                                             
                                            }
                                    
                                            //System.err.println("regev "+regevidence);
                                          //  if(regevidence > 130){
                                              for(int y = 0; y < deltaY; y++){
                                                
                                                 if(Estadisticas1[h][j+y].getpPlano()){
                                                     
                                                      Estadisticas1[h][j+y].setRegion(regActual);
                                                       Estadisticas1[h][j+y].setRegioncolor(colorActual);
                                                          buf2.setRGB(h, j+y, colorActual);
                                                     
                                                       
                                                    }
                                                   if(Estadisticas1[h][j-y].getpPlano()){
                                                   
                                                    
                                                      Estadisticas1[h][j-y].setRegion(regActual);
                                                       Estadisticas1[h][j-y].setRegioncolor(colorActual);
                                                          buf2.setRGB(h, j-y, colorActual);
                                                       
                                                       
                                                    }
                                                
                                                for(int x = 0; x < deltaY; x++){
                                                  
                                                       if(Estadisticas1[h+x][j].getpPlano()){
                                                          
                                                        Estadisticas1[h+x][j].setRegion(regActual);
                                                        Estadisticas1[h+x][j].setRegioncolor(colorActual);
                                                        buf2.setRGB(h+x, j, colorActual);
                                                           
                                                       }
                                                    
                                                     
                                                     
                                                    
                                                     
                                                     if(Estadisticas1[h-x][j].getpPlano()){
                                                         
                                                        Estadisticas1[h-x][j].setRegion(regActual);
                                                        Estadisticas1[h-x][j].setRegioncolor(colorActual);
                                                        buf2.setRGB(h-x, j, colorActual);
                                                     }
                                                     
                                                    
                                             
                                               
                                                //}
                                                
                                              }
                                            }
                                            
                                              
                                               } catch (Exception e) {
                              
                                               }
                              
                                            
                                            
//                                                
//                                                
//                                                    if( (h-x) == frame.cols() || (h+x) == frame.cols() || (j-x) == frame.rows() || (j+x) == frame.rows()){
//                                                        continue;
//                                                    }

//                                        
//                                        

                                   
                                
                                ///////////
                            }else{
                               
                               if(regObjetoPer !=  0){
                                   
                                   if(Estadisticas1[h][j].getRegion() == regObjetoPer){
                                       Estadisticas1[h][j].setRegioncolor(Color.RED.getRGB());
                                   }
                                   
                               }
                              
                              buf2.setRGB(h, j, Estadisticas1[h][j].getRegioncolor());
                              int posIdentif = scId.indexOf(Estadisticas1[h][j].getRegion());
                                 if(posIdentif == -1){
                                     scId.add (Estadisticas1[h][j].getRegion());
                                     FreqId.add(1);
                                  
                                  
                                  }else{
                                  
                                  
                                  int contAct = FreqId.get(posIdentif);
                                  FreqId.set(posIdentif, contAct+1);
                              }
                              
                              
                            }                         
                            
                       //    buf2.setRGB(h, j, 255-255-255);
                        }else{
                            Estadisticas1[h][j].setRegion(0);
                            Estadisticas1[h][j].setRegioncolor(0);
                           buf2.setRGB(h, j, 0-0-0);
                        }
                     
            
                    }
                }
                  
//                  for(Integer intAct: scId ){
//                      System.out.println("En sc "+intAct);
//                  }
                  int contfreq = 0;
                  for(Integer freqact: FreqId){
                     // System.out.println("frecuencia "+freqact); 150 1800  400 800
                     if(freqact > 400 && freqact < 800){
                         FreqIdCandidato.add(freqact);
                         identifCandidato.add(scId.get(contfreq));
                     }
                     
                         
                     contfreq++;
                  }
                  
                  int contfreq2 =0;
                      if(cont == 70){
                          
                          for( Integer idCandidatos: identifCandidato){
                              
                          if(scId.indexOf(idCandidatos) == -1){
             
                              //System.err.println("ID desaparecido");
                          
                          }else{
                              
                              if(FreqId.get(scId.indexOf(idCandidatos)) < FreqIdCandidato.get(contfreq2)){
                                  regObjetoPer = idCandidatos;
                             //     System.err.println("Hay un objeto tam "+FreqIdCandidato.get(contfreq2));
                              
                          
                             }
                          }
                              contfreq2++;
                        
                           }
                          
                            FreqIdCandidato.clear();
                          identifCandidato.clear();
//                                 if(freqact > FreqIdCandidato.get(identifCandidato.indexOf(scId.get(contfreq)))){
//                                     System.err.println("Existe un obj ");
//                                 }else{
//                                     FreqIdCandidato.remove(identifCandidato.indexOf(scId.get(contfreq)));
//                                 }
                                cont =0;
                      }
                  
                  
                  scId.clear();
                  FreqId.clear();
                  
                 
                  
                  cont++;
                ImageIcon img2 = new ImageIcon(buf2);
                vidpanel2.setIcon(img2);
              
              }
//          }, 0, 0, TimeUnit.MILLISECONDS);
               
            vidpanel2.repaint();
            
            vidpanel.repaint();
       
                    
//               long endTime = System.currentTimeMillis();
//                refTime += (endTime - startTime);
                
        }
        
    }
    
    /**
     *
     * @return
     */
    public static int crearColorRegion(){
        
         int r = (int)(Math.random()*256); //red
         int g = (int)(Math.random()*256); //green
         int b = (int)(Math.random()*256); //blue
 
        int p = (r<<16) | (g<<8) | b; //pixel
        
        return p;
//                                
        
    }
    
    public static int nuevaRegion(){
        return   nRegiones++;
    }
       
    
  
  private static BufferedImage Mat2bufferedImage(Mat m) {
         //Method converts a Mat to a Buffered Image
    int type = BufferedImage.TYPE_BYTE_GRAY;
     if ( m.channels() > 1 ) {
         type = BufferedImage.TYPE_3BYTE_BGR;
     }
     int bufferSize = m.channels()*m.cols()*m.rows();
     byte [] b = new byte[bufferSize];
     m.get(0,0,b); // get all the pixels
     BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
     final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
     System.arraycopy(b, 0, targetPixels, 0, b.length);  
     return image;
  }


  
 }
        
//        Thread hilo1;
//        trabajoGauss gau = new trabajoGauss();
//        hilo1 = new Thread(gau);
//        hilo1.start();
 
    




