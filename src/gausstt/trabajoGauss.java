/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gausstt;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FPS;

/**
 *
 * @author Eduardo
 */
public class trabajoGauss implements Runnable{
    private final EstadisticasPixel[] Estadisticas1;
 private final Mat frame;
  private final VideoCapture camera;
  private final JFrame jframe,jframe2;
  private final JLabel vidpanel,vidpanel2;
  private int ref = 0;
  public trabajoGauss(){
       long refTime = 0;
                     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    frame = new Mat();
    camera = new VideoCapture("video1.avi");
    //camera.set(CV_CAP_PROP_FPS,25);
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
    Estadisticas1 = new EstadisticasPixel[307200];
    for(int x = 0; x < 307200; x++){
         Estadisticas1[x] = new EstadisticasPixel();
    }       
  
  }
  
  
   @Override
    public void run() {
        
        
           Principal:   while (true) {
    
        if (camera.read(frame)) {
          ref++;
            if(ref < 120){
                continue Principal;
            }
            int [] observacionRGB = new int[2];
          
            ImageIcon image = new ImageIcon(Mat2bufferedImage(frame));
            vidpanel.setIcon(image);
          
            Image img = image.getImage();
            BufferedImage buf = (BufferedImage) img;
             
            
              
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(new Runnable() {
              @Override
              public void run() {
                  BufferedImage buf2 = new BufferedImage(frame.cols(), frame.rows(),BufferedImage.TYPE_INT_RGB);
                  int cont =0;
                       for (int j = 0; j < frame.cols(); j++) {
                    for(int h = 0; h < frame.rows(); h++){
                         Color col = new Color(buf.getRGB(j, h));
          //              System.out.println(""+col.getGreen());
                        observacionRGB[0] = col.getGreen();
                        observacionRGB[1] = col.getRed();
                        Estadisticas1[cont].actualizarObservacion(observacionRGB);
                        if(Estadisticas1[cont].getpPlano()){
                           buf2.setRGB(j, h, 55-255-255);
                        }else{
                           buf2.setRGB(j, h, 0-0-0);
                        }
                        cont++;
            
                    }
                }
                ImageIcon img2 = new ImageIcon(buf2);
                vidpanel2.setIcon(img2);
                vidpanel2.repaint();
              }
          }, 0, 2, TimeUnit.MILLISECONDS);
         
          
        
////            
              
                
               
            vidpanel.repaint();
            
     
          
//               long endTime = System.currentTimeMillis();
//                refTime += (endTime - startTime);
                
        }
        
    }
       
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
