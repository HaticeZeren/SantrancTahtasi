package mulakat;

//Örnek program. Thread oluþturma.
//Yazan: Mustafa Hadi Dilek
import java.awt.*;
import java.awt.event.*;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
class thread extends Frame
{
	thread yeni_tahta;
	
	//***************
	int x=60;
	int y=500;
	int dx=(500/8);
	int dy=(500/8);
	int r=30;
	private Image i;
	private Graphics graph;
	public  static int say;
/*************/
	int baslangic=7*8+0;
	int hedef=0*8+7;
	private static boolean sayac=true;
	private int[][] santranc_tah;
	private double[][] Q_mat;
	public static boolean yaz;
	public static Vector<Integer> yol;
	public static Vector<Integer> siyah_pul;
	private int[] sayilar={8,7,6,5,4,3,2,1};
	private char[] harfler={'A','B','C','D','E','F','G','H'};
 	private static  final int birim=(500/8);
 	public int j;
 	public static TextArea metin;
public thread(mulakat nesne) {
	santranc_tah=nesne.santranc_tahta;
	Q_mat=nesne.Q_matrisi;
	say=0;
	yol=new Vector<Integer>();
	siyah_pul=new Vector<Integer>();
 addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent e) {
     dispose();
     System.exit(0);
   }
 });
 
 
 setLayout(null);
 Button myObj1=new Button("BAÞLA");
 Button myObj2=new Button("BÝTÝR");
 Button myObj3=new Button("YENÝ OYUN");

 metin=new TextArea("",55,200);
 metin.setText("OYUN BÝTÝMÝ:");

 add(myObj3);
 add(myObj1);
 add(myObj2);
 add(metin);

 metin.setVisible(true);
 metin.setLocation(630,150);
 metin.setSize(250,400);
 myObj1.setSize(250,20);
 myObj2.setSize(250,20);
 myObj1.setLocation(630,50);
 myObj2.setLocation(630,70);
 myObj3.setSize(250,20);
 myObj3.setLocation(630,90);
 
 
 myObj1.addActionListener(new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {
			 
			  Thread myThread1=new myThread();
			   myThread1.start();
			   metin.setText("OYUN BÝTÝMÝ:");
               siyah_pul.clear();
               yol.clear();
			     
		}
	});
 
 myObj2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		   baslangic=hedef;
		  /**
		   * kýrmýzý pulun(beyaz pul) oyun bitirildiðinde izlediði yolu ekrana basar.
		   */
		  metin.setText("OYUN BÝTÝMÝ:");
		 // yolu_yaz(yol,"kýrmýzý pulun yolu");
		 // yolu_yaz(siyah_pul,"karsýlasýlan siyah pul");
		  
	}
});
 
 myObj3.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		  
		
		  dispose();//önceki pencereyi iptal eder.
		  thread yeni_tahta=new thread(new mulakat());
		  yeni_tahta.setSize(900,600);
		  yeni_tahta.setTitle("OYUN");
		  yeni_tahta.setBackground(Color.blue);
		  yeni_tahta.setResizable(false);
		  yeni_tahta.setVisible(true);
		
	}
});
}

public void yolu_yaz(Vector<Integer> vector,String isim){

	ListIterator<Integer> iter = vector.listIterator();
	metin.append("\n"+isim+"\n");
	if(isim.equals("kýrmýzý pulun yolu")) metin.append("A1->");
	for(j=0;j<vector.size();j++){
		//System.out.println();
		metin.append(Character.toString(harfler[vector.get(j+1)]));
		metin.append(Integer.toString(8-vector.get(j)));
		j++;
		if(j!=vector.size()-1) metin.append("->");
	}
	
	//metin.disable();
	
}

public boolean pul_var_mi(int i,int j){
	if(santranc_tah[i][j]==-1) return true;
	else return false;
}

public void siyah_pul_bul(int x,int y){
	int konum_x=x+1;//i+1
	int konum_y=y+1;//j+1 
	//System.out.println("karsilasýlan siyah pullar:");
	for(int sayac1=0;sayac1<3;sayac1++){
		for(int sayac2=0;sayac2<3;sayac2++){
			if(konum_x>=0&&konum_x<8&&konum_y>=0&&konum_y<8){
				   if(pul_var_mi(konum_x, konum_y)){
					   siyah_pul.add(konum_x);
					   siyah_pul.add(konum_y);
				   }				
			}
			konum_y--;
		}
		konum_x--;
		konum_y=y+1;//konum_y ilk deðerine döndürüldü.
	}
}


	public void paint(Graphics g) {
		
		this.setBackground(Color.darkGray);
        //Dik dörtgen
        g.setColor(Color.black);
        g.fillRect(50,50,500,500);
        g.drawRect(50,50,500,500);
        
        g.setColor(Color.lightGray);
        g.fillRect(600,0,300,600);
        g.drawRect(600,0,300,600);
        
       
        
        
        
        g.setColor(Color.white);
        for(int i=7;i>=0;i--){
       	 String b=Integer.toString(sayilar[i]);
       	 g.drawString(b,30,i*birim+90);
       	
        }
        
        for(int i=0;i<8;i++){
       	 String b=Character.toString(harfler[i]);
       	 g.drawString(b,i*birim+80,560);
        }
        
       
       for(int j=0;j<8;j+=2){
        for(int i=0;i<8;i+=2){
            g.fill3DRect(i*birim+50,j*birim+50,birim,birim,true);
            g.fill3DRect(i*birim+birim+50,j*birim+birim+50,birim,birim,true);
        }
       }
       
       
       //*****************************************
       /**
        * R[i][j]==-1 olduðunda santranç tahtasýna siyah pulu(gri) yerleþtirir.
        * */
          g.setColor(Color.gray);
          for(int i=0;i<8;i++){
    	   for(int j=0;j<8;j++){
    		   if(santranc_tah[i][j]==-1){
    			   g.fillOval(j*birim+birim,i*birim+birim,40,40);
    		   }
    	   }
       }
      
       //***********************************
       g.setColor(Color.red);
	   g.fillOval(x, y,r,r);
		
		
	}
	
	public void update(Graphics g) {
		
		
		/**
		 * Ekrandaki titremeyi engellemek  için.
		 */
		if(i==null){
			i=createImage(this.getSize().width,this.getSize().height);
			graph=i.getGraphics();
		}
		
		graph.setColor(getBackground());
		graph.fillRect(0,0,this.getSize().width,this.getSize().height); 
		graph.setColor(getForeground());
		paint(graph);
		g.drawImage(i,0,0,this);
	}

public class myThread extends Thread {
 
 public void run() {
  		double max=0;
		int hamle1=0;
		 //System.out.print("("+(baslangic/8)+","+(baslangic%8)+")");
		 x=(baslangic%8)*(500/8)+60;
		 y=(baslangic/8)*(500/8)+60;
		
		 siyah_pul_bul(baslangic/8,baslangic%8);
		 
		 yol.add(baslangic/8);
		 yol.add(baslangic%8);
		 repaint();
		 try {
			Thread.sleep(1000);//beyaz pulun(kýrmýzý) baþlangýç noktasýndaki konumunu net görebilmek için.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(baslangic!=hedef){
			int durum=baslangic;
			for(int j=0;j<64;j++){
				
				if(max<Q_mat[durum][j]&&durum!=j){//dugumun kendine donusu olmasýn
				  max=Q_mat[durum][j];
				  hamle1=j;
				  
				}
			}
			
			if(max>0){
				//durum-hamle düðümü arasinda yol çiz
				
				// System.out.print("("+(hamle1/8)+","+(hamle1%8)+")");
				 x=(hamle1%8)*(500/8)+60;
				 y=(hamle1/8)*(500/8)+60;
				 yol.add(hamle1/8);
				 yol.add(hamle1%8);
				 siyah_pul_bul(hamle1/8, hamle1%8);

			}
			baslangic=hamle1;
			repaint();
			try{
				Thread.sleep(1000);
			} catch (InterruptedException ex){
				ex.printStackTrace();

			}
		}
		
		yolu_yaz(yol,"kýrmýzý pulun yolu");//oyun bitiminde kýrmýzý pulun izlediði yolu yazdýrýr.
		yolu_yaz(siyah_pul,"karþýlaþýlan siyah pullar");
		baslangic=7*8+0;
		hedef=0*8+7;
		
}
}


}